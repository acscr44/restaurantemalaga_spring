package edu.arelance.nube.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.arelance.nube.repository.RestauranteRepository;
import edu.arelance.nube.repository.entity.Restaurante;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	
	RestauranteRepository restauranteRepository;
		
	@Override
	@Transactional(readOnly = true)  // permitimos acceso concurrente a la tabla Restaurantes.
	public Iterable<Restaurante> consultarTodos() {
		// TODO Auto-generated method stub
		return this.restauranteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Restaurante> consultarRestaurante(Long id) {
		// TODO Auto-generated method stub
		return this.restauranteRepository.findById(id);
	}

	@Override
	@Transactional
	public Restaurante altaRestaurante(Restaurante restaurante) {
		// TODO Auto-generated method stub
		return this.restauranteRepository.save(restaurante);
	}

	@Override
	@Transactional
	public void borrarRestaurante(Long id) {
		// TODO Auto-generated method stub
		if (this.restauranteRepository.existsById(id)) {
			this.restauranteRepository.deleteById(id);
		} else {
			System.out.println("No se encontró restaurante con ID: " + id );
		}
	}

	@Override
	@Transactional
	public void borrarRestaurante(Restaurante rest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public Optional<Restaurante> modificarRestaurante(Long id, Restaurante restaurante) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
}
