/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author henrique
 */
public class Home extends Configuration {

    JComboBox<String> amizades = new JComboBox();
    JComboBox<String> namoros = new JComboBox();
    JComboBox<String> filmes = new JComboBox();
    JComboBox<String> jogos = new JComboBox();
    JComboBox<String> viagens = new JComboBox();
    JComboBox<String> musicas = new JComboBox();

    ArrayList<JComboBox> arrayComboBox = new ArrayList();

    public Home(String usuarioLogado) {

        JLabel title = new JLabel();
        title.setText("Home CHATados");
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        title.setBounds(200, 20, 500, 70);
        add(title);

        JButton sair = new JButton();
        sair.setText("Sair");
        sair.setBounds(680, 40, 70, 30);
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login1 login = new Login1();
                dispose();
            }
        });
        add(sair);

        JButton perfil = new JButton();
        perfil.setText("Meu Perfil");
        perfil.setBounds(570, 40, 100, 30);
        perfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perfil p = new Perfil(usuarioLogado);
                dispose();
            }
        });
        add(perfil);

        JButton atualizar = new JButton("Atualizar");
        atualizar.setBounds(460, 40, 100, 30);
        atualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home p = new Home(usuarioLogado);
                dispose();
            }
        });
        add(atualizar);

        amizades.addItem("--- Amizades ---");
        amizades.setName("amizade");
        String[] nomesAmizade = pegarSalas("amizade");

        for (String n : nomesAmizade) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                amizades.addItem(n);
            }
        }

        amizades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(amizades);
            }
        });
        amizades.setBounds(50, 100, 700, 75);
        add(amizades);

        namoros.addItem("--- Namoros ---");
        namoros.setName("namoro");
        String[] nomesNamoro = pegarSalas("namoro");

        for (String n : nomesNamoro) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                namoros.addItem(n);
            }

        }
        namoros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(namoros);
            }
        });
        namoros.setBounds(50, 185, 700, 75);
        add(namoros);

        filmes.addItem("--- Filmes ---");
        filmes.setName("filmes");
        String[] nomesFilme = pegarSalas("filmes");

        for (String n : nomesFilme) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                filmes.addItem(n);
            }
        }
        filmes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(filmes);
            }
        });
        filmes.setBounds(50, 270, 700, 75);
        add(filmes);

        jogos.addItem("--- Jogos ---");
        jogos.setName("jogos");
        String[] nomesJogo = pegarSalas("jogos");

        for (String n : nomesJogo) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                jogos.addItem(n);
            }
        }
        jogos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(jogos);
            }
        });
        jogos.setBounds(50, 355, 700, 75);
        add(jogos);

        viagens.addItem("--- Viagens ---");
        viagens.setName("viagens");
        String[] nomesViajem = pegarSalas("viagens");

        for (String n : nomesViajem) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                viagens.addItem(n);
            }
        }
        viagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(viagens);
            }
        });
        viagens.setBounds(50, 440, 700, 75);
        add(viagens);

        musicas.addItem("--- Músicas ---");
        musicas.setName("musicas");
        String[] nomesMusica = pegarSalas("músicas");

        for (String n : nomesMusica) {
            if (!n.equals("false") && !n.equals("=..=") && !n.equals(null)) {
                musicas.addItem(n);
            }
        }
        musicas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBox(musicas);
            }
        });
        musicas.setBounds(50, 525, 700, 75);
        add(musicas);

        arrayComboBox.add(amizades);
        arrayComboBox.add(namoros);
        arrayComboBox.add(filmes);
        arrayComboBox.add(jogos);
        arrayComboBox.add(viagens);
        arrayComboBox.add(musicas);

        JButton entrar = new JButton();
        entrar.setText("Entrar");
        entrar.setBounds(600, 610, 150, 50);
        entrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionado(usuarioLogado);
            }
        });
        add(entrar);

        JButton criarSala = new JButton();
        criarSala.setText("Criar Sala");
        criarSala.setBounds(430, 610, 150, 50);
        criarSala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriarSala sala = new CriarSala(usuarioLogado);
                dispose();
            }
        });
        add(criarSala);

        JButton voltar = new JButton();
        voltar.setText("Voltar");
        voltar.setBounds(260, 610, 150, 50);
        voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(voltar);

        setVisible(true);
    }

    private void selectedBox(JComboBox comboBox) {

        if (comboBox.getSelectedIndex() == 0) {

            for (JComboBox i : arrayComboBox) {
                i.setEnabled(true);
            }

        } else {
            for (JComboBox i : arrayComboBox) {
                if (i == comboBox) {
                    i.setEnabled(true);
                } else {
                    i.setEnabled(false);
                }
            }
        }

    }

    public void selecionado(String usuarioLogado) {
        String nome = null;
        String tipo = null;
        String resposta = "false";

        for (JComboBox c : arrayComboBox) {
            if (c.isEnabled() == true) {
                nome = c.getSelectedItem().toString();
                tipo = c.getName().toString();
                resposta = addUserNaSala(nome, usuarioLogado);
            }
        }

        if (resposta.equals("false")) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel adicionar o usuario a sala!");
        } else {
            TelaChat tela = new TelaChat(nome, usuarioLogado, tipo);
        }

    }

    public String addUserNaSala(String sala, String usuarioLogado) {
        try {

            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("addUserNaSala=..=" + sala + "=..=" + usuarioLogado);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            return resposta;

        } catch (Exception e) {
            System.out.println("erro ao add usuario na sala");
        }
        return "false";
    }

    public String[] pegarSalas(String tipo) {

        try {

            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("pegarTSala=..=" + tipo);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            String nomes[] = resposta.split("=..=");

            return nomes;

        } catch (Exception e) {
            System.out.println("erro ao achar sala");
        }

        return null;

    }

//    public void abrirSala(String nomeTipo) {
//
//        try {
//
//            Socket cliente = new Socket("localhost", 5555);
//            System.out.println("Cliente conectado ao servidor");
//
//            PrintStream saida = new PrintStream(cliente.getOutputStream());
//            saida.println("abrirSala=..=" + nomeTipo);
//
//            Scanner s = new Scanner(cliente.getInputStream());
//            String resposta = s.nextLine();
//
//            String nomes[] = resposta.split(" ");
//
//        } catch (Exception e) {
//            System.out.println("erro ao achar sala");
//        }
//
//    }
}
