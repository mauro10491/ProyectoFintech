package fintech1;

import fintech1.DB.Conexion;
import fintech1.IGU.ActualizarUsuario;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import fintech1.IGU.VerUsuarios;

public class Administrador extends Usuario {

    private Date ultimoLogin;

    public Administrador() {
    }

    public Administrador(String cedula, String nombre, String apellidos, String direccionCorrespondal,
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
            PreparedStatement pst = cn.prepareStatement("select celular, cedula, nombre, apellidos, direccionCorrespondencia, direccionEmail, contraseña from usuarios");

            ResultSet rs = pst.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Celular");
            modelo.addColumn("Cedula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellidos");
            modelo.addColumn("Direccion");
            modelo.addColumn("Email");
            modelo.addColumn("Contraseña");

            while (rs.next()) {
                Object[] fila = new Object[7];

                for (int i = 0; i < 7; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            jt.setModel(modelo);

            jt.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int fila = jt.getSelectedRow();

                    if (fila != -1) {
                        String celular = jt.getValueAt(fila, 0).toString();
                        String cedula = jt.getValueAt(fila, 1).toString();
                        String nombre = jt.getValueAt(fila, 2).toString();
                        String apellidos = jt.getValueAt(fila, 3).toString();
                        String direccion = jt.getValueAt(fila, 4).toString();
                        String email = jt.getValueAt(fila, 5).toString();
                        String contraseña = jt.getValueAt(fila, 6).toString();

                        abrirInterfaz(celular, cedula, nombre, apellidos, direccion, email, contraseña);
                    }
                }
            });

            cn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void abrirInterfaz(String celular, String cedula, String nombre, String apellidos, String direccion, String email, String contraseña) {
        ActualizarUsuario actUsuario = new ActualizarUsuario();
        actUsuario.setVisible(true);
    }

    public void crearAdmin(String cedula, String nombre, String apellidos, String direccionCorrespondencia,
            String direccionEmail, String celular, String contraseña) {
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement("select celularAdm from administradores where celularAdm = ?");
            pst2.setString(1, celular);
            ResultSet rs = pst2.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "El numero de telefono ingresado ya se encuentra registrado");
            } else {
                try {
                    Connection cn = Conexion.conectar();
                    PreparedStatement pst = cn.prepareStatement("insert into administradores values(?,?,?,?,?,?,?)");

                    pst.setString(1, celular);
                    pst.setString(2, cedula);
                    pst.setString(3, nombre);
                    pst.setString(4, apellidos);
                    pst.setString(5, direccionCorrespondencia);
                    pst.setString(6, direccionEmail);
                    pst.setString(7, contraseña);

                    pst.executeUpdate();

                    cn.close();

                    JOptionPane.showMessageDialog(null, "Administrador creado con exito");

                } catch (SQLException e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Error al crear el Administrador");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
        }

    }
}
