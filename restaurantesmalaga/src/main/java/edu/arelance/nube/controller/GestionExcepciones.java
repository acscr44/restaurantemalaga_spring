package edu.arelance.nube.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Esta clase está escuchando/listener todas las excepciones de este paquete y subfolders.
@RestControllerAdvice(basePackages = {"edu.arelance.nube"})
public class GestionExcepciones {
	// para cada tipo de excepción o fallo se define un método.
	// Instancia logger de nivel debug
	Logger logger = LoggerFactory.getLogger(RestauranteController.class);
	
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> gestionStringOutIndexException(StringIndexOutOfBoundsException e){
		ResponseEntity<?> responseEntity = null;
		
			responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
			// Se puede generar un log y dulcificar la respuesta
			logger.error(e.getMessage(), e);
		return responseEntity;
	}
	
	
	// Control de error muy genérico, MUY recomendable para errores que se nos escapan. 
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> gestionExceptionGenerica(Throwable e){
		ResponseEntity<?> responseEntity = null;
		
			responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
			// Se puede generar un log y dulcificar la respuesta
			logger.error(e.getMessage(), e);
			
		return responseEntity;
	}
}
