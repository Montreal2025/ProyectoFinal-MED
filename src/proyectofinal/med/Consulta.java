package proyectofinal.med;

import java.time.LocalDate;

public class Consulta {

    private final LocalDate fecha;
    private final String especialidad;
    private final String medico;
    private final String motivo;

    public Consulta(LocalDate fecha, String especialidad, String medico, String motivo) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de consulta es obligatoria.");
        }
        this.fecha = fecha;
        this.especialidad = validar(especialidad, "La especialidad es obligatoria.");
        this.medico = validar(medico, "El medico es obligatorio.");
        this.motivo = validar(motivo, "El motivo de consulta es obligatorio.");
    }

    private String validar(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return fecha + " | " + especialidad + " | " + medico + " | " + motivo;
    }
}
