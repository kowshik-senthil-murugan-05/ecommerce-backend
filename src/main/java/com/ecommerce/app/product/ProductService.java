package com.ecommerce.app.product;

import com.ecommerce.app.util.PageDetails;

public interface ProductService
{
    ProductViewDTO saveProduct(ProductDTO dto);

    PageDetails<ProductViewDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortOrder);

    PageDetails<ProductViewDTO> getAllProductsForCategoryId(int pageNum, int pageSize, String sortBy, String sortOrder, long categoryId);

    ProductViewDTO updateProduct(ProductDTO dto);

    void deleteProduct(long productId);

    ProductViewDTO fetchProduct(long productId);

    ProductDTO getProductObjForProductId(long productId);

    PageDetails<ProductViewDTO> searchProducts(String keyword, int pageNum, int pageSize, String sortOrder, String sortBy);

    PageDetails<ProductViewDTO> fetchProductsBySeller(long sellerId, int pageNum, int pageSize, String sortOrder, String sortBy);

    long sellerIdForProduct(long productId);
}
