<html>
	<head>
	<link rel="stylesheet" type="text/css" href="/ChuKanBoard/css/main.css" />
	</head>
	<body>
		<h1>Estadisticas de <a href="${resource()}/kanban/tablero">${nombre}<a></h1>
		<ul>
		<li>Total de tareas: ${total}</li>
		<li>Empezadas: ${empezadas}</li>
		<li>Acabadas: ${acabadas}</li>
		</ul>
		<img src="${createLink(controller:'estadisticas',action:'graph')}"/>
	</body>
</html>