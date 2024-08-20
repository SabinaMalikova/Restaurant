package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.categoryDto.CategoryRequest;
import peaksoft.dto.categoryDto.CategoryResponseAll;

import java.util.List;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponseAll getCategory(Long id);
    List<CategoryResponseAll> getAllCategories();
    SimpleResponse updateCategory (Long id, CategoryRequest newCategoryRequest);
    SimpleResponse deleteCategory (Long id);

}
