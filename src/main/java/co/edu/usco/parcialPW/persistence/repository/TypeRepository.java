
package co.edu.usco.parcialPW.persistence.repository;

import co.edu.usco.parcialPW.persistence.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends JpaRepository<Type, Long>{
    
    @Query(value = "SELECT t FROM Type t WHERE t.name = :name", nativeQuery = false)
    Type findByName(@Param("name") String name);
    
}
