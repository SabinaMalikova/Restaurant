package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.subcategoryDto.SubcategoryRequest;
import peaksoft.dto.subcategoryDto.SubcategoryResponse;
import peaksoft.service.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@Tag(name = "subcategory-api")
@RequiredArgsConstructor
public class SubcategoryApi {

    private final SubcategoryService subcategoryService;

    @PostMapping("/save/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse saveSubCategory(@RequestBody @Valid SubcategoryRequest subcategoryRequest, @PathVariable Long categoryId){
        return subcategoryService.saveSubCategory(subcategoryRequest,categoryId);
    }

    @GetMapping("/get/{id}")
    @PermitAll
    SubcategoryResponse getSubcategoryById(@PathVariable Long id){
        return subcategoryService.getSubcategoryById(id);
    }

    @GetMapping("/getAll")
    @PermitAll
    List<SubcategoryResponse> getAllSubcategories(){
        return subcategoryService.getAllSubcategories();
    }

    @GetMapping("/getAllSubcategoriesByCategoryId/{categoryId}")
    @PermitAll
    List<SubcategoryResponse> getAllSubcategoriesByCategoryId(@PathVariable Long categoryId){
        return subcategoryService.getAllSubcategoriesByCategoryId(categoryId);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse updateSubcategory(@PathVariable Long id, @RequestBody @Valid SubcategoryRequest newSubcategoryRequest){
        return subcategoryService.updateSubcategory(id, newSubcategoryRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse deleteSubcategory(@PathVariable Long id){
        return subcategoryService.deleteSubcategory(id);
    }
}
