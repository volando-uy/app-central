package domain.services.category;


import domain.dtos.category.CategoryDTO;
import domain.models.category.Category;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.category.CategoryRepository;
import infra.repository.category.ICategoryRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public CategoryService() {
        this.categoryRepository = RepositoryFactory.getCategoryRepository();
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO cat) {
        // Mapeamos el DTO y cheackeamos que la ciudad no exista
        Category category = customModelMapper.map(cat, Category.class);
        if (existsCategory(category.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_EXISTS, category.getName()));
        }

        // Validamos la entidad
        ValidatorUtil.validate(category);

        // Guardamos y devolvemos el DTO
        categoryRepository.save(category);
        return customModelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryDetailsByName(String categoryName) {
        // Checkeamos que la ciudad exista
        Category category = categoryRepository.getCategoryByName(categoryName);
        if(category == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName));
        }

        // Si existe, devolvemos el DTO
        return customModelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByName(categoryName);
    }

    @Override
    public boolean existsCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }
}
