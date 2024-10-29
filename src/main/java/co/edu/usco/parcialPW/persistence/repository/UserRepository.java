
package co.edu.usco.parcialPW.persistence.repository;

import co.edu.usco.parcialPW.persistence.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u FROM UserEntity u WHERE u.username = :username", nativeQuery = false)
    Optional<UserEntity> findUser(@Param("username") String username);

    @Query(value = "SELECT t FROM UserEntity t JOIN t.roles r WHERE r.name = 'CLIENTE'", nativeQuery = false)
    List<UserEntity> findUsuarios();
    
}
