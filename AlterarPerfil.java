package chateadosDrive;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AlterarPerfil extends Configuration {

  public AlterarPerfil(String[] usu) {

    JLabel title = new JLabel();
    title.setText("Alterar dados");
    title.setFont(new Font("Dialog", Font.BOLD, 30));
    title.setBounds(300, 20, 500, 70);
    add(title);

    JLabel senha = new JLabel();
    senha.setText("Senha: ");
    senha.setFont(new Font("Dialog", Font.BOLD, 20));
    senha.setBounds(150, 290, 500, 30);
    add(senha);

    JTextField campoSenha = new JTextField();
    campoSenha.setBounds(300, 290, 360, 30);
    campoSenha.setText(usu[4]);
    add(campoSenha);

    JLabel Nome = new JLabel();
    Nome.setText("Nome: ");
    Nome.setFont(new Font("Dialog", Font.BOLD, 20));
    Nome.setBounds(150, 130, 300, 30);
    add(Nome);

    JTextField campoNome = new JTextField();
    campoNome.setBounds(300, 130, 360, 30);
    campoNome.setText(usu[0]);
    add(campoNome);

    JLabel Sobrenome = new JLabel();
    Sobrenome.setText("Sobrenome: ");
    Sobrenome.setFont(new Font("Dialog", Font.BOLD, 20));
    Sobrenome.setBounds(150, 170, 300, 30);
    add(Sobrenome);

    JTextField campoSobrenome = new JTextField();
    campoSobrenome.setBounds(300, 170, 360, 30);
    campoSobrenome.setText(usu[1]);
    add(campoSobrenome);

    JLabel Usuario = new JLabel();
    Usuario.setText("Usuario: ");
    Usuario.setFont(new Font("Dialog", Font.BOLD, 20));
    Usuario.setBounds(150, 210, 300, 30);
    add(Usuario);

    JTextField campoUsuario = new JTextField();
    campoUsuario.setBounds(300, 210, 360, 30);
    campoUsuario.setText(usu[2]);

    add(campoUsuario);

    JLabel CPF = new JLabel();
    CPF.setText("CPF: ");
    CPF.setFont(new Font("Dialog", Font.BOLD, 20));
    CPF.setBounds(150, 250, 300, 30);
    add(CPF);

    JTextField campoCPF = new JTextField();
    campoCPF.setBounds(300, 250, 360, 30);
    campoCPF.setText(usu[3]);

    add(campoCPF);

    JButton botaoCancelar = new JButton();
    botaoCancelar.setText("CANCELAR");
    botaoCancelar.setBounds(310, 390, 160, 50);

    botaoCancelar.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Perfil p = new Perfil(usu[2]);
        dispose();
      }
    });
    add(botaoCancelar);

    JButton botaoAlterar = new JButton();
    botaoAlterar.setText("ALTERAR");
    botaoAlterar.setBounds(500, 390, 160, 50);

    botaoAlterar.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Cliente novo = new Cliente(campoNome.getText(), campoSobrenome.getText(), campoCPF.getText(), campoUsuario.getText(), campoSenha.getText());
        
        String resultado = alterarCliente(usu[2], novo);
          if(resultado.equals("true")){
            JOptionPane.showMessageDialog(null, "Usuário alterado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            Perfil p = new Perfil(novo.getUsuario());
            dispose();
          }else{
            JOptionPane.showMessageDialog(null, "Erro ao alterar informações do usuário", "Erro", JOptionPane.ERROR_MESSAGE);
          }
        
      }

    });
    add(botaoAlterar);

    setVisible(true);
  }

      public static String alterarCliente(String usuarioAntigo, Cliente novo) {
        try {
            Socket cliente = new Socket("localhost", 5555);
            System.out.println("Cliente conectado ao servidor");

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("AlterarPerfil=..=" + novo.getNome() + "=..=" + novo.getSobrenome() + "=..=" + novo.getCpf() + "=..=" + novo.getUsuario() + "=..=" + novo.getSenha() + "=..=" + usuarioAntigo);

            Scanner s = new Scanner(cliente.getInputStream());
            String resposta = s.nextLine();

            if (resposta.equals("false")) {

                JOptionPane.showMessageDialog(null, "Erro ao encntrar informações");

                cliente.close();
            } else {

                String values[] = resposta.split(" ");
                cliente.close();
                return "true";

            }

        } catch (Exception e) {
            System.out.println("erro perfil");
        }

        return "false";
    }
  
}
