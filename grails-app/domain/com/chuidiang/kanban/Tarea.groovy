package com.chuidiang.kanban

class Tarea {
	String descripcion;
	String personaAsignada;
	Date fechaComienzo;
	Date fechaModificacion=new Date();
	int estado;
	
	static belongsTo = [tablero:Tablero]
	
    static constraints = {
       personaAsignada(nullable:true)
       fechaComienzo(nullable:true)
       descripcion(blank:false)
    }
}
