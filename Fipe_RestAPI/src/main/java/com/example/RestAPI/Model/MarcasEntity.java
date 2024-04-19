package com.example.RestAPI.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marcas")
public class MarcasEntity {

    @Id
    private String id;
    private int codigo;
    private String nome;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

}
