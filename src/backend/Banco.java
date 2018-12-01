package backend;

import frontend.Cliente;
import frontend.Mensagem;
import frontend.Sala;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Banco {

    private static List<Socket> sockets;
    private static List<Cliente> clientes;
    private static List<Sala> salas;

    private Banco() {
        salas = new ArrayList();
        clientes = new ArrayList();
        sockets = new ArrayList();
    }

    private static Banco instancia = null;

    public static Banco getInstance() {
        if (instancia == null) {
            synchronized (Banco.class) {
                if (instancia == null) {
                    instancia = new Banco();
                }
            }

        }
        return instancia;
    }

    public String excluirSala(String sala, String usuarioLogado) {
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                for (Cliente c : clientes) {
                    if (c.getUsuario().equals(usuarioLogado)) {
                        //for (String ss : c.getSalas()) {
                        //if (ss.equals(sala)) {
                        if (c.getSalas().contains(sala)) {
                            salas.remove(s);
                            c.getSalas().remove(sala);
                            return "true";
                        }
                        //}
                        //}
                    }
                }
            }
        }
        return "false";
    }

    public String removerUsuario(String sala, String usuario) {
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                s.getUsuarios().remove(usuario);
                return "true";
            }
        }
        return "false";
    }

    public String addUser(String sala, String usuario) {
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                s.getUsuarios().add(usuario);
                return "true";
            }
        }
        return "false";
    }

//add socket    
    public void addSocket(Socket e) {
        sockets.add(e);
    }

    // add cliente 
    public void addCliente(Cliente e) {
        clientes.add(e);
    }

    public String enviarUsuarios(String sala) {
        String usuarios = "";
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                for (String c : s.getUsuarios()) {
                    usuarios += c + "!...!";
                }
                return usuarios;
            }
        }
        return "false";
    }

    public String enviarMensagens(String sala) {
        String conversa = " !...!";
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                for (Mensagem m : s.getConversa()) {
                    conversa += m.getUsuario() + ": " + m.getTexto() + "!...!";
                }
                return conversa;
            }
        }
        return "false";
    }

    //pega informações do cliente
    public String pegarCliente(String usuario) {
        for (Cliente c : clientes) {
            if (c.getUsuario().equals(usuario)) {
                return c.getNome() + "=..=" + c.getSobrenome() + "=..=" + c.getUsuario() + "=..=" + c.getCpf() + "=..=" + c.getSenha();
            }
        }
        return "false";
    }

    //muda informaçoes do cliente 
    public static String mudarCliente(String usuarioAntigo, String nome, String sobrenome, String cpf, String usuario, String senha) {
        Cliente novo = new Cliente(nome, sobrenome, cpf, usuario, senha);
        for (Cliente c : clientes) {
            if (c.getUsuario().equals(usuarioAntigo)) {
                clientes.remove(c);
                clientes.add(novo);
                return "true";
            }
        }
        return "false";
    }

    //cria nova sala
    public String criarSala(String nome, String tipo, String dono) {
        for (Sala t : salas) {
            if (t.getNome().equals(nome)) {
                return "false";
            }
        }
        for (Cliente c : clientes) {
            if (c.getUsuario().equals(dono)) {
                salas.add(new Sala(nome, tipo, dono));
                c.addSala(nome);
                return "true";
            }

        }
        return "false";
    }

    public String pegarSala(String cliente) {

        for (Cliente c : clientes) {
            if (c.getUsuario().equals(cliente)) {

                return c.nomeSalas();
            }
        }

        return "false";
    }

    public String pegarTodasSalas(String tipo) {
        String nomeSalas = "false=..=";
        for (Sala s : salas) {
            if (s.getTipo().equals(tipo)) {
                nomeSalas += s.getNome() + "=..=";
            }
            if (salas == null) {
                return nomeSalas;
            }
        }

        return nomeSalas;

    }

    public String addMensagem(String sala, String usuario, String mensagem) {
        for (Sala s : salas) {
            if (s.getNome().equals(sala)) {
                s.adicionarMensagem(new Mensagem(usuario, mensagem));
                return "true";
            }
        }
        return "false";
    }

    public String informacaoSala(String nome, String tipo) {
        System.out.println(nome + " " + tipo);
        String informacao = null;

        for (Sala s : salas) {
            if (s.getNome().equals(nome)) {
                informacao = s.getDono() + "=..=" + s.getTipo() + "=..=" + s.getNome();

                return informacao;

            }
        }

        return informacao;
    }

    public static List<Socket> getSockets() {
        return sockets;
    }

    public static void setSockets(List<Socket> sockets) {
        Banco.sockets = sockets;
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void setClientes(List<Cliente> clientes) {
        Banco.clientes = clientes;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public static void setSalas(List<Sala> salas) {
        Banco.salas = salas;
    }

}
