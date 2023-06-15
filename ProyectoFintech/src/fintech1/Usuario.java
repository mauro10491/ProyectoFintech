package fintech1;

import java.sql.*;
import fintech1.DB.Conexion;
import javax.swing.JOptionPane;

public class Usuario {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String direccionCorrespondencia;
    private String direccionEmail;
    public String celular;
    private String contraseña;

    public Usuario() {
    }

    public Usuario(String cedula, String nombre, String apellidos, String direccionCorrespondencia, String direccionEmail,
            String celular, String contraseña) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccionCorrespondencia = direccionCorrespondencia;
        this.direccionEmail = direccionEmail;
        this.celular = celular;
        this.contraseña = contraseña;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccionCorrespondencia() {
        return direccionCorrespondencia;
    }

    public void setDireccionCorrespondencia(String direccionCorrespondencia) {
        this.direccionCorrespondencia = direccionCorrespondencia;
    }

    public String getDireccionEmail() {
        return direccionEmail;
    }

    public void setDireccionEmail(String direccionEmail) {
        this.direccionEmail = direccionEmail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void crearUsuario(String cedula, String nombre, String apellidos, String direccionCorrespondencia,
            String direccionEmail, String celular, String contraseña) {

        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement("select celular from usuarios where celular = ?");
            pst2.setString(1, celular);
            ResultSet rs = pst2.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "El numero de telefono ingresado ya se encuentra registrado");
            } else {
                try {
                    Connection cn = Conexion.conectar();
                    PreparedStatement pst = cn.prepareStatement("insert into usuarios values(?,?,?,?,?,?,?,?)");

                    pst.setString(1, celular);
                    pst.setString(2, cedula);
                    pst.setString(3, nombre);
                    pst.setString(4, apellidos);
                    pst.setString(5, direccionCorrespondencia);
                    pst.setString(6, direccionEmail);
                    pst.setString(7, contraseña);
                    pst.setInt(8, 0);

                    pst.executeUpdate();

                    cn.close();

                    JOptionPane.showMessageDialog(null, "Usuario creado con exito");

                } catch (SQLException e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Error al crear el usuario");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
        }
    }

    public void restaurarContrasñe(String password, String confirmarPassword, String celular) {

        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement("select celular from usuarios where celular = ?");
            pst2.setString(1, celular);
            ResultSet rs = pst2.executeQuery();

            if (rs.next()) {
                if (!password.equals("") && !confirmarPassword.equals("")) {
                    if (password.equals(confirmarPassword)) {
                        try {
                            Connection cn = Conexion.conectar();
                            PreparedStatement pst = cn.prepareStatement("update usuarios set contraseña=? where celular = '" + celular + "'");

                            pst.setString(1, password);
                            pst.executeUpdate();
                            cn.close();

                            JOptionPane.showMessageDialog(null, "Restauración Exitosa");

                        } catch (SQLException e) {
                            System.out.println(e);
                            JOptionPane.showMessageDialog(null, "Error al restarurar la contraseña");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debes ingresar los datos");
                }

            } else {
                JOptionPane.showMessageDialog(null, "No existe el usuario registrado con el celular");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
