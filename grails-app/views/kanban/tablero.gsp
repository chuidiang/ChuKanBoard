<%@ page import="com.chuidiang.kanban.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body style="boder:solid 1px;">
       <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
       </g:if>
       <% def titulo = ["PILA","PREPARADA","EN PROGRESO", "HECHA"]
          [0,1,2,3].each { estado ->  %>
          <div style="float:left; width:20%; padding:10px; border-right:solid 1px; height:100%;">
             <h2 style="text-align:center; border-bottom:solid 1px;">${titulo[estado]}</h2>
             <g:each in="${tareaInstanceList}" status="i" var="tareaInstance">
                <g:if test="${tareaInstance.estado == estado}">
                   <div style="background-color:#ffffcc; padding:10px; position:relative;">
                      <g:if test="${(estado==0)||(estado==3)}">
                         <g:link style="position:absolute; right:10px;" action="borra" id="${tareaInstance.id}" onclick="return confirm('Mira que lo borramos...')"><image src="${resource(dir:'images',file:'action_stop.gif')}"/></g:link></br> 
                      </g:if>
                      ${fieldValue(bean: tareaInstance, field: "descripcion")}</br>
                      <g:if test="${estado>0}">
                         <g:link action="regresa" id="${tareaInstance.id}"><image src="${resource(dir:'images',file:'action_back.gif')}"/></g:link>
                      </g:if>
                      <g:else>
                         &nbsp;
                      </g:else>
                      <g:if test="${estado<3}">
                         <g:link style="position:absolute; right:10px;" action="progresa" id="${tareaInstance.id}"><image src="${resource(dir:'images',file:'action_forward.gif')}"/></g:link>
                      </g:if>
                   </div></br>
                </g:if>
             </g:each>
          <g:if test="${estado==0}">
             <div style="background-color:#ccffff; padding:10px;">
                <g:form method="post" >
                   <label for="descripcion">Descripci&oacute;n</label>
                   <g:textArea name="descripcion" value="${tareaInstance?.descripcion}" rows="5"  />
                   <g:actionSubmit value="Crear" action="save" />
                </g:form>
             </div>
          </g:if> 
          </div>
       <%}%>
    </body>
</html>