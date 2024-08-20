package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.subcategoryDto.SubcategoryRequest;
import peaksoft.dto.subcategoryDto.SubcategoryResponse;

import java.util.List;

public interface SubcategoryService {
    SimpleResponse saveSubCategory(SubcategoryRequest subcategoryRequest, Long categoryId);
    SubcategoryResponse getSubcategoryById(Long id);
    List<SubcategoryResponse> getAllSubcategories(); //группировка по категориям
    List<SubcategoryResponse> getAllSubcategoriesByCategoryId(Long categoryId);  //сортировка в алфавитном порядке по имени
    SimpleResponse updateSubcategory(Long id, SubcategoryRequest newSubcategoryRequest);
    SimpleResponse deleteSubcategory(Long id);

}
