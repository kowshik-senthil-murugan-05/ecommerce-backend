package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.product.ProductDTO;
import com.ecommerce.app.product.ProductService;
import com.ecommerce.app.product.ProductViewDTO;
import com.ecommerce.app.util.PageDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/product")
//@PreAuthorize("hasRole('USER', 'ADMIN')")
public class UserProductController
{
    private final ProductService productService;
    public UserProductController(ProductService productService) { this.productService = productService; }

    @GetMapping("/fetch/all")
    public ResponseEntity<PageDetails<ProductViewDTO>> fetchAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        PageDetails<ProductViewDTO> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(
                allProducts,
                HttpStatus.OK
        );
    }

    @GetMapping("/fetch/{productId}")
    public ResponseEntity<APIResponse<ProductViewDTO>> fetchProduct(@PathVariable long productId) {
        ProductViewDTO fetchedProduct = productService.fetchProduct(productId);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Product fetched successfully!!",
                        true,
                        fetchedProduct
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<PageDetails<ProductViewDTO>> searchProduct(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        PageDetails<ProductViewDTO> products = productService.searchProducts(keyword, pageNumber, pageSize, sortOrder, sortBy);

        return new ResponseEntity<>(
                products,
                HttpStatus.OK
        );
    }
}
