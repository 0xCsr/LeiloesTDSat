/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.SQLException;

public class ProdutosDAO {    
    public void cadastrarProduto(ProdutosDTO produto){
        String sql = "insert into produtos(nome, valor, status) values(?, ?, ?);";
        
        try (
            Connection conn = new conectaDAO().connectDB();
            PreparedStatement prep = conn.prepareStatement(sql)
        ) {
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle.getMessage(), sqle);
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "select * from produtos";
        
        try (
            Connection conn = new conectaDAO().connectDB();
            PreparedStatement prep = conn.prepareStatement(sql)
        ) {
            ArrayList<ProdutosDTO> produtosDTO = new ArrayList<>();
            
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    Integer valor = rs.getInt("valor");
                    String status = rs.getString("status");
                    
                    produtosDTO.add(new ProdutosDTO(
                        id,
                        nome,
                        valor,
                        status
                    ));
                }
            }
            
            return produtosDTO;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle.getMessage(), sqle);
        }
    }
}

