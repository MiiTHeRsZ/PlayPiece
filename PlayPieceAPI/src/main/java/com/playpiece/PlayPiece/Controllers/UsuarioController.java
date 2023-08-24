/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.playpiece.PlayPiece.Controllers;

import com.playpiece.PlayPiece.Models.ContatoModel;
import com.playpiece.PlayPiece.Models.PessoaModel;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KINOO
 */
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    private final ArrayList<PessoaModel> pessoas;

    public UsuarioController() {
        pessoas = new ArrayList<PessoaModel>(Arrays.asList(
                new PessoaModel("Leonardo", 43183345897L, "leonardo.fujimura123@gmail.com", 4671071L, "rua 853",
                        new ContatoModel(551155239249L, 5511959287161L),
                        "fotoLeo"),

                new PessoaModel("Lara", 43183345837L, "lara.fujimura123@gmail.com", 5671071L, "rua 833",
                        new ContatoModel(551153239249L),
                        "fotoLara")));
    }

    @GetMapping
    public ResponseEntity<ArrayList<PessoaModel>> GetPessoa() {

        return ResponseEntity.ok(pessoas);
    }
}
