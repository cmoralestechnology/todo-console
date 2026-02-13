package repository;

import model.Tarea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* Persistencia de dstos
 */
public class TareaRepository {

    private static final String ARCHIVO_TAREAS = "tareas.csv";
    // Path reutilizable de la ubicación del archivo
    private static final Path PATH_TAREAS = Paths.get(ARCHIVO_TAREAS);
    // Carga las tareas desde el csv
    public List<Tarea> cargar() throws IOException {
        List<Tarea> tareas = new ArrayList<>();
        // si el archivo no existe, se retorna una lista vacía
        if (!Files.exists(PATH_TAREAS)) {
            return tareas;
        }
        // lee texto del archivo linea a linea
        try (BufferedReader reader = Files.newBufferedReader(PATH_TAREAS)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                // La linea debe tener 4 campos
                if (partes.length != 4) {
                    continue;
                }

                tareas.add(new Tarea(
                        Integer.parseInt(partes[0]), // id
                        partes[1], // descripcion
                        LocalDate.parse(partes[2]), // fecha vencimiento
                        Boolean.parseBoolean(partes[3]) // estado
                ));
            }
        }

        return tareas;
    }

    public void guardar(List<Tarea> tareas) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(PATH_TAREAS)) {
            for (Tarea tarea : tareas) {
                // escribe texto en el archivo
                writer.write(
                        tarea.getId() + ";" +
                                tarea.getDescripcion() + ";" +
                                tarea.getFechaVencimiento() + ";" +
                                tarea.isCompletada()
                );
                // nueva línea por cada tarea
                writer.newLine();
            }
        }
    }
}
