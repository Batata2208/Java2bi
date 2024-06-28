package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

    private ConexaoMysql conexao;

    @SuppressWarnings("static-access")
    public TelaPrincipal(String usuarioLogado) {
        conexao = new ConexaoMysql();
        conexao.Conectar();

        setTitle("Game Point");
        setSize(812, 666);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel logoLabel = new JLabel(new ImageIcon(TelaPrincipal.class.getResource("/imgs/file (3).png")));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(logoLabel, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel();
        mainPanel.add(menuPanel, BorderLayout.WEST);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuLabel);

        JButton clientesButton = new JButton("Clientes");
        clientesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraCliente dialog = new CadastraCliente(TelaPrincipal.this);
                dialog.setVisible(true);
            }
        });
        clientesButton.setFont(new Font("Minecraft", Font.BOLD, 14));

        JButton produtosButton = new JButton("Produtos");
        produtosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroMudarProduto dialog = new CadastroMudarProduto(TelaPrincipal.this);
                dialog.setVisible(true);
            }
        });
        produtosButton.setFont(new Font("Minecraft", Font.BOLD, 14));

        JButton bttVendas = new JButton("Vendas");
        bttVendas.setFont(new Font("Minecraft", Font.BOLD, 14));
        bttVendas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new TelaDeVendas();
            }
        });

        JButton funcionariosButton = new JButton("Funcionários");
        funcionariosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroFuncionarios dialog = new CadastroFuncionarios(TelaPrincipal.this);
                dialog.setVisible(true);
            }
        });
        funcionariosButton.setFont(new Font("Minecraft", Font.BOLD, 14));

        clientesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        produtosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bttVendas.setAlignmentX(Component.CENTER_ALIGNMENT);
        funcionariosButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 45)));
        menuPanel.add(clientesButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(produtosButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(bttVendas);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(funcionariosButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("Logado como: " + usuarioLogado);
        statusLabel.setHorizontalAlignment(JLabel.RIGHT);
        statusPanel.add(statusLabel, BorderLayout.EAST);

        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
