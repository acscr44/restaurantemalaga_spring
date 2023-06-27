package edu.arelance.nube.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurantes", schema = "bdrestaurantes")
public class Restaurante {
	
	@Id  // Indica a Spring que este atributo será la clave primaria.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoInc en MySQL
	private Long id;
	
	private String nombre;
	private String direccion;
	private String barrio;
	private String web;
	private String fichaGoogle;
	
	private Float latitud;
	private Float longitud;
	
	private Integer precio;
	
	private String especialidad1;
	private String especialidad2;
	private String especialidad3;
	
	
	
	
}
