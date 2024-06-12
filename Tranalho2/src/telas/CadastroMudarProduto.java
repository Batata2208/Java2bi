package telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Choice;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Dimension;

@SuppressWarnings("unused")
public class CadastroMudarProduto {

    JFrame frame;
    private JTextField NomeProduto;
    private JTextField PrecoProduto;
    private Choice choice;
   
    private ConexaoMysql conexao;
    private Statement statement;
    private ResultSet resultSet;
    private JTable table;
    private JScrollPane scrollPane;
    
    /**
     * Create the application.
     */
    public CadastroMudarProduto() {
        initialize();
    } 
       

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setBounds(100, 100, 616, 495);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Escolha o Produto a ser Cadastrado");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel.setBounds(40, 24, 344, 23);
        frame.getContentPane().add(lblNewLabel);

        choice = new Choice();
        choice.setFont(new Font("Minecraft", Font.BOLD, 14));
        choice.setForeground(Color.WHITE);
        choice.setBackground(Color.DARK_GRAY);
        choice.setBounds(40, 47, 234, 20);
        frame.getContentPane().add(choice);
        choice.add("Jogos");
        choice.add("Perifericos");
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String selectedChoice = choice.getSelectedItem();
                popularTabela(selectedChoice);
            }
        });

        NomeProduto = new JTextField();
        NomeProduto.setFont(new Font("Minecraft", Font.BOLD, 14));
        NomeProduto.setForeground(Color.WHITE);
        NomeProduto.setBackground(Color.DARK_GRAY);
        NomeProduto.setBounds(40, 120, 234, 20);
        frame.getContentPane().add(NomeProduto);
        NomeProduto.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Nome do Produto");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel_1.setBounds(40, 100, 176, 20);
        frame.getContentPane().add(lblNewLabel_1);

        PrecoProduto = new JTextField();
        PrecoProduto.setForeground(Color.WHITE);
        PrecoProduto.setFont(new Font("Minecraft", Font.BOLD, 14));
        PrecoProduto.setBackground(Color.DARK_GRAY);
        PrecoProduto.setColumns(10);
        PrecoProduto.setBounds(323, 120, 234, 20);
        frame.getContentPane().add(PrecoProduto);

        JLabel lblNewLabel_1_1 = new JLabel("Preco");
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(323, 100, 176, 20);
        frame.getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Produtos");
        lblNewLabel_1_2.setForeground(Color.WHITE);
        lblNewLabel_1_2.setFont(new Font("Minecraft", Font.BOLD, 18));
        lblNewLabel_1_2.setBounds(40, 151, 176, 30);
        frame.getContentPane().add(lblNewLabel_1_2);          

        JButton btnNewButton = new JButton("Cadastrar/Atualizar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });
        btnNewButton.setForeground(Color.GREEN);
        btnNewButton.setFont(new Font("Minecraft", Font.BOLD, 14));
        btnNewButton.setBounds(323, 402, 234, 30);
        frame.getContentPane().add(btnNewButton);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeletarProduto();
            }
        });
        btnDeletar.setForeground(Color.RED);
        btnDeletar.setFont(new Font("Minecraft", Font.BOLD, 14));
        btnDeletar.setBounds(40, 402, 149, 30);
        frame.getContentPane().add(btnDeletar);
        
     // Criando o modelo de tabela
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

     // Criando o modelo de tabela
        DefaultTableModel model1 = new DefaultTableModel();
        table = new JTable(model1);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // Adicionando as colunas à tabela
        model1.addColumn("Nome");
        model1.addColumn("Preco");      
        // Desabilitar edição direta na tabela
        table.setDefaultEditor(Object.class, null);
        
        // Adicione um MouseListener para detectar cliques na tabela
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifique se foi um duplo clique
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int row = table.getSelectedRow();
                    // Preencher os campos com os dados da linha clicada
                    NomeProduto.setText(model.getValueAt(row, 0).toString());
                    PrecoProduto.setText(model.getValueAt(row, 1).toString());
                }
            }
        });


        // Adicione uma JScrollPane para permitir a rolagem vertical
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(50, 500));
        scrollPane.setBounds(40, 192, 517, 191);
        frame.getContentPane().add(scrollPane);
        
    }
    
    @SuppressWarnings("static-access")
    private void popularTabela(String selectedChoice) {
    	limparTabela();
        String selectedOption = choice.getSelectedItem();   	
    	try {
        	 String query = "";
             
             if(selectedOption.equals("Jogos")) {
                 query = "SELECT nome_jogo, preco_jogo FROM jogos";
             } else if(selectedOption.equals("Perifericos")) {
                 query = "SELECT nome_perif, preco_perif FROM perifericos";
             }
          
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
    
    @SuppressWarnings("static-access")
    private void cadastrarProduto() {
        String nome = NomeProduto.getText();
        String preco = PrecoProduto.getText();
        String selectedOption = choice.getSelectedItem();
		String sql = "";
        
        try {
        	if(selectedOption.equals("Jogos")) {
	            sql = "SELECT nome_jogo FROM jogos where nome_jogo = ?";
	        } else if(selectedOption.equals("Perifericos")) {
	            sql = "SELECT nome_perif FROM perifericos where nome_perif = ?";
	        }
        	
            // Preparar a declaração SQL
            PreparedStatement statement = conexao.con.prepareStatement(sql);

            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
            statement.setString(1, nome);

            // Executar a consulta
            ResultSet result = statement.executeQuery();

            // Verificar se a consulta retornou algum resultado
            if (result.next()) {
                // Se sim, o cadastrp é inválido
           	 int i = JOptionPane.showConfirmDialog(null, "Produto ja existe deseja atualiza-lo?", null, JOptionPane.YES_NO_OPTION);
            	if(i == JOptionPane.YES_OPTION) {
            		
            		if(selectedOption.equals("Jogos")) {
			            sql = "UPDATE jogos SET preco_jogo = ? WHERE nome_jogo = ? ";
			        } else if(selectedOption.equals("Perifericos")) {
			            sql = "UPDATE perifericos SET preco_perif = ? where nome_perif = ?";
			        }
			        
			        // Preparar a declaração SQL
					PreparedStatement statement1 = conexao.con.prepareStatement(sql);
					
		            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
		            statement1.setString(1, preco);
		            statement1.setString(2, nome);
		            
		            // Executar a atualização
		            int rowsInserted = statement1.executeUpdate();
		            if (rowsInserted > 0) {
		                JOptionPane.showMessageDialog(null, "Produto Atualizado com sucesso");
		            }		           
		            
		            // Fechar o statement
		            statement1.close();
		            popularTabela(selectedOption);
            	}
            	else if(i == JOptionPane.NO_OPTION) {
            	    JOptionPane.showMessageDialog(null, "Produto, não foi atualizado e não foi cadastrado");
            	}
            } else {
                 try {
		        	
			        if(selectedOption.equals("Jogos")) {
			            sql = "INSERT INTO jogos (nome_jogo, preco_jogo) VALUES (?, ?) ";
			        } else if(selectedOption.equals("Perifericos")) {
			            sql = "INSERT INTO perifericos (nome_perif, preco_perif) VALUES (?, ?) ";
			        }
			        
			        // Preparar a declaração SQL
					PreparedStatement statement1 = conexao.con.prepareStatement(sql);
					
		            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
		            statement1.setString(1, nome);
		            statement1.setString(2, preco);
		            
		            // Executar a atualização
		            int rowsInserted = statement1.executeUpdate();
		            if (rowsInserted > 0) {
		                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
		            }
		
		            // Fechar o statement
		            statement1.close();
				} catch (SQLException e) {
					 e.printStackTrace();
			         JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto");
				}
		            }
		            // Fechar o result set e o statement
		            result.close();
		            statement.close();
	                popularTabela(selectedOption);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
    }
    
    @SuppressWarnings("static-access")
    private void DeletarProduto() {
    	 String nome = NomeProduto.getText();
         String preco = PrecoProduto.getText();
         String selectedOption = choice.getSelectedItem();
         String sql = "";
         
         try {
         	if(selectedOption.equals("Jogos")) {
 	            sql = "SELECT nome_jogo FROM jogos where nome_jogo = ?";
 	        } else if(selectedOption.equals("Perifericos")) {
 	            sql = "SELECT nome_perif FROM perifericos where nome_perif = ?";
 	        }
         	
             // Preparar a declaração SQL
			PreparedStatement statement = conexao.con.prepareStatement(sql);

             // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
             statement.setString(1, nome);

             // Executar a consulta
             ResultSet result = statement.executeQuery();

             // Verificar se a consulta retornou algum resultado
             if (result.next()) {
            	 //se sim ele deleta
            	 
            	 int i = JOptionPane.showConfirmDialog(null, "Deseja realmente apagar o produtos", null, JOptionPane.YES_NO_OPTION);
            	     if(i == JOptionPane.YES_OPTION) {
            		 if(selectedOption.equals("Jogos")) {
			            sql = "DELETE FROM jogos WHERE nome_jogo = ? ";
			        } else if(selectedOption.equals("Perifericos")) {
			            sql = "DELETE FROM perifericos WHERE nome_perif = ? ";
			        }
			        
			        // Preparar a declaração SQL
					PreparedStatement statement1 = conexao.con.prepareStatement(sql);
					
		            // Substituir os parâmetros da consulta pelos valores dos campos do formulário de cadastro
		            statement1.setString(1, nome);
		            
		            // Executar a atualização
		            int rowsInserted = statement1.executeUpdate();
		            if (rowsInserted > 0) {
		                JOptionPane.showMessageDialog(null, "Produto deletado com sucesso");
		            }		           
		            
		            // Fechar o statement
		            statement1.close();
		            popularTabela(selectedOption);
            	 } else {
          			JOptionPane.showMessageDialog(null, "nenhum produto foi apagado");         			
            	 }            	           	 
            	          	       
             	} else{
         			//se não estiver selecionado
         			JOptionPane.showMessageDialog(null, "nenhum produto selecionado");         			
         		}           	 
	         } catch (SQLException e) {
			 e.printStackTrace();
	         JOptionPane.showMessageDialog(null, "erro ao apagar");
		}
  }
    
    private void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    
}