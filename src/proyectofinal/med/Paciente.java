package proyectofinal.med;

import java.time.LocalDate;
import java.util.List;

public class Paciente {

    private final String expediente;
    private final String nombres;
    private final String apellidos;
    private final LocalDate fechaNacimiento;
    private final ListaEnlazadaConsultas historial;

    public Paciente(String expediente, String nombres, String apellidos, LocalDate fechaNacimiento) {
        this.expediente = validar(expediente, "El expediente es obligatorio.");
        this.nombres = validar(nombres, "Los nombres son obligatorios.");
        this.apellidos = validar(apellidos, "Los apellidos son obligatorios.");
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        this.fechaNacimiento = fechaNacimiento;
        this.historial = new ListaEnlazadaConsultas();
    }

    private String validar(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public String getExpediente() {
        return expediente;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public List<Consulta> getHistorial() {
        return historial.recorrer();
    }

    public void agregarConsulta(Consulta consulta) {
        historial.agregarAlFinal(consulta);
    }

    public LocalDate getFechaUltimaConsulta() {
        Consulta ultimaConsulta = historial.obtenerUltimo();
        return ultimaConsulta == null ? null : ultimaConsulta.getFecha();
    }

    @Override
    public String toString() {
        return expediente + " | " + apellidos + ", " + nombres
                + " | Nacimiento: " + fechaNacimiento
                + " | Ultima consulta: "
                + (getFechaUltimaConsulta() == null ? "Sin consultas" : getFechaUltimaConsulta());
    }
}
