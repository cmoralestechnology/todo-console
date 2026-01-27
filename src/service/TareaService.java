package service;

import model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareaService {

    private final List<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    public Tarea buscarPorId(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    public boolean eliminarPorId(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea != null) {
            tareas.remove(tarea);
            return true;
        }
        return false;
    }

    public boolean marcarComoCompletada(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea != null) {
            tarea.setCompletada(true);
            return true;
        }
        return false;
    }

    public int totalTareas() {
        return tareas.size();
    }
}
