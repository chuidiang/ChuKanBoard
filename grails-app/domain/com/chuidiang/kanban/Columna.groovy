package com.chuidiang.kanban

class Columna implements Comparable {
	String titulo
	int maximoTareas
	int numeroColumna
	boolean borrable
	int idTablero
	
	int compareTo(obj) {
		numeroColumna.compareTo(obj.numeroColumna)
	}
}