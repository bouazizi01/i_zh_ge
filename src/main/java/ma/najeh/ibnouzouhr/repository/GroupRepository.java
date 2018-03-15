package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface GroupRepository extends CrudRepository<Group,Long> {

    Group findByName(String gr);

    //@Query(value = "select * FROM planing p INNER JOIN seance s on p.seance_id = s.id INNER JOIN groups g on s.group_id = g.id where g.id=:id",nativeQuery = true)
    @Query(value = "select count(*) FROM planing p INNER JOIN seance s on p.seance_id = s.id INNER JOIN groups g on s.group_id = g.id where EXISTS (select planing.id from planing where (day_id=:dayId) AND ( (start_h > :startH AND start_h < :endH ) OR (end_h > :startH AND end_h < :endH )))  AND g.id=:id",nativeQuery = true)
    long planingsOfAGroup(@Param("id") Long id,@Param("startH") Integer startH,@Param("endH") Integer endH,@Param("dayId") Long dayId);

    Iterable<Group> findAllByModuleBranchSpecialtyId(Long id);
}
