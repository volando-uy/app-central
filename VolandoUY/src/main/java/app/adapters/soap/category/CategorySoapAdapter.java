package app.adapters.soap.category;

import app.adapters.soap.BaseSoapAdapter;
import controllers.category.ICategoryController;
import domain.dtos.category.CategoryDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CategorySoapAdapter extends BaseSoapAdapter implements ICategoryController {

    private final ICategoryController controller;

    public CategorySoapAdapter(ICategoryController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "categoryService";
    }

    @Override
    @WebMethod
    public CategoryDTO createCategory(CategoryDTO category) {
        return controller.createCategory(category);
    }

    @Override
    @WebMethod
    public CategoryDTO getCategoryDetailsByName(String categoryName) {
        return controller.getCategoryDetailsByName(categoryName);
    }

    @Override
    @WebMethod
    public List<String> getAllCategoriesNames() {
        return controller.getAllCategoriesNames();
    }
}
