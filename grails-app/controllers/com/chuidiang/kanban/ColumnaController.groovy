package com.chuidiang.kanban

class ColumnaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [columnaInstanceList: Columna.list(params), columnaInstanceTotal: Columna.count()]
    }

    def create = {
        def columnaInstance = new Columna()
        columnaInstance.properties = params
        return [columnaInstance: columnaInstance]
    }

    def save = {
        def columnaInstance = new Columna(params)
        if (columnaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'columna.label', default: 'Columna'), columnaInstance.id])}"
            redirect(action: "show", id: columnaInstance.id)
        }
        else {
            render(view: "create", model: [columnaInstance: columnaInstance])
        }
    }

    def show = {
        def columnaInstance = Columna.get(params.id)
        if (!columnaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
            redirect(action: "list")
        }
        else {
            [columnaInstance: columnaInstance]
        }
    }

    def edit = {
        def columnaInstance = Columna.get(params.id)
        if (!columnaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [columnaInstance: columnaInstance]
        }
    }

    def update = {
        def columnaInstance = Columna.get(params.id)
        if (columnaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (columnaInstance.version > version) {
                    
                    columnaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'columna.label', default: 'Columna')] as Object[], "Another user has updated this Columna while you were editing")
                    render(view: "edit", model: [columnaInstance: columnaInstance])
                    return
                }
            }
            columnaInstance.properties = params
            if (!columnaInstance.hasErrors() && columnaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'columna.label', default: 'Columna'), columnaInstance.id])}"
                redirect(action: "show", id: columnaInstance.id)
            }
            else {
                render(view: "edit", model: [columnaInstance: columnaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def columnaInstance = Columna.get(params.id)
        if (columnaInstance) {
            try {
                columnaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'columna.label', default: 'Columna'), params.id])}"
            redirect(action: "list")
        }
    }
}
