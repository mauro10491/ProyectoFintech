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
                    Connection cn1 = Conexion.conectar();
                    PreparedStatement pst = cn1.prepareStatement("insert into usuarios values(?,?,?,?,?,?,?)");

                    PreparedStatement psts = cn1.prepareStatement("insert into cuentas values(?,?)");
                    PreparedStatement pst3 = cn1.prepareStatement("insert into clientes values(?,?,?,?,?,?,?,?)");

                    pst.setString(1, celular);
                    pst.setString(2, cedula);
                    pst.setString(3, nombre);
                    pst.setString(4, apellidos);
                    pst.setString(5, direccionCorrespondencia);
                    pst.setString(6, direccionEmail);
                    pst.setString(7, contraseña);

                    psts.setDouble(1, 0);
                    psts.setString(2, celular);

                    pst3.setString(1, celular);
                    pst3.setString(2, cedula);
                    pst3.setString(3, nombre);
                    pst3.setString(4, apellidos);
                    pst3.setString(5, direccionCorrespondencia);
                    pst3.setString(6, direccionEmail);
                    pst3.setString(7, contraseña);
                    pst3.setInt(8, 0);

                    pst.executeUpdate();
                    psts.executeUpdate();
                    pst3.executeUpdate();

                    cn1.close();

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

    public void restaurarContraseña(String password, String confirmarPassword, String celular) {

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
                            PreparedStatement psts = cn.prepareStatement("update clientes set contraseña = ? where celular = '" + celular + "'");

                            pst.setString(1, password);
                            pst.executeUpdate();
                            
                            psts.setString(1, password);
                            psts.executeUpdate();
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

    public void editarUsuario(String cedula, String nombre, String apellidos, String direccionCorrespondencia,
            String direccionEmail, String celular, String contraseña) {

        int validacion = 0;

        if (cedula.equals("")) {
            validacion++;
        }
        if (nombre.equals("")) {
            validacion++;
        }
        if (apellidos.equals("")) {
            validacion++;
        }
        if (direccionCorrespondencia.equals("")) {
            validacion++;
        }
        if (direccionEmail.equals("")) {
            validacion++;
        }
        if (celular.equals("")) {
            validacion++;
        }
        if (contraseña.equals("")) {
            validacion++;
        }

        if (validacion == 0) {
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("select celular from usuarios where celular = '" + celular + "'");
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Numero de telefono no disponible");
                } else {
                    Connection cn2 = Conexion.conectar();
                    PreparedStatement pst2 = cn2.prepareStatement("update usuarios set celular =?, cedula=?, nombre=?, apellidos=?, direccionCorrespondencia=?, direccionEmail=?, contraseña=?");

                    pst2.setString(1, celular);
                    pst2.setString(2, cedula);
                    pst2.setString(3, nombre);
                    pst2.setString(4, apellidos);
                    pst2.setString(5, direccionCorrespondencia);
                    pst2.setString(6, direccionEmail);
                    pst2.setString(7, contraseña);

                    pst2.executeUpdate();
                    cn.close();

                    JOptionPane.showMessageDialog(null, "Actualización exitosa");
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes de llenar los campos");
        }
    }
}
