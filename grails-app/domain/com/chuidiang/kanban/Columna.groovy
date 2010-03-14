package com.chuidiang.kanban

class Columna implements Comparable {
	String titulo
	int maximoTareas
	int numeroColumna
	boolean borrable
	
	static belongsTo = [tablero:Tablero]
	
	int compareTo(obj) {
		numeroColumna.compareTo(obj.numeroColumna)
	}
}