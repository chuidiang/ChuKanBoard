
<%@ page import="com.chuidiang.kanban.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'tarea.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'tarea.estado.label', default: 'Estado')}" />
                        
                            <g:sortableColumn property="fechaComienzo" title="${message(code: 'tarea.fechaComienzo.label', default: 'Fecha Comienzo')}" />
                        
                            <g:sortableColumn property="descripcion" title="${message(code: 'tarea.descripcion.label', default: 'Descripcion')}" />
                        
                            <g:sortableColumn property="personaAsignada" title="${message(code: 'tarea.personaAsignada.label', default: 'Persona Asignada')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tareaInstanceList}" status="i" var="tareaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tareaInstance.id}">${fieldValue(bean: tareaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "estado")}</td>
                        
                            <td><g:formatDate date="${tareaInstance.fechaComienzo}" /></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "descripcion")}</td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "personaAsignada")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tareaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
