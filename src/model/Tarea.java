package model;

import annotation.Importancia;

import java.time.LocalDate;

@Importancia
public class Tarea {

    private int id;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private boolean completada;
    // constructor que permite crear tareas inicializandolas con estos atributos
    public Tarea(int id, String descripcion, LocalDate fechaVencimiento, boolean completada) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
    // muestra la info al usuario en consola
    @Override
    public String toString() {
        String estado = completada ? "COMPLETADA" : "PENDIENTE";
        return "ID: " + id +
                " - [" + estado + "] - \"" + descripcion +
                "\" (Vence: " + fechaVencimiento + ")";
    }
}