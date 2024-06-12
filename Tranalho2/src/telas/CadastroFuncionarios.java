package telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class CadastroFuncionarios {

    protected JFrame frame;
    private JTextField NomeCliente;
    private JTextField EmailCliente;
    private JPasswordField TelefoneCliente;
    private ConexaoMysql conexao;

    /**
     * Launch the application.
     */

    /**
     * Create the application.
     */
    public CadastroFuncionarios() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //conexao = new ConexaoMysql();
        //conexao.Conectar();

        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBounds(100, 100, 616, 438);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel UserName = new JLabel("Nome");
        UserName.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserName.setBounds(28, 93, 90, 14);
        frame.getContentPane().add(UserName);

        JLabel UserEmail = new JLabel("Email");
        UserEmail.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserEmail.setBounds(28, 141, 46, 14);
        frame.getContentPane().add(UserEmail);

        NomeCliente = new JTextField();
        NomeCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        NomeCliente.setForeground(new Color(255, 255, 255));
        NomeCliente.setBackground(Color.DARK_GRAY);
        NomeCliente.setBounds(28, 114, 250, 20);
        frame.getContentPane().add(NomeCliente);
        NomeCliente.setColumns(10);

        EmailCliente = new JTextField();
        EmailCliente.setForeground(new Color(255, 255, 255));
        EmailCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        EmailCliente.setBackground(Color.DARK_GRAY);
        EmailCliente.setBounds(28, 162, 250, 20);
        frame.getContentPane().add(EmailCliente);
        EmailCliente.setColumns(10);

        JLabel UserSenha = new JLabel("Telefone");
        UserSenha.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserSenha.setBounds(326, 93, 90, 14);
        frame.getContentPane().add(UserSenha);

        TelefoneCliente = new JPasswordField();
        TelefoneCliente.setForeground(new Color(255, 255, 255));
        TelefoneCliente.setBackground(Color.DARK_GRAY);
        TelefoneCliente.setBounds(326, 114, 250, 20);
        frame.getContentPane().add(TelefoneCliente);

        JButton Cadastrar = new JButton("Cadastrar");
        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        Cadastrar.setFont(new Font("Minecraft", Font.BOLD, 12));
        Cadastrar.setBounds(405, 355, 149, 33);
        frame.getContentPane().add(Cadastrar);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(CadastroFuncionarios.class.getResource("/imgs/icon.png")));
        lblNewLabel.setBounds(115, 11, 70, 71);
        frame.getContentPane().add(lblNewLabel);
        
        JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
        rdbtnNewRadioButton.setBounds(467, 164, 109, 23);
        frame.getContentPane().add(rdbtnNewRadioButton);
    }

    @SuppressWarnings("static-access")
	private void cadastrarUsuario() {
        String nome = NomeCliente.getText();
        String email = EmailCliente.getText();
        String senha = new String(TelefoneCliente.getPassword());

        // Consulta SQL para inserir um novo usuário
        String sql = "INSERT INTO usuarios (usuario, email, senha) VALUES (?, ?, ?)";

        try {
            // Preparar a declaração SQL
            PreparedStatement statement = conexao.con.prepareStatement(sql);

            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);

            // Executar a atualização
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            }

            // Fechar o statement
            statement.close();

            // Fechar a tela de cadastro e voltar para a tela de login
            frame.dispose();
            Login loginWindow = new Login();
            loginWindow.frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o usuário.");
        }
    }
}