package gui.flightRoutePackage.createFlightRoutePackage;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.enums.EnumTipoAsiento;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
public class CreateFlightRoutePackagePanel extends JPanel {

    IFlightRoutePackageController flightRoutePackageController;

    public CreateFlightRoutePackagePanel(IFlightRoutePackageController flightRoutePackageController) {
        this.flightRoutePackageController = flightRoutePackageController;
        initComponents();
        initComponentsManually();
        initListeners();
        initPlaceholderForTextField(createdAtTextField, "dd/mm/yyyy");
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }


    private void initListeners() {
        createFlightRoutePackageBtn.addActionListener(e -> {
            try {

                String name = nameTextField.getText();
                Integer expirationInDays = Integer.parseInt(expirationTextField.getText());
                String description = descriptionTextField.getText();
                LocalDate createdAt = LocalDate.parse(createdAtTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Float discount = Float.parseFloat(discountCostTextField.getText());
                EnumTipoAsiento seatType = (EnumTipoAsiento) seatTypeComboBox.getSelectedItem();

                FlightRoutePackageDTO flightRoutePackageDTO = new FlightRoutePackageDTO(
                        name, description, expirationInDays, discount, createdAt, seatType
                );

                // Throws exception if something is invalid
                FlightRoutePackageDTO createdFlightRoutePackageDTO = flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO);

                JOptionPane.showMessageDialog(
                        this,
                        "Paquete de rutas de vuelo creado: " +
                                "\nNombre: " + createdFlightRoutePackageDTO.getName() +
                                "\nDescripción: " + createdFlightRoutePackageDTO.getDescription() +
                                "\nDias de expiración: " + createdFlightRoutePackageDTO.getValidityPeriodDays() +
                                "\nDescuento " + createdFlightRoutePackageDTO.getDiscount() +
                                "\nFecha de alta: " + createdFlightRoutePackageDTO.getCreationDate(),
                        "Paquete creado!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el paquete de rutas de vuelo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponentsManually() {
        createdAtTextField.setText("dd/mm/yyyy");
        seatTypeComboBox.addItem(EnumTipoAsiento.TURISTA);
        seatTypeComboBox.addItem(EnumTipoAsiento.EJECUTIVO);
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
        titleLabel = new JLabel();
        InfoFlightRoutePackagePanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        expirationLabel = new JLabel();
        expirationTextField = new JTextField();
        secondRowPanel = new JPanel();
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        seatTypeLabel = new JLabel();
        seatTypeComboBox = new JComboBox<>();
        thirdRowPanel = new JPanel();
        createdAtLabel = new JLabel();
        createdAtTextField = new JTextField();
        discountCostLabel = new JLabel();
        discountCostTextField = new JTextField();
        createBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createFlightRoutePackageBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
        ( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing. border
        . TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
        propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Crear paquete de rutas de vuelo");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoFlightRoutePackagePanel ========
        {
            InfoFlightRoutePackagePanel.setOpaque(false);
            InfoFlightRoutePackagePanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).rowHeights = new int[] {0, 25, 43, 35, 35, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoFlightRoutePackagePanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            vSpacer17.setOpaque(false);
            InfoFlightRoutePackagePanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoFlightRoutePackagePanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setOpaque(false);
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 0, 130, 110, 0};
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

                //---- expirationLabel ----
                expirationLabel.setText("Validez en d\u00edas:");
                expirationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                expirationLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                expirationLabel.setPreferredSize(new Dimension(120, 30));
                expirationLabel.setMaximumSize(new Dimension(70, 15));
                expirationLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(expirationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- expirationTextField ----
                expirationTextField.setPreferredSize(new Dimension(120, 30));
                expirationTextField.setMinimumSize(new Dimension(100, 30));
                expirationTextField.setMaximumSize(new Dimension(100, 30));
                expirationTextField.setOpaque(false);
                firstRowPanel.add(expirationTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setOpaque(false);
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- descriptionLabel ----
                descriptionLabel.setText("Descripcion:");
                descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                descriptionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                descriptionLabel.setPreferredSize(new Dimension(120, 30));
                descriptionLabel.setMaximumSize(new Dimension(70, 15));
                descriptionLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(descriptionLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- descriptionTextField ----
                descriptionTextField.setPreferredSize(new Dimension(120, 30));
                descriptionTextField.setMinimumSize(new Dimension(100, 30));
                descriptionTextField.setMaximumSize(new Dimension(100, 30));
                descriptionTextField.setOpaque(false);
                secondRowPanel.add(descriptionTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- seatTypeLabel ----
                seatTypeLabel.setText("Tipo de asiento:");
                seatTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                seatTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                seatTypeLabel.setPreferredSize(new Dimension(120, 30));
                seatTypeLabel.setMaximumSize(new Dimension(70, 15));
                seatTypeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(seatTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- seatTypeComboBox ----
                seatTypeComboBox.setOpaque(false);
                secondRowPanel.add(seatTypeComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setOpaque(false);
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- createdAtLabel ----
                createdAtLabel.setText("Fecha de alta:");
                createdAtLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                createdAtLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                createdAtLabel.setPreferredSize(new Dimension(120, 30));
                createdAtLabel.setMaximumSize(new Dimension(70, 15));
                createdAtLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(createdAtLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- createdAtTextField ----
                createdAtTextField.setPreferredSize(new Dimension(120, 30));
                createdAtTextField.setMinimumSize(new Dimension(100, 30));
                createdAtTextField.setMaximumSize(new Dimension(100, 30));
                createdAtTextField.setOpaque(false);
                thirdRowPanel.add(createdAtTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- discountCostLabel ----
                discountCostLabel.setText("Descuento (%)");
                discountCostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                discountCostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                discountCostLabel.setPreferredSize(new Dimension(120, 30));
                discountCostLabel.setMaximumSize(new Dimension(70, 15));
                discountCostLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(discountCostLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- discountCostTextField ----
                discountCostTextField.setPreferredSize(new Dimension(120, 30));
                discountCostTextField.setMinimumSize(new Dimension(100, 30));
                discountCostTextField.setMaximumSize(new Dimension(100, 30));
                discountCostTextField.setOpaque(false);
                thirdRowPanel.add(discountCostTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(thirdRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(InfoFlightRoutePackagePanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== createBtnPanel ========
        {
            createBtnPanel.setOpaque(false);
            createBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            createBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            createBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createFlightRoutePackageBtn ----
            createFlightRoutePackageBtn.setText("+ Crear Paquete");
            createFlightRoutePackageBtn.setOpaque(false);
            createBtnPanel.add(createFlightRoutePackageBtn, BorderLayout.CENTER);
        }
        add(createBtnPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        vSpacer19.setOpaque(false);
        add(vSpacer19, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JLabel titleLabel;
    private JPanel InfoFlightRoutePackagePanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel expirationLabel;
    private JTextField expirationTextField;
    private JPanel secondRowPanel;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JLabel seatTypeLabel;
    private JComboBox<EnumTipoAsiento> seatTypeComboBox;
    private JPanel thirdRowPanel;
    private JLabel createdAtLabel;
    private JTextField createdAtTextField;
    private JLabel discountCostLabel;
    private JTextField discountCostTextField;
    private JPanel createBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createFlightRoutePackageBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
