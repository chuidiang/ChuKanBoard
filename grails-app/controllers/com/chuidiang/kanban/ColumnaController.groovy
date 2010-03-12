package com.chuidiang.kanban

class ColumnaController {

    def nuevaColumna = {
       def columnas = Columna.list(sort:"numeroColumna", order:"asc")
       def incrementarNumeroColumna=false
       def columnaReferencia = Columna.get(params.id)
       
       columnas.each ( { columna ->
          if (columna.id.equals(columnaReferencia.id)) {
             Columna nuevaColumna= new Columna()
             nuevaColumna.titulo=""
             nuevaColumna.numeroColumna=columna.numeroColumna+1
             nuevaColumna.borrable=true
             nuevaColumna.save(flush:true)
             incrementarNumeroColumna=true
          } else {
             if (incrementarNumeroColumna==true) {
                columna.numeroColumna++
                columna.save(flush:true)
             }
          }
       })
       redirect(controller:"kanban", action: "tablero")
    }
    
    def borraColumna = {
       def columnas = Columna.list(sort:"numeroColumna", order:"asc")
       def decrementarNumeroColumna=false
       def columnaABorrar = Columna.get(params.id)
       
       columnas.each ( { columna ->
          if (columna.id.equals(columnaABorrar.id)) {
             columna.delete()
             decrementarNumeroColumna=true
          } else {
             if (decrementarNumeroColumna==true) {
                 columna.numeroColumna--
                 columna.save()
             }
          }
       })
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
