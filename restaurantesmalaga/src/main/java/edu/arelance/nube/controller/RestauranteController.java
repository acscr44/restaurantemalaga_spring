package edu.arelance.nube.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arelance.nube.repository.entity.Restaurante;
import edu.arelance.nube.service.RestauranteService;

/**
 * API WEB HTTP -> Deriva en la ejecución de un método
 * 
 * GET -> Consulta de TODOS GET -> Consulta de Uno (por ID)
 * 
 * POST -> Insertar un restaurante nuevo.
 * 
 * PUT -> Modificar un restaurante existente.
 * 
 * DELETE -> Borrar un restaurante (por ID)
 * 
 * GET -> Búsqueda -> Por barrio, por especialidad, por nombre, etc.
 * 
 */

//@Controller //Devolvemos una vista  (html/jsp)
@RestController // Devolvemos JSON // Subtipo de @Controller
@RequestMapping("/restaurante") // Para mejor orientación de Spring, le indicamos que todo lo que va a dicha URL
								// es para esta clase Controller (restaurantesmalaga)
public class RestauranteController {

	// @Autowired // Inyección de dependencias
	RestauranteService restauranteService;

	@GetMapping("/test") // GET http://localhost:8081/restaurante/test
	public Restaurante obtenerRestauranteTest() {
		Restaurante restaurante = null;

		System.out.println("Llamando a obtenerRestauranteTest");
		restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque",
				"https://www.mesonelmartinete.es/", "https://goo.gl/maps/526RFvXpxCkYQ7T69", 36.72f, -4.44f, 10,
				"gazpachuelo", "paella", "sopa de marisco", LocalDateTime.now());

		return restaurante;
	}

	// GET -> Consulta de TODOS GET http://localhost:8081/restaurante
	@GetMapping
	public ResponseEntity<?> listarTodos() {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Iterable<Restaurante> lista_restaurante = null;
		lista_restaurante = this.restauranteService.consultarTodos();
		responseEntity = ResponseEntity.ok(lista_restaurante);

		return responseEntity;
	}

	// GET -> Consulta de Uno (por ID) GET http://localhost:8081/restaurante/id
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		

		return responseEntity;
	}
	// POST -> Insertar un restaurante nuevo. POST
	// http://localhost:8081/restaurante(Body Restaurante)
	// PUT -> Modificar un restaurante existente. PUT
	// http://localhost:8081/restaurante/id (Body Restaurante)
	
	// DELETE -> Borrar un restaurante (por ID). DELETE http://localhost:8081/restaurante/id
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> borrarPorId() {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		

		return responseEntity;
	}

	// GET -> Búsqueda -> Por barrio, por especialidad, por nombre, etc.

}
