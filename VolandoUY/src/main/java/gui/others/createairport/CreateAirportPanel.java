package gui.others.createairport;

import controllers.airport.IAirportController;
import controllers.city.ICityController;
import domain.dtos.airport.BaseAirportDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.List;

@Setter
public class CreateAirportPanel extends JPanel {
    
    private IAirportController airportController;
    private ICityController cityController;

    public CreateAirportPanel(
            IAirportController airportController,
            ICityController cityController
    ) {
        this.airportController = airportController;
        this.cityController = cityController;
        initComponents();
        loadCitiesComboBox();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    
    private void initListeners() {
        createNewAirportBtn.addActionListener(e -> {
            try {
                String name = nameTextField.getText();
                String code = codeTextField.getText();
                String cityName = citiesComboBox.getItemAt(citiesComboBox.getSelectedIndex());

                BaseAirportDTO baseAirportDTO = new BaseAirportDTO(
                        name, code
                );

                BaseAirportDTO createdAirportDTO = airportController.createAirport(
                        baseAirportDTO,
                        cityName
                );

                JOptionPane.showMessageDialog(this,
                "Nombre: " + createdAirportDTO.getName() +
                        "\nCódigo: " + createdAirportDTO.getCode() +
                        "\nCiudad: " + cityName,
                        "Aeropuerto registrado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la ciudad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void loadCitiesComboBox() {
        citiesComboBox.removeAllItems();

        // asumo que tu IUserController tiene getAllAirlines()
        List<String> cities = cityController.getAllCitiesNames();
        if (cities == null) return;

        for (String city : cities) {
            citiesComboBox.addItem(city);
        }

        if (!cities.isEmpty()) citiesComboBox.setSelectedIndex(0);
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        titleLabel = new JLabel();
        InfoAirportPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        vSpacer13 = new JPanel(null);
        firstRowPanel = new JPanel();
        codeLabel = new JLabel();
        codeTextField = new JTextField();
        cityLabel = new JLabel();
        citiesComboBox = new JComboBox<>();
        secondRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        createNewAirportPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createNewAirportBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder
        ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border
        .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt
        . Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void
        propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Registrar nuevo aeropuerto");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoAirportPanel ========
        {
            InfoAirportPanel.setOpaque(false);
            InfoAirportPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoAirportPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoAirportPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 0, 0, 0, 0};
            ((GridBagLayout)InfoAirportPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoAirportPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoAirportPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            vSpacer17.setOpaque(false);
            InfoAirportPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoAirportPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoAirportPanel.add(vSpacer13, new GridBagConstraints(1, 4, 1, 1, 0.0, 400.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

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

                //---- codeLabel ----
                codeLabel.setText("C\u00f3digo:");
                codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                codeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                codeLabel.setPreferredSize(new Dimension(120, 30));
                codeLabel.setMaximumSize(new Dimension(70, 15));
                codeLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(codeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- codeTextField ----
                codeTextField.setPreferredSize(new Dimension(120, 30));
                codeTextField.setMinimumSize(new Dimension(100, 30));
                codeTextField.setMaximumSize(new Dimension(100, 30));
                codeTextField.setOpaque(false);
                firstRowPanel.add(codeTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- cityLabel ----
                cityLabel.setText("Ciudad:");
                cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                cityLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                cityLabel.setPreferredSize(new Dimension(120, 30));
                cityLabel.setMaximumSize(new Dimension(70, 15));
                cityLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(cityLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- citiesComboBox ----
                citiesComboBox.setPreferredSize(new Dimension(120, 30));
                citiesComboBox.setMinimumSize(new Dimension(100, 30));
                citiesComboBox.setMaximumSize(new Dimension(100, 30));
                citiesComboBox.setOpaque(false);
                firstRowPanel.add(citiesComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoAirportPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setOpaque(false);
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 0, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- nameLabel ----
                nameLabel.setText("Nombre:");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nameLabel.setPreferredSize(new Dimension(120, 30));
                nameLabel.setMaximumSize(new Dimension(70, 15));
                nameLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameTextField ----
                nameTextField.setPreferredSize(new Dimension(120, 30));
                nameTextField.setMinimumSize(new Dimension(100, 30));
                nameTextField.setMaximumSize(new Dimension(100, 30));
                nameTextField.setOpaque(false);
                secondRowPanel.add(nameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoAirportPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoAirportPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== createNewAirportPanel ========
        {
            createNewAirportPanel.setOpaque(false);
            createNewAirportPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            createNewAirportPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            createNewAirportPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createNewAirportBtn ----
            createNewAirportBtn.setText("+ Registrar aeropuerto");
            createNewAirportBtn.setOpaque(false);
            createNewAirportPanel.add(createNewAirportBtn, BorderLayout.CENTER);
        }
        add(createNewAirportPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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
    // Generated using JFormDesigner Evaluation license - asd
    private JLabel titleLabel;
    private JPanel InfoAirportPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel vSpacer13;
    private JPanel firstRowPanel;
    private JLabel codeLabel;
    private JTextField codeTextField;
    private JLabel cityLabel;
    private JComboBox<String> citiesComboBox;
    private JPanel secondRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JPanel createNewAirportPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewAirportBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
