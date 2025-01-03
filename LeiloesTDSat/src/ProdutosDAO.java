
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){           
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        conn = new conectaDAO().connectDB();  // Certifique-se de que a classe ConectaDAO existe e funciona
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }      
    }
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    conn = new conectaDAO().connectDB();
    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);
        prep.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
  
    public ArrayList<ProdutosDTO> listarProdutos(){     
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }            
        return listagem;
    }
    
   public ArrayList<ProdutosDTO> listarProdutosVendidos() { 
       ArrayList<ProdutosDTO> vendidos = new ArrayList<>(); 
       String sql = "SELECT * FROM produtos WHERE status = 'Vendido'"; 
       conn = new conectaDAO().connectDB(); 
       try { 
           prep = conn.prepareStatement(sql); 
           resultset = prep.executeQuery(); 
       while (resultset.next()) { 
           ProdutosDTO produto = new ProdutosDTO(); 
           produto.setId(resultset.getInt("id")); 
           produto.setNome(resultset.getString("nome")); 
           produto.setValor(resultset.getInt("valor")); 
           produto.setStatus(resultset.getString("status")); 
           vendidos.add(produto); 
        }
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage()); 
        } finally { 
           try { 
           if (resultset != null) resultset.close(); 
           if (prep != null) prep.close(); 
           if (conn != null) conn.close(); 
           } catch (SQLException e) { 
           JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage()); 
           } 
           } 
        return vendidos; 
   }      
}
