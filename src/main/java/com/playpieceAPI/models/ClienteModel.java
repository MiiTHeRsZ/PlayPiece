package com.playpieceAPI.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.validations.interfaces.IValidarNome;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Table(name = "cliente")
@Entity(name = "cliente")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "clienteId")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;

    @CPF
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "CPF deve conter apenas números")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z-À-ú= \\s]+$", message = "Nome deve conter apenas letras")
    @IValidarNome
    private String nome;

    @PastOrPresent
    private LocalDate dt_nascimento;

    @Size(min = 0, max = 2)
    @Pattern(regexp = "^[a-zA-Z \\s]+$")
    private String genero;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_fat", referencedColumnName = "endereco_id")
    @Nullable
    private EnderecoModel enderecoFaturamento;

    @OneToMany(mappedBy = "cliente", cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    @Nullable
    private List<EnderecoModel> listaEndereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PedidoModel> pedidos;

    private Boolean ativo;

    public ClienteModel() {
        listaEndereco = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

}