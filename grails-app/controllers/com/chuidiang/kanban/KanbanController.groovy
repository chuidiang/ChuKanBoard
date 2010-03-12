package com.chuidiang.kanban

class KanbanController {  
    def defaultAction="tablero"
      
    def tablero = {
       def columnas = Columna.list(sort:"numeroColumna", order:"asc")
       def todasLasTareas=[]
       
       // Se crean dos columnas por defecto si no hay ninguna
       // La de pila de tareas y la de tareas hechas
       if (columnas.size()==0){
          Columna columnaInicial=new Columna()
          columnaInicial.numeroColumna=0
          columnaInicial.titulo="PILA"
          columnaInicial.save(flush:true)
          columnaInicial.borrable=false
          columnas.add(columnaInicial)
          
          Columna columnaFinal=new Columna()
          columnaFinal.numeroColumna=1
          columnaFinal.titulo="HECHO"
          columnaFinal.borrable=false
          columnaFinal.save(flush:true)
          columnas.add(columnaFinal)
       }
       
       columnas.each ({ columna ->
          def tareas = Tarea.findAllByEstado(columna.numeroColumna)
          todasLasTareas.add(tareas)
       })
       [listaColumnas:columnas, listaTodasLasTareas:todasLasTareas]
    }
    
    def save = {
        def tareaInstance = new Tarea(params)
        tareaInstance.estado=0;
        tareaInstance.fechaModificacion=new Date()
        if (!tareaInstance.save(flush: true)) {
            flash.message = "Pues no se puede crear la tarea"
        }
        redirect(action: "tablero")
    }
    
    def progresa = {
    	def tareaInstance = Tarea.get(params.id)
    	if (tareaInstance) {
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
       tareaInstance.delete(flush:true)
       redirect(action: "tablero")
    }    
}