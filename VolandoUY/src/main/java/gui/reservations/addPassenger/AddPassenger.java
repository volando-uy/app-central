/*
 * Created by JFormDesigner on Thu Sep 04 19:05:36 UYT 2025
 */

package gui.reservations.addPassenger;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import domain.models.enums.EnumTipoDocumento;
import net.miginfocom.swing.*;

/**
 * @author AparicioQuian
 */
public class AddPassenger extends JPanel {
    public AddPassenger() {
        initComponents();    postInit(); // <-- nuestro hook


    }
    // Método auxiliar fuera del bloque generado:
    private void postInit() {
        // Poblar el combo con el enum
        seatTypeComboBox2.setModel(new DefaultComboBoxModel<>(EnumTipoDocumento.values()));

        // Asegurar que el panel generado esté agregado al root
        if (InfoUserPanel2.getParent() != this) {
            setLayout(new BorderLayout());
            add(InfoUserPanel2, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    // Getters para que BookFlightPanel lea el formulario
    public JButton getConfirmButton() { return createNewCustomerBtn2; }
    public domain.models.enums.EnumTipoDocumento getTipoDoc() {
        return (domain.models.enums.EnumTipoDocumento) seatTypeComboBox2.getSelectedItem();
    }
    public String getDocumento() { return numTicketsTextField2.getText().trim(); }
    public String getNombre()    { return extraLuggageTextField2.getText().trim(); }
    public String getApellido()  { return reservationdateTextField2.getText().trim(); }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        InfoUserPanel2 = new JPanel();
        titleLabel2 = new JLabel();
        firstRowPanel3 = new JPanel();
        seatLabel2 = new JLabel();
        seatTypeComboBox2 = new JComboBox();
        numTicketsLabel2 = new JLabel();
        numTicketsTextField2 = new JTextField();
        vSpacer20 = new JPanel(null);
        secondRowPanel2 = new JPanel();
        extraLuggageLabel2 = new JLabel();
        extraLuggageTextField2 = new JTextField();
        reservationdateTypeLabel2 = new JLabel();
        reservationdateTextField2 = new JTextField();
        updateBtnPanel2 = new JPanel();
        hSpacer3 = new JPanel(null);
        hSpacer4 = new JPanel(null);
        createNewCustomerBtn2 = new JButton();

        //======== InfoUserPanel2 ========
        {
            InfoUserPanel2.setOpaque(false);
            InfoUserPanel2.setMinimumSize(new Dimension(440, 258));
            InfoUserPanel2.setMaximumSize(new Dimension(440, 258));
            InfoUserPanel2.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax
            .swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing
            .border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.
            Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.red
            ),InfoUserPanel2. getBorder()));InfoUserPanel2. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override
            public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName(
            )))throw new RuntimeException();}});
            InfoUserPanel2.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel2.getLayout()).rowHeights = new int[] {0, 20, 0, 0, 0, 38, 15, 0};
            ((GridBagLayout)InfoUserPanel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- titleLabel2 ----
            titleLabel2.setText("Agregar Pasajero");
            titleLabel2.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            InfoUserPanel2.add(titleLabel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== firstRowPanel3 ========
            {
                firstRowPanel3.setPreferredSize(new Dimension(510, 30));
                firstRowPanel3.setMinimumSize(new Dimension(510, 30));
                firstRowPanel3.setOpaque(false);
                firstRowPanel3.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel3.getLayout()).columnWidths = new int[] {130, 0, 130, 110, 0};
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

                //---- numTicketsTextField2 ----
                numTicketsTextField2.setPreferredSize(new Dimension(120, 30));
                numTicketsTextField2.setMinimumSize(new Dimension(100, 30));
                numTicketsTextField2.setMaximumSize(new Dimension(100, 30));
                numTicketsTextField2.setOpaque(false);
                firstRowPanel3.add(numTicketsTextField2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel2.add(firstRowPanel3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer20 ----
            vSpacer20.setPreferredSize(new Dimension(10, 100));
            vSpacer20.setOpaque(false);
            InfoUserPanel2.add(vSpacer20, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowPanel2 ========
            {
                secondRowPanel2.setPreferredSize(new Dimension(510, 30));
                secondRowPanel2.setMinimumSize(new Dimension(510, 30));
                secondRowPanel2.setMaximumSize(new Dimension(510, 510));
                secondRowPanel2.setOpaque(false);
                secondRowPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel2.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowPanel2.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- extraLuggageLabel2 ----
                extraLuggageLabel2.setText("Nombre:");
                extraLuggageLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
                extraLuggageLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
                extraLuggageLabel2.setPreferredSize(new Dimension(120, 30));
                extraLuggageLabel2.setMaximumSize(new Dimension(70, 15));
                extraLuggageLabel2.setMinimumSize(new Dimension(70, 15));
                secondRowPanel2.add(extraLuggageLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageTextField2 ----
                extraLuggageTextField2.setPreferredSize(new Dimension(120, 30));
                extraLuggageTextField2.setMinimumSize(new Dimension(100, 30));
                extraLuggageTextField2.setMaximumSize(new Dimension(100, 30));
                extraLuggageTextField2.setOpaque(false);
                secondRowPanel2.add(extraLuggageTextField2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTypeLabel2 ----
                reservationdateTypeLabel2.setText("Apellido:");
                reservationdateTypeLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
                reservationdateTypeLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
                reservationdateTypeLabel2.setPreferredSize(new Dimension(120, 30));
                reservationdateTypeLabel2.setMaximumSize(new Dimension(70, 15));
                reservationdateTypeLabel2.setMinimumSize(new Dimension(70, 15));
                secondRowPanel2.add(reservationdateTypeLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTextField2 ----
                reservationdateTextField2.setPreferredSize(new Dimension(120, 30));
                reservationdateTextField2.setMinimumSize(new Dimension(100, 30));
                reservationdateTextField2.setMaximumSize(new Dimension(100, 30));
                reservationdateTextField2.setOpaque(false);
                secondRowPanel2.add(reservationdateTextField2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel2.add(secondRowPanel2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== updateBtnPanel2 ========
            {
                updateBtnPanel2.setOpaque(false);
                updateBtnPanel2.setMinimumSize(new Dimension(10, 34));
                updateBtnPanel2.setMaximumSize(new Dimension(19, 10));
                updateBtnPanel2.setLayout(new BorderLayout());

                //---- hSpacer3 ----
                hSpacer3.setPreferredSize(new Dimension(110, 10));
                hSpacer3.setOpaque(false);
                updateBtnPanel2.add(hSpacer3, BorderLayout.LINE_START);

                //---- hSpacer4 ----
                hSpacer4.setPreferredSize(new Dimension(110, 10));
                hSpacer4.setOpaque(false);
                updateBtnPanel2.add(hSpacer4, BorderLayout.LINE_END);

                //---- createNewCustomerBtn2 ----
                createNewCustomerBtn2.setText("+ Agregar Pasajero");
                createNewCustomerBtn2.setOpaque(false);
                createNewCustomerBtn2.setMinimumSize(new Dimension(10, 34));
                createNewCustomerBtn2.setMaximumSize(new Dimension(10, 34));
                createNewCustomerBtn2.setPreferredSize(new Dimension(10, 34));
                updateBtnPanel2.add(createNewCustomerBtn2, BorderLayout.CENTER);
            }
            InfoUserPanel2.add(updateBtnPanel2, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0,
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
    private JTextField numTicketsTextField2;
    private JPanel vSpacer20;
    private JPanel secondRowPanel2;
    private JLabel extraLuggageLabel2;
    private JTextField extraLuggageTextField2;
    private JLabel reservationdateTypeLabel2;
    private JTextField reservationdateTextField2;
    private JPanel updateBtnPanel2;
    private JPanel hSpacer3;
    private JPanel hSpacer4;
    private JButton createNewCustomerBtn2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
