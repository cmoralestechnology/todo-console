package service;

import model.Tarea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import exception.TareaNoEncontradaException;
/*
- Gestiona el ciclo de vida de una tarea en memoria
- Aplica las reglas de negocio
- Provee las operaciones CRUD
 */
public class TareaService {
    // Colecciòn en memoria que representa el estado actual de las tareas
    private final List<Tarea> tareas = new ArrayList<>();
    /*
    - Crea y agrega una nueva tarea al sistema
    - Una tarea nueva siempre inicia como NO completada
    */
    public void agregarTarea(String descripcion, LocalDate fechaVencimiento) {
        int nuevoId = generarNuevoId();
        Tarea tarea = new Tarea(nuevoId, descripcion, fechaVencimiento, false);
        tareas.add(tarea);
    }
    /*
    * Retorna todas las tareas
    * */
    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    // Buscar tareas por ID
    public Tarea buscarPorId(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }
    // No se puede eliminar una tarea que no existe
    public Tarea eliminarPorId(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            throw new TareaNoEncontradaException(id);
        }
        tareas.remove(tarea);
        return tarea;
    }
    /*
    * Sólo se oueden completar tareas existentes
    */
    public void  marcarComoCompletada(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) {
            throw new TareaNoEncontradaException(id);
        }
        tarea.setCompletada(true);
    }

    // Retorna la cantidad total de tareas registradas
    public int totalTareas() {
        return tareas.size();
    }

    // Obtiene solo las tareas completadas
    public List<Tarea> obtenerCompletadas() {
        return tareas.stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }
    // Solo obtiene a las pendientes
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

    //Busca tareas que tengan una palabra clave
    public List<Tarea> buscarPorDescripcion(String palabraClave) {
        String criterio = palabraClave.toLowerCase();

        return tareas.stream()
                .filter(t -> t.getDescripcion().toLowerCase().contains(criterio))
                .collect(Collectors.toList());
    }
    // Retorna solo las descripciones de las tareas
    public List<String> obtenerDescripciones() {
        return tareas.stream()
                .map(Tarea::getDescripcion)
                .collect(Collectors.toList());
    }

    public void cargarInicial(List<Tarea> tareasCargadas) {
        tareas.addAll(tareasCargadas);
    }
    // Se obtiene el ID máximo actual y se incrementa en uno
    private int generarNuevoId() {
        return tareas.stream()
                .mapToInt(Tarea::getId)
                .max()
                .orElse(0) + 1;

    }


}
