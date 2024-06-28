package telas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("static-access")
public class TelaDeVendas {
    private static ConexaoMysql conexao;
    private static JTextField textField;
    private static JTextField Telefone_cliente;
    private static JTextField textField_2;
    private static JTextField textField_3;
    private static JTextField textField_1;
    private static JTextField textField_4;
    private static JTextField textField_5;
    private static JComboBox<String> JogosComboBox;
    private static JComboBox<String> PerifericosComboBox;
    private static JComboBox<String> ClienteComboBox;
    private static JTextField quantidadeJogosField;
    private static JTextField quantidadePerifericosField;
    private static JLabel totalLabel;
    private static double totalVenda = 0.0;
    private static JTextField textField_6;
    private static JLabel dataLabel;
    private static DefaultTableModel tableModel;

    public TelaDeVendas() {
        initialize();
    }

    // Método para inicializar os componentes da tela de vendas
    @SuppressWarnings("serial")
	private void initialize() {
        conexao = new ConexaoMysql();
        conexao.Conectar();

        // Criar a janela principal como JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Tela de Vendas");
        dialog.setModal(true);  // Torna o diálogo modal
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(1079, 514); // Aumentado para incluir o campo de total

        // Painel principal
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        // Criar a tabela
        String[] colunas = {"Produto", "Quantidade", "Preço Total"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedir edição da tabela
            }
        };
        panel.setLayout(null);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(26, 157, 484, 269);
        panel.add(scrollPane);

        // Adicionar painel à janela
        dialog.getContentPane().add(panel);

        // Criar o botão
        JButton addButton = new JButton("Adicionar jogo");
        addButton.setBounds(300, 39, 189, 23);
        addButton.setFont(new Font("Minecraft", Font.BOLD, 12));
        panel.add(addButton);

        // Campo de quantidade de jogos
        quantidadeJogosField = new JTextField();
        quantidadeJogosField.setBounds(414, 68, 75, 22);
        panel.add(quantidadeJogosField);

        // Criar o combobox de jogos
        JogosComboBox = new JComboBox<>();
        JogosComboBox.setBounds(26, 39, 264, 22);
        JogosComboBox.setForeground(Color.BLACK);
        JogosComboBox.setBackground(Color.WHITE);
        panel.add(JogosComboBox);
        carregarJogos(JogosComboBox);

        JLabel lblNewLabel = new JLabel("Jogos");
        lblNewLabel.setBounds(26, 22, 82, 15);
        lblNewLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(lblNewLabel);

        // Criar o combobox de periféricos
        PerifericosComboBox = new JComboBox<>();
        PerifericosComboBox.setBounds(26, 95, 264, 22);
        PerifericosComboBox.setBackground(Color.WHITE);
        PerifericosComboBox.setForeground(Color.BLACK);
        panel.add(PerifericosComboBox);
        carregarPerifericos(PerifericosComboBox);

        JLabel lblPerifericos = new JLabel("Periféricos");
        lblPerifericos.setBounds(26, 79, 122, 15);
        lblPerifericos.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(lblPerifericos);

        JButton btnAdicionarPeriferico = new JButton("Adicionar periférico");
        btnAdicionarPeriferico.setBounds(300, 95, 189, 23);
        btnAdicionarPeriferico.setFont(new Font("Minecraft", Font.BOLD, 12));
        panel.add(btnAdicionarPeriferico);

        // Campo de quantidade de periféricos
        quantidadePerifericosField = new JTextField();
        quantidadePerifericosField.setBounds(414, 124, 75, 22);
        panel.add(quantidadePerifericosField);

        // Criar o combobox de clientes
        ClienteComboBox = new JComboBox<>();
        ClienteComboBox.setBounds(550, 124, 241, 22);
        ClienteComboBox.setForeground(Color.BLACK);
        ClienteComboBox.setBackground(Color.WHITE);
        panel.add(ClienteComboBox);
        carregarClientes(ClienteComboBox);

        JButton btnAdicionarCliente = new JButton("Escolher Cliente");
        btnAdicionarCliente.setBounds(853, 123, 189, 23);
        btnAdicionarCliente.setFont(new Font("Minecraft", Font.BOLD, 12));
        panel.add(btnAdicionarCliente);

        JLabel lblCliente = new JLabel("Clientes");
        lblCliente.setBounds(550, 108, 82, 15);
        lblCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(lblCliente);

