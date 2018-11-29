/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chateadosDrive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 * @author italo
 */
public class TelaChat extends JFrame {

    private JTextArea lblHistorico;
    private JTextArea lblUsuarios;
    private String tipo;
    private String nome;
    private String dono;
    private int contmsg, contuser;
    private JScrollPane sphistorico;
    private JScrollPane spusuarios;

    public TelaChat(String sala, String usuarioLogado, String tipo) {
        contmsg = 0;
        contuser = -1;
        String informacoes = informaçaoSala(sala, tipo);
        String[] dados = informacoes.split("=..=");
        System.out.println(dados[0]);

        this.dono = dados[0];
        this.tipo = dados[1];
        this.nome = dados[2];

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, 1366, 720);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(0, 121, 107));

        JLabel nomeSala = new JLabel("Sala: " + nome);
        nomeSala.setBounds(300, 20, 450, 80);
        nomeSala.setFont(new Font("Dialog", Font.PLAIN, 30));
        add(nomeSala);

        JLabel usuarios = new JLabel("Usuários");
        usuarios.setBounds(1120, 20, 450, 80);
        usuarios.setFont(new Font("Dialog", Font.PLAIN, 30));
        add(usuarios);

//        Usuarios u1 = new Usuarios();
//        add(u1);
//        WIDTHWIDTHWIDTH
//        Historico h1 = new Historico();
//        add(h1);
        lblHistorico = new JTextArea();
        lblHistorico.setBounds(10, 100, 980, 480);
        lblHistorico.setBackground(Color.white);
        lblHistorico.setFont(new Font("DIALOG", Font.PLAIN, 30));
        lblHistorico.setOpaque(true);
        lblHistorico.setLineWrap(true);
        lblHistorico.setWrapStyleWord(true);
        lblHistorico.setEditable(false);
        sphistorico = new JScrollPane(lblHistorico);
        sphistorico.setBounds(10, 100, 980, 480);
        add(sphistorico);

        lblUsuarios = new JTextArea();
        lblUsuarios.setBounds(1000, 100, 350, 480);
        lblUsuarios.setBackground(Color.white);
        lblUsuarios.setFont(new Font("DIALOG", Font.PLAIN, 30));
        lblUsuarios.setOpaque(true);
        lblUsuarios.setLineWrap(true);
        lblUsuarios.setWrapStyleWord(true);
        lblUsuarios.setEditable(false);
        spusuarios = new JScrollPane(lblUsuarios);
        spusuarios.setBounds(1000, 100, 350, 480);
        add(spusuarios);

        JTextArea campoTexto = new JTextArea();
        campoTexto.setBounds(10, 600, 800, 80);
        campoTexto.setLineWrap(true);
        campoTexto.setFont(new Font("DIALOG", Font.PLAIN, 20));
        campoTexto.setWrapStyleWord(true);
        campoTexto.setText("");
        campoTexto.addKeyListener(new KeyAdapter() {
            public void keyTyped (KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    enviarMensagem(campoTexto.getText(), usuarioLogado, nome);
                    campoTexto.setText("");
                }
            }
        });
        JScrollPane spcampo = new JScrollPane(campoTexto);
        spcampo.setBounds(10, 600, 800, 80);
        add(spcampo);

        JButton enviar = new JButton("Enviar");
        enviar.setBounds(850, 610, 130, 50);
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!campoTexto.getText().equals(null)) {
                    enviarMensagem(campoTexto.getText(), usuarioLogado, nome);
                    campoTexto.setText("");
                }
            }
        });
        add(enviar);

        JButton sair = new JButton("Sair da Sala");
        sair.setBounds(1000, 610, 130, 50);
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resposta = reqSairSala(sala, usuarioLogado);
                if (!resposta.equals("false")) {
                    dispose();
                }
            }
        });
        add(sair);

        new ThreadHistorico().start();
        new ThreadUsuarios().start();

        setVisible(true);

    }

    public String reqSairSala(String sala, String usuarioLogado) {
        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("SairDaSala=..=" + sala + "=..=" + usuarioLogado);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();
            cliente.close();

            if (!resposta.equals("false")) {
                return resposta;
            } else {
                System.out.println("Erro ao recuperar mensagens");
            }

        } catch (Exception e) {
            System.out.println("Erro ao sair da sala");
        }
        return "false";
    }

    private class ThreadUsuarios extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    String resposta = atualizarUsuarios(nome);
                    System.out.println(resposta);
                    if (!resposta.equals("false")) {
                        lblUsuarios.setText("");
                        String[] listusuarios = null;
                        listusuarios = resposta.split("!...!");
                        for (int i = 0; i < listusuarios.length; i++) {
                            lblUsuarios.append(" - "+listusuarios[i] + "\n");
                            spusuarios.getViewport().setViewPosition(new Point(0, spusuarios.getVerticalScrollBar().getMaximum()));
                            contuser++;
                        }
                        
                    }
                    this.sleep(1000);
                } catch (Exception e) {
                    System.out.println("erro na thread de atualizar");
                    break;
                }
            }
        }
    }

    public String atualizarUsuarios(String sala) {
        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("AtualizarUsuarios=..=" + sala);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();
            cliente.close();
            if (!resposta.equals("false")) {
                return resposta;
            } else {
                System.out.println("Erro ao recuperar mensagens");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar mensagens: " + e);
        }
        return "false";
    }

    private class ThreadHistorico extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    String resposta = atualizarConversa(nome);
                    if (!resposta.equals("false")) {
                        String[] listmensagens = resposta.split("!...!");
                        for (int i = 1; i < listmensagens.length; i++) {
                            if (i > contmsg) {
                                lblHistorico.append(" - "+listmensagens[i] + "\n");
                                sphistorico.getViewport().setViewPosition(new Point(0, sphistorico.getVerticalScrollBar().getMaximum()));
                                contmsg++;
                            }
                        }
                    }
                    this.sleep(1000);
                } catch (Exception e) {
                    System.out.println("erro na thread de atualizar");
                    break;
                }
            }
        }
    }

    public String atualizarConversa(String sala) {
        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("AtualizarMensagens=..=" + sala);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();
            cliente.close();
            if (!resposta.equals("false")) {
                return resposta;
            } else {
                System.out.println("Erro ao recuperar mensagens");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar mensagens: " + e);
        }
        return "false";
    }

    public void enviarMensagem(String mensagem, String usuario, String sala) {

        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("enviarMensagem=..=" + sala + "=..=" + usuario + "=..=" + mensagem);

        } catch (Exception ex) {
            System.out.println("error ao enviar mensagem");

        }

    }

    public String informaçaoSala(String nome, String tipo) {

        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("informacaoSala=..=" + nome + "=..=" + tipo);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            String informacoes = resposta;

            return informacoes;

        } catch (Exception ex) {
            System.out.println("error de logo");

        }

        return null;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

}
