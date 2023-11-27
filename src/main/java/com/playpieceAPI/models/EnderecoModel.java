package com.playpieceAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "endereco")
@Entity(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "endereco_id")
    private Long enderecoId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "cliente_id")
    @JsonIgnore
    private ClienteModel cliente;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "CEP deve conter apenas letras")
    private String cep;

    @NotBlank
    private String logradouro;

    private int numero;

    @Nullable
    private String complemento;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    @Size(max = 2, min = 2)
    private String uf;

    private boolean padrao;

    private boolean ativo;

}
