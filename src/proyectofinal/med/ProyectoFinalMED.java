package proyectofinal.med;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
            System.out.println("\n==========================================");
            System.out.println("      SISTEMA DE TURNOS - CLINICA MED     ");
            System.out.println("==========================================");
            System.out.println("--- GESTION DE PACIENTES ---");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Mostrar pacientes");
            System.out.println("3. Buscar paciente por expediente");
            System.out.println("4. Buscar paciente por nombre o apellido"); // NUEVA
            System.out.println("5. Reporte de pacientes por apellido");

            System.out.println("\n--- GESTION DE MEDICOS ---");
            System.out.println("6. Mostrar lista de medicos");             // NUEVA
            System.out.println("7. Buscar medico por codigo");
            System.out.println("8. Buscar medico por nombre o apellido"); // NUEVA

            System.out.println("\n--- TURNOS Y ATENCION ---");
            System.out.println("9. Asignar turno");
            System.out.println("10. Atender siguiente turno");
            System.out.println("11. Deshacer ultimo turno asignado");
            System.out.println("12. Mostrar turnos pendientes");

            System.out.println("\n--- HISTORIALES Y REPORTES ---");
            System.out.println("13. Mostrar historial clinico");
            System.out.println("14. Reporte por ultima consulta");

            System.out.println("\n--- SISTEMA Y ESTRUCTURAS ---");
            System.out.println("15. Mostrar total y altura del arbol");
            System.out.println("0. Salir");
            System.out.println("==========================================");

            try {
                int opcion = leerEntero("Opcion: ");
                switch (opcion) {
                    case 1 ->
                        registrarPaciente();
                    case 2 ->
                        mostrarPacientes();
                    case 3 ->
                        buscarPaciente();
                    case 4 ->
                        buscarPacientePorNombre(); // NUEVA
                    case 5 ->
                        SISTEMA.listarPacientesPorApellido()
                                .forEach(System.out::println);
                    case 6 ->
                        mostrarListaMedicos();     // NUEVA
                    case 7 ->
                        buscarMedico();
                    case 8 ->
                        buscarMedicoPorNombre();   // NUEVA
                    case 9 ->
                        asignarTurno();
                    case 10 ->
                        atenderTurno();
                    case 11 ->
                        deshacerTurno();
                    case 12 ->
                        SISTEMA.mostrarTurnosPendientes();
                    case 13 ->
                        mostrarHistorial();
                    case 14 ->
                        SISTEMA.listarPacientesPorUltimaConsulta()
                                .forEach(System.out::println);
                    case 15 ->
                        System.out.println("Pacientes en el arbol: "
                                + SISTEMA.contarPacientes() + " | Altura: "
                                + SISTEMA.alturaArbol());
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
        String expediente = SISTEMA.generarSiguienteExpediente();
        System.out.println("Numero de expediente asignado automaticamente: " + expediente);

        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        LocalDate nacimiento = leerFechaNacimiento();

        Paciente nuevoPaciente = new Paciente(expediente, nombres, apellidos, nacimiento);
        SISTEMA.registrarPaciente(nuevoPaciente);

        System.out.println("¡Paciente registrado exitosamente!");
        System.out.println("Fecha de nacimiento registrada: " + nacimiento);
    }

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

    private static void buscarPacientePorNombre() {
        String filtro = leerTexto("Ingrese el nombre o apellido del paciente a buscar: ");
        List<Paciente> resultados = SISTEMA.buscarPacientesPorNombre(filtro);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron pacientes que coincidan con: " + filtro);
        } else {
            System.out.println("\n--- RESULTADOS DE BUSQUEDA DE PACIENTES ---");
            resultados.forEach(System.out::println);
        }
    }

    private static void mostrarListaMedicos() {
        List<Medico> medicos = SISTEMA.getMedicos();
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos registrados en el sistema.");
            return;
        }
        System.out.println("\n--- LISTA GENERAL DE MEDICOS ---");
        medicos.forEach(System.out::println);
    }

    private static void buscarMedico() {
        String codigo = leerTexto("Codigo del medico: ");
        System.out.println(SISTEMA.buscarMedico(codigo));
    }

    private static void buscarMedicoPorNombre() {
        String filtro = leerTexto("Ingrese el nombre o apellido del medico a buscar: ");
        List<Medico> resultados = SISTEMA.buscarMedicosPorNombre(filtro);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron medicos que coincidan con: " + filtro);
        } else {
            System.out.println("\n--- RESULTADOS DE BUSQUEDA DE MEDICOS ---");
            resultados.forEach(System.out::println);
        }
    }

    private static void asignarTurno() {
        String expediente = leerTexto("Numero de expediente: ");
        List<Especialidad> especialidades = SISTEMA.getEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades disponibles en este momento.");
            return;
        }

        System.out.println("\n--- SELECCIONE UNA ESPECIALIDAD ---");
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + ". " + especialidades.get(i));
        }

        int seleccion = leerEntero("Ingrese el numero de la especialidad: ");
        if (seleccion < 1 || seleccion > especialidades.size()) {
            throw new IllegalArgumentException("Seleccion de especialidad fuera de rango.");
        }

        String nombreEspecialidad = especialidades.get(seleccion - 1).getNombre();
        Turno turno = SISTEMA.asignarTurno(expediente, nombreEspecialidad);
        System.out.println("¡Turno asignado exitosamente!\nDetalle: " + turno);
    }

    private static void atenderTurno() {
        List<Especialidad> especialidades = SISTEMA.getEspecialidades();
        if (especialidades.isEmpty()) {
            System.out.println("No hay especialidades disponibles en este momento.");
            return;
        }

        System.out.println("\n--- SELECCIONE LA ESPECIALIDAD A ATENDER ---");
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + ". " + especialidades.get(i));
        }

        int seleccion = leerEntero("Ingrese el numero de la especialidad: ");
        if (seleccion < 1 || seleccion > especialidades.size()) {
            throw new IllegalArgumentException("Seleccion de especialidad fuera de rango.");
        }

        String nombreEspecialidad = especialidades.get(seleccion - 1).getNombre();
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

    private static LocalDate leerFechaNacimiento() {
        LocalDate hoy = LocalDate.now();

        while (true) {
            int mes = leerEnteroEnRango("Mes de nacimiento (1-12): ", 1, 12,
                    "El mes debe estar entre 1 y 12.");
            int maximoDia = Month.of(mes).maxLength();
            int dia = leerEnteroEnRango("Dia de nacimiento: ", 1, maximoDia,
                    "El dia debe estar entre 1 y " + maximoDia + " para ese mes.");
            int anio = leerEnteroEnRango("Año de nacimiento: ", 1, hoy.getYear(),
                    "El año no puede ser futuro ni menor que 1.");

            int diasDelMes = YearMonth.of(anio, mes).lengthOfMonth();
            if (dia > diasDelMes) {
                System.out.println("Error: el dia " + dia + " no existe en el mes " + mes
                        + " del año " + anio + ". Febrero tiene 29 dias solo en años bisiestos.");
                continue;
            }

            LocalDate fecha = LocalDate.of(anio, mes, dia);
            if (fecha.isAfter(hoy)) {
                System.out.println("Error: la fecha de nacimiento no puede ser futura. Hoy es "
                        + hoy + ".");
                continue;
            }
            return fecha;
        }
    }

    private static int leerEnteroEnRango(String mensaje, int minimo, int maximo,
            String mensajeRango) {
        while (true) {
            System.out.print(mensaje);
            String valor = ENTRADA.nextLine().trim();
            int numero;
            try {
                numero = Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero entero.");
                continue;
            }
            if (numero < minimo || numero > maximo) {
                System.out.println("Error: " + mensajeRango);
                continue;
            }
            return numero;
        }
    }
}
