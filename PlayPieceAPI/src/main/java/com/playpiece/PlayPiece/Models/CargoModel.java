package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cargo")
@Entity(name = "cargo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CargoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
}
