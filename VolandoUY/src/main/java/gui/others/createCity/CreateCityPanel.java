package gui.others.createCity;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

@Setter
public class CreateCityPanel extends JPanel {
    
    ICityController cityController;
    
    public CreateCityPanel(ICityController cityController) {
        this.cityController = cityController;
        initComponents();
        initListeners();
        try { setBorder(null); } catch ( Exception ignored ) {
        }
    }
    
    private void initListeners() {
        createNewCityBtn.addActionListener(e -> {
            try {
                String name = nameTextField.getText();
                String country = countryTextField.getText();
                double latitude = Double.parseDouble(latitudeTextField.getText());
                double longitude = Double.parseDouble(longitudeTextField.getText());

                CityDTO cityDTO = new CityDTO(name, country, latitude, longitude,new ArrayList<>());

                CityDTO createdCityDTO = cityController.createCity(cityDTO);

                JOptionPane.showMessageDialog(this,
                "Nombre: " + createdCityDTO.getName() +
                        "\nPais: " + createdCityDTO.getCountry() +
                        "\nLatitud: " + createdCityDTO.getLatitude() +
                        "\nLongitud: " + createdCityDTO.getLongitude(),
                        "City Registered",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la ciudad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        titleLabel = new JLabel();
        InfoCityPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        vSpacer13 = new JPanel(null);
        secondRowPanel = new JPanel();
        latitudeLabel = new JLabel();
        latitudeTextField = new JTextField();
        longitudeLabel = new JLabel();
        longitudeTextField = new JTextField();
        firstRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        countryLabel = new JLabel();
        countryTextField = new JTextField();
        createNewCityPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createNewCityBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new
        javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax
        . swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java
        . awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 ) ,java . awt
        . Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .
        PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er" .
        equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Registrar nueva ciudad");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoCityPanel ========
        {
            InfoCityPanel.setOpaque(false);
            InfoCityPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoCityPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoCityPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 0, 0, 0, 0};
            ((GridBagLayout)InfoCityPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoCityPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoCityPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            vSpacer17.setOpaque(false);
            InfoCityPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoCityPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoCityPanel.add(vSpacer13, new GridBagConstraints(1, 4, 1, 1, 0.0, 400.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setOpaque(false);
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 0, 0, 110, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- latitudeLabel ----
                latitudeLabel.setText("Latitud:");
                latitudeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                latitudeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                latitudeLabel.setPreferredSize(new Dimension(120, 30));
                latitudeLabel.setMaximumSize(new Dimension(70, 15));
                latitudeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(latitudeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- latitudeTextField ----
                latitudeTextField.setPreferredSize(new Dimension(120, 30));
                latitudeTextField.setMinimumSize(new Dimension(100, 30));
                latitudeTextField.setMaximumSize(new Dimension(100, 30));
                latitudeTextField.setOpaque(false);
                secondRowPanel.add(latitudeTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- longitudeLabel ----
                longitudeLabel.setText("Longitud:");
                longitudeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                longitudeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                longitudeLabel.setPreferredSize(new Dimension(120, 30));
                longitudeLabel.setMaximumSize(new Dimension(70, 15));
                longitudeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(longitudeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- longitudeTextField ----
                longitudeTextField.setPreferredSize(new Dimension(120, 30));
                longitudeTextField.setMinimumSize(new Dimension(100, 30));
                longitudeTextField.setMaximumSize(new Dimension(100, 30));
                longitudeTextField.setOpaque(false);
                secondRowPanel.add(longitudeTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoCityPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
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

                //---- countryLabel ----
                countryLabel.setText("Pa\u00eds:");
                countryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                countryLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                countryLabel.setPreferredSize(new Dimension(120, 30));
                countryLabel.setMaximumSize(new Dimension(70, 15));
                countryLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(countryLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- countryTextField ----
                countryTextField.setPreferredSize(new Dimension(120, 30));
                countryTextField.setMinimumSize(new Dimension(100, 30));
                countryTextField.setMaximumSize(new Dimension(100, 30));
                countryTextField.setOpaque(false);
                firstRowPanel.add(countryTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoCityPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoCityPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== createNewCityPanel ========
        {
            createNewCityPanel.setOpaque(false);
            createNewCityPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            createNewCityPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            createNewCityPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createNewCityBtn ----
            createNewCityBtn.setText("+ Registrar ciudad");
            createNewCityBtn.setOpaque(false);
            createNewCityPanel.add(createNewCityBtn, BorderLayout.CENTER);
        }
        add(createNewCityPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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
    private JPanel InfoCityPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel vSpacer13;
    private JPanel secondRowPanel;
    private JLabel latitudeLabel;
    private JTextField latitudeTextField;
    private JLabel longitudeLabel;
    private JTextField longitudeTextField;
    private JPanel firstRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel countryLabel;
    private JTextField countryTextField;
    private JPanel createNewCityPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewCityBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
