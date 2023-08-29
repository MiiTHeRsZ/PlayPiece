package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.playpiece.PlayPiece.Models.LoginModel;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, Integer> {

    @Query(value = "select b.id, a.email_usuario as email, b.senha from usuario a inner join acesso b on a.id = b.id_usuario where a.email_usuario like ?1", nativeQuery = true)
    LoginModel getUserLoginInfo(String email);

    @Modifying
    @Transactional
    @Query(value = "insert into acesso (senha, id_pessoa, id_usuario) values (?1, ?2, ?3)", nativeQuery = true)
    int postLoginUser(String senha, int id_pessoa, int id_usuario);
}
