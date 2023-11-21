package com.playpieceAPI.models.carrinho;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.ClienteModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "carrinho")
@Entity(name = "carrinho")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "carrinhoId")
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrinho_id")
    // ! Mudei de Long > int
    private Long carrinhoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "cliente_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "clienteId")
    private ClienteModel cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<ItemCarrinhoModel> itens;

    public CarrinhoModel() {
        itens = new ArrayList<>();
    }
}
