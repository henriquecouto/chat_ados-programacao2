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
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author henrique
 */
public class RecuperarSenha extends Configuration {

  public RecuperarSenha() {

    JLabel title = new JLabel();
    title.setText("Recuperar Senha");
    title.setFont(new Font("Dialog", Font.BOLD, 30));
    title.setBounds(250, 20, 500, 70);
    add(title);

    JLabel text = new JLabel();
    text.setText("<html><body>Para recuperarmos sua senha, precisamos que você nos informe seu nome de Usuário e CPF.<br/>Use os campos disponíveis abaixo: </body></html>");
    text.setFont(new Font("Dialog", Font.PLAIN, 20));
    text.setBounds(100, 100, 625, 100);
    add(text);

    JLabel usuarioJLabel = new JLabel();
    usuarioJLabel.setText("Usuário: ");
    usuarioJLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
    usuarioJLabel.setBounds(150, 250, 100, 30);
    add(usuarioJLabel);

    JTextField usuarioJTextField = new JTextField();
    usuarioJTextField.setBounds(265, 250, 400, 30);
    add(usuarioJTextField);

    JLabel cpfJLabel = new JLabel();
    cpfJLabel.setText("CPF: ");
    cpfJLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
    cpfJLabel.setBounds(150, 350, 100, 30);
    add(cpfJLabel);

    JTextField cpfJTextField = new JFormattedTextField(maskCPF);
    cpfJTextField.setBounds(265, 350, 400, 30);
    add(cpfJTextField);

    JButton voltarButton = new JButton();
    voltarButton.setText("Voltar");
    voltarButton.setBounds(350, 450, 150, 50);
    voltarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Login1 l = new Login1();
        dispose();
      }
    });
    add(voltarButton);

    JButton recuperarSenhaButton = new JButton();
    recuperarSenhaButton.setText("Recuperar Senha");
    recuperarSenhaButton.setBounds(515, 450, 150, 50);
    recuperarSenhaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        recuperar(usuarioJTextField.getText(), cpfJTextField.getText());

      }

    });

    add(recuperarSenhaButton);

    setVisible(true);
  }

  public void recuperar(String usuario, String cpf) {
    if (!usuario.equals("") && !cpf.equals("")) {
      try {
        Socket cliente = new Socket("localhost", 5555);
        System.out.println("Cliente conectado ao servidor");

        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println("RecuperarSenha=..=" + usuario + "=..=" + cpf);

        Scanner s = new Scanner(cliente.getInputStream());
        String resposta = s.nextLine();

        if (resposta.equals("false")) {

          JOptionPane.showMessageDialog(null, "O nome de usuário e/ou CPF está errado");

        } else {
          JOptionPane.showMessageDialog(null, "Senha:" + resposta);

        }
      } catch (Exception e) {
        System.out.println("erro recuperar senha");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Usuario e senha errados", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
