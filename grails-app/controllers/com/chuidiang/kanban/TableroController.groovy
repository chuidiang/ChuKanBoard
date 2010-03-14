package com.chuidiang.kanban

class TableroController {
	
	def crear = {
		def tablero = new Tablero()
		tablero.nombre = params.nombre
		tablero = tablero.save(flush:true)
		session.tablero=tablero.id
		redirect(controller:"kanban", action:"tablero")
	}
}