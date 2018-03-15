package ma.najeh.ibnouzouhr.rest;


import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.SeanceDto;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.model.rest.message.Message;
import ma.najeh.ibnouzouhr.repository.GroupRepository;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import ma.najeh.ibnouzouhr.service.DayService;
import ma.najeh.ibnouzouhr.service.PlaningService;
import ma.najeh.ibnouzouhr.service.SeanceService;
import ma.najeh.ibnouzouhr.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by youssef on 12/16/17.
 */
@RestController
public class SceanceController {

    private final GroupRepository groupRepository;
    private final SalleRepository salleRepository;
    private final TeacherService teacherService;
    private final SpecialtyRepository specialtyRepository;
    private boolean teacherAvailable =false;
    private boolean groupAvailable =false;
    private boolean salleAvailable =false;



    private final SeanceService seanceService;
    private final PlaningService planingService;
    private final DayService dayService;

    @Autowired
    public SceanceController(GroupRepository groupRepository, SalleRepository salleRepository, TeacherService teacherService, SpecialtyRepository specialtyRepository, SeanceService seanceService, PlaningService planingService, DayService dayService) {
        this.groupRepository = groupRepository;
        this.salleRepository = salleRepository;
        this.teacherService = teacherService;
        this.specialtyRepository = specialtyRepository;
        this.seanceService = seanceService;
        this.planingService = planingService;
        this.dayService = dayService;
    }

    @PostMapping("/api/seances")
    public ResponseEntity<?> addPlanning(SeanceDto seanceDto) {
        UserDetailsDto teacher = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean coordinatorForSpeciality=false;
        Group group=groupRepository.findOne(seanceDto.getGroupId());
        for (Specialty s:specialtyRepository.findAll()){
            if (Objects.nonNull(s) &&
                    Objects.nonNull(s.getTeacher()) &&
                    Objects.equals(s.getTeacher().getId(), teacher.getId()) &&
                    Objects.nonNull(group.getModule()) &&
                    Objects.nonNull(group.getModule().getBranch()) &&
                    Objects.nonNull(group.getModule().getBranch().getSpecialty()) &&
                    Objects.equals(s.getId(), group.getModule().getBranch().getSpecialty().getId())
                    ){
                coordinatorForSpeciality=true;
                break;
            }
        }


        Date dateStart = group.getModule().getSemestre().getDateStart();
        Date dateEnd = group.getModule().getSemestre().getDateEnd();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        int def = (int) Math.abs(calendar.get(Calendar.DAY_OF_WEEK) - seanceDto.getDay());
        if (seanceDto.getDay() <= calendar.get(Calendar.DAY_OF_WEEK)) {
            calendar.add(Calendar.DAY_OF_YEAR, 7 - def);
        } else if (seanceDto.getDay() > calendar.get(Calendar.DAY_OF_WEEK)) {
            calendar.add(Calendar.DAY_OF_YEAR, def);
        }
        Hour startTime = new Hour(seanceDto.getStartTime());
        Hour endTime = new Hour(seanceDto.getEndTime());
        if (isGroupAvailable()&& isSalleAvailable() && isTeacherAvailable() && coordinatorForSpeciality ){
            Seance seance = new Seance();
            seance.setScholarYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
                    .setGroup(groupRepository.findOne(seanceDto.getGroupId()))
                    .setSalle(salleRepository.findOne(seanceDto.getSalleId()))
                    .setTeacher(teacherService.findOne(seanceDto.getTeacherId()));
            seanceService.save(seance);

            dateStart = calendar.getTime();
            while (dateStart.before(dateEnd)) {
                Calendar c = Calendar.getInstance();
                c.setTime(dateStart);

                Planing planing = new Planing();
                planing.setSeance(seance)
                        .setPlaningDate(c.getTime())
                        .setStartHour(startTime)
                        .setEndHour(endTime)
                        .setDay(dayService.findOne(seanceDto.getDay()));

                planingService.save(planing);

                c.add(Calendar.DAY_OF_YEAR, 7);
                dateStart = c.getTime();
            }
            return new ResponseEntity<>(new Message("le planing a été bien ajouté", seanceDto, Constant.Status.SUCCESS), HttpStatus.ACCEPTED);

        }else {
            return new ResponseEntity<>(new Message("nous avons renconté un problème lors de l'enregistrement de ce planing \n veuillez réessayer", seanceDto, Constant.Status.ERROR), HttpStatus.ACCEPTED);

        }

    }

