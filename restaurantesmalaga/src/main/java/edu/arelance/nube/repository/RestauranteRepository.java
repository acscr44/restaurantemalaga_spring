package edu.arelance.nube.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.arelance.nube.repository.entity.Restaurante;

@Repository
public interface RestauranteRepository extends CrudRepository<Restaurante, Long>{
	// Hemos heredado todas la operaciones que tiene CrudRepository
	// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

	
	// 1 KEY WORD QUERIES - Consultas por palabras clave
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	
	// 2 JPQL - HQL - Pseudo SQL pero de JAVA - "Agnóstico"
	// 3 NATIVAS - SQL
	// 4 STORED PROCEDURES - Procedimientos Almacenados (pequeños métodos/funciones almacenadas en la BBDD, que podemos invocar desde java)
	// 5 CRITERIA API - Forma alejada de SQL. Librerías que permiten acceder a la bd mediante grafos.
	// https://www.arquitecturajava.com/jpa-criteria-api-un-enfoque-diferente/
	
	
	// 1 KEY WORD QUERIES
	// Obtener restaurantes en un rango de precio
	Iterable<Restaurante> findByPrecioBetween(int preciomin, int preciomax);
	
	
	// 3 NATIVAS - SQL
	// Devuelve un método
	@Query(value= "SELECT * FROM bdrestaurantes.restaurantes "
					+ "WHERE barrio LIKE %?1% OR "
					+ "nombre LIKE %?1% OR "
					+ "especialidad1 LIKE %?1% OR "
					+ "especialidad2 LIKE %?1% OR "
					+ "especialidad3 LIKE %?1%;" , 
			nativeQuery = true)
	Iterable<Restaurante> buscarPorBarrioNombreOrEspecialidad(String clave);

	
	// paginación spring boot
	// https://refactorizando.com/paginacion-ordenacion-spring-data/
	
}
