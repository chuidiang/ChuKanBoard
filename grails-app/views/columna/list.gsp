
<%@ page import="com.chuidiang.kanban.Columna" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'columna.label', default: 'Columna')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'columna.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'columna.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="numeroColumna" title="${message(code: 'columna.numeroColumna.label', default: 'Numero Columna')}" />
                        
                            <g:sortableColumn property="maximoTareas" title="${message(code: 'columna.maximoTareas.label', default: 'Maximo Tareas')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${columnaInstanceList}" status="i" var="columnaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${columnaInstance.id}">${fieldValue(bean: columnaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: columnaInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: columnaInstance, field: "numeroColumna")}</td>
                        
                            <td>${fieldValue(bean: columnaInstance, field: "maximoTareas")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${columnaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
