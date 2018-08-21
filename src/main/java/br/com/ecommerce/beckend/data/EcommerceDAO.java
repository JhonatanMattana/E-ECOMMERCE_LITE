package br.com.ecommerce.beckend.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ecommerce.beckend.infra.ConexaoJDBC;
import br.com.ecommerce.beckend.infra.ConexaoPostgresJDBC;

public class EcommerceDAO {

	private final ConexaoJDBC conexao;

    public EcommerceDAO() throws SQLException, ClassNotFoundException {
        this.conexao = new ConexaoPostgresJDBC();
    }

    public Long inserir(Produto produto) throws SQLException, ClassNotFoundException {
        Long id = null;
        String sqlQuery = "INSERT INTO Produto (produto, quantidadeEstoque, preco, status) VALUES (?, ?, ?, ?) RETURNING id";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, produto.getProduto());
            stmt.setInt(2, produto.getQuantidadeEstoque());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getStatus().toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }

            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return id;
    }

    public int alterar(Produto produto) throws SQLException, ClassNotFoundException {
        String sqlQuery = "UPDATE Produto SET produto = ?, quantidadeEstoque = ?, preco = ?, status = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, produto.getProduto());
            stmt.setInt(2, produto.getQuantidadeEstoque());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getStatus().toString());
            stmt.setLong(5, produto.getId());

            linhasAfetadas = stmt.executeUpdate();
            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return linhasAfetadas;
    }

    public int excluir(long id) throws SQLException, ClassNotFoundException {
        int linhasAlfetadas = 0;
        String sqlQuery = "DELETE FROM Produto WHERE id = ?";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setLong(1, id);
            linhasAlfetadas = stmt.executeUpdate();
            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return linhasAlfetadas;
    }

    public Produto selecionar(long id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM Produto WHERE id = ?";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return parser(rs);
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<Produto> listar() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM Produto ORDER BY id";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery();

            List<Produto> produtos = new ArrayList<>();

            while (rs.next()) {
                produtos.add(parser(rs));
            }

            return produtos;
        } catch (SQLException e) {
            throw e;
        }
    }

    private Produto parser(ResultSet resultSet) throws SQLException {
        Produto p = new Produto();

        p.setId(resultSet.getLong("id"));
        p.setProduto(resultSet.getString("produto"));
        p.setQuantidadeEstoque(resultSet.getInt("quantidadeEstoque"));
        p.setPreco(resultSet.getDouble("preco"));
        p.setStatus(Status.valueOf(resultSet.getString("status")));

        return p;
    }
}
