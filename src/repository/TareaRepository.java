package repository;

import model.Tarea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TareaRepository {

    private static final String ARCHIVO_TAREAS = "tareas.csv";
    private static final Path PATH_TAREAS = Paths.get(ARCHIVO_TAREAS);

    public List<Tarea> cargar() throws IOException {
        List<Tarea> tareas = new ArrayList<>();

        if (!Files.exists(PATH_TAREAS)) {
            return tareas;
        }

        try (BufferedReader reader = Files.newBufferedReader(PATH_TAREAS)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", -1);

                if (partes.length != 4) {
                    continue;
                }

                tareas.add(new Tarea(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        LocalDate.parse(partes[2]),
                        Boolean.parseBoolean(partes[3])
                ));
            }
        }

        return tareas;
    }

    public void guardar(List<Tarea> tareas) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(PATH_TAREAS)) {
            for (Tarea tarea : tareas) {
                writer.write(
                        tarea.getId() + ";" +
                                tarea.getDescripcion() + ";" +
                                tarea.getFechaVencimiento() + ";" +
                                tarea.isCompletada()
                );
                writer.newLine();
            }
        }
    }
}
