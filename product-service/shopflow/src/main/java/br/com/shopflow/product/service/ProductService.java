package br.com.shopflow.product.service;

import br.com.shopflow.product.dto.ProductRequest;
import br.com.shopflow.product.dto.ProductResponse;
import br.com.shopflow.product.dto.ProductUpdateRequest;
import br.com.shopflow.product.dto.StockDecreaseRequest;
import br.com.shopflow.product.exception.InsufficientStockException;
import br.com.shopflow.product.exception.ProductNotFoundException;
import br.com.shopflow.product.model.Product;
import br.com.shopflow.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private List<ProductResponse> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    private ProductResponse findById(Long id){
        return productRepository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse create(ProductRequest request){
        var product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .build();

        return ProductResponse.from(productRepository.save(product));
    }

    private ProductResponse update(Long id, ProductUpdateRequest request){
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (request.name() != null) product.setName(request.name());
        if (request.description() != null) product.setDescription(request.description());
        if (request.price() != null) product.setPrice(request.price());
        if (request.stockQuantity() != null) product.setStockQuantity(request.stockQuantity());

        return ProductResponse.from(productRepository.save(product));

    }

    private void delete(Long id){

        if (!productRepository.existsById(id)){
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    public ProductResponse decreaseStock(Long id, StockDecreaseRequest dto){

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getStockQuantity() < dto.quanttity()){
            throw new InsufficientStockException(dto.quanttity(), product.getStockQuantity());
        }

        product.setStockQuantity(product.getStockQuantity() - dto.quanttity());
        return ProductResponse.from(productRepository.save(product));
    }


}
