package com.ecommerce.app.product;

import com.ecommerce.app.appuser.AppUserService;
import com.ecommerce.app.category.Category;
import com.ecommerce.app.category.CategoryService;
import com.ecommerce.app.exceptionhandler.APIException;
import com.ecommerce.app.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.app.util.PageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{

    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    private final AppUserService userService;

    public ProductServiceImpl(ProductRepo productRepo, CategoryService categoryService, AppUserService userService)
    {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public ProductViewDTO saveProduct(ProductDTO dto)
    {
        Category category = categoryService.getCategoryObj(dto.categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", dto.categoryId));

        Product productObj = productRepo.findByCategoryIdAndProductName(dto.categoryId, dto.productName);

        if(productObj != null)
        {
            throw new APIException("Product already exists!");
        }

        Product savedProduct = productRepo.save(buildProductFromDTO(dto, category));

        ProductViewDTO productViewDTO = convertToViewDTO(savedProduct);
        System.out.println("Product view -> " + productViewDTO);

        return productViewDTO;
    }

    private Product buildProductFromDTO(ProductDTO dto, Category category)
    {
        Product product = new Product();

        product.setProductName(dto.productName);
        product.setDescription(dto.description);
        product.setCategory(category);
        product.setProductPrice(dto.productPrice);
        product.setProductImage("default.png");
        product.setDiscount(dto.discount);
        double finalPrice = calculationOfFinalPrice(dto.productPrice, dto.discount);
        product.setFinalPrice(finalPrice);
        product.setAvailableProductQuantity(dto.totalQuantity);
        product.setSellerId(1); //todo

        return product;
    }

    public PageDetails<ProductViewDTO> getAllProducts(int pageNum, int pageSize, String sortBy, String sortOrder)
    {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepo.findAll(pageDetails);

        if(productPage.isEmpty())
        {
            throw new APIException("No Products created!");
        }

        List<ProductViewDTO> productDTOS = productPage.getContent().stream()
                .map(this::convertToViewDTO)
                .toList();

        return new PageDetails<>(
                productDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public PageDetails<ProductViewDTO> getAllProductsForCategoryId(int pageNum, int pageSize, String sortBy, String sortOrder, long categoryId)
    {
        if(!categoryService.isValidCategory(categoryId))
        {
            throw new APIException("Category not exists!!");
        }

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepo.findAllByCategoryId(categoryId, pageDetails);

        if(productPage.isEmpty())
        {
            throw new APIException("No products available for this category!");
        }

        List<ProductViewDTO> productDTOS = productPage.getContent().stream()
                .map(this::convertToViewDTO)
                .toList();

        return new PageDetails<>(
                productDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }


    public ProductViewDTO updateProduct(ProductDTO dto)
    {
        Product product = productRepo.findById(dto.id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product", "productId", dto.id
                ));

        Optional.ofNullable(dto.imgUrl).ifPresent(product::setProductImage);
        Optional.ofNullable(dto.productName).ifPresent(product::setProductName);
        Optional.ofNullable(dto.description).ifPresent(product::setDescription);

        if(dto.productPrice != product.getProductPrice() || dto.discount != product.getDiscount())
        {
            product.setProductPrice(dto.productPrice);
            product.setDiscount(dto.discount);
            double finalPrice = calculationOfFinalPrice(dto.productPrice, dto.discount);
            product.setFinalPrice(finalPrice);
        }

        if(dto.totalQuantity != product.getAvailableProductQuantity())
        {
            product.setAvailableProductQuantity(dto.totalQuantity);
        }

        ProductViewDTO productViewDTO = convertToViewDTO(productRepo.save(product));
        System.out.println("Product View -> " + productViewDTO);

        return productViewDTO;
    }

    private double calculationOfFinalPrice(double productPrice, double discount)
    {
        return productPrice - ((discount/100) * productPrice);
    }

    public void deleteProduct(long productId)
    {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product", "productId", productId
                ));

        productRepo.delete(product);
    }


    private ProductViewDTO convertToViewDTO(Product savedProduct)
    {
        ProductViewDTO dto = new ProductViewDTO();

        dto.id = savedProduct.getId();
        dto.imgUrl = savedProduct.getProductImage();
        dto.productName = savedProduct.getProductName();
        dto.description = savedProduct.getDescription();
        dto.category = savedProduct.getCategory().getCategoryName();
        dto.productPrice = savedProduct.getFinalPrice();
        dto.discount = savedProduct.getDiscount();
        dto.seller = userService.getUserName(savedProduct.getSellerId());

        return dto;
    }

    private ProductDTO convertToDTO(Product savedProduct)
    {
        ProductDTO dto = new ProductDTO();

        dto.id = savedProduct.getId();
        dto.imgUrl = savedProduct.getProductImage();
        dto.productName = savedProduct.getProductName();
        dto.description = savedProduct.getDescription();
        dto.categoryId = savedProduct.getCategory().getId();
        dto.productPrice = savedProduct.getProductPrice();
        dto.discount = savedProduct.getDiscount();
        dto.sellerId = savedProduct.getSellerId();
        dto.totalQuantity = savedProduct.getAvailableProductQuantity();

        return dto;
    }

    public ProductDTO getProductObjForProductId(long productId)
    {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        return convertToDTO(product);
    }

    public PageDetails<ProductViewDTO> searchProducts(String keyword, int pageNum, int pageSize, String sortOrder, String sortBy) {

        Sort sortOrderAndBy = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortOrderAndBy);
        System.out.println("Keyword -> " + keyword);
        // Normalize empty strings to null for query compatibility
        keyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword : null;
//        category = (category != null && !category.trim().isEmpty()) ? category : null;
        System.out.println("Keyword -> " + keyword);
        Page<Product> productPage = productRepo.searchByProductName(keyword, pageDetails);

        System.out.println("Products -> " + productPage.getContent());

        if(productPage.isEmpty())
        {
            throw new APIException("No Products Available!");
        }

        List<ProductViewDTO> productDTOS = productPage.getContent().stream()
                .map(this::convertToViewDTO)
                .toList();

        return new PageDetails<>(
                productDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public PageDetails<ProductViewDTO> fetchProductsBySeller(long sellerId, int pageNum, int pageSize, String sortOrder, String sortBy)
    {
        Sort sortOrderAndBy = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortOrderAndBy);
        Page<Product> productPage = productRepo.findAllBySellerId(sellerId, pageDetails);

        if(productPage.isEmpty())
        {
            throw new APIException("No products available for seller!");
        }

        List<ProductViewDTO> productDTOS = productPage.getContent().stream()
                .map(this::convertToViewDTO)
                .toList();

        return new PageDetails<>(
                productDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public ProductViewDTO fetchProduct(long productId)
    {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        return convertToViewDTO(product);
    }

    public long sellerIdForProduct(long productId)
    {
        return productRepo.getReferenceById(productId).getSellerId();
    }
}
