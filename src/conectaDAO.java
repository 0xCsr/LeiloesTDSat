import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    private final String user = "root";
    private final String password = "abcd1234"; // Ao padrão da atividade, o campo de senha estava vazio. 
                                                // Alterei para conseguir conectar ao meu banco.
    
    public Connection connectDB() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?useSSL=false", user, password);
            return conn;
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO" + erro.getMessage());
            return null;
        }
    }
}
