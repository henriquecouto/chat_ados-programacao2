/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chateadosDrive;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author italo
 */
public class RecebedorServidor implements Runnable {

    private InputStream servidor;
    private Socket socket;

    public RecebedorServidor(Socket socket, InputStream servidor) {
        this.servidor = servidor;
        this.socket = socket;
    }

    public void run() {

        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {

            String linha = s.nextLine();

            System.out.println(linha);

            String[] values = linha.split("=..=");

            switch (values[0]) {

                case "login": 
                    enviarMensagem(validarLogin(values[1], values[2]));
                    break;

                case "cadastrar": 
                    enviarMensagem(cadastrarUsuario(values[1], values[2], values[3], values[4], values[5]));
                    break;

                case "RecuperarSenha": 
                    enviarMensagem(recuperarSenha(values[1], values[2]));
                    break;

                case "Perfil":
                    procurarCliente(values[1]);
                    break;

                case "AlterarPerfil":
                    editarPerfil(values[1], values[2], values[3], values[4], values[5], values[6]);
                    break;

                case "criarSala": 
                    novaSala(values[1], values[2], values[3]);
                    break;

                case "pegarSala": 
                    salasUsuario(values[1]);
                    break;
                case "pegarTSala": 
                    pegarTSalas(values[1]);
                    break;
                case "enviarMensagem": 
                    addMensagem(values[1], values[2], values[3]);
                    break;
                case "informacaoSala": 
                    informacaoSala(values[1], values[2]);
                    break;
                case "AtualizarMensagens": 
                    pegarMensagens(values[1]);
                    break;
                case "AtualizarUsuarios":
                    pegarUsuarios(values[1]);
                    break;
                case "addUserNaSala":
                    addUserNaSala(values[1], values[2]);
                    break;
                case "SairDaSala":
                    removerUsuario(values[1], values[2]);
                    break;
                case "excluirSala":
                    excluirSala(values[1], values[2]);
                    break;
            }

        }

    }
    
    public void excluirSala(String sala, String usuarioLogado){
        try{
            String resposta = Banco.getInstance().excluirSala(sala, usuarioLogado);
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(resposta);
        }catch(Exception e){
            System.out.println("erro ao excluir sala do banco");
        }
    }
    
    public void removerUsuario(String sala, String usuario){
        try {
            String resposta = Banco.getInstance().removerUsuario(sala, usuario);
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(resposta);
        } catch (Exception e) {
            System.out.println("Erro ao excluir usuario do banco");
        }
    }
    
    public void addUserNaSala(String sala, String usuario){
        try{
            String resposta = Banco.getInstance().addUser(sala, usuario);
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(resposta);
        }catch(Exception e){
            System.out.println("erro ao add user na sala");
        }
    }
    
    public void pegarUsuarios(String sala){
        try {
            String conversa = Banco.getInstance().enviarUsuarios(sala);
            
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(conversa);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuarios da sala "+sala);
        }
    }
    
    public void pegarMensagens(String sala){
        try {
            String conversa = Banco.getInstance().enviarMensagens(sala);
            
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(conversa);
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagens ao usuário");
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(mensagem);
        } catch (Exception e) {
            System.out.println("erro de eviador");
        }
    }

    public String validarLogin(String usuario, String senha) {

        for (Cliente c : Banco.getClientes()) {

            if (c.getUsuario().equals(usuario) && c.getSenha().equals(senha)) {

                return "true";

            }

        }
        return "false";

    }

    public String cadastrarUsuario(String nome, String sobrenome, String cpf, String usuario, String senha) {

        try {
            int contuser = 0, contcpf=0;
            for (Cliente c : Banco.getClientes()) {
                if (c.getUsuario().equals(usuario)) {
                    contuser++;
                }
                if(c.getCpf().equals(cpf)){
                    contcpf++;
                }
            }
            
            if (contuser == 0 && contcpf == 0) {
                Cliente c = new Cliente(nome, sobrenome, cpf, usuario, senha);
                Banco.getInstance().addCliente(c);
            } else if(contuser>0){
                JOptionPane.showMessageDialog(null, "Usuário já existente");
                return "false";
            }else{
                JOptionPane.showMessageDialog(null, "CPF já cadastrado");
                return "false";
            }
            return "true";
        } catch (Exception e) {
            System.out.println("error de cadastro");
        }

        return "false";

    }

    public String recuperarSenha(String usuario, String cpf) {

        for (Cliente c : Banco.getClientes()) {

            if (c.getUsuario().equals(usuario) && c.getCpf().equals(cpf)) {

                return c.getSenha();

            }

        }
        return "false";
    }

    public void procurarCliente(String usuario) {
        try {

            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(Banco.getInstance().pegarCliente(usuario));
        } catch (Exception e) {
            System.out.println("erro de procura");
        }
    }

    public void editarPerfil(String nome, String sobrenome, String cpf, String usuario, String senha, String usuarioAntigo) {
        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(Banco.getInstance().mudarCliente(usuarioAntigo, nome, sobrenome, cpf, usuario, senha));
        } catch (Exception e) {
            System.out.println("Erro ao alterar");
        }
    }

    public void novaSala(String nome, String tipo, String usuario) {

        String result = Banco.getInstance().criarSala(nome, tipo, usuario);

        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(result);
        } catch (Exception e) {
            System.out.println("Erro ao criar sala");
        }

    }

    public void salasUsuario(String cliente) {
        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(Banco.getInstance().pegarSala(cliente));
        } catch (Exception e) {
            System.out.println("Erro ao achar sala");
        }
    }

    public void pegarTSalas(String tipo) {
        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(Banco.getInstance().pegarTodasSalas(tipo));
        } catch (Exception e) {
            System.out.println("Erro ao achar sala");
        }
    }

    public void addMensagem(String sala, String usuario, String mensagem) {
        try {
            Banco.getInstance().addMensagem(sala, usuario, mensagem);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar mensagem");
        }
    }

    public void informacaoSala(String nome, String tipo) {
        try {
            PrintStream saida = new PrintStream(socket.getOutputStream());
            saida.println(Banco.getInstance().informacaoSala(nome, tipo));
        } catch (Exception e) {
            System.out.println("Erro ao achar sala");
        }
    }

}
