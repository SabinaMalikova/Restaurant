package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.categoryDto.CategoryRequest;
import peaksoft.dto.categoryDto.CategoryResponseAll;
import peaksoft.entity.Category;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepo;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {

        Category category = new Category();
        category.setName(categoryRequest.name());

        categoryRepo.save(category);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("category successfully saved")
                .build();
    }

    @Override
    public CategoryResponseAll getCategory(Long id) {
        return categoryRepo.getCategory(id)
                .orElseThrow(()->new NotFoundException("category with id: " + id +" not found"));
    }

    @Override
    public List<CategoryResponseAll> getAllCategories() {
        return categoryRepo.getAllCategories();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {

        Category oldCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("category with id: " + id + " not found"));

        oldCategory.setName(categoryRequest.name());
        categoryRepo.save(oldCategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("category successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("category with id: " + id + " not found"));

        categoryRepo.delete(category);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("category successfully deleted")
                .build();
    }
}
