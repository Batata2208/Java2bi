package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Choice;

@SuppressWarnings({ "unused", "serial" })
public class CadastroMudarProduto extends JDialog {

    private JTextField NomeProduto;
    private JTextField PrecoProduto;
    private Choice choice;

    private ConexaoMysql conexao;
    private JTable table;
    private JScrollPane scrollPane;

    @SuppressWarnings("static-access")
	public CadastroMudarProduto(JFrame parent) {
        super(parent, "Cadastro de Produtos", true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        conexao = new ConexaoMysql();
        conexao.Conectar();
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 616, 495);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Escolha o Produto a ser Cadastrado");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel.setBounds(40, 24, 344, 23);
        getContentPane().add(lblNewLabel);

        choice = new Choice();
        choice.setFont(new Font("Minecraft", Font.BOLD, 14));
        choice.setForeground(Color.WHITE);
        choice.setBackground(Color.DARK_GRAY);
        choice.setBounds(40, 47, 234, 20);
        getContentPane().add(choice);
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
        getContentPane().add(NomeProduto);
        NomeProduto.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Nome do Produto");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel_1.setBounds(40, 100, 176, 20);
        getContentPane().add(lblNewLabel_1);

        PrecoProduto = new JTextField();
        PrecoProduto.setForeground(Color.WHITE);
        PrecoProduto.setFont(new Font("Minecraft", Font.BOLD, 14));
        PrecoProduto.setBackground(Color.DARK_GRAY);
        PrecoProduto.setColumns(10);
        PrecoProduto.setBounds(323, 120, 234, 20);
        getContentPane().add(PrecoProduto);

        JLabel lblNewLabel_1_1 = new JLabel("Preco");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblNewLabel_1_1.setBounds(323, 100, 176, 20);
        getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Produtos");
        lblNewLabel_1_2.setForeground(Color.BLACK);
        lblNewLabel_1_2.setFont(new Font("Minecraft", Font.BOLD, 18));
        lblNewLabel_1_2.setBounds(40, 151, 176, 30);
        getContentPane().add(lblNewLabel_1_2);

        JButton btnNewButton = new JButton("Cadastrar/Atualizar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarProduto();
            }
        });
        btnNewButton.setForeground(Color.GREEN);
        btnNewButton.setFont(new Font("Minecraft", Font.BOLD, 14));
        btnNewButton.setBounds(323, 402, 234, 30);
        getContentPane().add(btnNewButton);

        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeletarProduto();
            }
        });
        btnDeletar.setForeground(Color.RED);
        btnDeletar.setFont(new Font("Minecraft", Font.BOLD, 14));
        btnDeletar.setBounds(40, 402, 149, 30);
        getContentPane().add(btnDeletar);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        model.addColumn("Nome");
        model.addColumn("Preco");
        table.setDefaultEditor(Object.class, null);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int row = table.getSelectedRow();
                    NomeProduto.setText(model.getValueAt(row, 0).toString());
                    PrecoProduto.setText(model.getValueAt(row, 1).toString());
                }
            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.setSize(new Dimension(50, 500));
        scrollPane.setBounds(40, 192, 517, 191);
        getContentPane().add(scrollPane);
        TableColumnModel columnModel = table.getColumnModel();
        int tableWidth = scrollPane.getWidth();
        columnModel.getColumn(0).setPreferredWidth((int) (tableWidth * 0.7));
        columnModel.getColumn(1).setPreferredWidth((int) (tableWidth * 0.3));
        
    }

    @SuppressWarnings("static-access")
    private void popularTabela(String selectedChoice) {
        limparTabela();
        String selectedOption = choice.getSelectedItem();
        try {
            String query = "";

            if (selectedOption.equals("Jogos")) {
                query = "SELECT nome_jogo, preco_jogo FROM jogos";
            } else if (selectedOption.equals("Perifericos")) {
                query = "SELECT nome_perif, preco_perif FROM perifericos";
            }

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
    private void cadastrarProduto() {
        String nome = NomeProduto.getText();
        String preco = PrecoProduto.getText();
        String selectedOption = choice.getSelectedItem();
        String sql = "";

        try {
            if (selectedOption.equals("Jogos")) {
                sql = "SELECT nome_jogo FROM jogos where nome_jogo = ?";
            } else if (selectedOption.equals("Perifericos")) {
                sql = "SELECT nome_perif FROM perifericos where nome_perif = ?";
            }

            PreparedStatement statement = conexao.con.prepareStatement(sql);
            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int i = JOptionPane.showConfirmDialog(null, "Produto ja existe deseja atualiza-lo?", null, JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    if (selectedOption.equals("Jogos")) {
                        sql = "UPDATE jogos SET preco_jogo = ? WHERE nome_jogo = ?";
                    } else if (selectedOption.equals("Perifericos")) {
                        sql = "UPDATE perifericos SET preco_perif = ? where nome_perif = ?";
                    }

                    PreparedStatement statement1 = conexao.con.prepareStatement(sql);
                    statement1.setString(1, preco);
                    statement1.setString(2, nome);
                    int rowsInserted = statement1.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Produto Atualizado com sucesso");
                    }
                    statement1.close();
                    popularTabela(selectedOption);
                } else if (i == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Produto, não foi atualizado e não foi cadastrado");
                }
            } else {
                if (selectedOption.equals("Jogos")) {
                    sql = "INSERT INTO jogos (nome_jogo, preco_jogo) VALUES (?, ?)";
                } else if (selectedOption.equals("Perifericos")) {
                    sql = "INSERT INTO perifericos (nome_perif, preco_perif) VALUES (?, ?)";
                }

                PreparedStatement statement1 = conexao.con.prepareStatement(sql);
                statement1.setString(1, nome);
                statement1.setString(2, preco);
                int rowsInserted = statement1.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                }
                statement1.close();
            }
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
            if (selectedOption.equals("Jogos")) {
                sql = "SELECT nome_jogo FROM jogos where nome_jogo = ?";
            } else if (selectedOption.equals("Perifericos")) {
                sql = "SELECT nome_perif FROM perifericos where nome_perif = ?";
            }

            PreparedStatement statement = conexao.con.prepareStatement(sql);
            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int i = JOptionPane.showConfirmDialog(null, "Deseja realmente apagar o produto?", null, JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    if (selectedOption.equals("Jogos")) {
                        sql = "DELETE FROM jogos WHERE nome_jogo = ?";
                    } else if (selectedOption.equals("Perifericos")) {
                        sql = "DELETE FROM perifericos WHERE nome_perif = ?";
                    }

                    PreparedStatement statement1 = conexao.con.prepareStatement(sql);
                    statement1.setString(1, nome);
                    int rowsInserted = statement1.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Produto deletado com sucesso");
                    }
                    statement1.close();
                    popularTabela(selectedOption);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum produto foi apagado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto selecionado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao apagar");
        }
    }

    private void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
}
