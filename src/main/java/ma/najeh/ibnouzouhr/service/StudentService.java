package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.Search;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.BranchRepository;
import ma.najeh.ibnouzouhr.repository.SemestreRepository;
import ma.najeh.ibnouzouhr.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by youssef on 12/15/17.
 */
@Service("StudentService")
public class StudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private SemestreRepository semestreRepository;

    public Student findOne(Long id) {
        return studentRepository.findOne(id);
    }

    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    public Student save(Student student) {
        student.setJoinAt(new Date());
        student.setUsername(student.getCodeAPOGEE());
        student.setRole(Constant.ROLE.STUDENT);
        student.setPassword(passwordEncoder.encode(student.getCin().toUpperCase()).toCharArray());
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }


    public void delete(Long id) {
        studentRepository.delete(id);
    }


    public void saveAll(Set<Student> students , String branchName) {
        for (Student student:students){
            student.setBranch(branchRepository.findByName(branchName));
            save(student);
        }
    }

    public Map<String,String> semestresValidatebyStudent(Student student){
        // for each semestre
        List<Semestre> semestres=(List<Semestre>) semestreRepository.findAll();
        Map<String,String> status=new HashMap<>();
        for (Semestre semestre:semestres){
            status.put(semestre.getName(),statusOfSemestreByStudent(student,semestre));
        }
        return status;
    }
    public String statusOfSemestreByStudent(Student student,Semestre semestre){
        List<Module> allModules=(List<Module>)moduleService.findAllBySemestreIdAndBranchId(semestre.getId(),student.getBranch().getId());
        List<Note> notesOfSemestre=new ArrayList<>();
        for (Module module:allModules){
            Note note=notesService.findByModuleIdAndStudentId(module.getId(),student.getId());
            if (note!=null){
                notesOfSemestre.add(note);
            }
        }
        String state="DONT_KNOW";
        if (notesOfSemestre.size()==allModules.size() && !allModules.isEmpty()){

            boolean existPointMort=false;
            int numberOfValided=0;
            double totalPoint=0;
            for (Note note:notesOfSemestre){
                totalPoint=+note.getNote();
                if (note.getNote()>=10){
                    numberOfValided++;
                }else if(note.getNote()<5){
                    existPointMort=true;
                }
            }


            double avgNote=totalPoint/notesOfSemestre.size();
            if (existPointMort || numberOfValided < notesOfSemestre.size()){
                state="NV";
            }else {
                if(numberOfValided == allModules.size() ){
                    state="V";
                }else if(avgNote>=10){
                    state="VC";
                }
            }
        }
        return state;
    }


    public Student findByUsername(String username){
        return studentRepository.findByUsername(username);
    }

    public boolean existsByCne(String cne) {
        return studentRepository.existsByCne(cne);
    }

    public boolean existsByCin(String cin) {
        return studentRepository.existsByCin(cin);
    }

    public Student getByCodeAPOGEE(String code) {
        return studentRepository.findByCodeAPOGEE(code);
    }

    public Iterable<Student> saveAll(Iterable<Student> students) {
        for (Student student :students){
            Set<Inscription> initialInscriptions=new HashSet<>();
            List<Module> modules=moduleService.findBySemestres(student.getBranch().getName(),"S1","S2");
            for (Module module:modules){
                initialInscriptions.add(new Inscription(student,module));
            }
            student.setInscriptions(initialInscriptions);
        }
        return studentRepository.save(students);
    }
    public List<Student> searchStudents(Search search) {
        return studentRepository
                .findAllByCodeAPOGEEOrCneOrCinOrFirstNameOrLastName(
                        search.getCodeApogee(),
                        search.getCne(),
                        search.getCin(),
                        search.getFirstName(),
                        search.getLastName());
    }

    public List<Student> studentByModuleInscription(Long id) {
        return studentRepository.findAllByInscriptions_ModuleId(id);
    }
}
