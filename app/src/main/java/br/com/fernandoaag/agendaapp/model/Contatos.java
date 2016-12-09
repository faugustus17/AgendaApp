package br.com.fernandoaag.agendaapp.model;

import com.google.gson.annotations.SerializedName;

public class Contatos {
    @SerializedName("idContato")
    private int idContato;
    @SerializedName("nome")
    private String nome;
    @SerializedName("apelido")
    private String apelido;
    @SerializedName("dtNasc")
    private String dtNasc;
    @SerializedName("telefone")
    private String telefone;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("email")
    private String email;

    @Override
    public String toString() {
        return super.toString();
    }

    public Contatos() {
    }

    public Contatos(int idContato, String email, String tipo, String telefone,
                    String dtNasc, String apelido, String nome) {
        this.idContato = idContato;
        this.email = email;
        this.tipo = tipo;
        this.telefone = telefone;
        this.dtNasc = dtNasc;
        this.apelido = apelido;
        this.nome = nome;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
