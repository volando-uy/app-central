/*
 * Created by JFormDesigner on Thu Sep 04 19:05:36 UYT 2025
 */

package gui.reservations.addPassenger;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.*;

import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.luggage.EnumCategoria;
import domain.models.luggage.EnumEquipajeBasico;

/**
 * @author AparicioQuian
 */
public class AddPassenger extends JPanel {

    // Widgets propios (reemplazan visualmente a los generados donde hace falta)
    private JComboBox<EnumEquipajeBasico> basicLuggageTypeCombo;
    private JComboBox<EnumCategoria>      extraLuggageTypeCombo;
    private JSpinner                      extraUnitsSpinner;

    public AddPassenger() {
        initComponents();   // (JFormDesigner) NO TOCAR
        postInit();         // orden, validaciones y separadores

        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    /* ======================= CONFIGURACIÓN POST-GENERADO ======================= */

    private void postInit() {
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12)); // padding seguro
        content.add(InfoUserPanel2, BorderLayout.CENTER);

        titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        // 1) Tipo de documento + doc solo dígitos
        seatTypeComboBox2.setModel(new DefaultComboBoxModel<>(EnumTipoDocumento.values()));
        documentoCmb.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') e.consume();
            }

        });


        // 4) Widgets correctos
        basicLuggageTypeCombo = new JComboBox<>(EnumEquipajeBasico.values());
        extraLuggageTypeCombo = new JComboBox<>(EnumCategoria.values());
        extraUnitsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
        if (extraUnitsSpinner.getEditor() instanceof JSpinner.DefaultEditor ed) {
            ed.getTextField().setEditable(false);
        }


        // 7) Montar el wrapper en el root (y no directamente InfoUserPanel2)
        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);

        revalidate();
        repaint();
    }







    /* ======================= GETTERS PARA EL PANEL PADRE ======================= */

    public JButton getConfirmButton()         { return createNewCustomerBtn2; }
    public EnumTipoDocumento getTipoDoc()     { return (EnumTipoDocumento) seatTypeComboBox2.getSelectedItem(); }
    public String getDocumento()              { return documentoCmb.getText().trim(); }
    public String getNombre()                 { return nameComboBox.getText().trim(); }
    public String getApellido()               { return surnameComboBox.getText().trim(); }
    public EnumEquipajeBasico getBasicLuggageType() { return (EnumEquipajeBasico) basicLuggageTypeCombo.getSelectedItem(); }
    public EnumCategoria getExtraLuggageType()       { return (EnumCategoria)      extraLuggageTypeCombo.getSelectedItem(); }
    public int getExtraUnits()                { return (Integer) extraUnitsSpinner.getValue(); } // 0..5


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        InfoUserPanel2 = new JPanel();
        titleLabel2 = new JLabel();
        firstRowPanel3 = new JPanel();
        seatLabel2 = new JLabel();
        seatTypeComboBox2 = new JComboBox();
        numTicketsLabel2 = new JLabel();
        documentoCmb = new JTextField();
        secondRowPanel2 = new JPanel();
        nameLabel = new JLabel();
        nameComboBox = new JTextField();
        surnameLabel = new JLabel();
        surnameComboBox = new JTextField();
        panel2 = new JPanel();
        secondRowCustomerPanel2 = new JPanel();
        basicLuggageLabel = new JLabel();
        basicLuggageTypeComboBox = new JComboBox();
        secondRowCustomerPanel = new JPanel();
        extraLuggageTypeLabel = new JLabel();
        extraLuggageTypeComboBox2 = new JComboBox<>();
        extraLuggageLabel = new JLabel();
        extraLuggageTextField = new JTextField();
        updateBtnPanel2 = new JPanel();
        createNewCustomerBtn2 = new JButton();

        //======== InfoUserPanel2 ========
        {
            InfoUserPanel2.setOpaque(false);
            InfoUserPanel2.setMinimumSize(new Dimension(440, 258));
            InfoUserPanel2.setMaximumSize(new Dimension(440, 258));
            InfoUserPanel2.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
            EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing
            . border. TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ),
            java. awt. Color. red) ,InfoUserPanel2. getBorder( )) ); InfoUserPanel2. addPropertyChangeListener (new java. beans. PropertyChangeListener( )
            { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .getPropertyName () ))
            throw new RuntimeException( ); }} );
            InfoUserPanel2.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel2.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)InfoUserPanel2.getLayout()).rowHeights = new int[] {0, 20, 0, 0, 0, 0, 0, 34, 38, 0};
            ((GridBagLayout)InfoUserPanel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- titleLabel2 ----
            titleLabel2.setText("Agregar Pasajero");
            titleLabel2.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            InfoUserPanel2.add(titleLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== firstRowPanel3 ========
            {
                firstRowPanel3.setPreferredSize(new Dimension(510, 30));
                firstRowPanel3.setMinimumSize(new Dimension(510, 30));
                firstRowPanel3.setOpaque(false);
                firstRowPanel3.setMaximumSize(new Dimension(510, 30));
                firstRowPanel3.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel3.getLayout()).columnWidths = new int[] {130, 110, 0, 120, 0};
                ((GridBagLayout)firstRowPanel3.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowPanel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- seatLabel2 ----
                seatLabel2.setText("TIpo Doc:");
                seatLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
                seatLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
                seatLabel2.setPreferredSize(new Dimension(120, 30));
                seatLabel2.setMaximumSize(new Dimension(70, 15));
                seatLabel2.setMinimumSize(new Dimension(70, 15));
                firstRowPanel3.add(seatLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- seatTypeComboBox2 ----
                seatTypeComboBox2.setMinimumSize(new Dimension(100, 30));
                seatTypeComboBox2.setPreferredSize(new Dimension(120, 30));
                firstRowPanel3.add(seatTypeComboBox2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- numTicketsLabel2 ----
                numTicketsLabel2.setText("Documento:");
                numTicketsLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
                numTicketsLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
                numTicketsLabel2.setPreferredSize(new Dimension(120, 30));
                numTicketsLabel2.setMaximumSize(new Dimension(70, 15));
                numTicketsLabel2.setMinimumSize(new Dimension(70, 15));
                firstRowPanel3.add(numTicketsLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- documentoCmb ----
                documentoCmb.setPreferredSize(new Dimension(120, 30));
                documentoCmb.setMinimumSize(new Dimension(100, 30));
                documentoCmb.setMaximumSize(new Dimension(100, 30));
                documentoCmb.setOpaque(false);
                firstRowPanel3.add(documentoCmb, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel2.add(firstRowPanel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowPanel2 ========
            {
                secondRowPanel2.setPreferredSize(new Dimension(510, 30));
                secondRowPanel2.setMinimumSize(new Dimension(510, 30));
                secondRowPanel2.setMaximumSize(new Dimension(510, 510));
                secondRowPanel2.setOpaque(false);
                secondRowPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel2.getLayout()).columnWidths = new int[] {130, 130, 0, 116, 0};
                ((GridBagLayout)secondRowPanel2.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- nameLabel ----
                nameLabel.setText("Nombre:");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nameLabel.setPreferredSize(new Dimension(120, 30));
                nameLabel.setMaximumSize(new Dimension(70, 15));
                nameLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel2.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameComboBox ----
                nameComboBox.setPreferredSize(new Dimension(120, 30));
                nameComboBox.setMinimumSize(new Dimension(100, 30));
                nameComboBox.setMaximumSize(new Dimension(100, 30));
                nameComboBox.setOpaque(false);
                secondRowPanel2.add(nameComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- surnameLabel ----
                surnameLabel.setText("Apellido:");
                surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                surnameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                surnameLabel.setPreferredSize(new Dimension(120, 30));
                surnameLabel.setMaximumSize(new Dimension(70, 15));
                surnameLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel2.add(surnameLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- surnameComboBox ----
                surnameComboBox.setPreferredSize(new Dimension(120, 30));
                surnameComboBox.setMinimumSize(new Dimension(100, 30));
                surnameComboBox.setMaximumSize(new Dimension(100, 30));
                surnameComboBox.setOpaque(false);
                secondRowPanel2.add(surnameComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel2.add(secondRowPanel2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== panel2 ========
            {
                panel2.setLayout(new FlowLayout());
            }
            InfoUserPanel2.add(panel2, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowCustomerPanel2 ========
            {
                secondRowCustomerPanel2.setPreferredSize(new Dimension(510, 30));
                secondRowCustomerPanel2.setMinimumSize(new Dimension(510, 30));
                secondRowCustomerPanel2.setMaximumSize(new Dimension(510, 510));
                secondRowCustomerPanel2.setOpaque(false);
                secondRowCustomerPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowCustomerPanel2.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowCustomerPanel2.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowCustomerPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowCustomerPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- basicLuggageLabel ----
                basicLuggageLabel.setText("Tipo Equipaje Basico:");
                basicLuggageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                basicLuggageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                basicLuggageLabel.setPreferredSize(new Dimension(120, 30));
                basicLuggageLabel.setMaximumSize(new Dimension(70, 15));
                basicLuggageLabel.setMinimumSize(new Dimension(70, 15));
                secondRowCustomerPanel2.add(basicLuggageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- basicLuggageTypeComboBox ----
                basicLuggageTypeComboBox.setMinimumSize(new Dimension(100, 30));
                basicLuggageTypeComboBox.setPreferredSize(new Dimension(120, 30));
                secondRowCustomerPanel2.add(basicLuggageTypeComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));
            }
            InfoUserPanel2.add(secondRowCustomerPanel2, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowCustomerPanel ========
            {
                secondRowCustomerPanel.setPreferredSize(new Dimension(510, 30));
                secondRowCustomerPanel.setMinimumSize(new Dimension(510, 30));
                secondRowCustomerPanel.setMaximumSize(new Dimension(510, 510));
                secondRowCustomerPanel.setOpaque(false);
                secondRowCustomerPanel.setBorder(null);
                secondRowCustomerPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- extraLuggageTypeLabel ----
                extraLuggageTypeLabel.setText("Tipo EquipajeExtra:");
                extraLuggageTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                extraLuggageTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                extraLuggageTypeLabel.setPreferredSize(new Dimension(120, 30));
                extraLuggageTypeLabel.setMaximumSize(new Dimension(70, 15));
                extraLuggageTypeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowCustomerPanel.add(extraLuggageTypeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageTypeComboBox2 ----
                extraLuggageTypeComboBox2.setMinimumSize(new Dimension(100, 30));
                extraLuggageTypeComboBox2.setPreferredSize(new Dimension(120, 30));
                secondRowCustomerPanel.add(extraLuggageTypeComboBox2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageLabel ----
                extraLuggageLabel.setText("Equipaje Extra:");
                extraLuggageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                extraLuggageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                extraLuggageLabel.setPreferredSize(new Dimension(120, 30));
                extraLuggageLabel.setMaximumSize(new Dimension(70, 15));
                extraLuggageLabel.setMinimumSize(new Dimension(70, 15));
                secondRowCustomerPanel.add(extraLuggageLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageTextField ----
                extraLuggageTextField.setPreferredSize(new Dimension(120, 30));
                extraLuggageTextField.setMinimumSize(new Dimension(100, 30));
                extraLuggageTextField.setMaximumSize(new Dimension(100, 30));
                extraLuggageTextField.setOpaque(false);
                secondRowCustomerPanel.add(extraLuggageTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel2.add(secondRowCustomerPanel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== updateBtnPanel2 ========
            {
                updateBtnPanel2.setOpaque(false);
                updateBtnPanel2.setMaximumSize(new Dimension(10, 10));
                updateBtnPanel2.setBorder(null);
                updateBtnPanel2.setLayout(new GridLayout());

                //---- createNewCustomerBtn2 ----
                createNewCustomerBtn2.setText("+ Agregar Pasajero");
                createNewCustomerBtn2.setOpaque(false);
                createNewCustomerBtn2.setMinimumSize(new Dimension(10, 10));
                createNewCustomerBtn2.setMaximumSize(new Dimension(10, 10));
                createNewCustomerBtn2.setPreferredSize(new Dimension(10, 10));
                updateBtnPanel2.add(createNewCustomerBtn2);
            }
            InfoUserPanel2.add(updateBtnPanel2, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel InfoUserPanel2;
    private JLabel titleLabel2;
    private JPanel firstRowPanel3;
    private JLabel seatLabel2;
    private JComboBox seatTypeComboBox2;
    private JLabel numTicketsLabel2;
    private JTextField documentoCmb;
    private JPanel secondRowPanel2;
    private JLabel nameLabel;
    private JTextField nameComboBox;
    private JLabel surnameLabel;
    private JTextField surnameComboBox;
    private JPanel panel2;
    private JPanel secondRowCustomerPanel2;
    private JLabel basicLuggageLabel;
    private JComboBox basicLuggageTypeComboBox;
    private JPanel secondRowCustomerPanel;
    private JLabel extraLuggageTypeLabel;
    private JComboBox<EnumTipoAsiento> extraLuggageTypeComboBox2;
    private JLabel extraLuggageLabel;
    private JTextField extraLuggageTextField;
    private JPanel updateBtnPanel2;
    private JButton createNewCustomerBtn2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
