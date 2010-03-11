package com.chuidiang.kanban

class TareaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tareaInstanceList: Tarea.list(params), tareaInstanceTotal: Tarea.count()]
    }

    def create = {
        def tareaInstance = new Tarea()
        tareaInstance.properties = params
        return [tareaInstance: tareaInstance]
    }

    def save = {
        def tareaInstance = new Tarea(params)
        if (tareaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tarea.label', default: 'Tarea'), tareaInstance.id])}"
            redirect(action: "show", id: tareaInstance.id)
        }
        else {
            render(view: "create", model: [tareaInstance: tareaInstance])
        }
    }

    def show = {
        def tareaInstance = Tarea.get(params.id)
        if (!tareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tareaInstance: tareaInstance]
        }
    }

    def edit = {
        def tareaInstance = Tarea.get(params.id)
        if (!tareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tareaInstance: tareaInstance]
        }
    }

    def update = {
        def tareaInstance = Tarea.get(params.id)
        if (tareaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tareaInstance.version > version) {
                    
                    tareaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tarea.label', default: 'Tarea')] as Object[], "Another user has updated this Tarea while you were editing")
                    render(view: "edit", model: [tareaInstance: tareaInstance])
                    return
                }
            }
            tareaInstance.properties = params
            if (!tareaInstance.hasErrors() && tareaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tarea.label', default: 'Tarea'), tareaInstance.id])}"
                redirect(action: "show", id: tareaInstance.id)
            }
            else {
                render(view: "edit", model: [tareaInstance: tareaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tareaInstance = Tarea.get(params.id)
        if (tareaInstance) {
            try {
                tareaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
    }
}
