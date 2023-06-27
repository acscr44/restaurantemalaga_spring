package edu.arelance.nube.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.arelance.nube.repository.entity.Restaurante;

@Repository
public interface RestauranteRepository extends CrudRepository<Restaurante, Long>{
	// Hemos heredado todas la operaciones que tiene CrudRepository
	// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
	
}
