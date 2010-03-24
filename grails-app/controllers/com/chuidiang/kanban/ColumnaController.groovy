package com.chuidiang.kanban

class ColumnaController {
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[columnaInstanceList: Columna.list(params),
                    columnaInstanceTotal: Columna.count()]
	}
	
	def nuevaColumna = {
		def tablero = Tablero.get(session.tablero)
		def columnas = Columna.findAllByIdTablero(tablero.id, [sort:"numeroColumna", order:"asc"])
		def incrementarNumeroColumna=false
		def columnaReferencia = Columna.get(params.id)
		def Columna nuevaColumna
		
		// Se desplazan las columnas posteriores
		columnas.each { columna ->
			if (columna.id.equals(columnaReferencia.id)) {
				nuevaColumna= new Columna()
				nuevaColumna.titulo=""
				nuevaColumna.numeroColumna=columna.numeroColumna+1
				nuevaColumna.borrable=true
				nuevaColumna.idTablero = tablero.id
				nuevaColumna.save(flush:true)
				
				incrementarNumeroColumna=true
			} else {
				if (incrementarNumeroColumna==true) {
					columna.numeroColumna++
					columna.save(flush:true)
				}
			}
		}
		
		// Se desplazan las tareas junto con sus columnas
		def tareasAMover = Tarea.findAllByIdTableroAndEstadoGreaterThan(tablero.id, columnaReferencia.numeroColumna)
		tareasAMover.each ( { tarea ->
			tarea.estado++
			tarea.save(flush:true)
		})
		
		redirect(controller:"kanban", action: "tablero")
	}
	
	def borraColumna = {
		def tablero = Tablero.get(session.tablero)
		def columnas = Columna.findAllByIdTablero(tablero.id, [sort:"numeroColumna", order:"asc"])
		def decrementarNumeroColumna=false
		def columnaABorrar = Columna.get(params.id)
		
		columnas.each ( { columna ->
			if (columna.id.equals(columnaABorrar.id)) {
				decrementarNumeroColumna=true
			} else {
				if (decrementarNumeroColumna==true) {
					columna.numeroColumna--
					columna.save(flush:true)
				}
			}
		})
		
		def tareasAMover = Tarea.findAllByIdTableroAndEstadoGreaterThan(tablero.id, columnaABorrar.numeroColumna)
		tareasAMover.each ( { tarea ->
			tarea.estado--
			tarea.save(flush:true)
		})
		
		columnaABorrar.delete(flush:true)
		
		redirect(controller:"kanban", action: "tablero")
	}
	
	def cambiaTituloColumna = {
		def columna = Columna.get(params.idColumna)
		if (columna==null){
			flash.message="${params}"
		}else{
			columna.titulo=params.nuevoTituloColumna
			columna.save(flush:true)
		}
		redirect(controller:"kanban", action:"tablero")
	}
}
