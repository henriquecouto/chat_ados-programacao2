package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Perfil extends Configuration {

    public Perfil(String usuarioLogado) {

        String usu[] = pegarUsuario(usuarioLogado);

        JLabel title = new JLabel();
        title.setText("Perfil CHATados");
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        title.setBounds(300, 20, 500, 70);
        add(title);

        JLabel Nome = new JLabel();
        Nome.setText("Nome: ");
        Nome.setFont(new Font("Dialog", Font.BOLD, 20));
        Nome.setBounds(150, 130, 300, 30);
        add(Nome);

        JLabel nome1 = new JLabel();
        nome1.setText(usu[0]);
        nome1.setFont(new Font("dialog", Font.BOLD, 20));
        nome1.setBounds(300, 130, 200, 30);
        add(nome1);

        JLabel Sobrenome = new JLabel();
        Sobrenome.setText("Sobrenome: ");
        Sobrenome.setFont(new Font("Dialog", Font.BOLD, 20));
        Sobrenome.setBounds(150, 170, 300, 30);
        add(Sobrenome);

        JLabel Sobrenome1 = new JLabel();
        Sobrenome1.setText(usu[1]);
        Sobrenome1.setFont(new Font("Dialog", Font.BOLD, 20));
        Sobrenome1.setBounds(300, 170, 200, 30);
        add(Sobrenome1);

        JLabel Usuario = new JLabel();
        Usuario.setText("Usuario: ");
        Usuario.setFont(new Font("Dialog", Font.BOLD, 20));
        Usuario.setBounds(150, 210, 300, 30);
        add(Usuario);

        JLabel Usuario1 = new JLabel();
        Usuario1.setText(usu[2]);
        Usuario1.setFont(new Font("Dialog", Font.BOLD, 20));
        Usuario1.setBounds(300, 210, 200, 30);
        add(Usuario1);

        JLabel CPF = new JLabel();
        CPF.setText("CPF: ");
        CPF.setFont(new Font("Dialog", Font.BOLD, 20));
        CPF.setBounds(150, 250, 300, 30);
        add(CPF);

        JLabel CPF1 = new JLabel();
        CPF1.setText(usu[3]);
        CPF1.setFont(new Font("Dialog", Font.BOLD, 20));
        CPF1.setBounds(300, 250, 200, 30);
        add(CPF1);

        JButton botaoLogout = new JButton();
        botaoLogout.setText("LOGOUT");
        botaoLogout.setBounds(150, 350, 150, 50);
        botaoLogout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Login1 l = new Login1();
                dispose();
            }

        });
        add(botaoLogout);

        JButton botaoHome = new JButton();
        botaoHome.setText("HOME");
        botaoHome.setBounds(330, 350, 150, 50);
        botaoHome.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Home home = new Home(usuarioLogado);
                dispose();

            }

        });
        add(botaoHome);

        JButton botaoAlterarDados = new JButton();
        botaoAlterarDados.setText("ALTERAR DADOS");
        botaoAlterarDados.setBounds(510, 350, 150, 50);
        botaoAlterarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlterarPerfil alterarPerfil = new AlterarPerfil(usu);
                dispose();
            }
        });
        add(botaoAlterarDados);

        JComboBox salas = new JComboBox();
        salas.removeAllItems();
        salas.addItem("MINHAS SALAS");

        String[] values = pegarSalas(usuarioLogado);

        for (String i : values) {
            if (!i.equals("null") && !i.equals(" ")) {
                salas.addItem(i);
            }
        }

        salas.setBounds(150, 450, 510, 50);
        add(salas);

        JButton btnExcluirSala = new JButton();
        btnExcluirSala.setText("Excluir Sala");
        btnExcluirSala.setBounds(510, 550, 150, 50);
        btnExcluirSala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirSala(salas.getSelectedItem().toString(), usuarioLogado);
            }
        });
        add(btnExcluirSala);

        setVisible(true);

    }
    
    public void excluirSala(String sala, String usuarioLogado){
        try {

            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("excluirSala=..=" + sala+"=..="+usuarioLogado);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();
            
            if(resposta.equals("true")){
                JOptionPane.showMessageDialog(null, "Sala Excluida!", "Sucesso!", JOptionPane.OK_OPTION);
                Perfil p = new Perfil(usuarioLogado);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Nao foi possivel excluir sala, tente novamente", "Erro!", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("erro ao achar sala");
        }

    }

    public String[] pegarSalas(String usuarioLogado) {

        try {

            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("pegarSala=..=" + usuarioLogado);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            String nomes[] = resposta.split(" ");

            return nomes;

        } catch (Exception e) {
            System.out.println("erro ao achar sala");
        }

        return null;

    }

    public static String[] pegarUsuario(String usuarioLogado) {

        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("Perfil=..=" + usuarioLogado);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            if (resposta.equals("false")) {

                JOptionPane.showMessageDialog(null, "Erro ao encntrar informações");

                cliente.close();
            } else {

                String values[] = resposta.split("=..=");
                cliente.close();
                return values;

            }

        } catch (Exception e) {
            System.out.println("erro perfil");
        }

        return null;
    }

}
