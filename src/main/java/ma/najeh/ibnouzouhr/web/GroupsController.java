package ma.najeh.ibnouzouhr.web;


import ma.najeh.ibnouzouhr.dto.GroupCreation;
import ma.najeh.ibnouzouhr.model.Group;
import ma.najeh.ibnouzouhr.model.Module;
import ma.najeh.ibnouzouhr.model.Salle;
import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.repository.GroupRepository;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.repository.StudentRepository;
import ma.najeh.ibnouzouhr.service.ModuleService;
import ma.najeh.ibnouzouhr.service.StudentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static ma.najeh.ibnouzouhr.constant.Constant.ATTACHMENT_FILENAME;
import static ma.najeh.ibnouzouhr.constant.Constant.Content_Disposition;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class GroupsController implements ServletContextAware {

    private final StudentRepository  studentRepository;
    private final SalleRepository salleRepository;
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final ModuleService moduleService;

    private ServletContext servletContext;
        //report_group_student.jrxml

    private static final String REPORT_PATH = "reports/report_group_student.jrxml";

    @Autowired
    public GroupsController(StudentRepository studentRepository, SalleRepository salleRepository, GroupRepository groupRepository, StudentService studentService, ModuleService moduleService) {
        this.studentRepository = studentRepository;
        this.salleRepository = salleRepository;
        this.groupRepository = groupRepository;
        this.studentService = studentService;
        this.moduleService = moduleService;
    }

    @GetMapping(ADMIN_GROUPS_ADD)
    public String add(Model model) {
        model.addAttribute("salles",salles());
        model.addAttribute("modules",moduleService.findAll());
        return "/groups/add";
    }
    @GetMapping(ADMIN_GROUPS_ALL)
    public String all(Model model) {
        model.addAttribute("groups",groups());
        return "/groups/all";
    }


    @GetMapping(ADMIN_STUDENTS_OF_GROUP)
    public String studentsInGroup(@PathVariable Long id,Model model) {
        model.addAttribute("group",groupRepository.findOne(id));
        model.addAttribute("students",studentRepository.findAllByGroupsId(id));
        return "/groups/students_in_group";
    }
    @GetMapping("/admin/group/{id}/delete")
    public ModelAndView delete(ModelMap model,@PathVariable long id) {
        groupRepository.delete(id);
        return new ModelAndView("redirect:"+ADMIN_GROUPS_ALL, model);
    }
    @PostMapping(ADMIN_GROUPS_ADD)
    public String create(GroupCreation groupCreation) {
        Salle salle = salleRepository.findTopById(Long.parseLong(groupCreation.getSalle()));
        Module module=moduleService.findOne(groupCreation.getModule());
        List<Student> students = studentService.studentByModuleInscription(module.getId());
        long studentCount=students.size();
        long capacity=salle.getCapacity();
        long nbrGroup= studentCount/capacity;
        if (studentCount%capacity!=0){
            nbrGroup++;
        }
        long studentPerGroup=studentCount/nbrGroup;
        for (int i = 1;i<=nbrGroup;i++){
            Group group=new Group();
            group.setModule(module);
            group.setName(groupCreation.getName()+i);


            if (i==nbrGroup){
                List<Student>studentList= students.subList((int)studentPerGroup*(i-1),students.size());
                group.setStudents(studentList);
            }else{
                List<Student>studentList= students.subList((int)studentPerGroup*(i-1),(int)studentPerGroup*i);
                group.setStudents(studentList);
            }


            groupRepository.save(group);
        }

        return "redirect:"+ADMIN_GROUPS_ADD;
    }
    private Set<Salle> salles(){
        Set<Salle> salles = new HashSet<>();
        salleRepository.findAll().forEach(salles::add);
        return salles;
    }

    private List<Group> groups(){
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @GetMapping("/admin/print-group/{gr}/pdf")
    public void downloadsListPDFReporting( HttpServletResponse response ,@PathVariable("gr") String gr) throws IOException, JRException {
        Group group=groupRepository.findByName(gr);
        Map<String, Object> params = new HashMap<String, Object>();
        File logo = new ClassPathResource("static/imgs/LOGO_ibn.jpg").getFile();
        File bgImage = new ClassPathResource("static/imgs/bg_ibn_zhr.jpg").getFile();
        params.put("dateOperation", new SimpleDateFormat("mm-dd-yyyy").format(new java.util.Date()));
        params.put("name_module", group.getModule().getName() +" "+group.getModule().getCode());
        params.put("name_group", group.getName());
        params.put("logo", logo.getAbsolutePath());
        params.put("bg_image", bgImage.getAbsolutePath());
        params.put("number_students", group.getStudents().size());
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(group.getStudents());
        File rootDir = new ClassPathResource(REPORT_PATH).getFile();
        InputStream in = new FileInputStream(rootDir);
        JasperReport report = JasperCompileManager.compileReport(in);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, beanColDataSource);
        OutputStream outStream = response.getOutputStream();

        String disHeader = ATTACHMENT_FILENAME + "group_"+group.getModule().getCode()+"_"+group.getName() + ".pdf" + "\"";
        response.setHeader(Content_Disposition, disHeader);

        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        outStream.flush();
        outStream.close();

    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext =servletContext;
    }
}
