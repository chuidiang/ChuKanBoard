
<%@ page import="com.chuidiang.kanban.Columna" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'columna.label', default: 'Columna')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${columnaInstance}">
            <div class="errors">
                <g:renderErrors bean="${columnaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="columna.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: columnaInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${columnaInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeroColumna"><g:message code="columna.numeroColumna.label" default="Numero Columna" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: columnaInstance, field: 'numeroColumna', 'errors')}">
                                    <g:textField name="numeroColumna" value="${fieldValue(bean: columnaInstance, field: 'numeroColumna')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maximoTareas"><g:message code="columna.maximoTareas.label" default="Maximo Tareas" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: columnaInstance, field: 'maximoTareas', 'errors')}">
                                    <g:textField name="maximoTareas" value="${fieldValue(bean: columnaInstance, field: 'maximoTareas')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
