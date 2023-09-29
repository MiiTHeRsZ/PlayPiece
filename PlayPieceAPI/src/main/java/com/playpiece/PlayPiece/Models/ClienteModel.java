package com.playpiece.PlayPiece.Models;

import java.sql.Date;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.*;
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
    private String cpf;
    private String nome;
    private Date dt_nascimento;
    private String genero;
    private String email;
    private String senha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_fat", referencedColumnName = "id")
    private EnderecoModel enderecoFaturamento;
    private Boolean ativo;
}
