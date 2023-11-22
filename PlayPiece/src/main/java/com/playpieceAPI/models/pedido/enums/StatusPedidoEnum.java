package com.playpieceAPI.models.pedido.enums;

public enum StatusPedidoEnum {
    AGUARDANDO_PAGAMENTO("AP", "Aguardando Pagamento"),
    PAGAMENTO_REJEITADO("PR", "Pagamento Rejeitado"),
    PAGAMENTO_COM_SUCESSO("PS", "Pagamento Com Sucesso"),
    AGUARDANDO_RETIRADA("AR", "Aguardando Retirada"),
    EM_TRANSITO("ET", "Em Trânsito"),
    ENTREGUE("EN", "Entregue");

    private final String valor;
    private final String descricao;

    StatusPedidoEnum(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

}
