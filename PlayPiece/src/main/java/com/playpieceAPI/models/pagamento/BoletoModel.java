// package com.playpieceAPI.models.pagamento;

// import java.util.Date;

// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// import jakarta.persistence.*;
// import lombok.*;

// @Table(name = "boleto")
// @Entity(name = "boleto")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
// @Getter
// @Setter
// @AllArgsConstructor
// @ToString
// public class BoletoModel {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @JoinColumn(name = "pagamento_id", referencedColumnName = "id")
// private PagamentoModel pagamento;

// @Column(name = "numero_boleto")
// private String numeroBoleto;

// @Column(name = "data_vencimento")
// private Date dataVencimento;
// }
