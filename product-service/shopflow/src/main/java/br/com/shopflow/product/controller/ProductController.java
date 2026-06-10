package br.com.shopflow.product.controller;

import br.com.shopflow.product.dto.ProductRequest;
import br.com.shopflow.product.dto.ProductResponse;
import br.com.shopflow.product.dto.ProductUpdateRequest;
import br.com.shopflow.product.dto.StockDecreaseRequest;
import br.com.shopflow.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@RequestBody ProductUpdateRequest request){
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock/decrease")
    public ResponseEntity<ProductResponse> decreaseStock(@PathVariable Long id, @RequestBody @Valid StockDecreaseRequest dto){
        return ResponseEntity.ok(productService.decreaseStock(id, dto));
    }

}
