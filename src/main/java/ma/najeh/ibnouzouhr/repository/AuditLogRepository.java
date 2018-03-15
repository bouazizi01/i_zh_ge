package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.audit.AuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by youssef on 12/26/17.
 */
@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog,Long> {
}
