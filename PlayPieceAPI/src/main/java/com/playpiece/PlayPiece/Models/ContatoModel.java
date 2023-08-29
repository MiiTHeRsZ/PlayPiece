package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "contato")
@Entity(name = "contato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "celular_principal")
    private Long celularPrincipal;
    @Column(name = "celular_adicional")
    private Long celular_adicional;
    @Column(name = "telefone_fixo")
    private Long telefoneFixo;
}
