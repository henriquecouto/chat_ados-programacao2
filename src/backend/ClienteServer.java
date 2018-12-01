/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.Recebedor;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author italo
 */
public class ClienteServer {

  public static void main(String[] args) throws IOException {

    Socket cliente = new Socket("localhost", 5555);
    System.out.println("Cliente conectado ao servidor");

    Recebedor r = new Recebedor(cliente.getInputStream());
    new Thread(r).start();

    //Enviador e = new Enviador(cliente.getOutputStream());
    //new Thread(e).start();
  }
}
