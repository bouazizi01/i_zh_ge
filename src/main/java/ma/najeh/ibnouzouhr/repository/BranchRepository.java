package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Branch;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface BranchRepository extends CrudRepository<Branch,Long> {
    boolean existsByName(String name);
    Branch findByName(String branchName);
}
