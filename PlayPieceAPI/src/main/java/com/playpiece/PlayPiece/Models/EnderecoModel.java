package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "endereco")
@Entity(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_cliente")
    private Long idCliente;
    private String cep;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private boolean padrao;
    private boolean ativo;
}
