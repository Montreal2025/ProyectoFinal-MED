package proyectofinal.med;

import java.time.LocalDateTime;

public class Turno {

    private final Paciente paciente;
    private final Medico medico;
    private final Especialidad especialidad;
    private final LocalDateTime fechaAsignacion;

    public Turno(Paciente paciente, Medico medico, Especialidad especialidad) {
        this.paciente = paciente;
        this.medico = medico;
        this.especialidad = especialidad;
        this.fechaAsignacion = LocalDateTime.now();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    @Override
    public String toString() {
        return paciente.getExpediente() + " | " + paciente.getApellidos() + ", "
                + paciente.getNombres() + " | " + especialidad.getNombre()
                + " | " + medico.getNombreCompleto();
    }
}
