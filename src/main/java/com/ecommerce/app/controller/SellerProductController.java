package com.ecommerce.app.controller;

import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.product.ProductDTO;
import com.ecommerce.app.product.ProductService;
import com.ecommerce.app.product.ProductViewDTO;
import com.ecommerce.app.util.AppUtil;
import com.ecommerce.app.util.PageDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/product")
//@PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
public class SellerProductController {

    private final ProductService productService;

    public SellerProductController(ProductService productService)
    {
        this.productService = productService;
    }

    // --- Seller Product APIs ---
    @PostMapping("/add")
    public ResponseEntity<APIResponse<ProductViewDTO>> addProduct(@Valid @RequestBody ProductDTO dto)
    {
        ProductViewDTO savedProduct = productService.saveProduct(dto);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Product created successfully",
                        true,
                        savedProduct),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse<ProductDTO>> updateProduct(@RequestBody ProductDTO dto)
    {
        productService.updateProduct(dto);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Product with product id : " + dto.id + " updated successfully!!",
                        true, null),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<APIResponse<ProductDTO>> deleteProduct(@PathVariable long productId)
    {
        productService.deleteProduct(productId);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Product with product id : " + productId + " deleted successfully!!",
                        true, null),
                HttpStatus.OK
        );
    }

    @GetMapping("/fetch/products/forSeller/{sellerId}")
    public ResponseEntity<PageDetails<ProductViewDTO>> fetchProductsBySeller(
            @PathVariable long sellerId,
            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder)
    {
        PageDetails<ProductViewDTO> products = productService.fetchProductsBySeller(sellerId, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(
                products,
                HttpStatus.OK
        );
    }
}

