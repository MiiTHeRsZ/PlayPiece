package com.playpieceAPI.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.validations.interfaces.IValidarNome;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Pattern(regexp = "^[a-zA-Z \\s]+$", message = "Nome deve conter apenas letras")
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
    private EnderecoModel enderecoFaturamento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<EnderecoModel> listaEndereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<PedidoModel> pedidos;

    private Boolean ativo;

    public ClienteModel() {
        listaEndereco = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

}