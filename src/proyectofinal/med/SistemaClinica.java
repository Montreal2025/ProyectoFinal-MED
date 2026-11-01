package proyectofinal.med;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SistemaClinica {

    private final ArrayList<Paciente> pacientes = new ArrayList<>();
    private final ArrayList<Medico> medicos = new ArrayList<>();
    private final ArrayList<Especialidad> especialidades = new ArrayList<>();
    private final PilaTurnos turnosAsignados = new PilaTurnos();
    private final ArbolBinarioPacientes arbolPacientes = new ArbolBinarioPacientes();

    public void cargarDatosIniciales() {
        if (!pacientes.isEmpty()) {
            return;
        }

        Medico medico1 = new Medico("MED001", "Laura", "Mendez", "Medicina general");
        Medico medico2 = new Medico("MED002", "Carlos", "Pineda", "Pediatria");
        Medico medico3 = new Medico("MED003", "Ana", "Castillo", "Cardiologia");
        Medico medico4 = new Medico("MED004", "Jorge", "Ramirez", "Dermatologia");
        Medico medico5 = new Medico("MED005", "Sofia", "Herrera", "Ginecologia");
        medicos.addAll(List.of(medico1, medico2, medico3, medico4, medico5));
        medicos.sort(Comparator.comparing(Medico::getCodigo, String.CASE_INSENSITIVE_ORDER));

        especialidades.addAll(List.of(
                new Especialidad("Medicina general", medico1),
                new Especialidad("Pediatria", medico2),
                new Especialidad("Cardiologia", medico3),
                new Especialidad("Dermatologia", medico4),
                new Especialidad("Ginecologia", medico5)
        ));

        registrarPacienteConHistorial(new Paciente(
                "EXP001", "Maria", "Lopez", LocalDate.of(1990, 3, 12)),
                "Medicina general", medico1, "Control general", LocalDate.of(2026, 1, 15));
        registrarPacienteConHistorial(new Paciente(
                "EXP002", "Juan", "Garcia", LocalDate.of(1985, 7, 24)),
                "Cardiologia", medico3, "Revision cardiaca", LocalDate.of(2026, 2, 8));
        registrarPacienteConHistorial(new Paciente(
                "EXP003", "Elena", "Martinez", LocalDate.of(2015, 11, 5)),
                "Pediatria", medico2, "Control pediatrico", LocalDate.of(2026, 3, 2));
        registrarPacienteConHistorial(new Paciente(
                "EXP004", "Pedro", "Hernandez", LocalDate.of(1978, 1, 30)),
                "Dermatologia", medico4, "Revision de piel", LocalDate.of(2026, 4, 10));
        registrarPacienteConHistorial(new Paciente(
                "EXP005", "Lucia", "Morales", LocalDate.of(1998, 9, 18)),
                "Ginecologia", medico5, "Consulta preventiva", LocalDate.of(2026, 5, 6));
    }

    private void registrarPacienteConHistorial(
            Paciente paciente, String especialidad, Medico medico, String motivo, LocalDate fecha) {
        paciente.agregarConsulta(new Consulta(fecha, especialidad, medico.getNombreCompleto(), motivo));
        registrarPaciente(paciente);
    }

    public void registrarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente es obligatorio.");
        }
        arbolPacientes.insertar(paciente);
        pacientes.add(paciente);
    }

    public Paciente buscarPaciente(String expediente) {
        if (expediente == null || expediente.isBlank()) {
            throw new IllegalArgumentException("El expediente es obligatorio.");
        }
        return arbolPacientes.buscar(expediente.trim());
    }

    public Medico buscarMedico(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo del medico es obligatorio.");
        }
        int inferior = 0;
        int superior = medicos.size() - 1;
        while (inferior <= superior) {
            int medio = inferior + (superior - inferior) / 2;
            int comparacion = medicos.get(medio).getCodigo().compareToIgnoreCase(codigo.trim());
            if (comparacion == 0) {
                return medicos.get(medio);
            }
            if (comparacion < 0) {
                inferior = medio + 1;
            } else {
                superior = medio - 1;
            }
        }
        throw new IllegalArgumentException("No existe un medico con ese codigo.");
    }

    public Turno asignarTurno(String expediente, String nombreEspecialidad) {
        Paciente paciente = buscarPaciente(expediente);
        Especialidad especialidad = buscarEspecialidad(nombreEspecialidad);
        if (especialidad.getMedico() == null) {
            throw new IllegalStateException("La especialidad no tiene un medico disponible.");
        }

        Turno turno = new Turno(paciente, especialidad.getMedico(), especialidad);
        especialidad.agregarTurno(turno);
        turnosAsignados.apilar(turno);
        return turno;
    }

    public Turno atenderSiguienteTurno(String nombreEspecialidad) {
        Especialidad especialidad = buscarEspecialidad(nombreEspecialidad);
        if (especialidad.getMedico() == null) {
            throw new IllegalStateException("La especialidad no tiene un medico disponible.");
        }
        Turno turno = especialidad.atenderSiguiente();
        if (turno == null) {
            throw new IllegalStateException("No hay turnos pendientes en esta especialidad.");
        }
        turnosAsignados.eliminar(turno);
        turno.getPaciente().agregarConsulta(new Consulta(
                LocalDate.now(),
                especialidad.getNombre(),
                turno.getMedico().getNombreCompleto(),
                "Atencion de turno"));
        return turno;
    }

    public Turno deshacerUltimoTurno() {
        if (turnosAsignados.estaVacia()) {
            throw new IllegalStateException("No hay turnos asignados para deshacer.");
        }
        Turno turno = turnosAsignados.verTope();
        if (!turno.getEspecialidad().eliminarTurno(turno)) {
            throw new IllegalStateException("El turno ya no esta pendiente y no se puede deshacer.");
        }
        return turnosAsignados.desapilar();
    }

    private Especialidad buscarEspecialidad(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("La especialidad es obligatoria.");
        }
        return especialidades.stream()
                .filter(especialidad -> especialidad.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe esa especialidad."));
    }

    public List<Paciente> listarPacientesPorApellido() {
        return pacientes.stream()
                .sorted(Comparator.comparing(Paciente::getApellidos, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Paciente::getNombres, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Paciente> listarPacientesPorUltimaConsulta() {
        return pacientes.stream()
                .sorted(Comparator.comparing(
                        Paciente::getFechaUltimaConsulta,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    public List<Paciente> getPacientes() {
        return List.copyOf(pacientes);
    }

    public List<Medico> getMedicos() {
        return List.copyOf(medicos);
    }

    public List<Especialidad> getEspecialidades() {
        return List.copyOf(especialidades);
    }

    public int contarPacientes() {
        return arbolPacientes.contar();
    }

    public int alturaArbol() {
        return arbolPacientes.altura();
    }

    //Metodo para ver los turnos pendientes por cada especialidad
    public void mostrarTurnosPendientes() {
        boolean hayTurnos = false;
        System.out.println("\n--- TURNOS PENDIENTES POR ESPECIALIDAD ---");
        for (Especialidad especialidad : especialidades) {
            System.out.println("\nEspecialidad: " + especialidad.getNombre() + " (Medico: " + (especialidad.getMedico() == null ? "Ninguno" : especialidad.getMedico().getNombreCompleto()) + ")");
            if (especialidad.getTurnos().estaVacia()) {
                System.out.println("  [No hay turnos pendientes]");
            } else {
                hayTurnos = true;
                // Recorremos la cola sin perder los elementos (creando una copia)
                int contador = 1;
                for (Turno turno : especialidad.getTurnos().obtenerTurnos()) {
                    System.out.println("  " + contador + ". " + turno);
                    contador++;
                }
            }
        }
    }
}
