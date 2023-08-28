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
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private PessoaModel pessoa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    @Enumerated(EnumType.STRING)
    private CargoModel cargo;

    private double salario;
    private Boolean ativo;
}
