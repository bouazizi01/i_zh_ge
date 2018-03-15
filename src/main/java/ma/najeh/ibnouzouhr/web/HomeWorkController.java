package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.HomeWorkDto;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.HomeWork;
import ma.najeh.ibnouzouhr.model.Planing;
import ma.najeh.ibnouzouhr.repository.PlaningRepository;
import ma.najeh.ibnouzouhr.service.HomeWorkService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static ma.najeh.ibnouzouhr.constant.Constant.ABSOLUTE_DIR;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.TEACHER;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

@Controller
public class HomeWorkController implements ServletContextAware{
    private Logger log = Logger.getLogger(HomeWorkController.class);
    private final HomeWorkService homeWorkService;
    private final PlaningRepository planingRepository;

    private ServletContext servletContext;

    @Autowired
    public HomeWorkController(HomeWorkService homeWorkService, PlaningRepository planingRepository) {
        this.homeWorkService = homeWorkService;
        this.planingRepository = planingRepository;
    }



    @GetMapping(value = {TEACHER_HOMEWORK_ALL,TEACHER_HOMEWORK,STUDENT_HOMEWORK_ALL,STUDENT_HOMEWORK})
    public String all(Model model) {
        UserDetailsDto user = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (user.getRole()) {
            case TEACHER:
                model.addAttribute("homeworks", homeWorkService.findAllBySeanceTeacherId(user.getId()));
                break;
            case STUDENT:
                model.addAttribute("homeworks", homeWorkService.findAllBySeanceGroupStudentsId(user.getId()));
                break;
            default:
                return "redirect:"+DASHBOARD;
        }

        return "/devoirs/all";
    }

    @GetMapping(TEACHER_HOMEWORK_ADD)
    public String addHomeWork(Model model,@PathVariable("id") Long id) {
        UserDetailsDto user = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("plannings",planingRepository.findAllBySeanceTeacherId(user.getId()));
        model.addAttribute("id",id);
        return "/devoirs/add";
    }

    @PostMapping(TEACHER_HOMEWORK_SAVE)
    public String saveHomeWork(HomeWorkDto  homeWorkDto,@RequestParam(value = "file") MultipartFile file) {
        Planing planing=planingRepository.findOne(homeWorkDto.getPlaningId());
        HomeWork homeWork=new HomeWork();
        homeWork.setCreatedAt(new Date());
        homeWork.setDescription(homeWorkDto.getDescription());
        homeWork.setTitle(homeWorkDto.getTitle());
        homeWork.setPathHomeWork(createFile(file));
        homeWork.setPlaning(planing);
        homeWork.setSeance(planing.getSeance());
        homeWorkService.save(homeWork);
        return "redirect:"+TEACHER_TIMELINE;


    }


    @GetMapping(ADMIN_HOMEWORK_DELETE)
    public String delete(@PathVariable(value="id") Long id) {
        homeWorkService.delete(id);
        return  "redirect:"+TEACHER_TIMELINE;

    }



    public String createFile( MultipartFile multipartFile) {
        String appPath = ABSOLUTE_DIR;
        StringBuilder filePath = new StringBuilder().append(appPath)
                .append("devoirs");

        File fileSaveDir = new File(filePath.toString());
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        filePath = filePath.append(File.separator).append(multipartFile.getOriginalFilename());
        log.info(filePath);

        try {
            multipartFile.transferTo(new File(filePath.toString()));
        } catch (Exception e) {
            log.error("Error " + e.getMessage());
        }
        return filePath.toString();

    }

    protected void deleteFile(String typeFolder, long id) {
        String appPath = getServletContext().getRealPath("");
        StringBuilder filePath = new StringBuilder().append(appPath)
                .append("").append(File.separator).append(typeFolder)
                .append(File.separator).append(id).append("");
        File fileSaveDir = new File(filePath.toString());
        if (fileSaveDir.exists()) {
            boolean delete = fileSaveDir.delete();
            log.info("delete file return  " + delete);
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }



    @GetMapping("/files/{homeWorkId}")
    public void getFile(
            @PathVariable("homeWorkId") Long homeWorkId,
            HttpServletResponse response) {
        HomeWork homeWork=homeWorkService.findOne(homeWorkId);
        if (homeWork!=null){
            try {
                File file=new File(homeWork.getPathHomeWork());
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\""+file.getName()+"\"");
                // get your file as InputStream
                InputStream is  = new FileInputStream(file);;
                // copy it to response's OutputStream
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException ex) {
                log.error("File not found");
            }
        }

    }
}
