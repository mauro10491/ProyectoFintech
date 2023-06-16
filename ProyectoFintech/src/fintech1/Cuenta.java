package fintech1;

import fintech1.DB.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;

public class Cuenta extends Usuario {

    private double saldo;
    private String celular;

    public Cuenta() {
    }

    public Cuenta(double saldo, String celular) {
        this.saldo = saldo;
        this.celular = celular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void realizarDeposito(double monto, String celular) {

        try {

            Connection cn1 = Conexion.conectar();
            PreparedStatement pst = cn1.prepareStatement("select * from usuarios where celular = ?");
            pst.setString(1, celular);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                try {
                    Connection cn2 = Conexion.conectar();
                    PreparedStatement pst3 = cn2.prepareStatement("select * from cuentas where celular = ?");
                    pst3.setString(1, celular);
                    ResultSet rs2 = pst3.executeQuery();

                    if (rs2.next()) {
                        double saldoActual = rs2.getDouble("saldo");

                        try {

                            Connection cn3 = Conexion.conectar();
                            PreparedStatement pst2 = cn3.prepareStatement("update cuentas set saldo = ? where celular = ?");
                            pst2.setDouble(1, saldoActual + monto);
                            pst2.setString(2, celular);

                            pst2.executeUpdate();

                            cn2.close();

                            JOptionPane.showMessageDialog(null, "Deposito Exitoso");

                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cuenta no existe");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Errrrrrroooooorrr");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta especificada no existe");
            }

        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al realizar el deposito");
        }
    }

    public void realizarRetiro(double monto, String celular) {

        try {

            Connection cn1 = Conexion.conectar();
            PreparedStatement pst = cn1.prepareStatement("select * from usuarios where celular = ?");
            pst.setString(1, celular);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                try {
                    Connection cn2 = Conexion.conectar();
                    PreparedStatement pst3 = cn2.prepareStatement("select * from cuentas where celular = ?");
                    pst3.setString(1, celular);
                    ResultSet rs2 = pst3.executeQuery();

                    if (rs2.next()) {
                        try {
                            double saldoActual = rs2.getDouble("saldo");
                            if (saldoActual > 0) {
                                Connection cn3 = Conexion.conectar();
                                PreparedStatement pst2 = cn3.prepareStatement("update cuentas set saldo = ? where celular = ?");

                                pst2.setDouble(1, saldoActual - monto);
                                pst2.setString(2, celular);

                                pst2.executeUpdate();

                                cn2.close();

                                JOptionPane.showMessageDialog(null, "Retiro Exitoso");
                            } else {
                                JOptionPane.showMessageDialog(null, "Saldo insuficiente");
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cuenta no existe");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(null, "Errrrrrroooooorrr");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta especificada no existe");
            }

        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al realizar el retiro");
        }
    }
}
