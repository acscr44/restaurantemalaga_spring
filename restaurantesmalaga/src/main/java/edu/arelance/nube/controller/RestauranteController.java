package edu.arelance.nube.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.arelance.nube.repository.entity.Restaurante;
import edu.arelance.nube.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;

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

	@Autowired // Inyección de dependencias
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
		Iterable<Restaurante> lista_Restaurantes = null;
			
		/*
		 *  Gestión Automática de Excepciones (GestAutExc) */
//				String saludo = "HOLA";
//				saludo.charAt(10);
		/*	END GestAutExc
		 * 
		 */
			lista_Restaurantes = this.restauranteService.consultarTodos();
			responseEntity = ResponseEntity.ok(lista_Restaurantes);

		return responseEntity;
	}

	// GET -> Consulta de Uno (por ID) GET http://localhost:8081/restaurante/id
	@Operation(
			description = "Servicio que recibe la consulta de un restaurante por ID",
			summary = "esquema"
			)
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> or = null;
			
			or = this.restauranteService.consultarRestaurante(id);
			if (or.isPresent()){
				// la consulta ha recuperado un registro
				// ok == ok response 200
				Restaurante restauranteLeido = or.get();
				responseEntity = ResponseEntity.ok().body(restauranteLeido);
			} else {
				// la consulta NO ha recuperado ningún registro
				// noContent == error response 204
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;
	}
	
	//
	// GET -> Búsqueda -> Por barrio, por especialidad, por nombre, etc.
	//
	
	// GET acotado por precio: he usado dos tipos de predicado distintos.
	// GET http://localhost:8081/restaurante/11/19
	//@GetMapping("/{preciomin}/{preciomax}")
	//public ResponseEntity<?> consultarPorPrecioAcotado(@PathVariable int preciomin, @PathVariable int preciomax ){
	
	// GET http://localhost:8081/restaurante/buscarPorPrecio?preciomin=11&preciomax=19
	@GetMapping("/buscarPorPrecio")
	public ResponseEntity<?> consultarPorPrecioAcotado(
			@RequestParam(name = "preciomin") int preciomin, 
			@RequestParam(name = "preciomax") int preciomax ){
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> iterRest = null;
		iterRest = this.restauranteService.encuentraPorPrecioAcotado(preciomin, preciomax);
		responseEntity = ResponseEntity.ok(iterRest);
		return  responseEntity;
	}
	
	// GET 
	@GetMapping("/searchByAny")
	public ResponseEntity<?> listarPorAlgunCriterioMultiple(
				@RequestParam(name = "clave") String clave){
			ResponseEntity<?> responseEntity = null;
			Iterable<Restaurante> iterRest = null;
			iterRest = this.restauranteService.listarPorAlgunCriterioMultiple(clave);
			responseEntity = ResponseEntity.ok(iterRest);
		
			return responseEntity;
		
	}
	

	// POST -> Insertar un restaurante nuevo. POST  http://localhost:8081/restaurante(Body Restaurante)
	@PostMapping
	public ResponseEntity<?> insertarRestaurante(@RequestBody Restaurante restaurante){
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Restaurante restauranteNuevo = null;
			// Respuesta para un registro correcto: 201
			restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
		return  responseEntity; 
	}
	
	// PUT -> Modificar un restaurante existente. PUT   http://localhost:8081/restaurante/id (Body Restaurante)
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarRestaurante(
			@RequestBody Restaurante restaurante, 
			@PathVariable Long id){
		
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> opRest = null;
			opRest = this.restauranteService.modificarRestaurante(id, restaurante);
			if (opRest.isPresent()) {
					Restaurante restModificado = opRest.get();
					responseEntity = ResponseEntity.ok(restModificado);
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		return  responseEntity; 
	}
	
	// DELETE -> Borrar un restaurante (por ID). DELETE http://localhost:8081/restaurante/id
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> borrarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
			// Respuesta para el borrado correcto: 200
			// Respuesta para el borrado incorrecto: 500
			this.restauranteService.borrarRestaurante(id);
			responseEntity = ResponseEntity.ok().build();

		return responseEntity;
	}

	
}
