package fintech1;

import fintech1.DB.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;


public class Cuenta extends Usuario{
    private double saldo;  
    
    public Cuenta(){
    }
    
    public Cuenta(double saldo, String cedula, String nombre, String apellidos, String direccionCorrespondal,
            String direccionEmail, String celular, String contraseña){
        super(cedula, nombre, apellidos, direccionCorrespondal, direccionEmail, celular, contraseña);
        this.saldo = saldo;
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
    
    public void realizarDeposito(double monto, String celular){
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select * from cuentas where celular = ?");
            pst.setString(1, celular);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                double saldoActual = rs.getDouble("saldo");
                
                PreparedStatement pst2 = cn.prepareStatement("update cuentas set saldo = ? where celular = ?");
                pst2.setDouble(1, saldoActual + monto);
                pst2.setString(2, celular);
                
                pst2.executeUpdate();
                
                cn.close();
                
                JOptionPane.showMessageDialog(null, "Deposito Exitoso");
            } else {
                JOptionPane.showMessageDialog(null, "La cuenta especificada no existe");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al realizar el deposito");
        }
    
    }
}
