package edu.arelance.nube.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.arelance.nube.repository.RestauranteRepository;
import edu.arelance.nube.repository.entity.Restaurante;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	@Autowired
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
	@Transactional	// todos los setMethod realizados están bajo una operativa Transaccional.
	public Optional<Restaurante> modificarRestaurante(Long id, Restaurante restaurante) {
		// TODO Auto-generated method stub
		Optional<Restaurante> opRest = Optional.empty();
		// 1 Leer
			opRest = this.restauranteRepository.findById(id);
			if (opRest.isPresent()) {
				// Al estar dentro de una transacción, restauranteLeido está asociado
				// a un registro de la tabla. Si modifico un campo, estoy modificando
				// la columna asociada (Estado "Persistent" - JPA Standard). 
				Restaurante restauranteLeido = opRest.get();
				// 2 Actualizar
				// Si no existe, devolvemos un Optional vacío, así evitamos devolver un null. 
				// 		De recorrer ese null, obtendríamos un nullpointexception.
				// Podríamos ir seteando campo a campo
				// 		restauranteLeido.setNombre(restaurante.getNombre());
				// Pero lo haremos de otro modo más rápido.
				BeanUtils.copyProperties(restaurante, restauranteLeido, "id", "creadoEn");
				// Solo queda envolver este object_target en un Optional
				opRest = Optional.of(restauranteLeido);  // "relleno el Optional"
			}
			
			
		return opRest;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> encuentraPorPrecioAcotado(int preciomin, int preciomax){
//		Iterable<Restaurante> listaRestPrecioAcotado = this.restauranteRepository.findByPrecioBetween(preciomin, preciomax);
//		
//		return listaRestPrecioAcotado;
		return this.restauranteRepository.findByPrecioBetween(preciomin, preciomax);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> listarPorAlgunCriterioMultiple(String clave) {
		Iterable<Restaurante> listaByAnyCriteria = null; 
		
		listaByAnyCriteria = this.restauranteRepository.buscarPorBarrioNombreOrEspecialidad(clave);
		return listaByAnyCriteria;
	}
	
}
