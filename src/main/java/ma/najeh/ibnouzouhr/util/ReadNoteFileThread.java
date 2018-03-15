package ma.najeh.ibnouzouhr.util;

import ma.najeh.ibnouzouhr.model.Module;
import ma.najeh.ibnouzouhr.model.Note;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.repository.NoteRepository;
import ma.najeh.ibnouzouhr.service.InscriptionService;
import ma.najeh.ibnouzouhr.service.ModuleService;
import ma.najeh.ibnouzouhr.service.NotificationService;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.web.NotesController;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_MY_NOTES;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_TIMELINE;

/**
 * Created by youssef on 1/25/18.
 */
public class ReadNoteFileThread extends Thread {
    private Integer year;
    private ModuleService moduleService;
    private StudentService studentService;
    private NoteRepository noteRepository;
    private InscriptionService inscriptionService;
    private NotificationService notificationService;
    private Logger logger= Logger.getLogger(ReadNoteFileThread.class);

    public ReadNoteFileThread(Integer year, ModuleService moduleService, StudentService studentService, NoteRepository noteRepository, InscriptionService inscriptionService, NotificationService notificationService) {
        this.year = year;
        this.moduleService = moduleService;
        this.studentService = studentService;
        this.noteRepository = noteRepository;
        this.inscriptionService = inscriptionService;
        this.notificationService = notificationService;
    }

    @Override
    public void run() {
        process();
    }


    private void process(){
        if (NotesController.files!=null && !NotesController.files.isEmpty()){
            List<Student> students=new ArrayList<>();

            String path=NotesController.files.firstElement();
            logger.info("There is exactly "+NotesController.files.size()+" on the queue, current worker is "+Thread.currentThread().getName());
            NotesController.files.remove(path);
            File file=new File(path);
            String[] parts=file.getName().split("\\.");
            if (parts.length>0){
                String moduleCode=parts[0];
                Module module=moduleService.getByCode(moduleCode);
                StringBuilder message=new StringBuilder()
                                .append("les notes du module ")
                                .append(module.getName())
                                .append(" sont publié");

                List<Note> notes = XLSUtil.getNotes(file,year,module,studentService);
                for (Note n :notes) {
                    students.add(n.getStudent());
                    n=noteRepository.saveAndFlush(n);
                    inscriptionService.nextModule(n);
                }


                createNotifications(message,students);
            }
            file.delete();
            if (!NotesController.files.isEmpty()){
                process();
            }
        }

    }

    private void createNotifications(StringBuilder message, List<Student> students) {
        Notification notification=new Notification(message,STUDENT_MY_NOTES,students);
        notificationService.save(notification);
    }
}
