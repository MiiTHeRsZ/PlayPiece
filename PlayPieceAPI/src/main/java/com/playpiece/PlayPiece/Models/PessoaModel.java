/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "pessoa")
@Entity(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PessoaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Long cpf;
    private String email;
    private Long cep;
    private String endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contato", referencedColumnName = "id")
    private ContatoModel contato;

    private String fotoPerfil;
    private Boolean ativo;
}
