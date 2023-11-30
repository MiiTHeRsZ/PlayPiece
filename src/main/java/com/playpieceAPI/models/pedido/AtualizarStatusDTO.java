package com.playpieceAPI.models.pedido;

public class AtualizarStatusDTO {
    private Long id;
    private String sigla;

    public AtualizarStatusDTO(Long id, String sigla) {
        this.id = id;
        this.sigla = sigla;
    }

    public AtualizarStatusDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
