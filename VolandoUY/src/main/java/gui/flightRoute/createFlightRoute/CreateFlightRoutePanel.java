package gui.flightRoute.createFlightRoute;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
public class CreateFlightRoutePanel extends JPanel {

    IFlightRouteController flightRouteController;
    IUserController userController;
    ICategoryController categoryController;

    AirlineDTO selectedAirline;

    public CreateFlightRoutePanel(IFlightRouteController flightRouteController, IUserController userController, ICategoryController categoryController) {
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.categoryController = categoryController;
        initComponents();
        initComponentsManually();
        initListeners();
        initAirlineList();
        initCategoryList();
        initPlaceholderForTextField(createdAtTextField, "dd/mm/yyyy");
        try {
            setBorder(null);
        } catch (Exception ignored) {
        }
    }

    private void initAirlineList() {
        List<String> airlinesNickanmes = userController.getAllAirlinesNicknames();
        for (String nickname : airlinesNickanmes) {
            airlineComboBox.addItem(nickname);
        }
    }

    private void initCategoryList() {
        List<String> categoriesNames = categoryController.getAllCategoriesNames();
        
        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"Nombre"};
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        // Add rows to the model
        for (String categoryName : categoriesNames) {
            tableModel.addRow(new Object[]{
                    categoryName
            });
        }
        categoriesTable.setModel(tableModel);

        // Dynamic height
        int maxRows = 5;
        int visibleRows = Math.max(categoriesTable.getRowCount(), maxRows);
        categoriesTable.setPreferredSize(
                new Dimension(categoriesTable.getPreferredSize().width, visibleRows * categoriesTable.getRowHeight())
        );

        categoriesTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void initListeners() {
        loadAirlineBtn.addActionListener(e -> {
            try {
                String selectedNickname = (String) airlineComboBox.getSelectedItem();
                // If ariline doesn't exist, throw exception
                selectedAirline = userController.getAirlineByNickname(selectedNickname);
                nameTextField.setEnabled(true);
                descriptionTextField.setEnabled(true);
                createdAtTextField.setEnabled(true);
                additionalLuggageCostTextField.setEnabled(true);
                touristCostTextField.setEnabled(true);
                businessCostTextField.setEnabled(true);
                originCityTextField.setEnabled(true);
                destinationCityTextField.setEnabled(true);
                categoriesTable.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Aerolinea seleccionada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la aerolinea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        createFlightRouteBtn.addActionListener(e -> {
            try {
                if (selectedAirline == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, carga una aerolinea primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Get info from rows selected in categoriesTable
                int[] selectedRows = categoriesTable.getSelectedRows();
                List<String> selectedCategories = new ArrayList<>();
                for (int row : selectedRows) {
                    selectedCategories.add(categoriesTable.getValueAt(row, 0).toString());
                }

                // Create the DTO
                FlightRouteDTO flightRouteDTO = new FlightRouteDTO(
                    nameTextField.getText(),
                    descriptionTextField.getText(),
                    LocalDate.parse(createdAtTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(additionalLuggageCostTextField.getText()),
                    Double.parseDouble(touristCostTextField.getText()),
                    Double.parseDouble(businessCostTextField.getText()),
                    originCityTextField.getText(),
                    destinationCityTextField.getText(),
                    selectedAirline.getNickname(),
                    selectedCategories,
                    new ArrayList<>() //TODO: Cambiar esto porque ni idea lo que hace, por ahora lo puse temporal para que compile
                );

                // Call the controller to create the flight route
                FlightRouteDTO createdFlightRouteDTO = flightRouteController.createFlightRoute(
                        flightRouteDTO
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Nombre: " + createdFlightRouteDTO.getName() +
                        "\nDescripción: " + createdFlightRouteDTO.getDescription() +
                        "\nFecha de alta: " + createdFlightRouteDTO.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        "\nCosto equipaje extra: " + createdFlightRouteDTO.getPriceExtraUnitBaggage() +
                        "\nCosto turista: " + createdFlightRouteDTO.getPriceTouristClass() +
                        "\nCosto ejecutivo: " + createdFlightRouteDTO.getPriceBusinessClass() +
                        "\nCiudad origen: " + createdFlightRouteDTO.getOriginCityName() +
                        "\nCiudad destino: " + createdFlightRouteDTO.getDestinationCityName() +
                        "\nCategorías: " + String.join(", ", createdFlightRouteDTO.getCategories()),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE)
                ;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la ruta de vuelo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponentsManually() {
        createdAtTextField.setText("dd/mm/yyyy");
    }

    private void initPlaceholderForTextField(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);

        // Set the placeholder text when the field loses focus
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        selectUserPanel = new JPanel();
        userLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        loadAirlineBtn = new JButton();
        InfoUserPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        secondRowPanel = new JPanel();
        createdAtLabel = new JLabel();
        createdAtTextField = new JTextField();
        additionalLuggageCostLabel = new JLabel();
        additionalLuggageCostTextField = new JTextField();
        fourthRowPanel = new JPanel();
        originCityLabel = new JLabel();
        originCityTextField = new JTextField();
        destinationCityLabel = new JLabel();
        destinationCityTextField = new JTextField();
        thirdRowPanel = new JPanel();
        touristCostLabel = new JLabel();
        touristCostTextField = new JTextField();
        businessCostLabel = new JLabel();
        businessCostTextField = new JTextField();
        fourthRowPanel2 = new JPanel();
        categoriesLabel = new JLabel();
        categoriesScrollPane = new JScrollPane();
        categoriesTable = new JTable();
        updateBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createFlightRouteBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
        border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER
        ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069alog", java .awt . Font
        . BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order"
        .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //======== selectUserPanel ========
        {
            selectUserPanel.setLayout(new GridLayout(1, 3));

            //---- userLabel ----
            userLabel.setText("Selecciona aerolinea:");
            selectUserPanel.add(userLabel);

            //---- airlineComboBox ----
            airlineComboBox.setMinimumSize(new Dimension(100, 30));
            airlineComboBox.setPreferredSize(new Dimension(100, 30));
            selectUserPanel.add(airlineComboBox);

            //---- loadAirlineBtn ----
            loadAirlineBtn.setText("Cargar aerolinea");
            selectUserPanel.add(loadAirlineBtn);
        }
        add(selectUserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 25, 43, 35, 35, 0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            InfoUserPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 0, 0, 110, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- nameLabel ----
                nameLabel.setText("Nombre:");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nameLabel.setPreferredSize(new Dimension(120, 30));
                nameLabel.setMaximumSize(new Dimension(70, 15));
                nameLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameTextField ----
                nameTextField.setPreferredSize(new Dimension(120, 30));
                nameTextField.setMinimumSize(new Dimension(100, 30));
                nameTextField.setMaximumSize(new Dimension(100, 30));
                nameTextField.setEnabled(false);
                firstRowPanel.add(nameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- descriptionLabel ----
                descriptionLabel.setText("Descripci\u00f3n:");
                descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                descriptionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                descriptionLabel.setPreferredSize(new Dimension(120, 30));
                descriptionLabel.setMaximumSize(new Dimension(70, 15));
                descriptionLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(descriptionLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- descriptionTextField ----
                descriptionTextField.setPreferredSize(new Dimension(120, 30));
                descriptionTextField.setMinimumSize(new Dimension(100, 30));
                descriptionTextField.setMaximumSize(new Dimension(100, 30));
                descriptionTextField.setEnabled(false);
                firstRowPanel.add(descriptionTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- createdAtLabel ----
                createdAtLabel.setText("Fecha de alta:");
                createdAtLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                createdAtLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                createdAtLabel.setPreferredSize(new Dimension(120, 30));
                createdAtLabel.setMaximumSize(new Dimension(70, 15));
                createdAtLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(createdAtLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- createdAtTextField ----
                createdAtTextField.setPreferredSize(new Dimension(120, 30));
                createdAtTextField.setMinimumSize(new Dimension(100, 30));
                createdAtTextField.setMaximumSize(new Dimension(100, 30));
                createdAtTextField.setEnabled(false);
                secondRowPanel.add(createdAtTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- additionalLuggageCostLabel ----
                additionalLuggageCostLabel.setText("Costo Equi. Extra:");
                additionalLuggageCostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                additionalLuggageCostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                additionalLuggageCostLabel.setPreferredSize(new Dimension(120, 30));
                additionalLuggageCostLabel.setMaximumSize(new Dimension(70, 15));
                additionalLuggageCostLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(additionalLuggageCostLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- additionalLuggageCostTextField ----
                additionalLuggageCostTextField.setPreferredSize(new Dimension(120, 30));
                additionalLuggageCostTextField.setMinimumSize(new Dimension(100, 30));
                additionalLuggageCostTextField.setMaximumSize(new Dimension(100, 30));
                additionalLuggageCostTextField.setEnabled(false);
                secondRowPanel.add(additionalLuggageCostTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== fourthRowPanel ========
            {
                fourthRowPanel.setPreferredSize(new Dimension(510, 30));
                fourthRowPanel.setMinimumSize(new Dimension(510, 30));
                fourthRowPanel.setMaximumSize(new Dimension(510, 510));
                fourthRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- originCityLabel ----
                originCityLabel.setText("Ciudad origen:");
                originCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                originCityLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                originCityLabel.setPreferredSize(new Dimension(120, 30));
                originCityLabel.setMaximumSize(new Dimension(70, 15));
                originCityLabel.setMinimumSize(new Dimension(70, 15));
                fourthRowPanel.add(originCityLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- originCityTextField ----
                originCityTextField.setPreferredSize(new Dimension(120, 30));
                originCityTextField.setMinimumSize(new Dimension(100, 30));
                originCityTextField.setMaximumSize(new Dimension(100, 30));
                originCityTextField.setEnabled(false);
                fourthRowPanel.add(originCityTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- destinationCityLabel ----
                destinationCityLabel.setText("Ciudad destino");
                destinationCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                destinationCityLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                destinationCityLabel.setPreferredSize(new Dimension(120, 30));
                destinationCityLabel.setMaximumSize(new Dimension(70, 15));
                destinationCityLabel.setMinimumSize(new Dimension(70, 15));
                fourthRowPanel.add(destinationCityLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- destinationCityTextField ----
                destinationCityTextField.setPreferredSize(new Dimension(120, 30));
                destinationCityTextField.setMinimumSize(new Dimension(100, 30));
                destinationCityTextField.setMaximumSize(new Dimension(100, 30));
                destinationCityTextField.setEnabled(false);
                fourthRowPanel.add(destinationCityTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(fourthRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- touristCostLabel ----
                touristCostLabel.setText("Costo Turista:");
                touristCostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                touristCostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                touristCostLabel.setPreferredSize(new Dimension(120, 30));
                touristCostLabel.setMaximumSize(new Dimension(70, 15));
                touristCostLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(touristCostLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- touristCostTextField ----
                touristCostTextField.setPreferredSize(new Dimension(120, 30));
                touristCostTextField.setMinimumSize(new Dimension(100, 30));
                touristCostTextField.setMaximumSize(new Dimension(100, 30));
                touristCostTextField.setEnabled(false);
                thirdRowPanel.add(touristCostTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- businessCostLabel ----
                businessCostLabel.setText("Costo Ejecutivo");
                businessCostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                businessCostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                businessCostLabel.setPreferredSize(new Dimension(120, 30));
                businessCostLabel.setMaximumSize(new Dimension(70, 15));
                businessCostLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(businessCostLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- businessCostTextField ----
                businessCostTextField.setPreferredSize(new Dimension(120, 30));
                businessCostTextField.setMinimumSize(new Dimension(100, 30));
                businessCostTextField.setMaximumSize(new Dimension(100, 30));
                businessCostTextField.setEnabled(false);
                thirdRowPanel.add(businessCostTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(thirdRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== fourthRowPanel2 ========
            {
                fourthRowPanel2.setPreferredSize(new Dimension(510, 100));
                fourthRowPanel2.setMinimumSize(new Dimension(510, 100));
                fourthRowPanel2.setMaximumSize(new Dimension(510, 510));
                fourthRowPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)fourthRowPanel2.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)fourthRowPanel2.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)fourthRowPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)fourthRowPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- categoriesLabel ----
                categoriesLabel.setText("Categorias:");
                categoriesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                categoriesLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                categoriesLabel.setPreferredSize(new Dimension(120, 30));
                categoriesLabel.setMaximumSize(new Dimension(120, 30));
                categoriesLabel.setMinimumSize(new Dimension(120, 30));
                fourthRowPanel2.add(categoriesLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== categoriesScrollPane ========
                {
                    categoriesScrollPane.setPreferredSize(new Dimension(300, 100));
                    categoriesScrollPane.setMinimumSize(new Dimension(300, 100));
                    categoriesScrollPane.setMaximumSize(new Dimension(300, 100));
                    categoriesScrollPane.setEnabled(false);

                    //---- categoriesTable ----
                    categoriesTable.setPreferredSize(new Dimension(300, 100));
                    categoriesTable.setMaximumSize(new Dimension(300, 100));
                    categoriesTable.setMinimumSize(new Dimension(300, 100));
                    categoriesTable.setEnabled(false);
                    categoriesScrollPane.setViewportView(categoriesTable);
                }
                fourthRowPanel2.add(categoriesScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(fourthRowPanel2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(InfoUserPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== updateBtnPanel ========
        {
            updateBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            updateBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            updateBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createFlightRouteBtn ----
            createFlightRouteBtn.setText("Crear ruta de vuelo");
            updateBtnPanel.add(createFlightRouteBtn, BorderLayout.CENTER);
        }
        add(updateBtnPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        add(vSpacer19, new GridBagConstraints(0, 4, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel selectUserPanel;
    private JLabel userLabel;
    private JComboBox<String> airlineComboBox;
    private JButton loadAirlineBtn;
    private JPanel InfoUserPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JPanel secondRowPanel;
    private JLabel createdAtLabel;
    private JTextField createdAtTextField;
    private JLabel additionalLuggageCostLabel;
    private JTextField additionalLuggageCostTextField;
    private JPanel fourthRowPanel;
    private JLabel originCityLabel;
    private JTextField originCityTextField;
    private JLabel destinationCityLabel;
    private JTextField destinationCityTextField;
    private JPanel thirdRowPanel;
    private JLabel touristCostLabel;
    private JTextField touristCostTextField;
    private JLabel businessCostLabel;
    private JTextField businessCostTextField;
    private JPanel fourthRowPanel2;
    private JLabel categoriesLabel;
    private JScrollPane categoriesScrollPane;
    private JTable categoriesTable;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createFlightRouteBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
