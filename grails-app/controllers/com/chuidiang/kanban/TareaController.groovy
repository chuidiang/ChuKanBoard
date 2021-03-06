package com.chuidiang.kanban

class TareaController {
	
	def save = {
		def tareaInstance = new Tarea(params)
		tareaInstance.estado=0;
		tareaInstance.idTablero = session.tablero
		tareaInstance.save(flush:true)
		
		redirect(controller:"kanban", action: "tablero")
	}
	
	def progresa = {
		def tareaInstance = Tarea.get(params.id)
		if (tareaInstance) {
			if (tareaInstance.estado==0) {
				tareaInstance.fechaComienzo=new Date()
			}
			tareaInstance.estado+=1
			int numeroColumnas = Columna.countByIdTablero(session.tablero)
			if (tareaInstance.estado==(numeroColumnas-1)) {
				tareaInstance.fechaFinalizacion=new Date()
			}
			tareaInstance.save(flush:true)
		}
		redirect(controller:"kanban",action: "tablero")
	}
	
	def regresa = {
		def tareaInstance = Tarea.get(params.id)
		if (tareaInstance) {
			tareaInstance.estado-=1
			if (tareaInstance.estado==0) {
				tareaInstance.fechaComienzo=null
			}
			int numeroColumnas = Columna.countByIdTablero(session.tablero)
			if (tareaInstance.estado <(numeroColumnas-1)){
				tareaInstance.fechaFinalizacion=null
			}
			tareaInstance.save(flush:true)
		}
		redirect(controller:"kanban",action: "tablero")
	}
	
	def borra = {
		def tareaInstance = Tarea.get(params.id)
		tareaInstance.delete(flush:true)
		
		redirect(controller:"kanban",action: "tablero")
	}    
	
	def modifica = {
		Tarea tareaInstance=Tarea.get(params.idTarea)
		tareaInstance.descripcion=params.descripcion
		tareaInstance.save(flush:true)
		
		redirect(controller:"kanban",action:"tablero")
	}
}
