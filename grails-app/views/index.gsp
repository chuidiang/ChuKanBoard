<html>
    <head>
    	<link rel="stylesheet" type="text/css" href="css/main.css" />
        <title>ChuKanBan</title>
        <script type="text/javascript">
            function permuta(id1, id2) {
               if (document.getElementById(id1).style.visibility=='hidden') {
                  document.getElementById(id1).style.visibility='visible';
                  document.getElementById(id1).style.display='block';
                  document.getElementById(id2).style.visibility='hidden';
                  document.getElementById(id2).style.display='none';
               } else {
                  document.getElementById(id2).style.visibility='visible';
                  document.getElementById(id2).style.display='block';
                  document.getElementById(id1).style.visibility='hidden';
                  document.getElementById(id1).style.display='none';
               }
            }
        </script>
        
    </head>
    <body>
    <div style="tablero">
	   <h1>Tableros disponibles</h1> 
	   <ul>      
       <%  session.invalidate()
           def tableros = com.chuidiang.kanban.Tablero.list() %>
       <g:each in="${tableros}" var="tablero">
          <li>
             <div id="li_${tablero.id}">
             <g:link controller="kanban" action="tablero" id="${tablero.id}">${tablero.nombre}</g:link>
             <image onclick="permuta('li_${tablero.id}','form_${tablero.id}')" src="${resource(dir:'images',file:'page_edit.gif')}" style="border-style: none"/>
             <g:link controller="tablero" action="borra" id="${tablero.id}" 
             	onclick="return confirm('Mira que lo borramos...')">
             	<image src="${resource(dir:'images',file:'action_stop.gif')}" style="border-style: none"/>
             	</g:link>
             </div>
          <g:form id="form_${tablero.id}" style="display:none; visibility:hidden;" method="post" 
             url="[controller:'tablero',action:'cambiaNombreTablero']">
             <g:hiddenField name="idTablero" value="${tablero.id}"/>
             <g:textField name="nuevoNombreTablero" value="${tablero?.nombre}"/>
          </g:form>
          </li>
       </g:each>
       <li>
       <g:form controller="tablero" action="crear" method="post">
          Crear uno nuevo: <g:textField  name="nombre"/>
       </g:form>
       </li></ul>
       </div>
    </body>
</html>