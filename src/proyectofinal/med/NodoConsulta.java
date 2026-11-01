package proyectofinal.med;

public class NodoConsulta {

    private final Consulta consulta;
    private NodoConsulta siguiente;

    public NodoConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("La consulta del nodo es obligatoria.");
        }
        this.consulta = consulta;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public NodoConsulta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoConsulta siguiente) {
        this.siguiente = siguiente;
    }
}
