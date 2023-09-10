package com.playpiece.PlayPiece.repositories;

import com.playpiece.PlayPiece.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

/***
 * @author Ian S. pereira
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}
