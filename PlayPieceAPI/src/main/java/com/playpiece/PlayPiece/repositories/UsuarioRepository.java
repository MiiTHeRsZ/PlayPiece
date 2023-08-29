package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.Models.UsuarioModel;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    List<UsuarioModel> findByEmailUsuario(String emailUsuario);
}
