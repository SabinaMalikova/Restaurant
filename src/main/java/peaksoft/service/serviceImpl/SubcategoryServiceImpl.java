package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.subcategoryDto.SubcategoryRequest;
import peaksoft.dto.subcategoryDto.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Subcategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepo;
import peaksoft.repository.MenuItemRepo;
import peaksoft.repository.SubcategoryRepo;
import peaksoft.service.SubcategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepo subcategoryRepo;
    private final CategoryRepo categoryRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponse saveSubCategory(SubcategoryRequest subcategoryRequest, Long categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("category with id: " + categoryId + " not found"));

        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(category);

        subcategoryRepo.save(subcategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("subcategory successfully saved")
                .build();
    }

    @Override
    public SubcategoryResponse getSubcategoryById(Long id) {
        return subcategoryRepo.getSubcategoryById(id)
                .orElseThrow(()->new NotFoundException("subcategory with id: " + id + " not found"));
    }

    @Override
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepo.getAllSubcategories();
    }

    @Override
    public List<SubcategoryResponse> getAllSubcategoriesByCategoryId(Long categoryId) {
        return subcategoryRepo.getAllSubcategoriesByCategoryId(categoryId);
    }

    @Override
    public SimpleResponse updateSubcategory(Long id, SubcategoryRequest newSubcategoryRequest){

        Subcategory oldSubcategory = subcategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("subcategory with id: " + id + " not found"));

        oldSubcategory.setName(newSubcategoryRequest.name());
        subcategoryRepo.save(oldSubcategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("subcategory successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteSubcategory(Long id) {

        Subcategory subcategory = subcategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("subcategory with id: " + id + " not found"));

        for (MenuItem menuItem : subcategory.getMenuItems()) {
            menuItemRepo.delete(menuItem);
        }

        subcategoryRepo.delete(subcategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("subcategory successfully deleted")
                .build();

    }
}
