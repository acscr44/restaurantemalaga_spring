package edu.arelance.nube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.arelance.nube.dto.FraseChuckNorris;
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

/**
 * SIGNIFICADO de la CLASE --> FECHA - VERSIÓN - AUTO - COMENTAR LA CLASE
 * 
 * @author ALEJANDRO
 */
//@Controller //Devolvemos una vista  (html/jsp)
@CrossOrigin(originPatterns = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController // Devolvemos JSON // Subtipo de @Controller
@RequestMapping("/restaurante") // Para mejor orientación de Spring, le indicamos que todo lo que va a dicha URL
								// es para esta clase Controller (restaurantesmalaga)
public class RestauranteController {

	@Autowired // Inyección de dependencias
	RestauranteService restauranteService;

	@Autowired
	Environment environment; // de aquí obtenemos la informacioón del puerto.

	// Instancia logger de nivel debug
	Logger logger = LoggerFactory.getLogger(RestauranteController.class);

	@GetMapping("/test") // GET http://localhost:8081/restaurante/test
	public Restaurante obtenerRestauranteTest() {
		Restaurante restaurante = null;

		System.out.println("Llamando a obtenerRestauranteTest");
		// logger de nivel debug
		logger.debug("estoy en obtenerRestauranteTest");
		restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque",
				"https://www.mesonelmartinete.es/", "https://goo.gl/maps/526RFvXpxCkYQ7T69", 36.72f, -4.44f, 10,
				"gazpachuelo", "paella", "sopa de marisco", LocalDateTime.now());

		return restaurante;
	}

	/**
	 * ENTRADAS - SALIDAS - FUNCIONALIDAD - PRECONDICIONES Y POSTCONDICIONES
	 * 
	 * @param e
	 * @return ResponseEntity
	 * 
	 */
	// GET -> Consulta de TODOS GET http://localhost:8081/restaurante
	@GetMapping
	public ResponseEntity<?> listarTodos() {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Iterable<Restaurante> lista_Restaurantes = null;

		/*
		 * Gestión Automática de Excepciones (GestAutExc)
		 */
//				String saludo = "HOLA";
//				saludo.charAt(10);
		/*
		 * END GestAutExc
		 * 
		 */
		logger.debug("ATENDIDO POR EL PUERTO " + environment.getProperty("local.server.port"));
		lista_Restaurantes = this.restauranteService.consultarTodos();
		responseEntity = ResponseEntity.ok(lista_Restaurantes);

		return responseEntity;
	}

	private ResponseEntity<?> generarRespuestaErroresValidacion(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;
		listaErrores = bindingResult.getAllErrors();
		// Hay que dejar constancia de los errores por el log
		listaErrores.forEach(e -> logger.error(e.toString()));

		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);

		return responseEntity;
	}

	// GET -> Consulta de Uno (por ID) GET http://localhost:8081/restaurante/id
	@Operation(description = "Servicio que recibe la consulta de un restaurante por ID", summary = "esquema")
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> or = null;

		// logger de nivel debug
		logger.debug("En listarPorId" + id);
		or = this.restauranteService.consultarRestaurante(id);
		if (or.isPresent()) {
			// la consulta ha recuperado un registro
			// ok == ok response 200
			Restaurante restauranteLeido = or.get();
			responseEntity = ResponseEntity.ok().body(restauranteLeido);
			logger.debug("Recuperado el registro " + restauranteLeido.toString());
		} else {
			// la consulta NO ha recuperado ningún registro
			// noContent == error response 204
			responseEntity = ResponseEntity.noContent().build();
			logger.debug("El restaurante con  " + id + " no existe");
		}
		logger.debug("Saliendo de listarPorId");

		return responseEntity;
	}

	//
	// GET -> Búsqueda -> Por barrio, por especialidad, por nombre, etc.
	//

	// GET acotado por precio: he usado dos tipos de predicado distintos.
	// GET http://localhost:8081/restaurante/11/19
	// @GetMapping("/{preciomin}/{preciomax}")
	// public ResponseEntity<?> consultarPorPrecioAcotado(@PathVariable int
	// preciomin, @PathVariable int preciomax ){

	// GET
	// http://localhost:8081/restaurante/buscarPorPrecio?preciomin=11&preciomax=19
	@GetMapping("/buscarPorPrecio")
	public ResponseEntity<?> consultarPorPrecioAcotado(@RequestParam(name = "preciomin") int preciomin,
			@RequestParam(name = "preciomax") int preciomax) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> iterRest = null;
		iterRest = this.restauranteService.encuentraPorPrecioAcotado(preciomin, preciomax);
		responseEntity = ResponseEntity.ok(iterRest);
		return responseEntity;
	}

	// GET
	// http://localhost:8081/restaurante/buscarPorPrecioPaginado?preciomin=11&preciomax=19&page=0&size=3
	@GetMapping("/buscarPorPrecioPaginado")
	public ResponseEntity<?> listarPorRangoPrecioPaginado(@RequestParam(name = "preciomin") int preciomin,
			@RequestParam(name = "preciomax") int preciomax, Pageable pageable) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> iterRest = null;
		iterRest = this.restauranteService.encuentraPorPrecioAcotado(preciomin, preciomax, pageable);
		responseEntity = ResponseEntity.ok(iterRest);
		return responseEntity;
	}

	// http://localhost:8081/restaurante/searchByAny?clave=carranque
	// GET 
	@GetMapping("/searchByAny")
	public ResponseEntity<?> listarPorAlgunCriterioMultiple(@RequestParam(name = "clave") String clave) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> iterRest = null;
		iterRest = this.restauranteService.listarPorAlgunCriterioMultiple(clave);
		responseEntity = ResponseEntity.ok(iterRest);

		return responseEntity;

	}

	// POST -> Insertar un restaurante nuevo. POST
	// http://localhost:8081/restaurante(Body Restaurante)
	// Bean Validation: Los métodos POST y PUT sí requieren validación
	@PostMapping
	public ResponseEntity<?> insertarRestaurante(@Valid @RequestBody Restaurante restaurante,
			BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Restaurante restauranteNuevo = null;
		// TODO form validator @Valid y BindingResult(informa si ha ido bien o mal)
		if (bindingResult.hasErrors()) {
			logger.debug("Errores en la entrada POST");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {
			logger.debug("Entrada POST correcta");
			// Respuesta para un registro correcto: 201
			restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
		}
		return responseEntity;
	}

	// PUT -> Modificar un restaurante existente. PUT
	// http://localhost:8081/restaurante/id (Body Restaurante)
	// Bean Validation: Los métodos POST y PUT sí requieren validación
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarRestaurante(@Valid @RequestBody Restaurante restaurante,
			BindingResult bindingResult, @PathVariable Long id) {

		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> opRest = null;

		if (bindingResult.hasErrors()) {
			logger.debug("Errores en la entrada PUT");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {
			logger.debug("Entrada POST correcta");
			opRest = this.restauranteService.modificarRestaurante(id, restaurante);
			if (opRest.isPresent()) {
				Restaurante restModificado = opRest.get();
				responseEntity = ResponseEntity.ok(restModificado);
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		}
		return responseEntity;
	}

	// DELETE -> Borrar un restaurante (por ID). DELETE
	// http://localhost:8081/restaurante/id
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> borrarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		// Respuesta para el borrado correcto: 200
		// Respuesta para el borrado incorrecto: 500
		this.restauranteService.borrarRestaurante(id);
		responseEntity = ResponseEntity.ok().build();

		return responseEntity;
	}

	// Consultar todos los barrios. Metodo GET a
	// http://localhost:8081/restaurante/barrios
	@GetMapping("/barrios")
	public ResponseEntity<?> obtenerListadoBarrios() {

		ResponseEntity<?> responseEntity = null;

		List<String> lista_barrios = null;

		lista_barrios = this.restauranteService.obtenerTodosLosBarrios();

		responseEntity = ResponseEntity.ok(lista_barrios);

		return responseEntity;
	}

	/**
	 * Obtener una frase aleatoria de Chuck Norris Metodo GET a
	 * http://localhost:8081/restaurante/frasechuck
	 * 
	 * @return
	 */
	//
	@GetMapping("/frasechuck")
	public ResponseEntity<?> obtenerFraseChuck() {
		ResponseEntity<?> responseEntity = null;
		Optional<FraseChuckNorris> obFraseAleaChuck = null;

		obFraseAleaChuck = this.restauranteService.obtenerFraseAleatorioChuckNorris();
		if (obFraseAleaChuck.isPresent()) {
			FraseChuckNorris frase = obFraseAleaChuck.get();
			logger.debug("Frase recibida");
			responseEntity = ResponseEntity.ok(frase);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	}

	// POST -> Insertar un restaurante nuevo con foto. POST
	// http://localhost:8081/restaurante/crear-con-foto (Body Restaurante)
	// Bean Validation: Los métodos POST y PUT sí requieren validación
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> insertarRestauranteConFoto(@Valid Restaurante restaurante, BindingResult bindingResult,
			MultipartFile archivo) throws IOException {

		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Restaurante restauranteNuevo = null;
		// TODO form validator @Valid y BindingResult(informa si ha ido bien o mal)
		if (bindingResult.hasErrors()) {
			logger.debug("Errores en la entrada POST");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {
			logger.debug("Entrada POST correcta");
			if (!archivo.isEmpty()) {
				logger.debug("El restaurante trae foto");
				try {
					restaurante.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Error al tratar la foto", e);
					logger.debug("Error al tratar la foto", e);
					e.printStackTrace();
					throw e;
				}
			}
			// Respuesta para un registro correcto: 201
			restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
		}
		return responseEntity;
	}

	// GET -> Consulta de Uno (por ID) GET
	// http://localhost:8081/restaurante/obtenerFoto/id
	@Operation(description = "Servicio que recibe la foto de un restaurante por ID", summary = "esquema")
	@GetMapping("/obtenerFoto/{id}")
	public ResponseEntity<?> obtenerFotoRestaurante(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> or = null;
		Resource imagen = null; // esto representa el archivo (imagen)

		// logger de nivel debug
		logger.debug("En obtenerFotoRestaurante" + id);
		or = this.restauranteService.consultarRestaurante(id);
		if (or.isPresent() && or.get().getFoto() != null) {
			// la consulta ha recuperado un registro
			// ok == ok response 200
			Restaurante restauranteLeido = or.get();
			imagen = new ByteArrayResource(restauranteLeido.getFoto());
			responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			logger.debug("Recuperado el registro " + restauranteLeido.toString());
		} else {
			// la consulta NO ha recuperado ningún registro
			// noContent == error response 204
			responseEntity = ResponseEntity.noContent().build();
			logger.debug("El restaurante con  " + id + " no existe o no tiene foto");
		}
		logger.debug("Saliendo de obtenerFotoRestaurante");

		return responseEntity;
	}

	// PUT -> Modificar un restaurante existente. PUT
	// http://localhost:8081/restaurante/editar-con-foto/id (Body Restaurante)
	// Bean Validation: Los métodos POST y PUT sí requieren validación
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> modificarRestauranteConFoto(@Valid Restaurante restaurante, BindingResult bindingResult, // Resultado
																														// de
																														// la
																														// carga,
																														// nos
																														// informa
																														// si
																														// ha
																														// habido
																														// algún
																														// problema
																														// con
																														// la
																														// carga
																														// del
																														// objeto
																														// JSON
																														// Restaurante
																														// recibido.
			MultipartFile archivo, @PathVariable Long id) throws IOException {

		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Optional<Restaurante> opRest = null;

		if (bindingResult.hasErrors()) {
			logger.debug("Errores en la entrada PUT");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {
			logger.debug("Entrada PUT correcta");
			if (!archivo.isEmpty()) {
				try {
					restaurante.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error al tratar la foto ", e); // Registra el error
					throw e; // Y lo propaga
				} // Hasta que incluyamos el Try/Catch el método getBytes() devuelve una excepción
					// de las que hay que controlar (checked)
			}
			opRest = this.restauranteService.modificarRestaurante(id, restaurante);
			if (opRest.isPresent()) {
				Restaurante restModificado = opRest.get();
				responseEntity = ResponseEntity.ok(restModificado);
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		}
		return responseEntity;
	}

	// GET -> Consulta de TODOS GET
	// http://localhost:8081/restaurante/pagina?page=0&size=2
	@GetMapping("/pagina")
	public ResponseEntity<?> obtenerRestaurantePorPagina(Pageable pageable) {
		ResponseEntity<?> responseEntity = null; // responseEntity representa el Header y Body de una petición HTTP
		Iterable<Restaurante> pagina_Restaurantes = null;

		pagina_Restaurantes = this.restauranteService.consultarPorPagina(pageable);
		responseEntity = ResponseEntity.ok(pagina_Restaurantes);

		return responseEntity;
	}

}
