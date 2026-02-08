import repository.TareaRepository;
import service.TareaService;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TareaService service = new TareaService();
        TareaRepository repository = new TareaRepository();

        // carga inicial
        try {
            service.cargarInicial(repository.cargar());
        } catch (IOException e) {
            System.out.println("No se pudieron cargar las tareas. Se iniciará vacío.");
        }

        // Punto de entrada de la app

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.println("Elige una opción: ");
            int option;

            try {
                option = scanner.nextInt();
                scanner.nextLine();

            } catch (InputMismatchException ex) {
                System.out.println("Entrada inválida. Debes ingresar un número ");
                scanner.nextLine();
                System.out.println();
                continue;
            }

            switch (option) {
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
                case 2 -> {
                    var tareas = service.obtenerTodas();

                    if (tareas.isEmpty()) {
                        System.out.println("No hay tareas registradas.");
                    } else {
                        System.out.println("Lista de tareas");
                        tareas.forEach(System.out::println);
                    }
                }
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
                case 4 -> {
                    try {
                        System.out.println("Ingrese ID de la tarea a eliminar: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        service.eliminarPorId(id);
                        System.out.println("Tarea eliminada correctamente.");
                    } catch (InputMismatchException e) {
                        System.out.println("ID inválido. Debe ser un número");
                        scanner.nextLine();
                    } catch (exception.TareaNoEncontradaException e) {
                        System.out.println(e.getMessage());
                    }
                }
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

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("GESTOR DE TAREAS");
        System.out.println("1.- Agregar Tarea");
        System.out.println("2.- Ver Tareas");
        System.out.println("3.- Marcar la tarea como completada");
        System.out.println("4.- Eliminar Tarea");
        System.out.println("5.- Salir");
    }
}
