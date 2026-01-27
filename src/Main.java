import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Punto de entrada de la app

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.println("Elige una opción: ");

            String input = scanner.nextLine().trim();
            int option;

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Debes ingresar un número ");
                System.out.println();
                continue;
            }

            switch (option) {
                case 1 -> System.out.println("Agregar Tarea (pendiente de implementar).");
                case 2 -> System.out.println("Ver Tareas (pendiente de implementar).");
                case 3 -> {
                    System.out.println("Saliendo...");
                    running = false;
                }
                default -> System.out.println("Opción inválida. Intenta nuevamente");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("GESTOR DE TAREAS");
        System.out.println("1.- Agregar Tarea");
        System.out.println("2.- Ver Tareas");
        System.out.println("3.- Salir");
    }
}
