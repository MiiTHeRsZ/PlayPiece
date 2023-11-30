package com.playpieceAPI.models;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Table(name = "usuario")
@Entity(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z-À-ú= \\s]+$", message = "Nome deve conter apenas letras")
    private String nome;

    @CPF
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "CPF deve conter apenas números")
    private String cpf;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cargo_id", referencedColumnName = "cargo_id")
    @Enumerated(EnumType.STRING)
    private CargoModel cargo;

    @Column(name = "email")
    @NotBlank
    @Email
    private String emailUsuario;

    private String senha;

    private Boolean ativo;
}
