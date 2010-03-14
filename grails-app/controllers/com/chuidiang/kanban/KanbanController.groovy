package com.chuidiang.kanban

class KanbanController {  
	def defaultAction="tablero"
	
	def tablero = {
		log.info ("params.id="+params.id)
		log.info ("session.tablero="+ session.tablero)
		Tablero tablero
		if (params.id != null) {
			tablero = Tablero.get(params.id)
			session.tablero=tablero.id
		} else {
			tablero = Tablero.get(session.tablero)
		}
		
		if (null==tablero){
			render(view:"/index")
			return;
		}
		
		def columnas = Columna.findAllByIdTablero(tablero.id,[sort:"numeroColumna", order:"asc"])
		log.info "columnas="+columnas.size()
		// Se crean dos columnas por defecto si no hay ninguna
		// La de pila de tareas y la de tareas hechas
		if (columnas.size()==0){
			
			Columna columnaInicial=new Columna()
			columnaInicial.numeroColumna=0
			columnaInicial.titulo="PILA"
			columnaInicial.borrable=false
			columnaInicial.idTablero = tablero.id
			columnaInicial.save(flush:true)
			columnas.add(columnaInicial)
			
			Columna columnaFinal=new Columna()
			columnaFinal.numeroColumna=1
			columnaFinal.titulo="HECHO"
			columnaFinal.borrable=false
			columnaFinal.idTablero = tablero.id
			columnaFinal.save(flush:true)
			columnas.add(columnaFinal)
		}
		
		def todasLasTareas = []
		columnas.each ({ columna ->
			def tareas = Tarea.findAllByEstadoAndIdTablero(columna.numeroColumna,tablero.id)
			todasLasTareas.add(tareas)
		})
		[listaColumnas:columnas, listaTodasLasTareas:todasLasTareas]
	}
	
	def save = {
		def tareaInstance = new Tarea(params)
		tareaInstance.estado=0;
		tareaInstance.fechaModificacion=new Date()
		tareaInstance.idTablero = session.tablero
		tareaInstance.save(flush:true)
		
		redirect(action: "tablero")
	}
	
	def progresa = {
		def tareaInstance = Tarea.get(params.id)
		if (tareaInstance) {
			tablero = Tablero.get(session.tablero)
			tareaInstance.estado+=1
			tareaInstance.fechaModificacion=new Date()
			tareaInstance.save(flush:true)
		}
		redirect(action: "tablero")
	}
	
	def regresa = {
		def tareaInstance = Tarea.get(params.id)
		if (tareaInstance) {
			tareaInstance.estado-=1
			tareaInstance.fechaModificacion=new Date()
			tareaInstance.save(flush:true)
		}
		redirect(action: "tablero")
	}
	
	def borra = {
		def tareaInstance = Tarea.get(params.id)
		tareaInstance.delete(flush:true)
		
		redirect(action: "tablero")
	}    
}