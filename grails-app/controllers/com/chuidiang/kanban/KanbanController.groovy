package com.chuidiang.kanban

class KanbanController {  
	
	static allowedMethods = [tablero:['POST','GET']];
	
	def defaultAction="tablero"
	
	def tablero = {
		
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
	
	
}