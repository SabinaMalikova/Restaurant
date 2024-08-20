package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.categoryDto.CategoryRequest;
import peaksoft.dto.categoryDto.CategoryResponseAll;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "category-api")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse saveCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/get/{id}")
    @PermitAll
    CategoryResponseAll getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @GetMapping("/getAllCategories")
    @PermitAll
    List<CategoryResponseAll> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

}
