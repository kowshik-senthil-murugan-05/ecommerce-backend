package com.ecommerce.app.controller;

import com.ecommerce.app.category.CategoryDTO;
import com.ecommerce.app.category.CategoryService;
import com.ecommerce.app.exceptionhandler.APIResponse;
import com.ecommerce.app.product.ProductDTO;
import com.ecommerce.app.product.ProductService;
import com.ecommerce.app.product.ProductViewDTO;
import com.ecommerce.app.util.AppUtil;
import com.ecommerce.app.util.PageDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminProductController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    // --- Category Management ---
    @PostMapping("/category/add")
    public ResponseEntity<APIResponse<CategoryDTO>> addCategory(@Valid @RequestBody CategoryDTO dto)
    {
        CategoryDTO categoryDTO = categoryService.addCategory(dto);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Category created successfully!!",
                        true,
                        categoryDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/categories/listAll")
    public ResponseEntity<PageDetails<CategoryDTO>> listAllCategories
            (@RequestParam(name = "pageNum", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNum,
             @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
             @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder)
    {
        PageDetails<CategoryDTO> categoryDTOPageDetails = categoryService.listAllCategories(pageNum, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(
                categoryDTOPageDetails,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<APIResponse<Void>> removeCategory(@PathVariable long categoryId)
    {
        categoryService.removeCategory(categoryId);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Category with category id : " + categoryId + " removed successfully!!",
                        true, null
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/category/update")
    public ResponseEntity<APIResponse<CategoryDTO>> updateCategory(@RequestBody CategoryDTO dto)
    {
        CategoryDTO updatedCategory = categoryService.updateCategory(dto);

        return new ResponseEntity<>(
                new APIResponse<>(
                        "Category updated!",
                        true,
                        updatedCategory),
                HttpStatus.OK
        );
    }

    //todo Get category by ID

    // --- Product Management ---
    @GetMapping("/products/listAll")
    public ResponseEntity<PageDetails<ProductViewDTO>> listAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder)
    {
        PageDetails<ProductViewDTO> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(
                allProducts,
                HttpStatus.OK
        );
    }

    @GetMapping("/products/listAll/byCategory/{categoryId}")
    public ResponseEntity<PageDetails<ProductViewDTO>> listAllProductsForCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppUtil.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppUtil.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppUtil.SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppUtil.SORT_DIR) String sortOrder,
            @PathVariable long categoryId)
    {
        PageDetails<ProductViewDTO> allProductsForCategoryId = productService.getAllProductsForCategoryId(pageNumber, pageSize, sortBy, sortOrder, categoryId);

        return new ResponseEntity<>(
                allProductsForCategoryId,
                HttpStatus.OK
        );
    }
    
}
