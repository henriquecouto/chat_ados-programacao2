/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author italo
 */
public class Cadastro extends Configuration {
  
  private List<String> allUsers;

  public Cadastro() {

    JLabel nomeSala = new JLabel("CADASTRO");
    nomeSala.setBounds(340, 20, 450, 80);
    nomeSala.setFont(new Font("Dialog", Font.PLAIN, 30));
    add(nomeSala);

    JLabel text = new JLabel();
    text.setText("<html><body>Crie seu cadastro no CHATados e crie salas e convide seus amigos para participarem! :D <br/> Use os campos disponíveis abaixo: </body></html>");
    text.setFont(new Font("Dialog", Font.PLAIN, 20));
    text.setBounds(100, 100, 625, 100);
    add(text);

    JLabel nome = new JLabel("Nome: ");
    nome.setBounds(100, 230, 130, 30);
    nome.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(nome);

    JTextField campoTexto = new JTextField();
    campoTexto.setBounds(230, 230, 470, 30);
    campoTexto.setVisible(true);
    add(campoTexto);

    JLabel sobrenome = new JLabel("Sobrenome: ");
    sobrenome.setBounds(100, 280, 130, 30);
    sobrenome.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(sobrenome);

    JTextField campoTextoSobrenome = new JTextField();
    campoTextoSobrenome.setBounds(230, 280, 470, 30);
    campoTextoSobrenome.setVisible(true);
    add(campoTextoSobrenome);

    JLabel CPF = new JLabel("CPF: ");
    CPF.setBounds(100, 330, 130, 30);
    CPF.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(CPF);

    JFormattedTextField campoTextoCPF = new JFormattedTextField(maskCPF);
    campoTextoCPF.setBounds(230, 330, 470, 30);
    campoTextoCPF.setVisible(true);
    add(campoTextoCPF);

    JLabel usuario = new JLabel("Usuario: ");
    usuario.setBounds(100, 380, 130, 30);
    usuario.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(usuario);

    JTextField campoTextoUsuario = new JTextField();
    campoTextoUsuario.setBounds(230, 380, 470, 30);
    campoTextoUsuario.setVisible(true);
    add(campoTextoUsuario);

    JLabel senha = new JLabel("Senha: ");
    senha.setBounds(100, 430, 130, 30);
    senha.setFont(new Font("Dialog", Font.PLAIN, 20));
    add(senha);

    JTextField campoTextoSenha = new JPasswordField();
    campoTextoSenha.setBounds(230, 430, 470, 30);
    campoTextoSenha.setVisible(true);
    add(campoTextoSenha);

    JButton botaoCadastro = new JButton();
    botaoCadastro.setText("CADASTRAR-SE");
    botaoCadastro.setBounds(550, 500, 150, 50);
    botaoCadastro.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        ValidarCadastro(campoTexto.getText(), campoTextoSobrenome.getText(), campoTextoCPF.getText(), campoTextoUsuario.getText(), campoTextoSenha.getText());
      }
    });
    add(botaoCadastro);

    JButton botaoVoltar = new JButton();
    botaoVoltar.setText("VOLTAR");
    botaoVoltar.setBounds(385, 500, 150, 50);
    botaoVoltar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Login1 l = new Login1();
        dispose();
      }
    });
    add(botaoVoltar);

    setVisible(true);

  }
  
  public void ValidarCadastro(String nome, String sobrenome, String cpf, String usuario, String senha) {

    if (!nome.equals("") && !sobrenome.equals("") && !cpf.equals("") && !usuario.equals("") && !senha.equals("")) {
      Cliente c = new Cliente(nome, sobrenome, cpf, usuario, senha);
      try {
        Socket cliente = new Socket("localhost", 5555);
        System.out.println("Cliente conectado ao servidor");

        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println("cadastrar=..=" + nome + "=..=" + sobrenome + "=..=" + cpf + "=..=" + usuario + "=..=" + senha);

        Scanner s = new Scanner(cliente.getInputStream());
        String resposta = s.nextLine();

        if (resposta.equals("true")) {
          JOptionPane.showMessageDialog(null, "Sucesso");
          Home home = new Home(usuario);
          cliente.close();
          dispose();
        } else {

          JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
          cliente.close();
        }
      } catch (Exception e) {
        System.out.println("error cadastro");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Você precisa informar todos os dados pedidos", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
