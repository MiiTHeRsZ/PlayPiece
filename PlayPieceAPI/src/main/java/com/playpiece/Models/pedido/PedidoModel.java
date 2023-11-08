package com.playpiece.Models.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpiece.Models.ClienteModel;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "pedido")
@Entity(name = "pedido")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedidoModel> itens;

    @Column(name = "valor_total")
    private double valorTotal;

}
