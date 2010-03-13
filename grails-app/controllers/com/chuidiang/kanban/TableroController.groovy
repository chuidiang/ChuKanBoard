package com.chuidiang.kanban

class TableroController {
   
   def crear = {
      def tablero = new Tablero()
      tablero.nombre = params.nombre
      tablero.save(flush:true)
      redirect(uri:"/")
   }
}