package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans;

public class nomeCheckBean {
    private Integer id;
    private String nome;

    public nomeCheckBean(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Integer getId() {
        return id;
    }
}
