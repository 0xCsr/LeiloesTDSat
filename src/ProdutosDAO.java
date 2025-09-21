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
                    produtosDTO.add(new ProdutosDTO(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("valor"),
                            rs.getString("status")
                    ));
                }
            }
            
            return produtosDTO;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle.getMessage(), sqle);
        }
    }

    
    public boolean venderProduto(int id) {
        if (id <= 0 || id > Integer.MAX_VALUE) {
            return false;
        }
        
        String verificar = "select status from produtos where id = ?;";
        String sql = "update produtos set status = 'Vendido' where id = ?;";
    
        try (Connection conn = new conectaDAO().connectDB()) {
            try (PreparedStatement prep = conn.prepareStatement(verificar)) {
                prep.setInt(1, id);
                
                try (ResultSet rs = prep.executeQuery()) {
                    while (rs.next()) {
                        if (rs.getString("status").equals("Vendido")) {
                            return false;
                        }
                    }
                }
            }
            
            try (PreparedStatement prep = conn.prepareStatement(sql)) {
                prep.setInt(1, id);
                prep.executeUpdate();
                return true;
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle.getMessage(), sqle);
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "select * from produtos where status = 'Vendido';";
        
        try (
            Connection conn = new conectaDAO().connectDB();
            PreparedStatement prep = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = prep.executeQuery()) {
                ArrayList<ProdutosDTO> produtos = new ArrayList<>();
                while (rs.next()) {
                    produtos.add(new ProdutosDTO(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("valor"),
                            rs.getString("status")
                    ));
                }
                
                return produtos;
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle.getMessage(), sqle);
        }
    }
}
