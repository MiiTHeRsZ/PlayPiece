/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playpiece.PlayPiece.Models;

/**
 *
 * @author KINOO
 */
public class PessoaModel {

    private static int count;
    private int id;
    private String nome;
    private Long cpf;
    private String email;
    private Long cep;
    private String endereco;
    private ContatoModel contato;
    private String fotoPerfil;

    public PessoaModel(String nome, Long cpf, String email, Long cep, String endereco, ContatoModel contato,
            String fotoPerfil) {
        this.id = ++count;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.contato = contato;
        this.fotoPerfil = fotoPerfil;
    }

    public PessoaModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ContatoModel getContato() {
        return contato;
    }

    public void setContato(ContatoModel contato) {
        this.contato = contato;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "PessoaModel [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", cep=" + cep
                + ", endereco=" + endereco + ", contato=" + contato + ", fotoPerfil=" + fotoPerfil + "]";
    }

}
