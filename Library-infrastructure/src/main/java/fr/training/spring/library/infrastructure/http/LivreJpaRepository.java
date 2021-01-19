package fr.training.spring.library.infrastructure.http;

import fr.training.spring.library.infrastructure.entiteJpa.LivreJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreJpaRepository extends CrudRepository<LivreJpa,Long> {

}
