package com.chuidiang.kanban

class KanbanController {    
    def tablero = {
        [tareaInstanceList: Tarea.list(sort:"fechaModificacion", order:"asc")]
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