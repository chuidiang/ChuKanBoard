package com.chuidiang.kanban;




class EstadisticasController {
	def defaultAction="estadisticas"
	
	def estadisticas= {
		def tareas = Tarea.findAllByIdTablero(session.tablero)
		def tablero = Tablero.findById(session.tablero)
		def fechasComienzo = []
		def fechasFin=[]
		tareas.each ({ tarea -> 
			if (null!=tarea.fechaComienzo) {
				fechasComienzo.add(tarea.fechaComienzo)
			}
			if (null!=tarea.fechaFinalizacion) {
				fechasFin.add(tarea.fechaFinalizacion)
			}
		})
		
		fechasComienzo.sort()
		fechasFin.sort()
		
		def fechas =[] 
		fechasComienzo.each() { fecha ->
			fechas.add(fecha)
		}
		fechasFin.each() { fecha->
			fechas.add (fecha)
		}
		fechas.sort()
		
		def tareasEnMarcha = []
		def tareasAcabadas = []
		int totalTareasEmpezadas=0
		int totalTareasAcabadas=0
		
		int iComienzo=0;
		int iFin=0;
		
		for (int i=0;i<fechas.size();i++){
			if ((iComienzo<fechasComienzo.size())&&(fechas[i].equals(fechasComienzo[iComienzo]))) {
				totalTareasEmpezadas++
				iComienzo++
			} 
			if ((iFin<fechasFin.size())&&(fechas[i].equals(fechasFin[iFin]))){
				totalTareasAcabadas++
				iFin++
			}
			tareasEnMarcha.add(totalTareasEmpezadas)
			tareasAcabadas.add(totalTareasAcabadas)
		}
		
		if (fechas.size()==0){
			fechas.add( new Date())
		}
		def inicioYFin = [fechas[0],fechas[fechas.size()-1]]
		def totalTareas = Tarea.countByIdTablero(session.tablero) 
		[nombre:tablero.nombre, fechas:inicioYFin, total:totalTareas, empezadas:totalTareasEmpezadas, acabadas:totalTareasAcabadas]
	}
	
	def graph = {
		def tareas = Tarea.findAllByIdTablero(session.tablero)
		def fechasComienzo = []
		def fechasFin=[]
		tareas.each ({ tarea -> 
			if (null!=tarea.fechaComienzo) {
				fechasComienzo.add(tarea.fechaComienzo)
			}
			if (null!=tarea.fechaFinalizacion) {
				fechasFin.add(tarea.fechaFinalizacion)
			}
		})
		
		fechasComienzo.sort()
		fechasFin.sort()
		
		def fechas =[] 
		fechasComienzo.each() { fecha ->
			fechas.add(fecha)
		}
		fechasFin.each() { fecha->
			fechas.add (fecha)
		}
		fechas.sort()
		
		def tareasEnMarcha = []
		def tareasAcabadas = []
		int totalTareasEmpezadas=0
		int totalTareasAcabadas=0
		
		int iComienzo=0;
		int iFin=0;
		
		for (int i=0;i<fechas.size();i++){
			if ((iComienzo<fechasComienzo.size())&&(fechas[i].equals(fechasComienzo[iComienzo]))) {
				totalTareasEmpezadas++
				iComienzo++
			} 
			if ((iFin<fechasFin.size())&&(fechas[i].equals(fechasFin[iFin]))){
				totalTareasAcabadas++
				iFin++
			}
			tareasEnMarcha.add(totalTareasEmpezadas)
			tareasAcabadas.add(totalTareasAcabadas)
		}
		
		double xInicio = fechas[0].getTime()
		double xFinal= fechas[fechas.size()-1].getTime()
		def x = []
		def puntos1=[10,510]
		def puntos2=[10,510]
		for (int i=0;i<fechas.size();i++) {
			x[i] = (fechas[i].getTime() - xInicio)*500.0/(xFinal-xInicio) + 10
			puntos1.add(x[i])
			puntos1.add(510-tareasEnMarcha[i]*500.0/totalTareasEmpezadas)
			puntos2.add(x[i])
			puntos2.add(510-tareasAcabadas[i]*500.0/totalTareasEmpezadas)
		}
		puntos1.add(510)
		puntos1.add(510)
		puntos2.add(510)
		puntos2.add(510)
		
		renderImage(width: 520, height: 540) {
			rect( x: 9, y: 9, width: 502, height: 502, borderColor: 'black', borderWidth: 1, fill:'#eeeeee' )
			polygon( points: puntos1, borderColor: 'black', borderWidth: 1, fill: '#dddddd' )
			polygon( points: puntos2, borderColor: 'black', borderWidth: 1, fill: '#cccccc' )
			for (int i=0;i<puntos1.size(); i+=2) {
				circle( cx: puntos1[i], cy: puntos1[i+1], radius: 2, borderColor: 'black', borderWidth:1, fill: 'black' )
			}
			for (int i=0;i<puntos2.size(); i+=2) {
				circle( cx: puntos2[i], cy: puntos2[i+1], radius: 2, borderColor: 'black', borderWidth:1, fill: 'black' )
			}
			for (int i=0;i<x.size();i++){
				line (x1:x[i], y1:10, x2:x[i], y2:510, borderWidth:1, fill:'gray') 
			}
			
			font( face: "Arial", size: 12 )
			text( text: String.format('%td %<tb %<tY', fechas[0]), x:10, y:515, borderColor: 'black' ) 
			text( text: String.format('%td %<tb %<tY', fechas[fechas.size()-1]), x:440, y:515, borderColor: 'black' ) 
		}
	}
}