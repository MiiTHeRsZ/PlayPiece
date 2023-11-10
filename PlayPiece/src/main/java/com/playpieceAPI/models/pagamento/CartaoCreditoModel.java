// package com.playpieceAPI.models.pagamento;

// import java.util.Date;

// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// import jakarta.persistence.*;
// import lombok.*;

// @Table(name = "cartao_credito")
// @Entity(name = "cartao_credito")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
// @Getter
// @Setter
// @AllArgsConstructor
// @ToString
// public class CartaoCreditoModel {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
// private PagamentoModel pagamento;

// @Column(name = "numero_cartao")
// private String numeroCartao;

// @Column(name = "cvv_cartao")
// private String cvv;

// @Column(name = "nome_titular")
// private String nomeTitular;

// @Column(name = "data_validade")
// private Date dataValidade;

// @Column(name = "parcelas")
// private int parcelas;

// @Column(name = "valor_parcela")
// private Double valorParcela;

// @Column(name = "data_vencimento")
// private Date dataVencimento;
// }
