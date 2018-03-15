package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.dto.SeanceDto;
import ma.najeh.ibnouzouhr.model.Hour;
import ma.najeh.ibnouzouhr.model.Planing;
import ma.najeh.ibnouzouhr.repository.GroupRepository;
import ma.najeh.ibnouzouhr.repository.PlaningRepository;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youssef on 12/15/17.
 */
@Service("PlaningService")
public class PlaningService {
    private final GroupRepository groupRepository;
    private final SalleRepository salleRepository;
    private final TeacherRepository teacherRepository;
    private final PlaningRepository planingRepository;


    @Autowired
    public PlaningService(GroupRepository groupRepository, SalleRepository salleRepository, TeacherRepository teacherRepository, PlaningRepository planingRepository) {
        this.groupRepository = groupRepository;
        this.salleRepository = salleRepository;
        this.teacherRepository = teacherRepository;
        this.planingRepository = planingRepository;
    }

    public long planingsOfTeacher(SeanceDto seanceDto) {
        return teacherRepository.planingsOfATeacher(seanceDto.getTeacherId(),new Hour(seanceDto.getStartTime()).getH(),new Hour(seanceDto.getEndTime()).getH(),seanceDto.getDay());
    }
    public long planingsOfSalle(SeanceDto seanceDto) {

        return salleRepository.planingsOfASalle(seanceDto.getSalleId(),new Hour(seanceDto.getStartTime()).getH(),new Hour(seanceDto.getEndTime()).getH(),seanceDto.getDay());
    }
    public long planingsOfGroup(SeanceDto seanceDto) {
        return groupRepository.planingsOfAGroup(seanceDto.getGroupId(),new Hour(seanceDto.getStartTime()).getH(),new Hour(seanceDto.getEndTime()).getH(),seanceDto.getDay());
    }


    public Planing save(Planing planing) {
        return planingRepository.save(planing);
    }
}
