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

    }

    public void realizarRetiro(double monto, String celular) {

        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst3 = cn2.prepareStatement("select * from cuentas where celular = ?");
            pst3.setString(1, celular);
            ResultSet rs2 = pst3.executeQuery();

            if (rs2.next()) {
                try {
                    double saldoActual = rs2.getDouble("saldo");
                    if (saldoActual > 0 && monto <= saldoActual) {
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
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
        }
    }

    public void realizarTransferencia(double monto, String celularSalida, String celularLlegada) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select * from cuentas where celular = ?");
            pst.setString(1, celularSalida);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                try {
                    Connection cn2 = Conexion.conectar();
                    PreparedStatement pst2 = cn2.prepareStatement("select * from cuentas where celular = ?");
                    pst2.setString(1, celularLlegada);
                    ResultSet rs2 = pst2.executeQuery();

                    if (rs2.next()) {
                        try {
                            double saldoActualSalida = rs.getDouble("saldo");
                            double saldoActualLlegada = rs2.getDouble("saldo");
                            if (saldoActualSalida > 0 && monto <= saldoActualSalida) {
                                Connection cn3 = Conexion.conectar();
                                PreparedStatement pst3 = cn3.prepareStatement("update cuentas set saldo = ? where celular = ?");
                                pst3.setDouble(1, saldoActualSalida - monto);
                                pst3.setString(2, celularSalida);

                                Connection cn4 = Conexion.conectar();
                                PreparedStatement pst4 = cn4.prepareStatement("update cuentas set saldo = ? where celular = ?");
                                pst4.setDouble(1, saldoActualLlegada + monto);
                                pst4.setString(2, celularLlegada);

                                pst3.executeUpdate();
                                pst4.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Transferencia exitosa");
                            } else {
                                JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar la transferencia");
                            }

                        } catch (Exception e) {
                        }
                    }

                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta ingresada no existe");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void verSaldo(String celular) {
        Cuenta cuenta = new Cuenta();
        try {

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select saldo from cuentas where celular = ?");
            pst.setString(1, celular);
            ResultSet rs = pst.executeQuery();

            cuenta.setSaldo(rs.getDouble("saldo"));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
