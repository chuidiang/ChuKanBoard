package com.chuidiang.kanban

class Tarea {
	String descripcion;
	String personaAsignada;
	Date fechaComienzo;
	Date fechaFinalizacion;
	int estado;
	int idTablero
	
	static constraints = {
		personaAsignada(nullable:true)
		fechaComienzo(nullable:true)
		descripcion(blank:false)
		fechaFinalizacion(nullable:true)
		fechaComienzo(nullable:true)
	}
}
