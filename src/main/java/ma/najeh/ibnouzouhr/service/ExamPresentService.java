package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.ExamPresent;
import ma.najeh.ibnouzouhr.repository.ExamPresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youssef on 12/15/17.
 */
@Service("ExamPresentService")
public class ExamPresentService {
    private final ExamPresentRepository examPresentRepository;

    @Autowired
    public ExamPresentService(ExamPresentRepository examPresentRepository) {
        this.examPresentRepository = examPresentRepository;
    }

    public ExamPresent save(ExamPresent examPresent){
        return examPresentRepository.save(examPresent);
    }
}
