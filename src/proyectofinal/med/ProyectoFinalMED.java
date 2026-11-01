package proyectofinal.med;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProyectoFinalMED {

    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final SistemaClinica SISTEMA = new SistemaClinica();

    public static void main(String[] args) {
        SISTEMA.cargarDatosIniciales();
        ejecutarMenu();
    }

    private static void ejecutarMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSISTEMA DE TURNOS DE CLINICA");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Mostrar pacientes");
            System.out.println("3. Buscar paciente por expediente");
            System.out.println("4. Buscar medico por codigo");
            System.out.println("5. Asignar turno");
            System.out.println("6. Atender siguiente turno");
            System.out.println("7. Deshacer ultimo turno asignado");
            System.out.println("8. Mostrar historial clinico");
            System.out.println("9. Reporte de pacientes por apellido");
            System.out.println("10. Reporte por ultima consulta");
            System.out.println("11. Mostrar total y altura del arbol");
            System.out.println("12. Mostrar turnos pendientes");
            System.out.println("0. Salir");

            try {
                int opcion = leerEntero("Opcion: ");
                switch (opcion) {
                    case 1 ->
                        registrarPaciente(); //Cambie esto
                    case 2 ->
                        mostrarPacientes();
                    case 3 ->
                        buscarPaciente();
                    case 4 ->
                        buscarMedico();
                    case 5 ->
                        asignarTurno();
                    case 6 ->
                        atenderTurno();
                    case 7 ->
                        deshacerTurno();
                    case 8 ->
                        mostrarHistorial();
                    case 9 ->
                        SISTEMA.listarPacientesPorApellido()
                                .forEach(System.out::println);
                    case 10 ->
                        SISTEMA.listarPacientesPorUltimaConsulta()
                                .forEach(System.out::println);
                    case 11 ->
                        System.out.println("Pacientes en el arbol: "
                                + SISTEMA.contarPacientes() + " | Altura: "
                                + SISTEMA.alturaArbol());
                    case 12 ->
                        SISTEMA.mostrarTurnosPendientes();
                    case 0 ->
                        continuar = false;
                    default ->
                        System.out.println("Opcion no valida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void registrarPaciente() {
        String expediente = leerTexto("Numero de expediente: 'EXP000'");
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        LocalDate nacimiento = leerFecha("Fecha de nacimiento (AAAA-MM-DD): ");

        Paciente nuevoPaciente = new Paciente(expediente, nombres, apellidos, nacimiento);
        SISTEMA.registrarPaciente(nuevoPaciente);

        //Mensaje de confirmación para registro
        System.out.println("¡Paciente registrado exitosamente con expediente " + expediente + "!");
    }

    //agregue este metodo por si llegara a estar vacia la lista de pacientes registrados
    private static void mostrarPacientes() {
        List<Paciente> lista = SISTEMA.getPacientes();
        if (lista.isEmpty()) {
            System.out.println("No hay pacientes registrados en el sistema.");
            return;
        }
        System.out.println("\n--- LISTA DE PACIENTES ---");
        lista.forEach(System.out::println);
    }

    private static void buscarPaciente() {
        String expediente = leerTexto("Numero de expediente: ");
        System.out.println(SISTEMA.buscarPaciente(expediente));
    }

    private static void buscarMedico() {
        String codigo = leerTexto("Codigo del medico: ");
        System.out.println(SISTEMA.buscarMedico(codigo));
    }

    //Actualización para poder elegir una especilidad
    private static void asignarTurno() {
        String expediente = leerTexto("Numero de expediente: ");
        // Obtener la lista de especialidades registradas en el sistema
        List<Especialidad> especialidades = SISTEMA.getEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades disponibles en este momento.");
            return;
        }

        // Mostrar el menú interactivo de especialidades con su respectivo médico
        System.out.println("\n--- SELECCIONE UNA ESPECIALIDAD ---");
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + ". " + especialidades.get(i));
        }

        // Leer la opción seleccionada por el usuario de forma numérica
        int seleccion = leerEntero("Ingrese el numero de la especialidad: ");
        if (seleccion < 1 || seleccion > especialidades.size()) {
            throw new IllegalArgumentException("Seleccion de especialidad fuera de rango.");
        }

        // Obtener el nombre de la especialidad elegida
        String nombreEspecialidad = especialidades.get(seleccion - 1).getNombre();

        // Asignar el turno usando la lógica del sistema
        Turno turno = SISTEMA.asignarTurno(expediente, nombreEspecialidad);
        System.out.println("¡Turno asignado exitosamente!\nDetalle: " + turno);
    }

    
    //Metodo Actualizado para evitar que el usuario digite el nombre de la especialidad
    private static void atenderTurno() {
        // Obtener la lista de especialidades registradas en el sistema
        List<Especialidad> especialidades = SISTEMA.getEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades disponibles en este momento.");
            return;
        }

        // Mostrar el menú interactivo de especialidades
        System.out.println("\n--- SELECCIONE LA ESPECIALIDAD A ATENDER ---");
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + ". " + especialidades.get(i));
        }

        // Leer la opción seleccionada por el usuario de forma numérica
        int seleccion = leerEntero("Ingrese el numero de la especialidad: ");
        if (seleccion < 1 || seleccion > especialidades.size()) {
            throw new IllegalArgumentException("Seleccion de especialidad fuera de rango.");
        }

        // Obtener el nombre de la especialidad elegida
        String nombreEspecialidad = especialidades.get(seleccion - 1).getNombre();

        // Atender el siguiente turno usando la lógica del sistema
        Turno turno = SISTEMA.atenderSiguienteTurno(nombreEspecialidad);
        System.out.println("¡Turno atendido con exito!\nDetalle del turno atendido: " + turno);
    }

    private static void deshacerTurno() {
        Turno turno = SISTEMA.deshacerUltimoTurno();
        System.out.println("Turno eliminado: " + turno);
    }

    private static void mostrarHistorial() {
        Paciente paciente = SISTEMA.buscarPaciente(leerTexto("Numero de expediente: "));
        if (paciente.getHistorial().isEmpty()) {
            System.out.println("El paciente no tiene consultas registradas.");
            return;
        }

        paciente.getHistorial().forEach(System.out::println);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String valor = ENTRADA.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El dato es obligatorio.");
        }
        return valor;
    }

    private static int leerEntero(String mensaje) {
        try {
            return Integer.parseInt(leerTexto(mensaje));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un numero valido.");
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        try {
            return LocalDate.parse(leerTexto(mensaje));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato AAAA-MM-DD.");
        }
    }
}
