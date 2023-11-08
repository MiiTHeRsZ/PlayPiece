package com.playpieceAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpieceAPI.models.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    List<UsuarioModel> findByEmailUsuario(String emailUsuario);

    List<UsuarioModel> findByNomeContaining(String nome);
}
