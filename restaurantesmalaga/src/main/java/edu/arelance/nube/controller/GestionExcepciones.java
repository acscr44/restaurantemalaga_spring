package edu.arelance.nube.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Esta clase está escuchando/listener todas las excepciones de este paquete y subfolders.
@RestControllerAdvice(basePackages = {"edu.arelance.nube"})
public class GestionExcepciones {
	// para cada tipo de excepción o fallo se define un método.
	
	
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> gestionStringOutIndexException(StringIndexOutOfBoundsException e){
		ResponseEntity<?> responseEntity = null;
		
			responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
			// Se puede generar un log y dulcificar la respuesta
		
		return responseEntity;
	}
	
	
	// Control de error muy genérico, MUY recomendable para errores que se nos escapan. 
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> gestionExceptionGenerica(Throwable e){
		ResponseEntity<?> responseEntity = null;
		
			responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
			// Se puede generar un log y dulcificar la respuesta
		
		return responseEntity;
	}
}
