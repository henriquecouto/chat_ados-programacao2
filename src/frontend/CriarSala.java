package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class CriarSala extends Configuration {

    private ArrayList<JRadioButton> botoes = new ArrayList();

    public CriarSala(String usuario) {
        JLabel title = new JLabel();
        title.setText("Criar Sala");
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        title.setBounds(300, 20, 500, 70);
        add(title);

        JLabel text = new JLabel();
        text.setText("<html><body>Para criar uma sala defina o nome e a categoria da sala! Use os campos disponíveis abaixo: </body></html>");
        text.setFont(new Font("Dialog", Font.PLAIN, 20));
        text.setBounds(100, 100, 625, 100);
        add(text);

        JLabel Nome = new JLabel();
        Nome.setText("Nome da Sala: ");
        Nome.setFont(new Font("Dialog", Font.BOLD, 20));
        Nome.setBounds(100, 240, 300, 30);
        add(Nome);

        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(280, 240, 400, 30);
        campoTexto.setVisible(true);
        add(campoTexto);

        JLabel sel = new JLabel();
        sel.setText("Selecione uma Categoria:");
        sel.setFont(new Font("Dialog", Font.BOLD, 20));
        sel.setBounds(100, 300, 300, 30);
        add(sel);

        JRadioButton buttonAmizade = new JRadioButton();
        buttonAmizade.setText("Amizade");
        buttonAmizade.setBackground(new Color(0, 121, 107));
        buttonAmizade.setBounds(400, 300, 100, 30);

        JRadioButton buttonNamoro = new JRadioButton();
        buttonNamoro.setText("Namoro");
        buttonNamoro.setBackground(new Color(0, 121, 107));
        buttonNamoro.setBounds(400, 330, 100, 30);

        JRadioButton buttonFilmes = new JRadioButton();
        buttonFilmes.setText("Filmes");
        buttonFilmes.setBackground(new Color(0, 121, 107));
        buttonFilmes.setBounds(400, 360, 100, 30);

        JRadioButton buttonViagens = new JRadioButton();
        buttonViagens.setText("Viagens");
        buttonViagens.setBackground(new Color(0, 121, 107));
        buttonViagens.setBounds(400, 390, 100, 30);

        JRadioButton buttonMusicas = new JRadioButton();
        buttonMusicas.setText("Músicas");
        buttonMusicas.setBackground(new Color(0, 121, 107));
        buttonMusicas.setBounds(400, 420, 100, 30);

        JRadioButton buttonJogos = new JRadioButton();
        buttonJogos.setText("Jogos");
        buttonJogos.setBackground(new Color(0, 121, 107));
        buttonJogos.setBounds(400, 450, 100, 30);

        ButtonGroup group = new ButtonGroup();
        group.add(buttonAmizade);
        group.add(buttonNamoro);
        group.add(buttonFilmes);
        group.add(buttonViagens);
        group.add(buttonMusicas);
        group.add(buttonJogos);
        add(buttonAmizade);
        add(buttonNamoro);
        add(buttonFilmes);
        add(buttonViagens);
        add(buttonMusicas);
        add(buttonJogos);
        botoes.add(buttonAmizade);
        botoes.add(buttonNamoro);
        botoes.add(buttonFilmes);
        botoes.add(buttonViagens);
        botoes.add(buttonMusicas);
        botoes.add(buttonJogos);

        JButton btnVoltar = new JButton();
        btnVoltar.setText("Voltar");
        btnVoltar.setBounds(365, 500, 150, 50);
        btnVoltar.setVisible(true);

        btnVoltar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Home c = new Home(usuario);
                dispose();

            }

        });

        add(btnVoltar);

        JButton btnCriarSala = new JButton();
        btnCriarSala.setText("Criar Sala");
        btnCriarSala.setBounds(530, 500, 150, 50);
        btnCriarSala.setVisible(true);
        btnCriarSala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoTexto.getText();
                String botao = botaoSelecionado().toLowerCase();

                if(!botao.equals("false") && !nome.equals("")){
                    novaSala(nome, botao, usuario);
                }else{
                    JOptionPane.showMessageDialog(null, "Voce precisa preencher todas as informações!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        add(btnCriarSala);

        setVisible(true);
    }

    public void novaSala(String nome, String tipo, String usuario) {
        if (nome != null && tipo != null) {

            try {

                Socket cliente = new Socket("localhost", 5555);
                System.out.println("Cliente conectado ao servidor");

                PrintStream saida = new PrintStream(cliente.getOutputStream());
                saida.println("criarSala=..=" + nome + "=..=" + tipo + "=..=" + usuario);

                Scanner s = new Scanner(cliente.getInputStream());
                String resposta = s.nextLine();

                if (resposta.equals("false")) {
                    JOptionPane.showMessageDialog(null, "sala existente", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Home h = new Home(usuario);
                    dispose();
                }

            } catch (Exception e) {
                System.out.println("erro de criaçao de sala");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Você precisa informar nome e tipo de sala", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public String botaoSelecionado() {
        String botao = "false";
        for (JRadioButton b : this.botoes) {
            if (b.isSelected()) {
                botao = b.getActionCommand();
                return botao;
            }
        }

        return "false";
    }

}
