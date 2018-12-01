package frontend;

import java.util.ArrayList;

public class Cliente {

  private ArrayList<String>salas;
    
  private String nome;
  private String sobrenome;
  private String cpf;
  private String usuario;
  private String senha;

  public Cliente(String nome, String sobrenome, String cpf, String usuario, String senha) {
    this.salas= new ArrayList();
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.cpf = cpf;
    this.usuario = usuario;
    this.senha = senha;
  }

  
  
  public void addSala(String nome){
      salas.add(nome);
     
  }
  
  
  
  
  public String nomeSalas(){
      String nomes="null ";
      for(String s:salas){
          nomes+=s+" ";
      }
      return nomes;
  }
  
  
  
  
  
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

    public ArrayList<String> getSalas() {
        return salas;
    }

    public void setSalas(ArrayList<String> salas) {
        this.salas = salas;
    }

    
}
