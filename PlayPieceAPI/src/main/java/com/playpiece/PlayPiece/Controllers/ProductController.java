package com.playpiece.PlayPiece.Controllers;

import com.playpiece.PlayPiece.dto.ProductRecordDto;
import com.playpiece.PlayPiece.Models.ProductModel;
import com.playpiece.PlayPiece.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/***
 * @author Ian S. pereira
 */
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    // método responsavel por criar um produto
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto)
    {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    // método responsavel por mostrar todos os produtos
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts()
    {
        List<ProductModel> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            for (ProductModel product : productList) {
                UUID id = product.getIdProduct();
                // link que irá redirecionar para o produto em especifico
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    // método responsavel por mostrar um unico produto (produto foi pesquisado atraves de seu ID)
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id)
    {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID: " + id + " not found");
        }
        // link que irá redirecionar para a lista de todos os produtos existentes
        product0.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    // método responsavel por atualizar um produto (produto foi pesquisado atraves de seu ID)
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto)
    {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID: " + id + " not found");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }
    // método responsavel por deletar um produto (produto foi pesquisado atraves de seu ID)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id)
    {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID: \"" + id + "\" not found!!");
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product with ID: \"" + id + "\" deleted successfully!!");
    }

}
