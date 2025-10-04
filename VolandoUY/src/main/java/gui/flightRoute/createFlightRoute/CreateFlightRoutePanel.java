package gui.flightRoute.createFlightRoute;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.enums.EnumEstatusRuta;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import shared.constants.Images;
import shared.utils.GUIUtils;
import shared.utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
public class CreateFlightRoutePanel extends JPanel {

    IFlightRouteController flightRouteController;
    IUserController userController;
    ICategoryController categoryController;

    File selectedImageFile = null;

    private List<BaseAirlineDTO> airlines = new ArrayList<>();


    public CreateFlightRoutePanel(
            IFlightRouteController flightRouteController,
            IUserController userController,
            ICategoryController categoryController
    ) {
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.categoryController = categoryController;
        initComponents();
        initListeners();
        loadAirlinesIntoCombo();
        initCategoryList();
        initPlaceholderForTextField(createdAtTextField, "dd/mm/yyyy");
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadAirlinesIntoCombo() {
        airlines.clear();
        airlineComboBox.removeAllItems();

        // asumo que tu IUserController tiene getAllAirlines()
        List<BaseAirlineDTO> list = userController.getAllAirlinesSimpleDetails();
        if (list == null) return;

        airlines.addAll(list);
        for (BaseAirlineDTO a : airlines) {
            // Muestra “Nombre (nickname)”
            String display = a.getName() + " (" + a.getNickname() + ")";
            airlineComboBox.addItem(display);
        }

        if (!airlines.isEmpty()) airlineComboBox.setSelectedIndex(0);
    }

    private String getSelectedAirlineNickname() {
        int idx = airlineComboBox.getSelectedIndex();
        if (idx < 0 || idx >= airlines.size()) return null;
        return airlines.get(idx).getNickname(); // lo que necesita tu método
    }


    private void initCategoryList() {
        List<String> categoriesNames = categoryController.getAllCategoriesNames();
        
        // Create the table model
        String[] columnNames = {"Nombre"};
        NonEditableTableModel tableModel = new NonEditableTableModel(columnNames, 0);
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

        createFlightRouteBtn.addActionListener(e -> {
            try {
                String selectedAirline = getSelectedAirlineNickname();
                if (selectedAirline == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecciona una aerolinea", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Get info from rows selected in categoriesTable
                int[] selectedRows = categoriesTable.getSelectedRows();
                List<String> selectedCategories = new ArrayList<>();
                for (int row : selectedRows) {
                    selectedCategories.add(categoriesTable.getValueAt(row, 0).toString());
                }


                // Create the DTO
                BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO(
                    nameTextField.getText(),
                    descriptionTextField.getText(),
                    LocalDate.parse(createdAtTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    Double.parseDouble(touristCostTextField.getText()),
                    Double.parseDouble(businessCostTextField.getText()),
                    Double.parseDouble(additionalLuggageCostTextField.getText()),
                    EnumEstatusRuta.SIN_ESTADO,
                    null
                );


                // Call the controller to create the flight route
                BaseFlightRouteDTO createdFlightRouteDTO = flightRouteController.createFlightRoute(
                        baseFlightRouteDTO,
                        originCityTextField.getText(),
                        destinationCityTextField.getText(),
                        selectedAirline,
                        selectedCategories,
                        selectedImageFile
                );

                resetImageSelector();

                JOptionPane.showMessageDialog(
                        this,
                        "Nombre: " + createdFlightRouteDTO.getName() +
                        "\nDescripción: " + createdFlightRouteDTO.getDescription() +
                        "\nFecha de alta: " + createdFlightRouteDTO.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        "\nCosto equipaje extra: " + createdFlightRouteDTO.getPriceExtraUnitBaggage() +
                        "\nCosto turista: " + createdFlightRouteDTO.getPriceTouristClass() +
                        "\nCosto ejecutivo: " + createdFlightRouteDTO.getPriceBusinessClass() +
                        "\nCiudad origen: " + originCityTextField.getText() +
                        "\nCiudad destino: " + destinationCityTextField.getText() +
                        (selectedCategories.isEmpty() ? "\n" : "\nCategorías:" + String.join(", ", selectedCategories)),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE)
                ;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la ruta de vuelo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                throw ex;
            }
        });
        
        categoriesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initCategoryList();    
            }
        });
        
        airlineLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadAirlinesIntoCombo();
            }
        });


        uploadImageBtn.addActionListener(e -> {
            // Open a new FileChooser in a new window
            JFileChooser fileChooser = GUIUtils.getImagesFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                selectedImageFile = fileChooser.getSelectedFile();
                // Set the image path to the label
                uploadedImageLabel.setText(selectedImageFile.getName());
            }
        });

    }

    private void resetImageSelector() {
        selectedImageFile = null;
        uploadedImageLabel.setText("-");
    }

    private void initPlaceholderForTextField(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

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
        // Generated using JFormDesigner Evaluation license - asd
        titleLabel = new JLabel();
        vSpacer18 = new JPanel(null);
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        InfoFlightRoutePanel = new JPanel();
        vSpacer17 = new JPanel(null);
        hSpacer5 = new JPanel(null);
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
        vSpacer13 = new JPanel(null);
        thirdRowPanel = new JPanel();
        touristCostLabel = new JLabel();
        touristCostTextField = new JTextField();
        businessCostLabel = new JLabel();
        businessCostTextField = new JTextField();
        fourthRowPanel = new JPanel();
        originCityLabel = new JLabel();
        originCityTextField = new JTextField();
        destinationCityLabel = new JLabel();
        destinationCityTextField = new JTextField();
        categoriesTablePanel = new JPanel();
        categoriesLabel = new JLabel();
        CategoriesScrollPane = new JScrollPane();
        categoriesTable = new JTable();
        sixthRowPanel = new JPanel();
        uploadImageLabel = new JLabel();
        uploadImageBtn = new JButton();
        uploadedImageLabel = new JLabel();
        vSpacer14 = new JPanel(null);
        createFlightRouteBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.
        swing.border.EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border
        .TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog"
        ,java.awt.Font.BOLD,12),java.awt.Color.red), getBorder
        ())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java
        .beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.getPropertyName()))throw new RuntimeException
        ();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Crear ruta de vuelo");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //---- vSpacer18 ----
        vSpacer18.setOpaque(false);
        add(vSpacer18, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== selectAirlinePanel ========
        {
            selectAirlinePanel.setOpaque(false);
            selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

            //---- airlineLabel ----
            airlineLabel.setText("\ud83d\udd04 Selecciona la Aerolinea:");
            airlineLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            selectAirlinePanel.add(airlineLabel);

            //---- airlineComboBox ----
            airlineComboBox.setMinimumSize(new Dimension(150, 30));
            airlineComboBox.setPreferredSize(new Dimension(150, 30));
            airlineComboBox.setOpaque(false);
            selectAirlinePanel.add(airlineComboBox);
        }
        add(selectAirlinePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoFlightRoutePanel ========
        {
            InfoFlightRoutePanel.setOpaque(false);
            InfoFlightRoutePanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoFlightRoutePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePanel.getLayout()).rowHeights = new int[] {0, 0, 22, 40, 17, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightRoutePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 20));
            vSpacer17.setPreferredSize(new Dimension(10, 20));
            vSpacer17.setOpaque(false);
            InfoFlightRoutePanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoFlightRoutePanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoFlightRoutePanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setOpaque(false);
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
                nameTextField.setOpaque(false);
                firstRowPanel.add(nameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- descriptionLabel ----
                descriptionLabel.setText("Descripcion:");
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
                descriptionTextField.setOpaque(false);
                firstRowPanel.add(descriptionTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(firstRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 30));
                secondRowPanel.setOpaque(false);
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
                createdAtTextField.setText("dd/mm/yyyy");
                createdAtTextField.setOpaque(false);
                secondRowPanel.add(createdAtTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- additionalLuggageCostLabel ----
                additionalLuggageCostLabel.setText("$ Equipaje extra:");
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
                additionalLuggageCostTextField.setOpaque(false);
                secondRowPanel.add(additionalLuggageCostTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(secondRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoFlightRoutePanel.add(vSpacer13, new GridBagConstraints(1, 10, 1, 1, 0.0, 200.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 30));
                thirdRowPanel.setOpaque(false);
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- touristCostLabel ----
                touristCostLabel.setText("$ Asiento turista:");
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
                touristCostTextField.setOpaque(false);
                thirdRowPanel.add(touristCostTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- businessCostLabel ----
                businessCostLabel.setText("$ Asiento ejecutivo:");
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
                businessCostTextField.setOpaque(false);
                thirdRowPanel.add(businessCostTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(thirdRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== fourthRowPanel ========
            {
                fourthRowPanel.setPreferredSize(new Dimension(510, 30));
                fourthRowPanel.setMinimumSize(new Dimension(510, 30));
                fourthRowPanel.setMaximumSize(new Dimension(510, 30));
                fourthRowPanel.setOpaque(false);
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
                originCityTextField.setOpaque(false);
                fourthRowPanel.add(originCityTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- destinationCityLabel ----
                destinationCityLabel.setText("Ciudad destino:");
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
                destinationCityTextField.setOpaque(false);
                fourthRowPanel.add(destinationCityTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(fourthRowPanel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== categoriesTablePanel ========
            {
                categoriesTablePanel.setPreferredSize(new Dimension(510, 100));
                categoriesTablePanel.setMinimumSize(new Dimension(510, 100));
                categoriesTablePanel.setMaximumSize(new Dimension(510, 510));
                categoriesTablePanel.setOpaque(false);
                categoriesTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)categoriesTablePanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)categoriesTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)categoriesTablePanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)categoriesTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- categoriesLabel ----
                categoriesLabel.setText("\ud83d\udd04 Categorias:");
                categoriesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                categoriesLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                categoriesLabel.setPreferredSize(new Dimension(120, 30));
                categoriesLabel.setMaximumSize(new Dimension(120, 30));
                categoriesLabel.setMinimumSize(new Dimension(120, 30));
                categoriesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                categoriesTablePanel.add(categoriesLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== CategoriesScrollPane ========
                {
                    CategoriesScrollPane.setPreferredSize(new Dimension(300, 100));
                    CategoriesScrollPane.setMinimumSize(new Dimension(300, 100));
                    CategoriesScrollPane.setMaximumSize(new Dimension(300, 100));
                    CategoriesScrollPane.setEnabled(false);
                    CategoriesScrollPane.setOpaque(false);

                    //---- categoriesTable ----
                    categoriesTable.setOpaque(false);
                    CategoriesScrollPane.setViewportView(categoriesTable);
                }
                categoriesTablePanel.add(CategoriesScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(categoriesTablePanel, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== sixthRowPanel ========
            {
                sixthRowPanel.setPreferredSize(new Dimension(510, 30));
                sixthRowPanel.setMinimumSize(new Dimension(510, 30));
                sixthRowPanel.setMaximumSize(new Dimension(510, 510));
                sixthRowPanel.setOpaque(false);
                sixthRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)sixthRowPanel.getLayout()).columnWidths = new int[] {163, 78, 0, 0};
                ((GridBagLayout)sixthRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)sixthRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)sixthRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- uploadImageLabel ----
                uploadImageLabel.setText("Subir una imagen:");
                uploadImageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                uploadImageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                uploadImageLabel.setPreferredSize(new Dimension(120, 30));
                uploadImageLabel.setMaximumSize(new Dimension(70, 15));
                uploadImageLabel.setMinimumSize(new Dimension(70, 15));
                sixthRowPanel.add(uploadImageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- uploadImageBtn ----
                uploadImageBtn.setText("Nueva");
                sixthRowPanel.add(uploadImageBtn, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- uploadedImageLabel ----
                uploadedImageLabel.setText("-");
                uploadedImageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                uploadedImageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                uploadedImageLabel.setPreferredSize(new Dimension(120, 30));
                uploadedImageLabel.setMaximumSize(new Dimension(70, 15));
                uploadedImageLabel.setMinimumSize(new Dimension(70, 15));
                sixthRowPanel.add(uploadedImageLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePanel.add(sixthRowPanel, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 2, 0), 0, 0));

            //---- vSpacer14 ----
            vSpacer14.setOpaque(false);
            InfoFlightRoutePanel.add(vSpacer14, new GridBagConstraints(1, 8, 1, 1, 0.0, 200.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));

            //---- createFlightRouteBtn ----
            createFlightRouteBtn.setText("+ Crear Ruta de Vuelo");
            createFlightRouteBtn.setOpaque(false);
            InfoFlightRoutePanel.add(createFlightRouteBtn, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));
        }
        add(InfoFlightRoutePanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - asd
    private JLabel titleLabel;
    private JPanel vSpacer18;
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JPanel InfoFlightRoutePanel;
    private JPanel vSpacer17;
    private JPanel hSpacer5;
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
    private JPanel vSpacer13;
    private JPanel thirdRowPanel;
    private JLabel touristCostLabel;
    private JTextField touristCostTextField;
    private JLabel businessCostLabel;
    private JTextField businessCostTextField;
    private JPanel fourthRowPanel;
    private JLabel originCityLabel;
    private JTextField originCityTextField;
    private JLabel destinationCityLabel;
    private JTextField destinationCityTextField;
    private JPanel categoriesTablePanel;
    private JLabel categoriesLabel;
    private JScrollPane CategoriesScrollPane;
    private JTable categoriesTable;
    private JPanel sixthRowPanel;
    private JLabel uploadImageLabel;
    private JButton uploadImageBtn;
    private JLabel uploadedImageLabel;
    private JPanel vSpacer14;
    private JButton createFlightRouteBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
