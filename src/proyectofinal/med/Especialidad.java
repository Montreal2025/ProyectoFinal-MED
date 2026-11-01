package proyectofinal.med;

public class Especialidad {

    private final String nombre;
    private final Medico medico;
    private final ColaTurnos turnos;

    public Especialidad(String nombre, Medico medico) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la especialidad es obligatorio.");
        }
        this.nombre = nombre.trim();
        this.medico = medico;
        this.turnos = new ColaTurnos();
    }

    public String getNombre() {
        return nombre;
    }

    public Medico getMedico() {
        return medico;
    }

    public ColaTurnos getTurnos() {
        return turnos;
    }

    public void agregarTurno(Turno turno) {
        turnos.encolar(turno);
    }

    public Turno atenderSiguiente() {
        return turnos.desencolar();
    }

    public boolean eliminarTurno(Turno turno) {
        return turnos.eliminar(turno);
    }

    @Override
    public String toString() {
        return nombre + " | " + (medico == null ? "Sin medico disponible" : medico.getNombreCompleto());
    }
}
