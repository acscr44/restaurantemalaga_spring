package edu.arelance.nube.service;


import java.util.Optional;

import edu.arelance.nube.repository.entity.Restaurante;

public interface RestauranteService{
	
	Iterable<Restaurante> consultarTodos();
	
	Optional<Restaurante> consultarRestaurante(Long id);
	
	Restaurante altaRestaurante(Restaurante restaurante);
	// Este Restaurante nuevo-saliente tendrá que incorporar dos campos más, el id y en creadoEn.
	// Este Restaurante saliente ya será un registro nuevo en la BD.
	
	
	void borrarRestaurante(Long id);
	
	void borrarRestaurante(Restaurante rest);
	
	Optional<Restaurante> modificarRestaurante(Long id, Restaurante restaurante);

	Iterable<Restaurante> encuentraPorPrecioAcotado(int preciomin, int preciomax);
	
	Iterable<Restaurante> listarPorAlgunCriterioMultiple(String clave);
	
	
}
