import repository.TareaRepository;
import service.TareaService;
import thread.RecordatorioTareaRunnable;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Servicio que encapsula la lògica de negocio relacionada con las tareas
        TareaService service = new TareaService();

        // Repositorio responsable de la persistencia
        TareaRepository repository = new TareaRepository();

        // carga inicial de datos:
        try {
            service.cargarInicial(repository.cargar());
        } catch (IOException e) {
            System.out.println("No se pudieron cargar las tareas. Se iniciará vacío.");
        }
        // Hilo de recordatorio que se ejecuta en segundo plano para revisar tareas que vencen hoy
        Thread recordatorioThread = new Thread(new RecordatorioTareaRunnable(service));

        recordatorioThread.setDaemon(true);
        recordatorioThread.start();

        // scanner centralizado para toda la interacciòn con el usuario
        Scanner scanner = new Scanner(System.in);
        // controla el ciclo de vida del menú principal
        boolean running = true;

        while (running) {
            printMenu();
            System.out.println("Elige una opción: ");
            int option;

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            // captura entradas con la excepción evitando entradas que no sean númericas
            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Debes ingresar un número ");
                scanner.nextLine(); // descarta la entrada incorrecta
                System.out.println();
                continue;
            }

            switch (option) {
                // Crear Tarea
                case 1 -> {
                    try {
                        System.out.println("Ingrese descripción: ");
                        String descripcion = scanner.nextLine();

                        System.out.println("Ingrese fecha de vencimiento (yyyy-MM-dd): ");
                        String fechaInput = scanner.nextLine();
                        LocalDate fechaVencimiento = LocalDate.parse(fechaInput);

                        service.agregarTarea(descripcion, fechaVencimiento);
                        System.out.println("Tarea agregada correctamente");
                    } catch (DateTimeException e) {
                        System.out.println("Formato de fecha inválido. Use yyyy-MM-dd");
                    }
                }
                // Leer / listar tareas
                case 2 -> {
                    var tareas = service.obtenerTodas();

                    if (tareas.isEmpty()) {
                        System.out.println("No hay tareas registradas.");
                    } else {
                        System.out.println("Lista de tareas");
                        tareas.forEach(System.out::println);
                    }
                }
                // Actualizar tarea marcandola como completada
                case 3 -> {
                    try {
                        System.out.println("Ingrese ID de la tarea a completar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        service.marcarComoCompletada(id);
                        System.out.println("Tarea marcada como completada.");
                    } catch (InputMismatchException e) {
                        System.out.println("ID inválido, Debe ser un número.");
                        scanner.nextLine();
                    } catch (exception.TareaNoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                // Eliminar tarea
                case 4 -> {
                    try {
                        System.out.println("Ingrese ID de la tarea a eliminar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        var tareaEliminada = service.eliminarPorId(id);
                        System.out.println("Tarea eliminada: \"" + tareaEliminada.getDescripcion() + "\"");
                    } catch (InputMismatchException e) {
                        System.out.println("ID inválido. Debe ser un número");
                        scanner.nextLine();
                    } catch (exception.TareaNoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
                // Salida del sistema con persistencia
                case 5 -> {
                    try {
                        repository.guardar(service.obtenerTodas());
                    } catch (IOException e) {
                        System.out.println("Error al guardar las tareas.");
                    }
                    System.out.println("Saliendo...");
                    running = false;
                }
                default -> System.out.println("Opción inválida. Intenta nuevamente");
            }
        }

        // Libera el recurso Scanner
        scanner.close();
    }

    // Impresión del menú principal
    private static void printMenu() {
        System.out.println("GESTOR DE TAREAS");
        System.out.println("1.- Agregar Tarea");
        System.out.println("2.- Ver Tareas");
        System.out.println("3.- Marcar la tarea como completada");
        System.out.println("4.- Eliminar Tarea");
        System.out.println("5.- Salir");
    }
}
