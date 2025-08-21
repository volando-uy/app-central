package domain.services.category;


import domain.dtos.category.CategoryDTO;
import domain.models.category.Category;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {
    private List<Category> categories = new ArrayList<>();

    private ModelMapper modelMapper;

    public CategoryService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO cat) {
        Category category = modelMapper.map(cat, Category.class);
        if (existsCategory(category.getName())) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_CATEGORY_EXISTS, category.getName()));
        }
        ValidatorUtil.validate(category);

        categories.add(category);
        System.out.println(categories);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryDetailsByName(String categoryName) {
        return categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(categoryName))
                .findFirst()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName)));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(categoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName)));
    }

    @Override
    public boolean existsCategory(String name) {
        return categories.stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }


}
