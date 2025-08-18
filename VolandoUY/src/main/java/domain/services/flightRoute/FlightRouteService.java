package domain.services.flightRoute;

import domain.dtos.flightRoute.CategoryDTO;
import domain.models.flightRoute.Category;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightRouteService implements IFlightRouteService {
    private List<Category> categories = new ArrayList<>();
    private ModelMapper modelMapper;

    public FlightRouteService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO cat) {
        Category category = modelMapper.map(cat, Category.class);
        System.out.println(category.getName());
        System.out.println(categories);
        if (_categoryExists(category)) {
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

    private boolean _categoryExists(Category category) {
        return categories.contains(category);
    }
}
