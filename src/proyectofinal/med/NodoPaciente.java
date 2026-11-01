package proyectofinal.med;

public class NodoPaciente {

    private final Paciente paciente;
    private NodoPaciente izquierdo;
    private NodoPaciente derecho;

    public NodoPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente del nodo es obligatorio.");
        }
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public NodoPaciente getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoPaciente izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoPaciente getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoPaciente derecho) {
        this.derecho = derecho;
    }
}
