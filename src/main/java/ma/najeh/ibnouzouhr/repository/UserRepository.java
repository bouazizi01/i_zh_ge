package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User getByUsername(String userName);

    boolean existsByUsername(String username);
    @Query(value = "SELECT u.* FROM user u WHERE u.password LIKE concat('%',?1)",nativeQuery = true)
    List<User> findByPasswordLike(String invalid);

    @Modifying
    @Transactional
    @Query("update User  u set u.password = :password where u.id = :id")
    int updatePassword(@Param("password") char[] password, @Param("id") Long id);
    @Modifying
    @Transactional
    @Query("update User  u set u.lastLogin = :lastLogin where u.id = :id")
    int updateLoginDate(@Param("lastLogin") Date lastLogin,@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("update User  u set u.lastLogout = :lastLogout where u.id = :id")
    int updateLogoutDate(@Param("lastLogout") Date lastLogout,@Param("id") Long id);

    List<User> findByRole(String role);
}
