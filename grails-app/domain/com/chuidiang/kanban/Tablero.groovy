package com.chuidiang.kanban

class Tablero {
   String nombre
   SortedSet columnas
   static hasMany = [ columnas : Columna, tareas:Tarea ]
   
}