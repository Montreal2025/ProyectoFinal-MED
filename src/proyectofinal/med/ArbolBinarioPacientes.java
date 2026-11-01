package proyectofinal.med;

public class ArbolBinarioPacientes {

    private NodoPaciente raiz;

    public void insertar(Paciente paciente) {
        raiz = insertar(raiz, paciente);
    }

    public Paciente buscar(String expediente) {
        return buscar(raiz, expediente);
    }

    public int contar() {
        return contar(raiz);
    }

    public int altura() {
        return altura(raiz);
    }

    private NodoPaciente insertar(NodoPaciente nodo, Paciente paciente) {
        if (nodo == null) {
            return new NodoPaciente(paciente);
        }

        int comparacion = paciente.getExpediente().compareToIgnoreCase(
                nodo.getPaciente().getExpediente());
        if (comparacion < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), paciente));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertar(nodo.getDerecho(), paciente));
        } else {
            throw new IllegalArgumentException("Ya existe un paciente con ese expediente.");
        }
        return nodo;
    }

    private Paciente buscar(NodoPaciente nodo, String expediente) {
        if (nodo == null) {
            throw new IllegalArgumentException("No existe un paciente con ese expediente.");
        }

        int comparacion = expediente.compareToIgnoreCase(nodo.getPaciente().getExpediente());
        if (comparacion == 0) {
            return nodo.getPaciente();
        }
        return comparacion < 0
                ? buscar(nodo.getIzquierdo(), expediente)
                : buscar(nodo.getDerecho(), expediente);
    }

    private int contar(NodoPaciente nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contar(nodo.getIzquierdo()) + contar(nodo.getDerecho());
    }

    private int altura(NodoPaciente nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }
}
