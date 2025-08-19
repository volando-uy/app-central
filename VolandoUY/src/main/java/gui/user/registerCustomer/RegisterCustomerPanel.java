/*
 * Created by JFormDesigner on Tue Aug 19 13:19:30 UYT 2025
 */

package gui.user.registerCustomer;

import javax.swing.border.*;
import domain.models.user.enums.EnumTipoDocumento;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author ignac
 */
@Setter
public class RegisterCustomerPanel extends JPanel {

    private CustomerFormListener customerFormListener;

    public RegisterCustomerPanel() {
        initComponents();
        initComponntsManually();
        initListeners();
    }

    private void initListeners() {
        createNewCustomerBtn.addActionListener(e -> {
            try {
                String nickname = nicknameTextField.getText();
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String mail = emailTextField.getText();
                String citizenship = citizenshipTextLabel.getText();
                String birthDateUnparsed = birthDayTextField.getText(); // format: "dd/mm/yyyy"
                LocalDate birthDate = LocalDate.parse(birthDateUnparsed, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String id = idTextField.getText();
                EnumTipoDocumento idType = (EnumTipoDocumento) idTypeComboBox.getSelectedItem();

                CustomerFormEvent ev = new CustomerFormEvent(this, nickname, name,
                        surname, mail, citizenship, birthDate, id, idType);
                if (customerFormListener != null) {
                    customerFormListener.formEventOccurred(ev);
                } else {
                    System.out.println("No listener registered for customer form events.");
                }
            } catch (Exception ex) {
                // Create a dialog to show the error
                JOptionPane.showMessageDialog(this, "Error al crear el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponntsManually() {
        // Aquí se puede inicializar los componentes manualmente si es necesario
        // Por ejemplo, agregar elementos al JComboBox de tipos de ID
        idTypeComboBox.addItem(EnumTipoDocumento.CI);
        idTypeComboBox.addItem(EnumTipoDocumento.PASAPORTE);
        idTypeComboBox.addItem(EnumTipoDocumento.RUT);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        createNewCustomerBtn = new JButton();
        nicknameLabel = new JLabel();
        nicknameTextField = new JTextField();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        surnameTextField = new JTextField();
        surnameLabel = new JLabel();
        emailLabel = new JLabel();
        emailTextField = new JTextField();
        citizenshipLabel = new JLabel();
        citizenshipTextLabel = new JTextField();
        idLabel = new JLabel();
        idTextField = new JTextField();
        idTypeLabel = new JLabel();
        idTypeComboBox = new JComboBox<>();
        birthDayLabel = new JLabel();
        birthDayTextField = new JTextField();

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
        EmptyBorder( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing
        . border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ),
        java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( )
        { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () ))
        throw new RuntimeException( ); }} );

        //---- createNewCustomerBtn ----
        createNewCustomerBtn.setText("Crear nuevo cliente");

        //---- nicknameLabel ----
        nicknameLabel.setText("Nickname:");

        //---- nicknameTextField ----
        nicknameTextField.setPreferredSize(new Dimension(100, 30));
        nicknameTextField.setMinimumSize(new Dimension(100, 30));

        //---- nameLabel ----
        nameLabel.setText("Nombre:");

        //---- nameTextField ----
        nameTextField.setPreferredSize(new Dimension(100, 30));
        nameTextField.setMinimumSize(new Dimension(100, 30));

        //---- surnameTextField ----
        surnameTextField.setPreferredSize(new Dimension(100, 30));
        surnameTextField.setMinimumSize(new Dimension(100, 30));

        //---- surnameLabel ----
        surnameLabel.setText("Apellido:");

        //---- emailLabel ----
        emailLabel.setText("Email:");

        //---- emailTextField ----
        emailTextField.setPreferredSize(new Dimension(100, 30));
        emailTextField.setMinimumSize(new Dimension(100, 30));

        //---- citizenshipLabel ----
        citizenshipLabel.setText("Nacionalidad:");

        //---- citizenshipTextLabel ----
        citizenshipTextLabel.setPreferredSize(new Dimension(100, 30));
        citizenshipTextLabel.setMinimumSize(new Dimension(100, 30));

        //---- idLabel ----
        idLabel.setText("ID:");

        //---- idTextField ----
        idTextField.setPreferredSize(new Dimension(100, 30));
        idTextField.setMinimumSize(new Dimension(100, 30));

        //---- idTypeLabel ----
        idTypeLabel.setText("Tipo de ID:");

        //---- idTypeComboBox ----
        idTypeComboBox.setMinimumSize(new Dimension(100, 30));
        idTypeComboBox.setPreferredSize(new Dimension(100, 30));

        //---- birthDayLabel ----
        birthDayLabel.setText("Fecha de nacimiento");

        //---- birthDayTextField ----
        birthDayTextField.setPreferredSize(new Dimension(100, 30));
        birthDayTextField.setMinimumSize(new Dimension(100, 30));
        birthDayTextField.setText("dd/mm/yyyy");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(235, 235, 235)
                            .addComponent(createNewCustomerBtn))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(73, 73, 73)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(idLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(idTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(surnameLabel)
                                            .addGap(6, 6, 6)
                                            .addComponent(surnameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nicknameLabel)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(nicknameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(citizenshipLabel)
                                                .addComponent(birthDayLabel))
                                            .addGap(6, 6, 6)
                                            .addGroup(layout.createParallelGroup()
                                                .addComponent(citizenshipTextLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(birthDayTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                    .addGap(60, 60, 60)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nameLabel)
                                            .addGap(6, 6, 6)
                                            .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(emailLabel)
                                            .addGap(6, 6, 6)
                                            .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(idTypeLabel)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(idTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))))
                    .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nicknameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(nicknameLabel))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(nameLabel))
                        .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(42, 42, 42)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(surnameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(surnameLabel)
                                .addComponent(emailLabel))))
                    .addGap(40, 40, 40)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(citizenshipTextLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(idTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(idTypeLabel))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(citizenshipLabel)))
                    .addGap(38, 38, 38)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(idTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(idLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(birthDayTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(birthDayLabel)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                    .addComponent(createNewCustomerBtn)
                    .addGap(30, 30, 30))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JButton createNewCustomerBtn;
    private JLabel nicknameLabel;
    private JTextField nicknameTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel citizenshipLabel;
    private JTextField citizenshipTextLabel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel idTypeLabel;
    private JComboBox<EnumTipoDocumento> idTypeComboBox;
    private JLabel birthDayLabel;
    private JTextField birthDayTextField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
