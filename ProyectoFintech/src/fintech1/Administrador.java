package fintech1;

import fintech1.DB.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Administrador extends Usuario {

    private Date ultimoLogin;

    public Administrador() {
    }

    public Administrador(double saldo, String cedula, String nombre, String apellidos, String direccionCorrespondal,
            String direccionEmail, String celular, String contraseña) {
        super(cedula, nombre, apellidos, direccionCorrespondal, direccionEmail, celular, contraseña);
        this.ultimoLogin = ultimoLogin;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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
                    pst2.close();

                    JOptionPane.showMessageDialog(null, "Actualización exitosa");
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes de llenar los campos");
        }

    }

    public void verListaUsuarios(JTable jt) {

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select celular, cedula, nombre, apellidos, direccionCorrespondencia, direccionEmail, contraseña, id from usuarios");

            ResultSet rs = pst.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Celular");
            modelo.addColumn("Cedula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellidos");
            modelo.addColumn("Direccion");
            modelo.addColumn("Email");
            modelo.addColumn("Contraseña");
            modelo.addColumn("ID");

            while (rs.next()) {
                Object[] fila = new Object[8];

                for (int i = 0; i < 8; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            jt.setModel(modelo);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public Usuario mostrarPersona() {

        Connection cn = Conexion.conectar();
        PreparedStatement pst;
        Usuario usuario = null;
        try {
            pst = cn.prepareStatement("select * from usuarios");
            ResultSet rs = pst.executeQuery();

            usuario = new Usuario();
            usuario.setCelular(rs.getString(1));
            usuario.setCedula(rs.getString(2));
            usuario.setNombre(rs.getString(3));
            usuario.setApellidos(rs.getString(4));
            usuario.setDireccionCorrespondencia(rs.getString(5));
            usuario.setDireccionEmail(rs.getString(6));
            usuario.setContraseña(rs.getString(7));
        } catch (SQLException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuario;
    }

}
