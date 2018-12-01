/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.util.ArrayList;

/**
 *
 * @author rychard
 */
public class Sala {
    
    private ArrayList<String>usuarios;
    private ArrayList<Mensagem>conversa;
    private String tipo;
    private String nome;
    private String dono;
    
    public Sala(String nome,String tipo,String dono){
        this.dono=dono;
        this.nome=nome;
        this.tipo=tipo;
        this.usuarios = new ArrayList();
        this.conversa = new ArrayList();
    }
    
    public void adicionarMensagem(Mensagem msg){
        conversa.add(msg);
    }

    public ArrayList<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<String> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Mensagem> getConversa() {
        return conversa;
    }

    public void setConversa(ArrayList<Mensagem> conversa) {
        this.conversa = conversa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }
    
}
