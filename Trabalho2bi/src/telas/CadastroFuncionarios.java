package telas;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class CadastroFuncionarios extends JDialog{
    
    private JTextField NomeUser;
    private JTextField EmailUser;
    private JTextField SenhaUser;
    private ConexaoMysql conexao;
    private JTable table;
    private JScrollPane scrollPane;
    
    @SuppressWarnings("static-access")
	public CadastroFuncionarios(JFrame parent) {
        super(parent, "Cadastro de Funcionários", true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        conexao = new ConexaoMysql();
        conexao.Conectar();
        initialize();
    }
    
	private void initialize() {        
        setBounds(100, 100, 600, 457);
        getContentPane().setLayout(null);

        JLabel UserName = new JLabel("Nome");
        UserName.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserName.setBounds(29, 93, 90, 14);
        getContentPane().add(UserName);

        JLabel UserEmail = new JLabel("Email");
        UserEmail.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserEmail.setBounds(29, 145, 46, 14);
        getContentPane().add(UserEmail);

        NomeUser = new JTextField();
        NomeUser.setFont(new Font("Minecraft", Font.BOLD, 14));
        NomeUser.setForeground(new Color(255, 255, 255));
        NomeUser.setBackground(Color.DARK_GRAY);
        NomeUser.setBounds(29, 114, 250, 20);
        getContentPane().add(NomeUser);
        NomeUser.setColumns(10);

        EmailUser = new JTextField();
        EmailUser.setForeground(new Color(255, 255, 255));
        EmailUser.setFont(new Font("Minecraft", Font.BOLD, 14));
        EmailUser.setBackground(Color.DARK_GRAY);
        EmailUser.setBounds(29, 162, 528, 20);
        getContentPane().add(EmailUser);
        EmailUser.setColumns(10);

        JLabel UserSenha = new JLabel("Senha");
        UserSenha.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserSenha.setBounds(307, 93, 90, 14);
        getContentPane().add(UserSenha);

        JButton Cadastrar = new JButton("Cadastrar/Atualizar");
        Cadastrar.setForeground(Color.GREEN);
        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
                popularTabela();
            }
        });
        Cadastrar.setFont(new Font("Minecraft", Font.BOLD, 12));
        Cadastrar.setBounds(354, 374, 203, 33);
        getContentPane().add(Cadastrar);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(CadastroFuncionarios.class.getResource("/imgs/fotor-20240612224554.png")));
        lblNewLabel.setBounds(241, 0, 78, 82);
        getContentPane().add(lblNewLabel);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.setForeground(Color.RED);
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletarUsuario();
                popularTabela();
            }
        });
        btnDeletar.setFont(new Font("Minecraft", Font.BOLD, 12));
        btnDeletar.setBounds(29, 374, 121, 33);
        getContentPane().add(btnDeletar);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        model.addColumn("Nome");
        model.addColumn("Email");
        model.addColumn("Senha");
        table.setDefaultEditor(Object.class, null);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int row = table.getSelectedRow();
                    NomeUser.setText(model.getValueAt(row, 0).toString());
                    EmailUser.setText(model.getValueAt(row, 1).toString());
                    SenhaUser.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(50, 500));
        scrollPane.setBounds(29, 193, 528, 174);
        getContentPane().add(scrollPane);
        
        SenhaUser = new JTextField();
        SenhaUser.setForeground(Color.WHITE);
        SenhaUser.setFont(new Font("Minecraft", Font.BOLD, 14));
        SenhaUser.setColumns(10);
        SenhaUser.setBackground(Color.DARK_GRAY);
        SenhaUser.setBounds(307, 114, 250, 20);
        getContentPane().add(SenhaUser);
        TableColumnModel columnModel = table.getColumnModel();
        int tableWidth = scrollPane.getWidth();
        columnModel.getColumn(0).setPreferredWidth((int) (tableWidth * 0.4));
        columnModel.getColumn(1).setPreferredWidth((int) (tableWidth * 0.3));
        columnModel.getColumn(2).setPreferredWidth((int) (tableWidth * 0.3));

        // Populate the table initially
        popularTabela();
    }

    @SuppressWarnings("static-access")
    private void popularTabela() {
        limparTabela();
        try {
            String query = "SELECT usuario, email, senha FROM usuarios";
            PreparedStatement statement = conexao.con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                Object[] rowData = new Object[model.getColumnCount()];
                for (int i = 0; i < model.getColumnCount(); i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                model.addRow(rowData);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("static-access")
    private void cadastrarUsuario() {
        String nome = NomeUser.getText();
        String email = EmailUser.getText();
        String senha = SenhaUser.getText();

        // Consulta SQL para verificar se o e-mail já existe
        String sqlCheck = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

        // Consulta SQL para inserir um novo usuário
        String sqlInsert = "INSERT INTO usuarios (usuario, email, senha) VALUES (?, ?, ?)";

        // Consulta SQL para atualizar um usuário existente
        String sqlUpdate = "UPDATE usuarios SET usuario = ?, senha = ? WHERE email = ?";

        try {
            // Preparar a declaração SQL para verificação
            PreparedStatement checkStatement = conexao.con.prepareStatement(sqlCheck);
            checkStatement.setString(1, email);

            // Executar a verificação
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            // Fechar o resultSet e checkStatement
            resultSet.close();
            checkStatement.close();

            PreparedStatement statement;
            if (count > 0) {
                // Preparar a declaração SQL para atualização
                statement = conexao.con.prepareStatement(sqlUpdate);
                statement.setString(1, nome);
                statement.setString(2, senha);
                statement.setString(3, email);
            } else {
                // Preparar a declaração SQL para inserção
                statement = conexao.con.prepareStatement(sqlInsert);
                statement.setString(1, nome);
                statement.setString(2, email);
                statement.setString(3, senha);
            }

            // Executar a inserção ou atualização
            int rowsAffected = statement.executeUpdate();

            // Fechar o statement
            statement.close();

            // Mostrar uma mensagem de sucesso
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado/atualizado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar/atualizar o usuário.");
        }
    }

    private void deletarUsuario() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um usuário para deletar.");
            return;
        }

        String email = table.getModel().getValueAt(row, 1).toString();

        // Consulta SQL para deletar um usuário
        String sqlDelete = "DELETE FROM usuarios WHERE email = ?";

        try {
            @SuppressWarnings("static-access")
            PreparedStatement statement = conexao.con.prepareStatement(sqlDelete);
            statement.setString(1, email);

            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja deletar este usuário?", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
                }
            }

            // Fechar o statement
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao deletar o usuário.");
        }
    }

    private void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
}
