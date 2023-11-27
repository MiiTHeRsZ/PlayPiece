package com.playpieceAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.UsuarioModel;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    UsuarioModel findByEmailUsuario(String emailUsuario);

    List<UsuarioModel> findByNomeContaining(String nome);
}
