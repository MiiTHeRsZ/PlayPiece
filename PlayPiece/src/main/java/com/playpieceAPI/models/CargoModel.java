package com.playpiece.models;

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
    private Integer id;
    @NotBlank
    private String nome;
}
