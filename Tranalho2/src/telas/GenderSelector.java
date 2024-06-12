package telas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GenderSelector extends JFrame {
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton otherRadioButton;
    private ButtonGroup genderGroup;

    public GenderSelector() {
        setTitle("Seletor de Gênero");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        maleRadioButton = new JRadioButton("Masculino");
        femaleRadioButton = new JRadioButton("Feminino");
        otherRadioButton = new JRadioButton("Outro");

        // Agrupa os botões de rádio para que apenas um possa ser selecionado por vez
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderGroup.add(otherRadioButton);

        // Adiciona um ActionListener para capturar quando um botão de rádio é selecionado
        ActionListener radioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
                JOptionPane.showMessageDialog(null, "Você selecionou: " + selectedRadioButton.getText());
            }
        };

        maleRadioButton.addActionListener(radioListener);
        femaleRadioButton.addActionListener(radioListener);
        otherRadioButton.addActionListener(radioListener);

        // Adiciona os botões de rádio ao painel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(maleRadioButton);
        panel.add(femaleRadioButton);
        panel.add(otherRadioButton);

        // Adiciona o painel ao frame
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GenderSelector().setVisible(true);
            }
        });
    }
}
