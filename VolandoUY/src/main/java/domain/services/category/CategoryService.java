package domain.services.category;


import domain.dtos.category.CategoryDTO;
import domain.models.category.Category;
import infra.repository.category.CategoryRepository;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.categoryRepository = new CategoryRepository();
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO cat) {
        Category category = modelMapper.map(cat, Category.class);
        if (existsCategory(category.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_EXISTS, category.getName()));
        }
        ValidatorUtil.validate(category);

        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryDetailsByName(String categoryName) {
//        return categories.stream()
//                .filter(category -> category.getName().equalsIgnoreCase(categoryName))
//                .findFirst()
//                .map(category -> modelMapper.map(category, CategoryDTO.class))
//                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName)));
        Category category = categoryRepository.getCategoryByName(categoryName);
        if(category == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName));
        }
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
//        return categories.stream()
//                .filter(category -> category.getName().equalsIgnoreCase(categoryName))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName)));
        return categoryRepository.getCategoryByName(categoryName);
    }

    @Override
    public boolean existsCategory(String name) {
//        return categories.stream()
//                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }


}
