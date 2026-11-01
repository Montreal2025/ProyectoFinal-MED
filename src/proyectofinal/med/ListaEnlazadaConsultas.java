package proyectofinal.med;

import java.util.ArrayList;
import java.util.List;

public class ListaEnlazadaConsultas {

    private NodoConsulta primero;
    private NodoConsulta ultimo;
    private int tamanio;

    public void agregarAlFinal(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("La consulta es obligatoria.");
        }

        NodoConsulta nuevo = new NodoConsulta(consulta);
        if (primero == null) {
            primero = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
        }
        ultimo = nuevo;
        tamanio++;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public Consulta obtenerUltimo() {
        return ultimo == null ? null : ultimo.getConsulta();
    }

    public List<Consulta> recorrer() {
        List<Consulta> consultas = new ArrayList<>(tamanio);
        NodoConsulta actual = primero;
        while (actual != null) {
            consultas.add(actual.getConsulta());
            actual = actual.getSiguiente();
        }
        return List.copyOf(consultas);
    }
}
