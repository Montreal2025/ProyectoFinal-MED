package proyectofinal.med;

import java.util.ArrayDeque;
import java.util.Queue;

public class ColaTurnos {

    private final Queue<Turno> turnos = new ArrayDeque<>();

    public void encolar(Turno turno) {
        if (turno == null) {
            throw new IllegalArgumentException("El turno es obligatorio.");
        }
        turnos.add(turno);
    }

    public Turno desencolar() {
        return turnos.poll();
    }

    public boolean eliminar(Turno turno) {
        return turnos.remove(turno);
    }

    public boolean estaVacia() {
        return turnos.isEmpty();
    }

    public Queue<Turno> obtenerTurnos() {
        return new ArrayDeque<>(turnos);
    }
}
