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
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Login extends JFrame {

    public JFrame frame;
    private JTextField TextUser;
    private JPasswordField SenhaUser;
    private ConexaoMysql conexao;

    public Login() {
        initialize();
    }

    @SuppressWarnings("static-access")
	private void initialize() {
        conexao = new ConexaoMysql();
        conexao.Conectar();

        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBounds(100, 100, 283, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel Emailuser = new JLabel("Usuario");
        Emailuser.setFont(new Font("Minecraft", Font.BOLD, 14));
        Emailuser.setBounds(25, 88, 111, 26);
        frame.getContentPane().add(Emailuser);

        TextUser = new JTextField();
        TextUser.setFont(new Font("Minecraft", Font.BOLD, 14));
        TextUser.setForeground(Color.WHITE);
        TextUser.setBackground(Color.DARK_GRAY);
        TextUser.setBounds(25, 109, 220, 26);
        frame.getContentPane().add(TextUser);
        TextUser.setColumns(10);

        JLabel Senhauser = new JLabel("Senha");
        Senhauser.setFont(new Font("Minecraft", Font.BOLD, 14));
        Senhauser.setBounds(25, 163, 60, 26);
        frame.getContentPane().add(Senhauser);

        SenhaUser = new JPasswordField();
        SenhaUser.setFont(new Font("Minecraft", Font.BOLD, 14));
        SenhaUser.setForeground(Color.WHITE);
        SenhaUser.setBackground(Color.DARK_GRAY);
        SenhaUser.setBounds(23, 184, 222, 26);
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
        Logar.setBounds(65, 246, 132, 26);
        frame.getContentPane().add(Logar);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/imgs/fotor-20240612224554.png")));
        lblNewLabel.setBounds(90, 11, 78, 76);
        frame.getContentPane().add(lblNewLabel);
    }

    @SuppressWarnings("static-access")
    public void ValidaLogin() {
        String sql = "SELECT usuario FROM usuarios WHERE usuario = ? AND senha = ?";

        try {
            PreparedStatement statement = conexao.con.prepareStatement(sql);
            statement.setString(1, TextUser.getText());
            statement.setString(2, new String(SenhaUser.getPassword())); 

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String usuario = result.getString("usuario");
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                frame.dispose(); // Fechar a tela de login
                new TelaPrincipal(usuario); // Abrir a tela principal passando o nome do usuário
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
