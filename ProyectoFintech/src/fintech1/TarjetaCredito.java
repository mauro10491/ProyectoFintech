package fintech1;

public class TarjetaCredito {

    private String nombreCompleto;
    private String fechaNacimiento;
    private String direccion;
    private String numero;
    private String correo;
    private double ingresosMensuales;
    private String informacionLaboral;

    public TarjetaCredito() {
    }

    public TarjetaCredito(String nombreCompleto, String fechaNacimiento, String direccion, String numero, String correo, double ingresosMensuales, String informacionLaboral) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numero = numero;
        this.correo = correo;
        this.ingresosMensuales = ingresosMensuales;
        this.informacionLaboral = informacionLaboral;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public String getInformacionLaboral() {
        return informacionLaboral;
    }

    public void setInformacionLaboral(String informacionLaboral) {
        this.informacionLaboral = informacionLaboral;
    }

    public void asignarTarjeta(Usuario usuario) {

        if (ingresosMensuales >= 2000000) {
            //usuario.setTarjetaAprobada(true);
            //usuario.setCupoDisponible(ingresosMensuales * 0.5);
        } else {
            //usuario.setTarjetaAprobada(false);
            //usuario.setCupoDisponible(0);
        }

    }
}
