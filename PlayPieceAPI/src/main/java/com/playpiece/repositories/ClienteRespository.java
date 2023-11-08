package com.playpiece.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playpiece.Models.ClienteModel;

@Repository
public interface ClienteRespository extends JpaRepository<ClienteModel, Long> {

    ClienteModel findByEmail(String email);

    ClienteModel findByCpf(String cpf);

}
