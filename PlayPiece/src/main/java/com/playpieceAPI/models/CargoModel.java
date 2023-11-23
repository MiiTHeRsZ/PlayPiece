package com.playpieceAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "cargo_id")
    private Integer cargoId;
    @NotBlank
    private String nome;
}
