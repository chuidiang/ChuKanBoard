<%@ page import="com.chuidiang.kanban.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <tooltip:resources/>
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body style="font-family:Arial,Helvetica,sans-serif; font-size:small; min-width:${listaColumnas.size()*230};">
       <p style="float:right"><a href="http://www.chuidiang.com">http://www.chuidiang.com</a></p>
       <h1>ChuKanBoard</h1>
       <g:if test="${flash.message}">
       <div style="color:#ff0000;">${flash.message}</div>
       </g:if>
          <!-- para cada columna -->
          <g:each in="${listaColumnas}" status="j" var="columna">
          <div style="float:left; width:200px; padding:10px; border-right:solid 1px; height:100%;">
             <g:if test="${(j<(listaColumnas.size()-1))}">
                <tooltip:tip value="Crear nueva columna">
                <g:link style="float:right; clear:right;" action="nuevaColumna" id="${columna.id}"><image src="${resource(dir:'images',file:'page_extension.gif')}"/></g:link>
                </tooltip:tip>
             </g:if>
             <g:if test="${columna.borrable && (listaTodasLasTareas[j].size()==0)}">
                <tooltip:tip value="Borrar la columna">
                <g:link style="float:left;" 
                        action="borraColumna" 
                        id="${columna.id}" onclick="return confirm('Mira que lo borramos...')">
                   <image src="${resource(dir:'images',file:'action_stop.gif')}"/>
                </g:link>
                </tooltip:tip>             
             </g:if>
             <h2 style="text-align:center; border-bottom:solid 1px;">
                <g:if test="${columna.titulo.equals('')}">
                   <g:form style="margin-bottom:0px; padding-bottom:0px;" method="post" action="cambiaTituloColumna">
                      <g:hiddenField name="idColumna" value="${columna.id}"/>
                      <g:textField name="nuevoTituloColumna" value="${columna?.titulo}"/>
                   </g:form>
                </g:if>
                <g:else>
                   ${columna.titulo}
                </g:else>
             </h2>
             <g:set value="${listaTodasLasTareas}" var="listaTareasColumna"/>
             <g:each in="${listaTareasColumna[j]}" var="tareaInstance">
                   <div style="background-color:#ffffcc; padding:10px; position:relative;">
                      <g:if test="${(columna.numeroColumna==0)}">
                         <g:link style="position:absolute; right:10px;" 
                                 action="borra" 
                                 id="${tareaInstance.id}" 
                                 onclick="return confirm('Mira que lo borramos...')">
                             <image src="${resource(dir:'images',file:'action_stop.gif')}"/>
                          </g:link></br> 
                      </g:if>
                      ${tareaInstance.descripcion}</br>
                      
                      
                      <g:if test="${columna.numeroColumna>0}">
                         <g:link action="regresa" id="${tareaInstance.id}"><image src="${resource(dir:'images',file:'action_back.gif')}"/></g:link>
                      </g:if>
                      <g:else>
                         &nbsp;
                      </g:else>
                         <g:link style="position:absolute; right:10px;" action="progresa" id="${tareaInstance.id}"><image src="${resource(dir:'images',file:'action_forward.gif')}"/></g:link>
                   </div></br>
             </g:each>
             <g:if test="${columna.numeroColumna==0}">
                <div style="background-color:#ffffcc; padding:10px;">
                   <g:form method="post" >
                       <label for="descripcion">Descripci&oacute;n</label>
                       <g:textArea name="descripcion" value="${tareaInstance?.descripcion}" rows="5"  />
                      <g:actionSubmit value="Crear" action="save" />
                   </g:form>
                </div>
             </g:if>
          </div>
       </g:each>
    </body>
</html>