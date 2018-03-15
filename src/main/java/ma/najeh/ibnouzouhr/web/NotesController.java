package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.NoteCreation;
import ma.najeh.ibnouzouhr.model.Module;
import ma.najeh.ibnouzouhr.model.Note;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.repository.NoteRepository;
import ma.najeh.ibnouzouhr.service.InscriptionService;
import ma.najeh.ibnouzouhr.service.ModuleService;
import ma.najeh.ibnouzouhr.service.NotificationService;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.util.FileUtil;
import ma.najeh.ibnouzouhr.util.ReadNoteFileThread;
import ma.najeh.ibnouzouhr.util.XLSUtil;
import ma.najeh.ibnouzouhr.validator.NotesValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static ma.najeh.ibnouzouhr.constant.Constant.ABSOLUTE_DIR;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef on 12/15/17.
 */
@Controller
public class NotesController {

    private final InscriptionService inscriptionService;
    private final ModuleService moduleService;
    private final StudentService studentService;
    private final NotificationService notificationService;
    private final NoteRepository noteRepository;
    private  final NotesValidator notesValidator;
    public static Vector<String> files=new Vector<>();
    private Logger logger=Logger.getLogger(NotesController.class);
    @Autowired
    public NotesController(InscriptionService inscriptionService, ModuleService moduleService, NoteRepository noteRepository,
                           StudentService studentService, NotesValidator notesValidator, NotificationService notificationService) {
        this.inscriptionService = inscriptionService;
        this.moduleService = moduleService;
        this.noteRepository = noteRepository;
        this.studentService = studentService;
        this.notesValidator=notesValidator;
        this.notificationService = notificationService;
    }

    @GetMapping(value = {"/admin/note/{id}/add"})
    public String create(Model model, @PathVariable Long id) {
        Student student=studentService.findOne(id);
        model.addAttribute("noteCreation",new NoteCreation());
        model.addAttribute("id",id);
        model.addAttribute("modules",moduleService.findAllByBranchId(student.getBranch().getId()));
        return "/notes/add";
    }
    @GetMapping(value = {"/admin/notes/add"})
    public String create(Model model) {
        model.addAttribute("noteCreation",new NoteCreation());
        return "/notes/add";
    }
    @PostMapping("/admin/note/{id}/add")
    public String add(NoteCreation noteCreation, Model model, @PathVariable int id) {

        Note note = new Note();
        note.setNote(noteCreation.getNote())
                .setYear(noteCreation.getYear())
                .setModule(moduleService.findOne(noteCreation.getModuleId()))
                .setStudent(studentService.findOne(noteCreation.getStudentId()));



        if(note.getNote()>=10){
            note.setSession("V");
        }else if(note.getNote()==0.25){
            note.setSession("NBI");
        }else{
            note.setSession("NV");
        }
        noteRepository.save(note);
        model.addAttribute("id",noteCreation.getStudentId());
        return "redirect:/admin/student/"+noteCreation.getStudentId()+"/notes";
    }

    @GetMapping(value = {"/admin/note/edit/{id}"})
    public String edit(Model model, @PathVariable Long id) {
        Note n = noteRepository.findOne(id);
        if (n!= null && n.getStudent() !=null && n.getStudent().getBranch() !=null){
            model.addAttribute("modules",moduleService.findAllByBranchId(n.getStudent().getBranch().getId()));

        }else{
            model.addAttribute("modules",moduleService.findAll());
        }
        model.addAttribute("note",noteRepository.findOne(id));
        return "/notes/edit";
    }
    @PostMapping("/admin/note/update")
    public String update(Note note) {

        Note n = noteRepository.findOne(note.getId());
        n.setNote(note.getNote())
                .setYear(note.getYear())
                .setModule(moduleService.findOne(note.getModule().getId()));
        if(n.getNote()>=10){
            n.setSession("V");
        }else if(n.getNote()==0.25){
            n.setSession("NBI");
        }else{
            n.setSession("NV");
        }

        n=noteRepository.save(note);
        return "redirect:/admin/student/"+n.getStudent().getId()+"/notes";
    }
    @PostMapping(ADMIN_NOTES_ADD_XLS)
    public String save(@RequestParam(name = "notes_xls_file") MultipartFile file, @RequestParam Integer year, @RequestParam String module) throws IOException {

        if (!file.isEmpty()) {
            File f = FileUtil.multipartToFile(file);
            Module module1=moduleService.getByCode(module);
            List<Note> notes = XLSUtil.getNotes(f,year,module1,studentService);
            List<Student> students=new ArrayList<>();
            StringBuilder message=new StringBuilder()
                    .append("les notes du module ")
                    .append(module1.getName())
                    .append(" sont publié");
            for (Note n :notes) {
                noteRepository.saveAndFlush(n);
                inscriptionService.nextModule(n);
                students.add(n.getStudent());
            }

            Notification notification=new Notification(message,STUDENT_MY_NOTES,students);
            notificationService.save(notification);
            f.delete();
        }

        return "redirect:" + ADMIN_NOTES_ADD;

    }
    @PostMapping(ADMIN_NOTES_ADD_XLS_ZIP)
    public String saveFromZip(@RequestParam(name = "note_zip") MultipartFile multipartFile, @RequestParam Integer year) throws IOException {


        byte[] buffer = new byte[1024];
        final String OUTPUT_FOLDER=ABSOLUTE_DIR+"notes";

        try{
            //create output directory is not exists
            File folder = new File(OUTPUT_FOLDER);

            if(!folder.exists()){
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(FileUtil.multipartToFile(multipartFile)));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File(OUTPUT_FOLDER + File.separator + fileName);

                files.add(newFile.getAbsolutePath());
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();


        }catch(IOException ex){
            logger.error("zip file note error"+ex.getMessage());
        }


        ReadNoteFileThread fileThread1=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread2=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread3=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread4=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread5=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread6=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread7=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);
        ReadNoteFileThread fileThread8=new ReadNoteFileThread(year,moduleService,studentService,noteRepository,inscriptionService,notificationService);

        fileThread1.start();
        fileThread2.start();
        fileThread3.start();
        fileThread4.start();
        fileThread5.start();
        fileThread6.start();
        fileThread7.start();
        fileThread8.start();


        return "redirect:" + ADMIN_NOTES_ADD;

    }




    @GetMapping("/admin/note/delete/{id}")
    public String save(@PathVariable Long id) {
        Note note=noteRepository.findOne(id);
        noteRepository.delete(note);
        if (note!=null && note.getStudent()!=null){
            return "redirect:/admin/student/"+note.getStudent().getId()+"/notes";
        }
        return "redirect:" + ADMIN_NOTES_ADD;

    }
}
