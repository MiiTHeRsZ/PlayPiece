package com.playpiece.PlayPiece.Models;

public class ContatoModel {
    private static int count;
    private int id;
    private Long telefoneFixo;
    private Long celularPrincipal;
    private Long celularAdicional;

    public ContatoModel(Long telefoneFixo) {
        this.id = ++count;
        this.telefoneFixo = telefoneFixo;
    }

    public ContatoModel(Long telefoneFixo, Long celularPrincipal) {
        this.id = ++count;
        this.telefoneFixo = telefoneFixo;
        this.celularPrincipal = celularPrincipal;
    }

    public ContatoModel(Long telefoneFixo, Long celularPrincipal, Long celularAdicional) {
        this.id = ++count;
        this.telefoneFixo = telefoneFixo;
        this.celularPrincipal = celularPrincipal;
        this.celularAdicional = celularAdicional;
    }

    public ContatoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(Long telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public Long getCelularPrincipal() {
        return celularPrincipal;
    }

    public void setCelularPrincipal(Long celularPrincipal) {
        this.celularPrincipal = celularPrincipal;
    }

    public Long getCelularAdicional() {
        return celularAdicional;
    }

    public void setCelularAdicional(Long celularAdicional) {
        this.celularAdicional = celularAdicional;
    }

}
