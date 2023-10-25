package com.playpiece.PlayPiece.Models;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.playpiece.validations.interfaces.IValidarNome;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "cliente")
@Entity(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[a-zA-Z \\s]+$")
    private String genero;

    @NotBlank
    @Email
    private String email;

    private String senha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_fat", referencedColumnName = "id")
    private EnderecoModel enderecoFaturamento;
    @Transient
    private List<EnderecoModel> listaEndereco;

    private Boolean ativo;
}
