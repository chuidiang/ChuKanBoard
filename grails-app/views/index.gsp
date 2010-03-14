<html>
    <head>
    	<link rel="stylesheet" type="text/css" href="css/main.css" />
        <title>ChuKanBan</title>
    </head>
    <body>
	   <p>Tableros disponibles</p>       
       <%  session.invalidate()
           def tableros = com.chuidiang.kanban.Tablero.list() %>
       <g:each in="${tableros}" var="tablero">
          <p><g:link controller="kanban" action="tablero" id="${tablero.id}">${tablero.nombre}</g:link></p>
       </g:each>
       <g:form controller="tablero" action="crear" method="post">
          Crear uno nuevo: <g:textField  name="nombre"/>
       </g:form>
    </body>
</html>