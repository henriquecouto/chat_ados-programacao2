/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.Color;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author henrique
 */
public class Configuration extends JFrame {

  MaskFormatter maskCPF = null;
  
  public Configuration() {
    //Configuração padrão para as salas
    setTitle("CHATados");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setBounds(320, 0, 800, 720);
    setLayout(null);
    setResizable(false);
    getContentPane().setBackground(new Color(0, 121, 107));

    //Mascara para os campos de cpf
    try {
      maskCPF = new MaskFormatter("###.###.###-##");
    } catch (ParseException ex) {
    }
  }
}
