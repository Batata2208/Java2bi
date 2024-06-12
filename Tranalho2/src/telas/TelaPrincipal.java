package telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.JTable;


@SuppressWarnings("unused")
public class TelaPrincipal {

    public JFrame frame;
	private ConexaoMysql conexao;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    /**
     * Create the application.
     */
    public TelaPrincipal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings("static-access")
	private void initialize() {
    	conexao = new ConexaoMysql();
    	conexao.Conectar();
    	
        frame = new JFrame();
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/imgs/icon.png")));
		frame.setBounds(100, 100, 894, 496);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
        	            public void actionPerformed(ActionEvent e) {
        	                // Inicializa e mostra a tela de login
        	                new Login().frame.setVisible(true);

        	            }
        	        });
        btnLogin.setBounds(767, 21, 89, 23);
        frame.getContentPane().add(btnLogin);
        
        JButton btnNewButton = new JButton("Cadastrar/Atualizar");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new CadastroMudarProduto().frame.setVisible(true);
        	}
        });
        btnNewButton.setBounds(304, 21, 263, 23);
        frame.getContentPane().add(btnNewButton);
        
        table = new JTable();
        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setBounds(87, 132, 682, 256);
        frame.getContentPane().add(table);
        
        JButton cadastrarCliente = new JButton("CadastrarClientes");
        cadastrarCliente.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new CadastraCliente().frame.setVisible(true);
        	}
        });
        cadastrarCliente.setBounds(45, 21, 161, 23);
        frame.getContentPane().add(cadastrarCliente);
        

       
    }
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}