    //ila kan endtime (par user) sghar mn endtime dyal planing
    @PostMapping("/api/isgroupav")
    public ResponseEntity<?> isGroupAv(SeanceDto seanceDto) {
        long countOfPlaningsG=planingService.planingsOfGroup(seanceDto);
        if (countOfPlaningsG!=0){
            setGroupAvailable(false);
            return new ResponseEntity<>(new Message("ce groupe n'est pas disponible dans la période ques vous avez choisis ", seanceDto,Constant.Status.ERROR), HttpStatus.ACCEPTED);

        }else {
            setGroupAvailable(true);
            return new ResponseEntity<>(new Message("ce group est disponible", seanceDto,Constant.Status.SUCCESS), HttpStatus.ACCEPTED);

        }
    }
    @PostMapping("/api/issalleav")
    public ResponseEntity<?> isSalleAv(SeanceDto seanceDto) {
        long countOfPlaningsS=planingService.planingsOfSalle(seanceDto);
        if (countOfPlaningsS!=0){
            setSalleAvailable(false);
            return new ResponseEntity<>(new Message("cette salle n'est pas disponible dans la période ques vous avez choisis ", seanceDto,Constant.Status.ERROR), HttpStatus.ACCEPTED);

        }else {
            setSalleAvailable(true);
            return new ResponseEntity<>(new Message("cette salle est disponible", seanceDto,Constant.Status.SUCCESS), HttpStatus.ACCEPTED);

        }
    }

    @PostMapping("/api/isteacherav")
    public ResponseEntity<?> isTeacherAv(SeanceDto seanceDto) {
        long countOfPlaningsT=planingService.planingsOfTeacher(seanceDto);
        if (countOfPlaningsT!=0){
            setTeacherAvailable(false);
            return new ResponseEntity<>(new Message("le groupe que vous avez choisis n'est pas disponible pour la période ques vous avez choisis ", seanceDto,Constant.Status.ERROR), HttpStatus.ACCEPTED);
        }else {
            setTeacherAvailable(true);
            return new ResponseEntity<>(new Message("ce prof est disponible", seanceDto,Constant.Status.SUCCESS), HttpStatus.ACCEPTED);

        }
    }


    public boolean isTeacherAvailable() {
        return teacherAvailable;
    }

    public SceanceController setTeacherAvailable(boolean teacherAvailable) {
        this.teacherAvailable = teacherAvailable;
        return this;
    }

    public boolean isGroupAvailable() {
        return groupAvailable;
    }

    public SceanceController setGroupAvailable(boolean groupAvailable) {
        this.groupAvailable = groupAvailable;
        return this;
    }

    public boolean isSalleAvailable() {
        return salleAvailable;
    }

    public SceanceController setSalleAvailable(boolean salleAvailable) {
        this.salleAvailable = salleAvailable;
        return this;
    }


}

 /*if (seances.isEmpty()) {
            Seance seance = new Seance();
            seance.setScholarYear(String.valueOf(Calendar.getInstance().getTime().getYear()))
                    .setGroup(groupRepository.findOne(seanceDto.getGroupId()))
                    .setSalle(salleRepository.findOne(seanceDto.getSalleId()))
                    .setTeacher(teacherService.findOne(seanceDto.getTeacherId()));
            seanceService.save(seance);

            dateStart = calendar.getTime();
            while (dateStart.before(dateEnd)) {
                Calendar c = Calendar.getInstance();
                c.setTime(dateStart);
                Planing planing = new Planing();
                planing.setSeance(seance)
                        .setPlaningDate(c.getTime())
                        .setStartHour(startTime)
                        .setEndHour(endTime)
                        .setDay(dayService.findOne(seanceDto.getDay()));

                planingRepository.save(planing);

                c.add(Calendar.DAY_OF_YEAR, 7);
                dateStart = c.getTime();
            }
        }else{
            for (Seance seance: seances) {
                for (Planing planing :seance.getPlanings())
                    if (!Objects.equals(planing.getSeance().getGroup().getId(), seanceDto.getGroupId())){
                        if (!Objects.equals(planing.getSeance().getSalle().getId(), seanceDto.getSalleId())){
                            if (!Objects.equals(planing.getSeance().getTeacher().getId(), seanceDto.getTeacherId())){
                                if (DateUtil.periodsNotOverLapping(startTime,endTime,planing)){
                                    canWeSaveThisPlaning = true;
                                }else {
                                    canWeSaveThisPlaning = false;
                                    return new ResponseEntity<>(new Message("seance added successfully", seanceDto), HttpStatus.ACCEPTED);
                                }
                            }
                        }

                    }



            }
        }*/


