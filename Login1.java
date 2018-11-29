package chateadosDrive;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Login1 extends Configuration {

    public Login1() {
        Border borda = BorderFactory.createLineBorder(new Color(0, 121, 107));

        JLabel chateadosTitulo = new JLabel();
        chateadosTitulo.setText("Login CHATados");
        chateadosTitulo.setFont(new Font("Dialog", Font.BOLD, 30));
        chateadosTitulo.setBounds(250, 20, 500, 70);

        add(chateadosTitulo);

        JLabel text = new JLabel();
        text.setText("<html><body>Para acessar o CHATados realize o login com seu nome de usuário e senha! Use os campos disponíveis abaixo: </body></html>");
        text.setFont(new Font("Dialog", Font.PLAIN, 20));
        text.setBounds(100, 100, 625, 100);
        add(text);

        JLabel usuario = new JLabel();
        usuario.setText("Usuário:");
        usuario.setFont(new Font("usuario", Font.PLAIN, 20));
        usuario.setBounds(150, 240, 300, 30);
        add(usuario);

        JTextField campoTextoUsuario = new JTextField();
        campoTextoUsuario.setBounds(265, 240, 400, 30);
        add(campoTextoUsuario);

        JLabel senha = new JLabel();
        senha.setText("Senha:");
        senha.setFont(new Font("senha", Font.PLAIN, 20));
        senha.setBounds(150, 300, 300, 30);

        add(senha);

        JTextField campoTextoSenha = new JPasswordField();
        campoTextoSenha.setBounds(265, 300, 400, 30);
        add(campoTextoSenha);
        
        campoTextoUsuario.addKeyListener(new KeyAdapter() {
            public void keyTyped (KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    ValidarLogin(campoTextoUsuario, campoTextoSenha);
                }
            }
        });
        
        campoTextoSenha.addKeyListener(new KeyAdapter() {
            public void keyTyped (KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    ValidarLogin(campoTextoUsuario, campoTextoSenha);
                }
            }
        });

        JButton botaoEntrar = new JButton();
        botaoEntrar.setText("ENTRAR");
        botaoEntrar.setBounds(515, 380, 150, 50);
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ValidarLogin(campoTextoUsuario, campoTextoSenha);

            }
        });

        add(botaoEntrar);

        JButton botaoCadastro = new JButton();
        botaoCadastro.setText("CADASTRAR-SE");
        botaoCadastro.setBounds(350, 380, 150, 50);
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cadastro cadastro = new Cadastro();
                dispose();
            }
        });

        add(botaoCadastro);

        JButton botaoEsqueci = new JButton();
        botaoEsqueci.setText("Esqueci minha senha");
        botaoEsqueci.setBounds(522, 340, 145, 20);
        botaoEsqueci.setBackground(new Color(0, 121, 107));
        botaoEsqueci.setBorder(borda);
        botaoEsqueci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecuperarSenha recuperarSenha = new RecuperarSenha();
                dispose();

            }
        });

        add(botaoEsqueci);

        JLabel informacao = new JLabel();
        informacao.setText("<html><body style='text-align: justify;'>Lorem Ipsum is simply dummy text of the printing and typesetting industry. * Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</body></html>");
        informacao.setFont(new Font("Roboto", Font.PLAIN, 15));
        informacao.setBounds(100, 460, 620, 200);

        add(informacao);

        setVisible(true);

    }

    public void ValidarLogin(JTextField campoTextoUsuario, JTextField campoTextoSenha) {
        String usuario = campoTextoUsuario.getText();
        String senha = campoTextoSenha.getText();
        if (!campoTextoUsuario.getText().equals("") && !campoTextoSenha.getText().equals("")) {
            try {
                Socket cliente = new Socket("localhost", 5555);
                System.out.println("Cliente conectado ao servidor");

                PrintStream saida = new PrintStream(cliente.getOutputStream());
                saida.println("login=..=" + usuario + "=..=" + senha);

                Scanner s = new Scanner(cliente.getInputStream());
                String resposta = s.nextLine();

                if (resposta.equals("true")) {
                    Home home = new Home(usuario);
                    cliente.close();
                    dispose();
                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao logar");
                    cliente.close();
                }

            } catch (Exception ex) {
                System.out.println("error de logo");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Você precisa informar seu nome de usuário e senha", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

}
