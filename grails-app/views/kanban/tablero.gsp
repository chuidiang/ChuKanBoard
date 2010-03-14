<%@ page import="com.chuidiang.kanban.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <tooltip:resources/>
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
        <title>ChuKanBoard</title>
        <link rel="stylesheet" type="text/css" href="/ChuKanBoard/css/main.css" />
    </head>
    <body style="margin:0px; border:0px;">
    <div style="width:${listaColumnas.size()*222}px; margin:auto; padding:0px; ">
       <% def tablero = com.chuidiang.kanban.Tablero.get(session.tablero) %>
       <a style="float:right" href="http://www.chuidiang.com">http://www.chuidiang.com</a>
       <p><a href="${resource()}">Listado de tableros</a></p>
       <h1>${tablero.nombre}</h1>
       
       <!-- posible mensaje de error -->
       <g:if test="${flash.message}">
          <div style="color:#ff0000;">${flash.message}</div>
       </g:if>
       
          <!-- para cada columna -->
          <g:each in="${listaColumnas}" status="j" var="columna">
          <div class="columna">
          <div>
             <!-- boton de crear nueva columna -->
             <g:if test="${(j<(listaColumnas.size()-1))}">
                <tooltip:tip value="Crear nueva columna">
                <g:link style="float:right; clear:right;" controller="columna" action="nuevaColumna" id="${columna.id}">
                   <image src="${resource(dir:'images',file:'page_extension.gif')}" style="border-style: none"/>
                </g:link>
                </tooltip:tip>
             </g:if>
             <!-- boton de borrar columna -->
             <g:if test="${columna.borrable && (listaTodasLasTareas[j].size()==0)}">
                <tooltip:tip value="Borrar la columna">
                <g:link style="float:left;"
                        controller="columna"  
                        action="borraColumna" 
                        id="${columna.id}" onclick="return confirm('Mira que lo borramos...')">
                   <image src="${resource(dir:'images',file:'action_stop.gif')}" style="border-style: none"/>
                </g:link>
                </tooltip:tip>             
             </g:if>
             </div>
             <!-- titulo de la columna -->
             <h2>
                <g:if test="${columna.titulo.equals('')}">
                   <g:form style="margin-bottom:0px; padding-bottom:0px;" method="post" controller="columna" action="cambiaTituloColumna">
                      <g:hiddenField name="idColumna" value="${columna.id}"/>
                      <g:textField name="nuevoTituloColumna" value="${columna?.titulo}"/>
                   </g:form>
                </g:if>
                <g:else>
                   <g:link controller="columna" action="cambiaTituloColumna" params="[idColumna:columna.id,nuevoTituloColumna:'']">
                   ${columna.titulo}
                   </g:link>
                </g:else>
             </h2>
             
             <!-- lista de tareas -->
             <g:set value="${listaTodasLasTareas}" var="listaTareasColumna"/>
             <g:each in="${listaTareasColumna[j]}" var="tareaInstance">
             
                   <!-- la tarea -->
                   <div class="postit">
                   <div>
                      
                      <!-- boton de borrar si esta en la primera columna -->
                      <g:if test="${(columna.numeroColumna==0)}">
                         <g:link style="position:absolute; right:10px;" 
                                 action="borra" 
                                 id="${tareaInstance.id}" 
                                 onclick="return confirm('Mira que lo borramos...')">
                             <image src="${resource(dir:'images',file:'action_stop.gif')}" style="border-style: none"/>
                          </g:link></br> 
                      </g:if>
                      
                      <!-- el texto de la tarea -->
                      ${tareaInstance.descripcion}</br></br>
                   </div>
                   
                   <div>
                      <!-- boton de ir a la izquierda -->
                      <g:if test="${columna.numeroColumna>0}">
                         <g:link action="regresa" id="${tareaInstance.id}">
                            <image src="${resource(dir:'images',file:'action_back.gif')}" style="border-style: none"/>
                         </g:link>
                      </g:if>
                      <g:else>
                         &nbsp;
                      </g:else>
                      
                      <!-- boton de ir a la derecha -->
                      <g:link style="position:absolute; right:10px;" action="progresa" id="${tareaInstance.id}">
                         <image src="${resource(dir:'images',file:'action_forward.gif')}" style="border-style: none"/>
                      </g:link>
                   </div>
                   </div>
                   </br>
             </g:each>
             
             <!-- caja de texto para crear nueva tarea -->
             <g:if test="${columna.numeroColumna==0}">
                <div class="postit" >
                   <g:form method="post" >
                       <label for="descripcion">Descripci&oacute;n</label>
                       <g:textArea name="descripcion" value="${tareaInstance?.descripcion}" rows="5" wrap="hard" />
                      <g:actionSubmit value="Crear" action="save" />
                   </g:form>
                </div>
             </g:if>
             
          </div>
       </g:each>
       </div>
    </body>
</html>