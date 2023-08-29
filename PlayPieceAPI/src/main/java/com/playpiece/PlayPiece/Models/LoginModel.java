package com.playpiece.PlayPiece.Models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "acesso")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String email;
    private String senha;
}
