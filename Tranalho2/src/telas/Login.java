package telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame {

    JFrame frame;
    private JTextField TextUser;
    private JPasswordField SenhaUser;
    private ConexaoMysql conexao;

    /**
     * Create the application.
     */
    public Login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    //@SuppressWarnings("static-access")
	private void initialize() {
        // Estabelece a conexão com o banco de dados
        //conexao = new ConexaoMysql();
        //conexao.Conectar();

        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBounds(100, 100, 320, 382);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel Emailuser = new JLabel("Email/User");
        Emailuser.setFont(new Font("Minecraft", Font.BOLD, 14));
        Emailuser.setBounds(25, 88, 111, 26);
        frame.getContentPane().add(Emailuser);

        TextUser = new JTextField();
        TextUser.setForeground(Color.WHITE);
        TextUser.setBackground(Color.DARK_GRAY);
        TextUser.setBounds(25, 109, 250, 26);
        frame.getContentPane().add(TextUser);
        TextUser.setColumns(10);

        JLabel Senhauser = new JLabel("Senha");
        Senhauser.setFont(new Font("Minecraft", Font.BOLD, 14));
        Senhauser.setBounds(25, 163, 60, 26);
        frame.getContentPane().add(Senhauser);

        SenhaUser = new JPasswordField();
        SenhaUser.setForeground(Color.WHITE);
        SenhaUser.setBackground(Color.DARK_GRAY);
        SenhaUser.setBounds(23, 184, 250, 26);
        frame.getContentPane().add(SenhaUser);

        JButton Logar = new JButton("Logar");
        Logar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ValidaLogin();
            }
        });
        Logar.setForeground(Color.BLACK);
        Logar.setBackground(new Color(134, 137, 145));
        Logar.setFont(new Font("Minecraft", Font.BOLD, 14));
        Logar.setBounds(176, 241, 97, 26);
        frame.getContentPane().add(Logar);

        JButton Cadastro = new JButton("Cadastrar");
        Cadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CadastraCliente().frame.setVisible(true);
                frame.dispose();
            }
        });
        Cadastro.setForeground(new Color(255, 0, 0));
        Cadastro.setFont(new Font("Minecraft", Font.BOLD, 10));
        Cadastro.setBackground(new Color(134, 137, 145));
        Cadastro.setBounds(25, 241, 97, 26);
        frame.getContentPane().add(Cadastro);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/imgs/icon.png")));
        lblNewLabel.setBounds(115, 11, 66, 71);
        frame.getContentPane().add(lblNewLabel);
    }
    
    @SuppressWarnings("static-access")
    public void ValidaLogin() {
        // Consulta SQL para selecionar o usuário com base no email e senha fornecidos
        String sql = "SELECT usuario FROM usuarios WHERE usuario = ? AND senha = ?";

        try {
            // Preparar a declaração SQL
            PreparedStatement statement = conexao.con.prepareStatement(sql);

            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de login
            statement.setString(1, TextUser.getText());
            statement.setString(2, new String(SenhaUser.getPassword())); // Use getPassword() para recuperar a senha como array de caracteres

            // Executar a consulta
            ResultSet result = statement.executeQuery();

            // Verificar se a consulta retornou algum resultado
            if (result.next()) {
                // Se sim, o login é válido
                JOptionPane.showMessageDialog(null, "foi");
            } else {
                // Se não, o login é inválido
                JOptionPane.showMessageDialog(null, "nao foi");
            }
            // Fechar o result set e o statement
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
