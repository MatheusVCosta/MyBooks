package br.com.senaijandira.mybooks.model;

public class livro {

    private int id;

    //A imagem de capa é um array de bytes
    private byte [] capa;

    private String titulo;
    private String descricao;



    public livro(){}

    public livro(int id, byte[] capa, String titulo, String descricao){
        this.id=id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
