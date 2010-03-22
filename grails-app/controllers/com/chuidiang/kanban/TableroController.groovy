package com.chuidiang.kanban

class TableroController {
	
	def crear = {
		def tablero = new Tablero()
		tablero.nombre = params.nombre
		tablero = tablero.save(flush:true)
		session.tablero=tablero.id
		redirect(controller:"kanban", action:"tablero")
	}
	
	def cambiaNombreTablero = {
		def tablero = Tablero.get(params.idTablero)
		tablero.nombre=params.nuevoNombreTablero
		tablero.save()
		redirect(uri:"/index.gsp")
	}
	
	def borra = {
		def tareas = Tarea.findAllByIdTablero(params.id)
		def columnas = Columna.findAllByIdTablero(params.id)
		
		tareas.each  { tarea ->
			tarea.delete()
		}
		columnas.each { columna->
			columna.delete()
		}
		log.info(params)
		def tablero = Tablero.get(params.id)
		tablero.delete()
		redirect(uri:"/index.gsp")
	}
}