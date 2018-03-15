package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Module;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface ModuleRepository extends CrudRepository<Module,Long> {

    Module findByCode(String code);

    List<Module> findAllBySemestreName(String semestreName);

    List<Module> findAllBySemestreNameAndBranchName(String semestreName,String branchName);

    List<Module> findAllByBranchId(Long id);

    Iterable<Module> findAllBySemestreId(Long id);

    Iterable<Module> findAllBySemestreIdAndBranchId(Long semestreId, Long branchId);

    Module findByTeacherId(Long id);
}
