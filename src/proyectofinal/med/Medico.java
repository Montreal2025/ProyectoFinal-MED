package proyectofinal.med;

public class Medico {

    private final String codigo;
    private final String nombres;
    private final String apellidos;
    private final String especialidad;

    public Medico(String codigo, String nombres, String apellidos, String especialidad) {
        this.codigo = validar(codigo, "El codigo del medico es obligatorio.");
        this.nombres = validar(nombres, "Los nombres del medico son obligatorios.");
        this.apellidos = validar(apellidos, "Los apellidos del medico son obligatorios.");
        this.especialidad = validar(especialidad, "La especialidad es obligatoria.");
    }

    private String validar(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return codigo + " | " + getNombreCompleto() + " | " + especialidad;
    }
}
