package ma.najeh.ibnouzouhr.web;


import ma.najeh.ibnouzouhr.dto.*;
import ma.najeh.ibnouzouhr.dto.ModuleDto;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.BranchRepository;
import ma.najeh.ibnouzouhr.repository.ReclamationRepository;
import ma.najeh.ibnouzouhr.repository.StudentRepository;
import ma.najeh.ibnouzouhr.service.InscriptionService;
import ma.najeh.ibnouzouhr.service.NotesService;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.util.FileUtil;
import ma.najeh.ibnouzouhr.util.XLSUtil;
import ma.najeh.ibnouzouhr.validator.StudentValidator;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static ma.najeh.ibnouzouhr.constant.Constant.ABSOLUTE_DIR;
import static ma.najeh.ibnouzouhr.constant.Constant.ATTACHMENT_FILENAME;
import static ma.najeh.ibnouzouhr.constant.Constant.Content_Disposition;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class StudentsController implements ServletContextAware {
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final InscriptionService inscriptionService;
    private final BranchRepository branchRepository;
    private final ReclamationRepository reclamationRepository;
    private final NotesService notesService;
    private final StudentValidator studentValidator;
    private final   PasswordEncoder passwordEncoder;
    private List<Student> students =new ArrayList<>();
    private Logger logger=Logger.getLogger(StudentsController.class);

    private ServletContext servletContext;

    @Autowired
    public StudentsController(InscriptionService inscriptionService, StudentRepository studentRepository, StudentService studentService, NotesService notesService, StudentValidator studentValidator, BranchRepository branchRepository, PasswordEncoder passwordEncoder, ReclamationRepository reclamationRepository) {
        this.inscriptionService = inscriptionService;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.notesService = notesService;
        this.studentValidator=studentValidator;
        this.branchRepository=branchRepository;
        this.passwordEncoder = passwordEncoder;
        this.reclamationRepository = reclamationRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @GetMapping(value = {ADMIN_STUDENTS_BY_BRANCH})
    public String allByBranch(Map<String, Object> model,@PathVariable Long branchId) {
        Iterable<Student> students = studentRepository.findAllByBranchId(branchId);
        model.put("students", students);
        return "/students/all";
    }
    @GetMapping(value = {ADMIN_STUDENTS,ADMIN_STUDENTS_ALL})
    public String all() {
        return "redirect:/admin/students/branch/1";
    }

    @GetMapping(ADMIN_STUDENTS_ADD)
    public String add(Model model) {
        model.addAttribute("student",new Student());
        model.addAttribute("branches",branchRepository.findAll());
        return "/students/add";
    }


    @GetMapping(value = {"/admin/student/{id}/notes","/scolarity/student/{id}/notes","/administration/student/{id}/notes"})
    public String noteOfStudent(@PathVariable long id,Model  model) {
        model.addAttribute("id",id);
        model.addAttribute("student",studentService.findOne(id));
        Map<String,List<Note>> notesOfStudentBySemesters = notesService.getNotesByStudentDividedOverAllSemesters(id);
        model.addAttribute("notes",notesOfStudentBySemesters);
        return "/students/students_notes";
    }
    @GetMapping(STUDENT_MY_NOTES)
    public String myNotes(Model  model) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("id",userDetails.getId());
        model.addAttribute("student",studentService.findOne(userDetails.getId()));
        Map<String,List<Note>> notesOfStudentBySemesters = notesService.getNotesByStudentDividedOverAllSemesters(userDetails.getId());
        model.addAttribute("notes",notesOfStudentBySemesters);
        return "/students/students_notes";
    }

    @PostMapping(ADMIN_STUDENTS_ADD)
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult, Model model) {
        studentValidator.validate(student,bindingResult);
        model.addAttribute("student",student);
        if (bindingResult.hasErrors()){
            return "/students/add";
        }
        model.addAttribute("student",studentService.save(student));
        return "/students/add";
    }

    @GetMapping(ADMIN_STUDENTS_DELETE)
    public ModelAndView deleteStudents(ModelMap model, @PathVariable(value = "id") Long id) {
        studentRepository.delete(id);
        return new ModelAndView("redirect:" + ADMIN_STUDENTS, model);
    }
    @Deprecated
    @PostMapping(ADMIN_STUDENTS_ADD_XLS)
    public String save(@RequestParam(name = "students_xls_file") MultipartFile file, Branch branch,Integer  annee) throws IOException {

        if (!file.isEmpty()) {
            File f = FileUtil.multipartToFile(file);
            Set<Student> students = XLSUtil.getStudent(f);
            studentService.saveAll(students, branch.getName());
            switch (annee){
                case 1:
                    inscriptionService.createInscriptions(students, branch.getName(), "S1", "S2");
                    break;
                case 2:
                    inscriptionService.createInscriptions(students, branch.getName(), "S3", "S4");
                    break;

                case 3:
                    inscriptionService.createInscriptions(students, branch.getName(), "S5", "S6");
                    break;
            }

            f.delete();
        }

        return "redirect:" + ADMIN_STUDENTS;

    }


    @PostMapping(ADMIN_STUDENTS_ADD_XLS_GLOBAL)
    public String saveGlobal(@RequestParam(name = "students_xls_file_global") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            File f = FileUtil.multipartToFile(file);
            List<Branch> branches= (List<Branch>) branchRepository.findAll();
            Set<Student> students = XLSUtil.getGlobalStudent(f,branches,passwordEncoder);
            studentService.saveAll(students);


            f.delete();
        }

        return "redirect:" + ADMIN_BRANCHES;

    }

    @GetMapping(value = {ADMIN_STUDENT_PROFILE,SCOLARITY_STUDENT_PROFILE,"/administration/students/profile/{id}"})
    public String profile(Map<String, Object> model, @Null @PathVariable(value = "id") Long id) {
        Student student = studentRepository.findOne(id);
        model.put("student", student);
        return "/students/profile";
    }

    @GetMapping(value = {STUDENT_MY_PROFILE})
    public String myProfile(Map<String, Object> model) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentRepository.findOne(userDetails.getId());
        model.put("student", student);
        return "/students/profile";
    }


    @GetMapping(value = {"/student/edit-profile"})
    public String editProfile(Map<String, Object> model) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentRepository.findOne(userDetails.getId());
        model.put("student", student);
        return "/students/edit_profile";
    }

    @PostMapping(value = {"/student/edit-profile"})
    public String updateProfile(Student student) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student s = studentRepository.findOne(userDetails.getId());
        s.setFirstNameAr(student.getFirstNameAr());
        s.setLastNameAr(student.getLastNameAr());
        s.setFirstName(student.getFirstName());
        s.setLastName(student.getLastName());
        s.setAdress(student.getAdress());
        s.setHometown(student.getHometown());
        s.setHometownAr(student.getHometownAr());
        studentRepository.save(s);
        return "redirect:"+STUDENT_MY_PROFILE;
    }

    @GetMapping(ADMIN_STUDENTS_EDIT)
    public String edit(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Student student = studentRepository.findOne(id);
        model.put("student", student);
        return "/students/edit";
    }

    @PostMapping(ADMIN_STUDENTS_UPDATE)
    public ModelAndView update(ModelMap model, Student student) {
        studentRepository.save(student);
        return new ModelAndView("redirect:" + ADMIN_STUDENTS, model);
    }

    @GetMapping("/administration/students/search")
    public String search(Model model){
        return "/students/search";
    }
    @PostMapping("/administration/students/search")
    public String find(Search search,Model model){
        Student student= new Student();
        student.setFirstName(search.getFirstName());
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        setStudents(students);
        model.addAttribute("students",studentService.searchStudents(search));
        return "/students/search";
    }

    @GetMapping("/administration/attestation/{id}/pdf")
    public void downloadsAttestationPDFReporting(HttpServletResponse response , @PathVariable("id") Long id) throws IOException, JRException {
        Student student=studentService.findOne(id);
        Map<String, Object> params = new HashMap<>();
        File logo = new ClassPathResource("static/imgs/LOGO_ibn.jpg").getFile();
        params.put("full_name", student.getFullName().toUpperCase());
        params.put("cin", student.getCin().toUpperCase());
        params.put("cne", student.getCne().toUpperCase());
        params.put("branch", student.getBranch().getName().toUpperCase());
        params.put("current_date", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new java.util.Date()));
        params.put("logo", logo.getAbsolutePath());

        File rootDir = new ClassPathResource("reports/attestation.jrxml").getFile();

        InputStream in = new FileInputStream(rootDir);
        JRDataSource dataSource = new JREmptyDataSource();
        JasperReport report = JasperCompileManager.compileReport(in);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report,params,dataSource);

        OutputStream outStream = response.getOutputStream();

        String disHeader = ATTACHMENT_FILENAME  + "attestation-"+student.getLastName()+".pdf" + "\"";
        response.setHeader(Content_Disposition, disHeader);

        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        outStream.flush();
        outStream.close();

    }


    @GetMapping("/administration/success-attestation/{id}/pdf")
    public void downloadsAttestationSuccessPDFReporting(HttpServletResponse response , @PathVariable("id") Long id) throws IOException, JRException {
        Student student=studentService.findOne(id);
        Map<String, Object> params = new HashMap<>();
        File logo = new ClassPathResource("static/imgs/LOGO_ibn.jpg").getFile();
        params.put("full_name", student.getFullName().toUpperCase());
        params.put("cin", student.getCin().toUpperCase());
        params.put("cne", student.getCne().toUpperCase());
        params.put("branch", student.getBranch().getName().toUpperCase());
        params.put("current_date", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new java.util.Date()));
        params.put("logo", logo.getAbsolutePath());
        StringBuilder sb=new StringBuilder();
        Map<String,String> semesterValidate=studentService.semestresValidatebyStudent(student);
        Set<Map.Entry<String, String>> semesEntry = semesterValidate.entrySet();
        Iterator<Map.Entry<String, String>> semstValidateIt = semesEntry.iterator();
        int i=0;
        while (semstValidateIt.hasNext()){
            Map.Entry<String, String> e = semstValidateIt.next();
            if (e.getValue().equals("V") || e.getValue().equals("VC")){
                if (i>0){
                    sb.append(", ");
                }
                sb.append(e.getKey());
                i++;
            }
        }
        params.put("validate_s", sb.toString());

        File rootDir = new ClassPathResource("reports/attestation_reussite.jrxml").getFile();

        InputStream in = new FileInputStream(rootDir);
        JRDataSource dataSource = new JREmptyDataSource();
        JasperReport report = JasperCompileManager.compileReport(in);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report,params,dataSource);

        OutputStream outStream = response.getOutputStream();

        String disHeader = ATTACHMENT_FILENAME  + "attestation-reussite-"+student.getLastName()+".pdf" + "\"";
        response.setHeader(Content_Disposition, disHeader);

        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        outStream.flush();
        outStream.close();

    }

    @GetMapping("/student/print-convocation")
    public void downloadsListPDFReporting( HttpServletResponse response ) throws  JRException {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student=studentService.findOne(userDetails.getId());
        generateConvocation(response,student);

    }
    @GetMapping("/administration/print-convocation/{id}")
    public void downloadsListPDFReportingForAStudent( HttpServletResponse response ,@PathVariable("id") Long id) throws  JRException {
        Student student=studentService.findOne(id);
        generateConvocation(response,student);
    }

    private void generateConvocation( HttpServletResponse response,Student student) throws JRException {
        if (student!=null){
            ByteArrayOutputStream bout =
                    QRCode.from(student.getCodeAPOGEE())
                            .withSize(250, 250)
                            .to(ImageType.PNG)
                            .stream();
            String qrCode=ABSOLUTE_DIR+"qrs\\";
            File fileSaveDir=new File(qrCode);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            qrCode+="qr-code.png";
            try {

                OutputStream out = new FileOutputStream(qrCode);
                bout.writeTo(out);
                out.flush();
                out.close();
                Map<String, Object> params = new HashMap<>();
                params.put("full_name", student.getFullName());
                params.put("qr_code",qrCode);
                params.put("cin", student.getCin());
                params.put("branch", student.getBranch().getName());
                params.put("code_apogee", student.getCodeAPOGEE());
                TreeSet<ModuleDto> modules=new TreeSet<>();
                for (Inscription inscription:student.getInscriptions()){
                    Module module=inscription.getModule();
                    modules.add(new ModuleDto(module.getCode(),module.getName(),module.getSemestre().getName()));
                }
                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(modules);
                File rootDir = new ClassPathResource("reports/convocation_qr.jrxml").getFile();

                InputStream in = new FileInputStream(rootDir);
                JasperReport report = JasperCompileManager.compileReport(in);
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, beanColDataSource);
                OutputStream outStream = response.getOutputStream();

                String disHeader = ATTACHMENT_FILENAME + "convocation_qr_"+student.getFullName() + ".pdf" + "\"";
                response.setHeader(Content_Disposition, disHeader);

                JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
                outStream.flush();
                outStream.close();

            } catch (FileNotFoundException e){
                logger.error("FILE NOT FOUND "+e.getMessage());
            } catch (IOException e) {
                logger.error("IOException "+e.getMessage());
            }
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext =servletContext;
    }

    public List<Student> getStudents() {
        return students;
    }

    public StudentsController setStudents(List<Student> students) {
        this.students = students;
        return this;
    }
}
