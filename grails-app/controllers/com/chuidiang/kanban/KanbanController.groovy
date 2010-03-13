package com.chuidiang.kanban

class KanbanController {  
    def defaultAction="tablero"
      
    def tablero = {
       
       def tablero
       if (params.id != null) {
          tablero = Tablero.get(params.id)
          session.tablero=tablero.id
       } else {
          tablero = Tablero.get(session.tablero)
       }
       
       def columnas = tablero.columnas
       
       // Se crean dos columnas por defecto si no hay ninguna
       // La de pila de tareas y la de tareas hechas
       if (columnas.size()==0){
          
          Columna columnaInicial=new Columna()
          columnaInicial.numeroColumna=0
          columnaInicial.titulo="PILA"
          columnaInicial.borrable=false
          tablero.addToColumnas(columnaInicial)
          columnas.add(columnaInicial)
          
          Columna columnaFinal=new Columna()
          columnaFinal.numeroColumna=1
          columnaFinal.titulo="HECHO"
          columnaFinal.borrable=false
          tablero.addToColumnas(columnaFinal)
          columnas.add(columnaFinal)
          
          tablero.save(flush:true)
       }
       
       def todasLasTareas = []
       columnas.each ({ columna ->
          def tareas = Tarea.findAllByEstadoAndTablero(columna.numeroColumna,tablero)
          todasLasTareas.add(tareas)
       })
       [listaColumnas:columnas, listaTodasLasTareas:todasLasTareas]
    }
    
    def save = {
        def tareaInstance = new Tarea(params)
        tareaInstance.estado=0;
        tareaInstance.fechaModificacion=new Date()
        
        def elTableroActual = Tablero.get(session.tablero)
        elTableroActual.addToTareas(tareaInstance)
        elTableroActual.save(flush:true)
        
        redirect(action: "tablero")
    }
    
    def progresa = {
    	def tareaInstance = Tarea.get(params.id)
    	if (tareaInstance) {
    	   tablero = Tablero.get(session.tablero)
    	   tareaInstance.estado+=1
    	   tareaInstance.fechaModificacion=new Date()
    	   tareaInstance.save()
    	}
    	redirect(action: "tablero")
    }
    
    def regresa = {
    	def tareaInstance = Tarea.get(params.id)
    	if (tareaInstance) {
    	   tareaInstance.estado-=1
    	   tareaInstance.fechaModificacion=new Date()
    	   tareaInstance.save()
    	}
    	redirect(action: "tablero")
    }
    
    def borra = {
       def tareaInstance = Tarea.get(params.id)
       def tablero = Tablero.get(session.tablero)
       tablero.deleteFromTareas(tareaInstance)
       tablero.delete(flush:true)
       redirect(action: "tablero")
    }    
}