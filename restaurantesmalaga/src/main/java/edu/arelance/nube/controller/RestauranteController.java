package edu.arelance.nube.controller;

import java.time.LocalDateTime;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arelance.nube.repository.entity.Restaurante;

/**
 * API WEB
 * HTTP -> Deriva en la ejecución de un método
 * 
 * GET -> Consulta de TODOS
 * GET -> Consulta de Uno (por ID)
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
@RestController  //Devolvemos JSON  // Subtipo de @Controller
@RequestMapping("/restaurante")  // Para mejor orientación de Spring, le indicamos que todo lo que va a dicha URL es para esta clase Controller (restaurantesmalaga)
public class RestauranteController {

		@GetMapping("/test")  // GET http://localhost:8081/restaurante/test
		public Restaurante obtenerRestauranteTest() {
			Restaurante restaurante = null;
			
				System.out.println("Llamando a obtenerRestauranteTest");
				restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque", "https://www.mesonelmartinete.es/", "https://goo.gl/maps/526RFvXpxCkYQ7T69", 36.72f, -4.44f, 10, "gazpachuelo", "paella", "sopa de marisco", LocalDateTime.now());
			
			
			return restaurante;
		}
}
