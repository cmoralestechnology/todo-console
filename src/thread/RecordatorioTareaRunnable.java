package thread;

import model.Tarea;
import service.TareaService;

import java.time.LocalDate;
import java.util.List;

import java.util.HashSet;
import java.util.Set;

/*
* Ejecuta recordatorio automático en segundos para tareas pendientes
*/
public class RecordatorioTareaRunnable implements Runnable {

    // Accede a servicio para ver el estado de las tareas
    private final TareaService service;
    // evita mostrar notificaciones de tareas que ya se mostraron
    private final Set<Integer> tareasNotificadas = new HashSet<>();

    public RecordatorioTareaRunnable(TareaService service) {
        this.service = service;
    }
    // Se mantiene activo mientras la app se este ejecutando
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30_000);

                LocalDate hoy = LocalDate.now();
                List<Tarea> tareas = service.obtenerTodas();

                tareas.stream()
                        .filter(t -> !t.isCompletada())
                        // filtra solo las tareas que vencen hoy
                        .filter(t -> hoy.equals(t.getFechaVencimiento()))
                        // notifica una vez la tarea
                        .filter(t -> !tareasNotificadas.contains(t.getId()))
                        .forEach(t -> {
                            System.out.println("Recordatorio: La tarea \"" +
                                    t.getDescripcion() + "\" vence HOY.");
                            tareasNotificadas.add(t.getId());
                        });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }
    }
}
