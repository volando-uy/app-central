package gui;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.CategoryDTO;
import domain.models.user.enums.EnumTipoDocumento;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {
    private IUserController userController;
    private IFlightRouteController flightRouteController;

    public MainFrame(IUserController userController, IFlightRouteController flightRouteController) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        initUI();
    }

    private void initUI() {
        setTitle("Volando App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new java.awt.GridLayout(3, 1)); // 3 rows, 1 column

        JLabel label = new JLabel("Bienvenido a Volando App");
        add(label);

        JButton registerCategoryBtn = new JButton("Crear Categoria");
        registerCategoryBtn.addActionListener(e -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName("Categoria " + System.currentTimeMillis());

            flightRouteController.createCategory(categoryDTO);
        });
        add(registerCategoryBtn);

        JButton showCategoriesBtn = new JButton("Mostrar Categorias");
        showCategoriesBtn.addActionListener(e -> {
            List<CategoryDTO> categories = flightRouteController.getAllCategories();
            StringBuilder allCategories = new StringBuilder("Categorias registradas:\n");
            for (CategoryDTO category : categories) {
                allCategories.append(category.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(this, allCategories);
        });
        add(showCategoriesBtn);


//        JButton registerCustomerBtn = new JButton("Crear Customer");
//        registerCustomerBtn.addActionListener(e -> {
//            CategoryDTO customerDTO = new CategoryDTO();
//            customerDTO.setNickname("cliente" + System.currentTimeMillis());
//            customerDTO.setName("Nombre");
//            customerDTO.setMail("asd@gmail.com");
//            customerDTO.setSurname("Apellido");
//            customerDTO.setBirthDate(LocalDate.now());
//            customerDTO.setIdType(EnumTipoDocumento.CI);
//            customerDTO.setId("12345678");
//            customerDTO.setCitizenship("Uruguay");
//
//            userController.registerCustomer(customerDTO);
//        });
//        add(registerCustomerBtn);
//
//        JButton showCustomersBtn = new JButton("Mostrar Clientes");
//        showCustomersBtn.addActionListener(e -> {
//            List<String> clientes = userController.getAllUsersNicknames();
//            StringBuilder allCustomers = new StringBuilder("Clientes registrados:\n");
//            for (String cliente : clientes) {
//                allCustomers.append(cliente).append("\n");
//            }
//            JOptionPane.showMessageDialog(this, allCustomers);
//        });
//        add(showCustomersBtn);
    }
}
