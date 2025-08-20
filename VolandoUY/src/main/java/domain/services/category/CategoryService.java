package domain.services.category;


import domain.dtos.category.CategoryDTO;
import domain.models.category.Category;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

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
        System.out.println(category.getName());
        System.out.println(categories);
        if (existsCategory(category.getName())) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_CATEGORY_EXISTS, category.getName()));
        }
        categories.add(category);
        System.out.println(categories);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsCategory(String name) {
        return categories.stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }


}
