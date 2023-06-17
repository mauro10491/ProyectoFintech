package fintech1;

public class Cliente extends Usuario{
    String codigoCliente;
    
    public Cliente(){
    }
    
    public Cliente(String cedula, String nombre, String apellidos, String direccionCorrespondal,
            String direccionEmail, String celular, String contraseña){
        super(cedula, nombre, apellidos, direccionCorrespondal, direccionEmail, celular, contraseña);
        this.codigoCliente = codigoCliente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
