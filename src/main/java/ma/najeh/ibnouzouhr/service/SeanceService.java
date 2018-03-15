package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Planing;
import ma.najeh.ibnouzouhr.model.Seance;
import ma.najeh.ibnouzouhr.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssef on 12/15/17.
 */
@Service("SeanceService")
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;

    public Seance save(Seance seance){
        return seanceRepository.save(seance);
    }

    public void save(Iterable<Seance> seanceList) {
        seanceRepository.save(seanceList);
    }

    public List<Seance> findAllByTeacherId(Long idTeacher){
        return seanceRepository.findAllByTeacherId(idTeacher);
    }
    public List<Seance> findAllByGroupStudentsId(Long idStudent){
        return seanceRepository.findAllByGroupStudentsId(idStudent);
    }
    public Seance findOneByTeacherId(Long idTeacher){
        return seanceRepository.findByTeacherId(idTeacher);
    }

    public List<Planing> allPlaningsOfSeancesOfATeacher(Long idTeacher){
        List<Planing > planings = new ArrayList<>();
        for (Seance seance:findAllByTeacherId(idTeacher)){
            planings.addAll(seance.getPlanings());
        }
        return planings;
    }


    public List<Planing> allPlaningsOfSeancesOfAStudent(Long idStudent) {
        List<Planing > planings = new ArrayList<>();
        for (Seance seance:findAllByGroupStudentsId(idStudent)){
            planings.addAll(seance.getPlanings());
        }
        return planings;
    }
}
