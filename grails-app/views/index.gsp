<html>
    <head>
    	<link rel="stylesheet" type="text/css" href="css/main.css" />
        <title>ChuKanBan</title>
    </head>
    <body>
    <div style="tablero">
	   <h1>Tableros disponibles</h1> 
	   <ul>      
       <%  session.invalidate()
           def tableros = com.chuidiang.kanban.Tablero.list() %>
       <g:each in="${tableros}" var="tablero">
          <li><g:link controller="kanban" action="tablero" id="${tablero.id}">${tablero.nombre}</g:link></li>
       </g:each>
       <li>
       <g:form controller="tablero" action="crear" method="post">
          Crear uno nuevo: <g:textField  name="nombre"/>
       </g:form>
       </li></ul>
       </div>
    </body>
</html>