        // Adicionar label de data
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);
        dataLabel = new JLabel("Data: " + dataFormatada);
        dataLabel.setBounds(550, 402, 200, 23);
        dataLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(dataLabel);

        // Campos de texto para cliente (não editáveis)
        textField = new JTextField();
        textField.setBounds(550, 202, 241, 20);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField.setEditable(false);
        panel.add(textField);
        textField.setColumns(10);

        JLabel NomeCliente = new JLabel("Nome");
        NomeCliente.setBounds(550, 182, 82, 15);
        NomeCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(NomeCliente);

        Telefone_cliente = new JTextField();
        Telefone_cliente.setBounds(801, 202, 241, 20);
        Telefone_cliente.setForeground(Color.WHITE);
        Telefone_cliente.setBackground(Color.DARK_GRAY);
        Telefone_cliente.setFont(new Font("Minecraft", Font.BOLD, 12));
        Telefone_cliente.setColumns(10);
        Telefone_cliente.setEditable(false);
        panel.add(Telefone_cliente);

        JLabel TelefoneCliente = new JLabel("Telefone");
        TelefoneCliente.setBounds(801, 182, 104, 15);
        TelefoneCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(TelefoneCliente);

        textField_2 = new JTextField();
        textField_2.setBounds(550, 253, 241, 20);
        textField_2.setBackground(Color.DARK_GRAY);
        textField_2.setForeground(Color.WHITE);
        textField_2.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_2.setColumns(10);
        textField_2.setEditable(false);
        panel.add(textField_2);

        JLabel EmailCliente = new JLabel("Email");
        EmailCliente.setBounds(550, 233, 82, 15);
        EmailCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(EmailCliente);

        textField_3 = new JTextField();
        textField_3.setBounds(801, 253, 241, 20);
        textField_3.setForeground(Color.WHITE);
        textField_3.setBackground(Color.DARK_GRAY);
        textField_3.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_3.setColumns(10);
        textField_3.setEditable(false);
        panel.add(textField_3);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setBounds(801, 233, 82, 15);
        lblCpf.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(lblCpf);

        textField_1 = new JTextField();
        textField_1.setBounds(550, 217, 0, 0);
        textField_1.setBackground(Color.DARK_GRAY);
        textField_1.setForeground(Color.WHITE);
        textField_1.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_1.setColumns(10);
        textField_1.setEditable(false);
        panel.add(textField_1);

        JLabel CEPCliente = new JLabel("CEP");
        CEPCliente.setBounds(550, 284, 82, 15);
        CEPCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(CEPCliente);

        JLabel BairroCliente = new JLabel("Bairro");
        BairroCliente.setBounds(801, 284, 82, 15);
        BairroCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(BairroCliente);

        textField_4 = new JTextField();
        textField_4.setBounds(801, 304, 241, 20);
        textField_4.setBackground(Color.DARK_GRAY);
        textField_4.setForeground(Color.WHITE);
        textField_4.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_4.setColumns(10);
        textField_4.setEditable(false);
        panel.add(textField_4);

        JLabel EnderecoCliente = new JLabel("Endereço");
        EnderecoCliente.setBounds(550, 335, 116, 15);
        EnderecoCliente.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(EnderecoCliente);

        textField_5 = new JTextField();
        textField_5.setBounds(550, 355, 492, 20);
        textField_5.setForeground(Color.WHITE);
        textField_5.setBackground(Color.DARK_GRAY);
        textField_5.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_5.setColumns(10);
        textField_5.setEditable(false);
        panel.add(textField_5);

        JButton btnEfetuarvenda = new JButton("Efetuar Venda");
        btnEfetuarvenda.setBounds(853, 403, 189, 23);
        btnEfetuarvenda.setFont(new Font("Minecraft", Font.BOLD, 12));
        panel.add(btnEfetuarvenda);

        // Label para exibir o total da venda
        totalLabel = new JLabel("Total: R$ 0.00");
        totalLabel.setBounds(36, 437, 200, 15);
        totalLabel.setFont(new Font("Minecraft", Font.BOLD, 14));
        panel.add(totalLabel);

        JLabel lblQtd = new JLabel("QTD");
        lblQtd.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblQtd.setBounds(364, 67, 40, 20);
        panel.add(lblQtd);

        JLabel lblQtd_1 = new JLabel("QTD");
        lblQtd_1.setFont(new Font("Minecraft", Font.BOLD, 14));
        lblQtd_1.setBounds(364, 126, 40, 20);
        panel.add(lblQtd_1);

        textField_6 = new JTextField();
        textField_6.setForeground(Color.WHITE);
        textField_6.setFont(new Font("Minecraft", Font.BOLD, 12));
        textField_6.setEditable(false);
        textField_6.setColumns(10);
        textField_6.setBackground(Color.DARK_GRAY);
        textField_6.setBounds(550, 304, 241, 20);
        panel.add(textField_6);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(TelaDeVendas.class.getResource("/imgs/fotor-20240612224554.png")));
        lblNewLabel_1.setBounds(784, 11, 82, 93);
        panel.add(lblNewLabel_1);

        // Adicionar ação ao botão de adicionar jogo
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produtoSelecionado = (String) JogosComboBox.getSelectedItem();
                String quantidadeText = quantidadeJogosField.getText();
                if (produtoSelecionado != null && !quantidadeText.isEmpty()) {
                    int quantidade = Integer.parseInt(quantidadeText);
                    double precoUnitario = extrairPreco(produtoSelecionado);
                    double precoTotal = precoUnitario * quantidade;
                    tableModel.addRow(new Object[]{produtoSelecionado, quantidade, precoTotal});
                    atualizarTotal(precoTotal);
                }
            }
        });

        // Adicionar ação ao botão de adicionar periférico
        btnAdicionarPeriferico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produtoSelecionado = (String) PerifericosComboBox.getSelectedItem();
                String quantidadeText = quantidadePerifericosField.getText();
                if (produtoSelecionado != null && !quantidadeText.isEmpty()) {
                    int quantidade = Integer.parseInt(quantidadeText);
                    double precoUnitario = extrairPreco(produtoSelecionado);
                    double precoTotal = precoUnitario * quantidade;
                    tableModel.addRow(new Object[]{produtoSelecionado, quantidade, precoTotal});
                    atualizarTotal(precoTotal);
                }
            }
        });

        // Adicionar ação ao botão de adicionar cliente
        btnAdicionarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clienteSelecionado = (String) ClienteComboBox.getSelectedItem();
                if (clienteSelecionado != null) {
                    carregarDadosCliente(clienteSelecionado);
                }
            }
        });

        // Adicionar ação ao botão de efetuar venda
        btnEfetuarvenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar se todos os campos necessários estão preenchidos
                if (tableModel.getRowCount() == 0 || ClienteComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, adicione produtos e selecione um cliente.");
                    return;
                }

                String clienteSelecionado = (String) ClienteComboBox.getSelectedItem();
                String[] partes = clienteSelecionado.split(" - ");
                String CPF_cliente = partes[1];

                try {
                    // Buscar o ID do cliente
                    String clienteStmt = "SELECT id_cliente, id_endereco FROM cliente WHERE CPF_cliente = ?";
                    PreparedStatement clienteStatement = conexao.con.prepareStatement(clienteStmt);
                    clienteStatement.setString(1, CPF_cliente);
                    ResultSet clienteResultSet = clienteStatement.executeQuery();

                    if (clienteResultSet.next()) {
                        int idCliente = clienteResultSet.getInt("id_cliente");
                        int idEndereco = clienteResultSet.getInt("id_endereco");

                        // Preparar a inserção na tabela compra
                        String insertStmt = "INSERT INTO compra (Id_cliente, id_jogo, preco_jogo, id_perif, preco_perif, id_endereco, data_compra) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertStatement = conexao.con.prepareStatement(insertStmt);

                        // Acumular os IDs e preços de jogos e periféricos
                        List<Integer> idJogos = new ArrayList<>();
                        List<Double> precoJogos = new ArrayList<>();
                        List<Integer> idPerifs = new ArrayList<>();
                        List<Double> precoPerifs = new ArrayList<>();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String produto = (String) tableModel.getValueAt(i, 0);
                            int quantidade = (int) tableModel.getValueAt(i, 1);

                            // Verificar se é jogo
                            String jogoStmt = "SELECT id_jogo, preco_jogo FROM jogos WHERE nome_jogo = ?";
                            PreparedStatement jogoStatement = conexao.con.prepareStatement(jogoStmt);
                            jogoStatement.setString(1, produto.split(" - ")[0]);
                            ResultSet jogoResultSet = jogoStatement.executeQuery();

                            if (jogoResultSet.next()) {
                                int idJogo = jogoResultSet.getInt("id_jogo");
                                double precoJogo = jogoResultSet.getDouble("preco_jogo") * quantidade;
                                idJogos.add(idJogo);
                                precoJogos.add(precoJogo);
                            }
                            jogoResultSet.close();
                            jogoStatement.close();

                            // Verificar se é periférico
                            String perifStmt = "SELECT id_perif, preco_perif FROM perifericos WHERE nome_perif = ?";
                            PreparedStatement perifStatement = conexao.con.prepareStatement(perifStmt);
                            perifStatement.setString(1, produto.split(" - ")[0]);
                            ResultSet perifResultSet = perifStatement.executeQuery();

                            if (perifResultSet.next()) {
                                int idPerif = perifResultSet.getInt("id_perif");
                                double precoPerif = perifResultSet.getDouble("preco_perif") * quantidade;
                                idPerifs.add(idPerif);
                                precoPerifs.add(precoPerif);
                            }
                            perifResultSet.close();
                            perifStatement.close();
                        }

                        // Inserir na tabela compra com os acumulados
                        for (int i = 0; i < Math.max(idJogos.size(), idPerifs.size()); i++) {
                            Integer idJogo = i < idJogos.size() ? idJogos.get(i) : null;
                            Double precoJogo = i < precoJogos.size() ? precoJogos.get(i) : null;
                            Integer idPerif = i < idPerifs.size() ? idPerifs.get(i) : null;
                            Double precoPerif = i < precoPerifs.size() ? precoPerifs.get(i) : null;

                            insertStatement.setInt(1, idCliente);
                            if (idJogo != null) {
                                insertStatement.setInt(2, idJogo);
                                insertStatement.setDouble(3, precoJogo);
                            } else {
                                insertStatement.setNull(2, java.sql.Types.INTEGER);
                                insertStatement.setNull(3, java.sql.Types.DOUBLE);
                            }
                            if (idPerif != null) {
                                insertStatement.setInt(4, idPerif);
                                insertStatement.setDouble(5, precoPerif);
                            } else {
                                insertStatement.setNull(4, java.sql.Types.INTEGER);
                                insertStatement.setNull(5, java.sql.Types.DOUBLE);
                            }
                            insertStatement.setInt(6, idEndereco);
                            insertStatement.setDate(7, java.sql.Date.valueOf(LocalDate.now()));

                            insertStatement.executeUpdate();
                        }

                        insertStatement.close();
                        JOptionPane.showMessageDialog(dialog, "Venda efetuada com sucesso!");
                        tableModel.setRowCount(0); // Limpar tabela
                        atualizarTotal(-totalVenda); // Resetar total
                    }
                    clienteResultSet.close();
                    clienteStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Erro ao efetuar a venda.");
                }
            }
        });

        // Tornar a janela visível
        dialog.setVisible(true);
    }

    private static void carregarJogos(JComboBox<String> comboBox) {
        String stmt = "SELECT nome_jogo, preco_jogo FROM jogos";

        try {
            PreparedStatement statement = conexao.con.prepareStatement(stmt);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome_jogo");
                double preco = resultSet.getDouble("preco_jogo");
                String item = nome + " - R$" + String.format("%.2f", preco);
                comboBox.addItem(item);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void carregarPerifericos(JComboBox<String> comboBox) {
        String stmt = "SELECT nome_perif, preco_perif FROM perifericos";

        try {
            PreparedStatement statement = conexao.con.prepareStatement(stmt);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome_perif");
                double preco = resultSet.getDouble("preco_perif");
                String item = nome + " - R$" + String.format("%.2f", preco);
                comboBox.addItem(item);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void carregarClientes(JComboBox<String> comboBox) {
        String stmt = "SELECT CPF_cliente, nome_cliente FROM cliente";

        try {
            PreparedStatement statement = conexao.con.prepareStatement(stmt);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String CPF = resultSet.getString("CPF_cliente");
                String nome = resultSet.getString("nome_cliente");
                String item = nome + " - " + CPF;
                comboBox.addItem(item);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void carregarDadosCliente(String clienteSelecionado) {
        // Verificar se o clienteSelecionado está no formato esperado
        if (!clienteSelecionado.contains(" - ")) {
            System.err.println("Formato inesperado para clienteSelecionado: " + clienteSelecionado);
            return;
        }

        String[] partes = clienteSelecionado.split(" - ");
        String CPF_cliente = partes[1];

        String stmt = "SELECT c.nome_cliente, c.telefone_cliente, c.email_cliente, c.CPF_cliente, e.cep, e.bairro, e.rua_casa " +
                      "FROM cliente c " +
                      "JOIN endereco e ON c.id_endereco = e.id_endereco " +
                      "WHERE c.CPF_cliente = ?";

        try {
            PreparedStatement statement = conexao.con.prepareStatement(stmt);
            statement.setString(1, CPF_cliente);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                textField.setText(resultSet.getString("nome_cliente"));
                Telefone_cliente.setText(resultSet.getString("telefone_cliente"));
                textField_2.setText(resultSet.getString("email_cliente"));
                textField_3.setText(resultSet.getString("CPF_cliente"));
                textField_6.setText(resultSet.getString("cep"));
                textField_4.setText(resultSet.getString("bairro"));
                textField_5.setText(resultSet.getString("rua_casa"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double extrairPreco(String item) {
        String precoString = item.substring(item.lastIndexOf("R$") + 2);
        return Double.parseDouble(precoString.replace(',', '.'));
    }

    private static void atualizarTotal(double valor) {
        totalVenda += valor;
        totalLabel.setText("Total: R$ " + String.format("%.2f", totalVenda));
    }
}
