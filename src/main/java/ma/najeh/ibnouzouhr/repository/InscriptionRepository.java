package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Inscription;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by youssef on 12/3/17.
 */
public interface InscriptionRepository extends CrudRepository<Inscription,Long> {

    List<Inscription> findAllByStudentId(long id);
    @Transactional
    long deleteByStudentIdAndModuleId(Long id, Long id1);

    Iterable<Inscription> findAllByModuleId(Long id);
}
