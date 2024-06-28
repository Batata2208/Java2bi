package telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class CadastraCliente extends JDialog {

    private JTextField NomeCliente;
    private JTextField EmailCliente;
    private ConexaoMysql conexao;
    private JTextField TelefoneCliente;
    private JTextField Cliente_CPF;
    private JTextField EnderecoCliente;
    private JTextField CEPCliente;
    private JTextField BairroCliente;
    private JTable table;

    public CadastraCliente(JFrame parent) {
        super(parent, true); // Torna o diálogo modal
        initialize();
    }

    private void initialize() {
        //conexao = new ConexaoMysql();
        //conexao.Conectar();

        setTitle("CadastrarCliente");
        getContentPane().setBackground(new Color(192, 192, 192));
        setBounds(100, 100, 635, 472);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel UserName = new JLabel("Nome");
        UserName.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserName.setBounds(28, 20, 90, 14);
        getContentPane().add(UserName);

        JLabel UserEmail = new JLabel("Email");
        UserEmail.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserEmail.setBounds(28, 73, 46, 14);
        getContentPane().add(UserEmail);

        NomeCliente = new JTextField();
        NomeCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        NomeCliente.setForeground(new Color(255, 255, 255));
        NomeCliente.setBackground(Color.DARK_GRAY);
        NomeCliente.setBounds(28, 41, 250, 20);
        getContentPane().add(NomeCliente);
        NomeCliente.setColumns(10);

        EmailCliente = new JTextField();
        EmailCliente.setForeground(new Color(255, 255, 255));
        EmailCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        EmailCliente.setBackground(Color.DARK_GRAY);
        EmailCliente.setBounds(28, 94, 250, 20);
        getContentPane().add(EmailCliente);
        EmailCliente.setColumns(10);

        JButton Cadastrar = new JButton("Cadastrar/Atualizar");
        Cadastrar.setForeground(Color.GREEN);
        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        Cadastrar.setFont(new Font("Minecraft", Font.BOLD, 12));
        Cadastrar.setBounds(388, 392, 208, 33);
        getContentPane().add(Cadastrar);
        
        JLabel SexoCliente = new JLabel("Telefone");
        SexoCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        SexoCliente.setBounds(346, 20, 250, 14);
        getContentPane().add(SexoCliente);
        
        TelefoneCliente = new JTextField();
        TelefoneCliente.setForeground(Color.WHITE);
        TelefoneCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        TelefoneCliente.setColumns(10);
        TelefoneCliente.setBackground(Color.DARK_GRAY);
        TelefoneCliente.setBounds(346, 41, 250, 20);
        getContentPane().add(TelefoneCliente);
        
        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblCpf.setBounds(346, 73, 250, 14);
        getContentPane().add(lblCpf);
        
        Cliente_CPF = new JTextField();
        Cliente_CPF.setForeground(Color.WHITE);
        Cliente_CPF.setFont(new Font("Minecraft", Font.BOLD, 14));
        Cliente_CPF.setColumns(10);
        Cliente_CPF.setBackground(Color.DARK_GRAY);
        Cliente_CPF.setBounds(346, 94, 250, 20);
        getContentPane().add(Cliente_CPF);
        
        EnderecoCliente = new JTextField();
        EnderecoCliente.setForeground(Color.WHITE);
        EnderecoCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        EnderecoCliente.setColumns(10);
        EnderecoCliente.setBackground(Color.DARK_GRAY);
        EnderecoCliente.setBounds(28, 196, 568, 20);
        getContentPane().add(EnderecoCliente);
        
        JLabel Endereco = new JLabel("Endereço");
        Endereco.setFont(new Font("Minecraft", Font.BOLD, 14));
        Endereco.setBounds(28, 175, 140, 20);
        getContentPane().add(Endereco);
        
        JLabel CEP = new JLabel("CEP");
        CEP.setFont(new Font("Minecraft", Font.BOLD, 14));
        CEP.setBounds(28, 124, 125, 14);
        getContentPane().add(CEP);
        
        CEPCliente = new JTextField();
        CEPCliente.setForeground(Color.WHITE);
        CEPCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        CEPCliente.setColumns(10);
        CEPCliente.setBackground(Color.DARK_GRAY);
        CEPCliente.setBounds(28, 145, 250, 20);
        getContentPane().add(CEPCliente);
        
        JLabel Bairro = new JLabel("Bairro");
        Bairro.setFont(new Font("Minecraft", Font.BOLD, 14));
        Bairro.setBounds(346, 124, 250, 14);
        getContentPane().add(Bairro);
        
        BairroCliente = new JTextField();
        BairroCliente.setForeground(Color.WHITE);
        BairroCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        BairroCliente.setColumns(10);
        BairroCliente.setBackground(Color.DARK_GRAY);
        BairroCliente.setBounds(346, 145, 250, 20);
        getContentPane().add(BairroCliente);
        
        table = new JTable();
        table.setBounds(28, 241, 568, 137);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desativar redimensionamento automático das colunas
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(28, 241, 568, 137);
        getContentPane().add(scrollPane);
        
        // Adiciona um MouseListener para detectar duplo clique na tabela
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        preencherCampos(selectedRow);
                    }
                }
            }
        });
        
        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deletarUsuario();
        	}
        });
        btnDeletar.setForeground(Color.RED);
        btnDeletar.setFont(new Font("Minecraft", Font.BOLD, 12));
        btnDeletar.setBounds(28, 392, 107, 33);
        getContentPane().add(btnDeletar);
        
        popularTabela();
    }

    @SuppressWarnings("static-access")
    private void cadastrarUsuario() {
        String nome = NomeCliente.getText();
        String email = EmailCliente.getText();
        String telefone = TelefoneCliente.getText();
        String cpf = Cliente_CPF.getText();
        String cep = CEPCliente.getText();
        String ruaCasa = EnderecoCliente.getText();
        String bairro = BairroCliente.getText();

        String sqlCheckCliente = "SELECT id_endereco FROM cliente WHERE CPF_cliente = ?";
        String sqlUpdateCliente = "UPDATE cliente SET nome_cliente = ?, email_cliente = ?, telefone_cliente = ? WHERE CPF_cliente = ?";
        String sqlUpdateEndereco = "UPDATE endereco SET cep = ?, rua_casa = ?, bairro = ? WHERE id_endereco = ?";
        String sqlInsertEndereco = "INSERT INTO endereco (cep, rua_casa, bairro) VALUES (?, ?, ?)";
        String sqlInsertCliente = "INSERT INTO cliente (id_endereco, nome_cliente, CPF_cliente, email_cliente, telefone_cliente) VALUES (?, ?, ?, ?, ?)";

        try {
            conexao.Conectar();

            // Verificar se o cliente já existe
            PreparedStatement statementCheckCliente = conexao.con.prepareStatement(sqlCheckCliente);
            statementCheckCliente.setString(1, cpf);
            ResultSet resultSet = statementCheckCliente.executeQuery();

            if (resultSet.next()) {
                // Cliente existe, realizar atualização
                int idEndereco = resultSet.getInt("id_endereco");

                // Atualizar endereço
                PreparedStatement statementUpdateEndereco = conexao.con.prepareStatement(sqlUpdateEndereco);
                statementUpdateEndereco.setString(1, cep);
                statementUpdateEndereco.setString(2, ruaCasa);
                statementUpdateEndereco.setString(3, bairro);
                statementUpdateEndereco.setInt(4, idEndereco);
                statementUpdateEndereco.executeUpdate();
                statementUpdateEndereco.close();

                // Atualizar cliente
                PreparedStatement statementUpdateCliente = conexao.con.prepareStatement(sqlUpdateCliente);
                statementUpdateCliente.setString(1, nome);
                statementUpdateCliente.setString(2, email);
                statementUpdateCliente.setString(3, telefone);
                statementUpdateCliente.setString(4, cpf);
                statementUpdateCliente.executeUpdate();
                statementUpdateCliente.close();

                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");

            } else {
                // Cliente não existe, realizar inserção

                // Inserir endereço
                PreparedStatement statementInsertEndereco = conexao.con.prepareStatement(sqlInsertEndereco, PreparedStatement.RETURN_GENERATED_KEYS);
                statementInsertEndereco.setString(1, cep);
                statementInsertEndereco.setString(2, ruaCasa);
                statementInsertEndereco.setString(3, bairro);
                statementInsertEndereco.executeUpdate();

                // Recuperar o ID do endereço inserido
                ResultSet generatedKeys = statementInsertEndereco.getGeneratedKeys();
                int idEndereco = 0;
                if (generatedKeys.next()) {
                    idEndereco = generatedKeys.getInt(1);
                }
                statementInsertEndereco.close();

                // Inserir cliente
                PreparedStatement statementInsertCliente = conexao.con.prepareStatement(sqlInsertCliente);
                statementInsertCliente.setInt(1, idEndereco);
                statementInsertCliente.setString(2, nome);
                statementInsertCliente.setString(3, cpf);
                statementInsertCliente.setString(4, email);
                statementInsertCliente.setString(5, telefone);
                statementInsertCliente.executeUpdate();
                statementInsertCliente.close();

                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            }

            resultSet.close();
            statementCheckCliente.close();

            // Atualizar a tabela
            popularTabela();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar ou atualizar o cliente.");
        }
    }

    @SuppressWarnings("static-access")
    private void deletarUsuario() {
        String cpf = Cliente_CPF.getText();

        String sqlDeleteCliente = "DELETE FROM cliente WHERE CPF_cliente = ?";
        String sqlDeleteEndereco = "DELETE FROM endereco WHERE id_endereco = ?";

        try {
            conexao.Conectar();

            // Obter id_endereco a partir do CPF do cliente
            String queryEndereco = "SELECT id_endereco FROM cliente WHERE CPF_cliente = ?";
            PreparedStatement statementQueryEndereco = conexao.con.prepareStatement(queryEndereco);
            statementQueryEndereco.setString(1, cpf);
            ResultSet resultSet = statementQueryEndereco.executeQuery();
            int idEndereco = 0;
            if (resultSet.next()) {
                idEndereco = resultSet.getInt("id_endereco");
            }
            resultSet.close();
            statementQueryEndereco.close();

            // Deletar cliente
            PreparedStatement statementCliente = conexao.con.prepareStatement(sqlDeleteCliente);
            statementCliente.setString(1, cpf);
            statementCliente.executeUpdate();
            statementCliente.close();

            // Deletar endereço
            PreparedStatement statementEndereco = conexao.con.prepareStatement(sqlDeleteEndereco);
            statementEndereco.setInt(1, idEndereco);
            statementEndereco.executeUpdate();
            statementEndereco.close();

            // Mostrar mensagem de sucesso
            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");

            // Atualizar a tabela
            popularTabela();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao deletar o cliente.");
        }
    }

    @SuppressWarnings({ "static-access" })
    private void popularTabela() {
        limparTabela();
        try {
            String query = "SELECT c.nome_cliente, c.email_cliente, c.telefone_cliente, c.CPF_cliente, e.cep, e.rua_casa, e.bairro " +
                           "FROM cliente c " +
                           "INNER JOIN endereco e ON c.id_endereco = e.id_endereco";

            PreparedStatement statement = conexao.con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Definir colunas da tabela
            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Nome", "Email", "Telefone", "CPF", "CEP", "Rua", "Bairro"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Torna todas as células não editáveis
                }
            };
            table.setModel(model);

            // Preencher a tabela com os dados do banco de dados
            while (resultSet.next()) {
                Object[] rowData = {
                    resultSet.getString("nome_cliente"),
                    resultSet.getString("email_cliente"),
                    resultSet.getString("telefone_cliente"),
                    resultSet.getString("CPF_cliente"),
                    resultSet.getString("cep"),
                    resultSet.getString("rua_casa"),
                    resultSet.getString("bairro")
                };
                model.addRow(rowData);
            }

            // Ajustar largura da coluna "Nome"
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(200); // Ajustar largura conforme necessário

            // Fechar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate exceções adequadamente
        }
    }

    private void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    private void preencherCampos(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        NomeCliente.setText((String) model.getValueAt(selectedRow, 0));
        EmailCliente.setText((String) model.getValueAt(selectedRow, 1));
        TelefoneCliente.setText((String) model.getValueAt(selectedRow, 2));
        Cliente_CPF.setText((String) model.getValueAt(selectedRow, 3));
        CEPCliente.setText((String) model.getValueAt(selectedRow, 4));
        EnderecoCliente.setText((String) model.getValueAt(selectedRow, 5));
        BairroCliente.setText((String) model.getValueAt(selectedRow, 6));
    }
}
