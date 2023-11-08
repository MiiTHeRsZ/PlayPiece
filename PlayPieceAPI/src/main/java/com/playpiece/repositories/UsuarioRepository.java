package com.playpiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.Models.UsuarioModel;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    List<UsuarioModel> findByEmailUsuario(String emailUsuario);

    List<UsuarioModel> findByNomeContaining(String nome);
}
