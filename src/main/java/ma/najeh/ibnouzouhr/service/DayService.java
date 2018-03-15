package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Day;
import ma.najeh.ibnouzouhr.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youssef on 12/15/17.
 */
@Service("DayService")
public class DayService {
    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public Day save(Day day){
        return dayRepository.save(day);
    }
    public Day findOne(Long id){
        return dayRepository.findOne(id);
    }


    public long count() {
        return dayRepository.count();
    }
}
