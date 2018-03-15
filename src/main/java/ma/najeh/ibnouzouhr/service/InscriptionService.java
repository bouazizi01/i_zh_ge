package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.InscriptionRepository;
import ma.najeh.ibnouzouhr.repository.ModuleRepository;
import ma.najeh.ibnouzouhr.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by youssef on 12/15/17.
 */
@Service("InscriptionService")
public class InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final ModuleService moduleService;

    @Autowired
    public InscriptionService(InscriptionRepository inscriptionRepository, ModuleService moduleService) {
        this.inscriptionRepository = inscriptionRepository;
        this.moduleService = moduleService;
    }

    public void createInscriptions(Set<Student> students, String branchName ,String ... semestreNames){

        List<Module> modules=moduleService.findBySemestres(branchName,semestreNames);
        for (Student student:students){
            for (Module module:modules ){
                Inscription inscription=new Inscription(student,module);
                inscriptionRepository.save(inscription);
            }
        }

    }

    public void nextModule(Note newNote) {
        for (String code:newNote.getModule().getPreviousModulesAsList()){
            Module m=moduleService.findByCode(code);
            if (m!=null){
                inscriptionRepository.deleteByStudentIdAndModuleId(newNote.getStudent().getId(),m.getId());
            }
        }
        if (newNote.getNote()>= 10 ){
            List<String> nexts=newNote.getModule().getNextModulesAsList();
            // pass to next module
            //
            if (nexts!=null){
                for (String code:nexts) {
                    Module newModule=moduleService.findByCode(code);
                    if (newModule!=null && newNote.getModule().getSemestre().getId().equals(newModule.getSemestre().getId()-2)){
                        inscriptionRepository.save(new Inscription(newNote.getStudent(),newModule));
                    }
                }
            }

            inscriptionRepository.deleteByStudentIdAndModuleId(newNote.getStudent().getId(),newNote.getModule().getId());

        }else{
            Module newModule=moduleService.findByCode(newNote.getModule().getCode());
            if (newModule!=null){
                inscriptionRepository.save(new Inscription(newNote.getStudent(),newModule));
            }

        }
    }

    public Iterable<Inscription> findAllByModuleId(Long id) {
        return inscriptionRepository.findAllByModuleId(id);
    }
}
