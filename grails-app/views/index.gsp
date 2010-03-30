<html>
<head>
<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
<title>ChuKanBan</title>
<script src="${resource(dir:'js',file:'application.js')}">
        </script>
</head>
<body>
<div style="">
<h1>Tableros disponibles</h1>
<ul>
	<%  session.invalidate()
           def tableros = com.chuidiang.kanban.Tablero.list() %>
	<g:each in="${tableros}" var="tablero">
		<li>
		<div id="li_${tablero.id}"><g:link controller="kanban"
			action="tablero" id="${tablero.id}">
			${tablero.nombre}
		</g:link> <image onclick="permuta('li_${tablero.id}','form_${tablero.id}')"
			src="${resource(dir:'images',file:'page_edit.gif')}"
			style="border-style: none" /> <g:link controller="tablero"
			action="borra" id="${tablero.id}"
			onclick="return confirm('Mira que lo borramos...')">
			<image src="${resource(dir:'images',file:'action_stop.gif')}"
				style="border-style: none" />
		</g:link></div>
		<g:form id="form_${tablero.id}"
			style="display:none; visibility:hidden;" method="post"
			url="[controller:'tablero',action:'cambiaNombreTablero']">
			<g:hiddenField name="idTablero" value="${tablero.id}" />
			<g:textField name="nuevoNombreTablero" value="${tablero?.nombre}" />
		</g:form></li>
	</g:each>
	<li><g:form controller="tablero" action="crear" method="post">
          Crear uno nuevo: <g:textField name="nombre" />
	</g:form></li>
</ul>
</div>
</body>
</html>