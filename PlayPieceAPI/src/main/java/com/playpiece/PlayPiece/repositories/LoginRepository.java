package com.playpiece.PlayPiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpiece.PlayPiece.Models.LoginModel;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, Integer> {

    @Query(value = "select b.id, a.email, b.senha from pessoa a inner join acesso b on a.id = b.id_pessoa where a.email like ?1", nativeQuery = true)
    LoginModel getLoginInfo(String email);

}
