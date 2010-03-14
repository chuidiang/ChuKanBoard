package com.chuidiang.kanban

class ColumnaController {
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[columnaInstanceList: Columna.list(params), columnaInstanceTotal: Columna.count()]
	}
	def nuevaColumna = {
		def tablero = Tablero.get(session.tablero)
		def columnas = tablero.columnas
		def incrementarNumeroColumna=false
		def columnaReferencia = Columna.get(params.id)
		def Columna nuevaColumna
		
		// Se desplazan las columnas posteriores
		Iterator iterador = columnas.iterator()
		while (iterador.hasNext()) {
			def columna = iterador.next()
			if (columna.id.equals(columnaReferencia.id)) {
				nuevaColumna= new Columna()
				nuevaColumna.titulo=""
				nuevaColumna.numeroColumna=columna.numeroColumna+1
				nuevaColumna.borrable=true
				
				incrementarNumeroColumna=true
			} else {
				if (incrementarNumeroColumna==true) {
					columna.numeroColumna++
				}
			}
		}
		
		// Se desplazan las tareas junto con sus columnas
		def tareasAMover = Tarea.findAllByTableroAndEstadoGreaterThan(tablero, columnaReferencia.numeroColumna)
		tareasAMover.each ( { tarea ->
			tarea.estado++
			tarea.save()
		})
		
		tablero.addToColumnas(nuevaColumna)
		tablero.save()
		
		redirect(controller:"kanban", action: "tablero")
	}
	
	def borraColumna = {
		def tablero = Tablero.get(session.tablero)
		def columnas = tablero.columnas
		def decrementarNumeroColumna=false
		def columnaABorrar = Columna.get(params.id)
		
		columnas.each ( { columna ->
			if (columna.id.equals(columnaABorrar.id)) {
				decrementarNumeroColumna=true
			} else {
				if (decrementarNumeroColumna==true) {
					columna.numeroColumna--
					columna.save()
				}
			}
		})
		
		def tareasAMover = Tarea.findAllByTableroAndEstadoGreaterThan(tablero, columnaABorrar.numeroColumna)
		tareasAMover.each ( { tarea ->
			tarea.estado--
			tarea.save()
		})
		
		tablero.removeFromColumnas(columnaABorrar)
		columnaABorrar.delete(flush:true)
		tablero.save()
		
		redirect(controller:"kanban", action: "tablero")
	}
	
	def cambiaTituloColumna = {
		def columna = Columna.get(params.idColumna)
		if (columna==null){
			flash.message="${params}"
		}else{
			columna.titulo=params.nuevoTituloColumna
			columna.save()
		}
		redirect(controller:"kanban", action:"tablero")
	}
}
