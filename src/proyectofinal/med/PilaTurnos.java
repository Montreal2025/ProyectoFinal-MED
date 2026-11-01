package proyectofinal.med;

import java.util.ArrayDeque;
import java.util.Deque;

public class PilaTurnos {

    private final Deque<Turno> turnos = new ArrayDeque<>();

    public void apilar(Turno turno) {
        if (turno == null) {
            throw new IllegalArgumentException("El turno es obligatorio.");
        }
        turnos.push(turno);
    }

    public Turno verTope() {
        return turnos.peek();
    }

    public Turno desapilar() {
        return turnos.poll();
    }

    public boolean eliminar(Turno turno) {
        return turnos.remove(turno);
    }

    public boolean estaVacia() {
        return turnos.isEmpty();
    }
}
