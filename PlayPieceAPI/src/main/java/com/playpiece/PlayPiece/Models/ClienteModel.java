package com.playpiece.PlayPiece.Models;

import java.time.LocalDate;
import java.util.List;

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
    private String cpf;
    private String nome;
    private LocalDate dt_nascimento;
    private String genero;
    private String email;
    private String senha;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_fat", referencedColumnName = "id")
    private EnderecoModel enderecoFaturamento;
    @Transient
    private List<EnderecoModel> listaEndereco;
    private Boolean ativo;
}
