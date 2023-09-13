package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
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
    private Long id;
    private String nome;

    private String cpf;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    @Enumerated(EnumType.STRING)
    private CargoModel cargo;

    @Column(name = "email")
    private String emailUsuario;

    private String senha;

    private Boolean ativo;
}
