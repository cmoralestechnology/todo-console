package service;

import model.Tarea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import exception.TareaNoEncontradaException;

public class TareaService {

    private final List<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(String descripcion, LocalDate fechaVencimiento) {
        int nuevoId = generarNuevoId();
        Tarea tarea = new Tarea(nuevoId, descripcion, fechaVencimiento, false);
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

    public void eliminarPorId(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            throw new TareaNoEncontradaException(id);
        }
        tareas.remove(tarea);
    }

    public void  marcarComoCompletada(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            throw new TareaNoEncontradaException(id);
        }
        tarea.setCompletada(true);
    }

    public int totalTareas() {
        return tareas.size();
    }

    public List<Tarea> obtenerCompletadas() {
        return tareas.stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }

    public List<Tarea> obtenerPendientes() {
        return tareas.stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    public List<Tarea> ordenarPorFechaVencimiento() {
        return tareas.stream()
                .sorted(Comparator.comparing(Tarea::getFechaVencimiento))
                .collect(Collectors.toList());
    }

    public List<Tarea> buscarPorDescripcion(String palabraClave) {
        String criterio = palabraClave.toLowerCase();

        return tareas.stream()
                .filter(t -> t.getDescripcion().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    }

    public List<String> obtenerDescripciones() {
        return tareas.stream()
                .map(Tarea::getDescripcion)
                .collect(Collectors.toList());
    }

    public void cargarInicial(List<Tarea> tareasCargadas) {
        tareas.addAll(tareasCargadas);
    }

    private int generarNuevoId() {
        return tareas.stream()
                .mapToInt(Tarea::getId)
                .max()
                .orElse(0) + 1;

    }


}
