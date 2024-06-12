package telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

@SuppressWarnings("unused")
public class CadastraCliente {

    JFrame frame;
    private JTextField NomeCadastro;
    private JTextField EmailCadastro;
    private ConexaoMysql conexao;
    private JTextField textField;
    private JTextField Cliente_CPF;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastraCliente window = new CadastraCliente();
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
    public CadastraCliente() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //conexao = new ConexaoMysql();
        //conexao.Conectar();

    	frame = new JFrame();
        frame.setTitle("CadastrarCliente");
        frame.getContentPane().setBackground(new Color(192, 192, 192));
        frame.setBounds(100, 100, 635, 472);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel UserName = new JLabel("Nome");
        UserName.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserName.setBounds(28, 20, 90, 14);
        frame.getContentPane().add(UserName);

        JLabel UserEmail = new JLabel("Email");
        UserEmail.setFont(new Font("Minecraft", Font.BOLD, 14));
        UserEmail.setBounds(28, 73, 46, 14);
        frame.getContentPane().add(UserEmail);

        NomeCadastro = new JTextField();
        NomeCadastro.setFont(new Font("Minecraft", Font.BOLD, 14));
        NomeCadastro.setForeground(new Color(255, 255, 255));
        NomeCadastro.setBackground(Color.DARK_GRAY);
        NomeCadastro.setBounds(28, 41, 250, 20);
        frame.getContentPane().add(NomeCadastro);
        NomeCadastro.setColumns(10);

        EmailCadastro = new JTextField();
        EmailCadastro.setForeground(new Color(255, 255, 255));
        EmailCadastro.setFont(new Font("Minecraft", Font.BOLD, 14));
        EmailCadastro.setBackground(Color.DARK_GRAY);
        EmailCadastro.setBounds(28, 94, 250, 20);
        frame.getContentPane().add(EmailCadastro);
        EmailCadastro.setColumns(10);

        JButton Cadastrar = new JButton("Cadastrar");
        Cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        Cadastrar.setFont(new Font("Minecraft", Font.BOLD, 12));
        Cadastrar.setBounds(447, 392, 149, 33);
        frame.getContentPane().add(Cadastrar);
        
        JLabel SexoCliente = new JLabel("Telefone");
        SexoCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        SexoCliente.setBounds(346, 20, 250, 14);
        frame.getContentPane().add(SexoCliente);
        
        textField = new JTextField();
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Dialog", Font.BOLD, 14));
        textField.setColumns(10);
        textField.setBackground(Color.DARK_GRAY);
        textField.setBounds(346, 41, 250, 20);
        frame.getContentPane().add(textField);
        
        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setFont(new Font("Dialog", Font.BOLD, 14));
        lblCpf.setBounds(346, 73, 250, 14);
        frame.getContentPane().add(lblCpf);
        
        Cliente_CPF = new JTextField();
        Cliente_CPF.setForeground(Color.WHITE);
        Cliente_CPF.setFont(new Font("Dialog", Font.BOLD, 14));
        Cliente_CPF.setColumns(10);
        Cliente_CPF.setBackground(Color.DARK_GRAY);
        Cliente_CPF.setBounds(346, 94, 250, 20);
        frame.getContentPane().add(Cliente_CPF);
        
        textField_1 = new JTextField();
        textField_1.setForeground(Color.WHITE);
        textField_1.setFont(new Font("Dialog", Font.BOLD, 14));
        textField_1.setColumns(10);
        textField_1.setBackground(Color.DARK_GRAY);
        textField_1.setBounds(28, 196, 568, 20);
        frame.getContentPane().add(textField_1);
        
        JLabel Endereco = new JLabel("Endereco");
        Endereco.setFont(new Font("Dialog", Font.BOLD, 14));
        Endereco.setBounds(28, 175, 90, 14);
        frame.getContentPane().add(Endereco);
        
        JLabel CEP = new JLabel("CEP");
        CEP.setFont(new Font("Dialog", Font.BOLD, 14));
        CEP.setBounds(28, 124, 125, 14);
        frame.getContentPane().add(CEP);
        
        textField_2 = new JTextField();
        textField_2.setForeground(Color.WHITE);
        textField_2.setFont(new Font("Dialog", Font.BOLD, 14));
        textField_2.setColumns(10);
        textField_2.setBackground(Color.DARK_GRAY);
        textField_2.setBounds(28, 145, 250, 20);
        frame.getContentPane().add(textField_2);
        
        JLabel Bairro = new JLabel("Bairro");
        Bairro.setFont(new Font("Dialog", Font.BOLD, 14));
        Bairro.setBounds(346, 124, 250, 14);
        frame.getContentPane().add(Bairro);
        
        textField_3 = new JTextField();
        textField_3.setForeground(Color.WHITE);
        textField_3.setFont(new Font("Dialog", Font.BOLD, 14));
        textField_3.setColumns(10);
        textField_3.setBackground(Color.DARK_GRAY);
        textField_3.setBounds(346, 145, 250, 20);
        frame.getContentPane().add(textField_3);
        
        table = new JTable();
        table.setBounds(31, 241, 565, 137);
        frame.getContentPane().add(table);
    }

    @SuppressWarnings("static-access")
	private void cadastrarUsuario() {
        String nome = NomeCadastro.getText();
        String email = EmailCadastro.getText();

        // Consulta SQL para inserir um novo usuário
        String sql = "INSERT INTO usuarios (usuario, email, senha) VALUES (?, ?, ?)";

        try {
            // Preparar a declaração SQL
            PreparedStatement statement = conexao.con.prepareStatement(sql);

            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
            statement.setString(1, nome);
            statement.setString(2, email);

            // Executar a atualização
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            }

            // Fechar o statement
            statement.close();

            // Fechar a tela de cadastro e voltar para a tela de login
            frame.dispose();
            //Login loginWindow = new Login();
            // loginWindow.frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o usuário.");
        }
    }
    
    @SuppressWarnings("static-access")
    private void popularTabela(String selectedChoice) {
    	limparTabela();	
    	try {
        	 String query = "select ";
                        
			PreparedStatement statement = conexao.con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Preencher a tabela com os dados do banco de dados
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                Object[] rowData = new Object[model.getColumnCount()];
                for (int i = 0; i < model.getColumnCount(); i++) {
                    rowData[i] = resultSet.getObject(i + 1);     
                }
                model.addRow(rowData);
            }
            
         // Defina a largura das colunas para preencher toda a largura da tabela
            TableColumnModel columnModel = table.getColumnModel();
            int tableWidth = table.getWidth();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);
                column.setPreferredWidth(tableWidth / columnModel.getColumnCount());
            }
            
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
}