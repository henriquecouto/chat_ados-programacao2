/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author italo
 */
import backend.Banco;
import java.io.IOException;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

  static List<Socket> clientes = new ArrayList();

  public static void main(String[] args) throws IOException {

    Banco banco = Banco.getInstance();

    ServerSocket servidor = new ServerSocket(5555);
    System.out.println("Porta 123 Aberta!");

    while (true) {

      Socket cliente = servidor.accept();
      System.out.println("Nova conexão com o Cliente "
              + cliente.getInetAddress().getHostAddress());

      banco.addSocket(cliente);

      RecebedorServidor r = new RecebedorServidor(cliente, cliente.getInputStream());
      new Thread(r).start();

    }

  }
}
