/*
 * Created by JFormDesigner on Tue Aug 19 13:19:30 UYT 2025
 */

package gui.user.registerCustomer;

import javax.swing.border.*;

import controllers.user.IUserController;
import domain.dtos.user.BaseCustomerDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.enums.EnumTipoDocumento;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 * @author ignac
 */
@Setter
public class RegisterCustomerPanel extends JPanel {

    private IUserController userController;

    public RegisterCustomerPanel(IUserController userController) {
        this.userController = userController;

        initComponents();
        initComponentsManually();
        initListeners();
        initPlaceholderForTextField(birthDateTextField, "dd/mm/yyyy");
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void initListeners() {
        createNewCustomerBtn.addActionListener(e -> {
            try {
                String nickname = nicknameTextField.getText();
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                String mail = emailTextField.getText();
                String citizenship = citizenshipTextField.getText();
                String birthDateUnparsed = birthDateTextField.getText(); // format: "dd/mm/yyyy"
                LocalDate birthDate = LocalDate.parse(birthDateUnparsed, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String id = idTextField.getText();
                EnumTipoDocumento idType = (EnumTipoDocumento) idTypeComboBox.getSelectedItem();

                // TODO: unhardcode this variables
                String password = "defaultPassword123"; // Default password
                String image = ""; // Default image path or URL

                BaseCustomerDTO baseCustomerDTO = new BaseCustomerDTO(
                        nickname,
                        name,
                        mail,
                        password,
                        image,
                        surname,
                        citizenship,
                        birthDate,
                        id,
                        idType
                );

                BaseCustomerDTO createdCustomerDTO = userController.registerCustomer(baseCustomerDTO);

                JOptionPane.showMessageDialog(this, createdCustomerDTO, "Cliente creado exitosamente", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                // Create a dialog to show the error
                JOptionPane.showMessageDialog(this, "Error al crear el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponentsManually() {
        // Aquí se puede inicializar los componentes manualmente si es necesario
        // Por ejemplo, agregar elementos al JComboBox de tipos de ID
        idTypeComboBox.addItem(EnumTipoDocumento.CI);
        idTypeComboBox.addItem(EnumTipoDocumento.PASAPORTE);
        idTypeComboBox.addItem(EnumTipoDocumento.RUT);

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
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        titleLabel = new JLabel();
        InfoUserPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        nicknameLabel = new JLabel();
        nicknameTextField = new JTextField();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        secondRowPanel = new JPanel();
        surnameLabel = new JLabel();
        surnameTextField = new JTextField();
        emailLabel = new JLabel();
        emailTextField = new JTextField();
        vSpacer13 = new JPanel(null);
        thirdRowPanel = new JPanel();
        citizenshipLabel = new JLabel();
        citizenshipTextField = new JTextField();
        idLabel = new JLabel();
        idTextField = new JTextField();
        fourthRowPanel = new JPanel();
        birthDateLabel = new JLabel();
        birthDateTextField = new JTextField();
        idTypeLabel = new JLabel();
        idTypeComboBox = new JComboBox<>();
        updateBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createNewCustomerBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0xeeeeee));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (
        new javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion"
        , javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
        , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 )
        , java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (
        new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Crear nuevo cliente");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setOpaque(false);
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 15, 0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoUserPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            vSpacer17.setOpaque(false);
            InfoUserPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoUserPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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

                //---- nicknameLabel ----
                nicknameLabel.setText("Nickname:");
                nicknameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nicknameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nicknameLabel.setPreferredSize(new Dimension(120, 30));
                nicknameLabel.setMaximumSize(new Dimension(70, 15));
                nicknameLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(nicknameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nicknameTextField ----
                nicknameTextField.setPreferredSize(new Dimension(120, 30));
                nicknameTextField.setMinimumSize(new Dimension(100, 30));
                nicknameTextField.setMaximumSize(new Dimension(100, 30));
                nicknameTextField.setOpaque(false);
                firstRowPanel.add(nicknameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameLabel ----
                nameLabel.setText("Nombre:");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nameLabel.setPreferredSize(new Dimension(120, 30));
                nameLabel.setMaximumSize(new Dimension(70, 15));
                nameLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(nameLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameTextField ----
                nameTextField.setPreferredSize(new Dimension(120, 30));
                nameTextField.setMinimumSize(new Dimension(100, 30));
                nameTextField.setMaximumSize(new Dimension(100, 30));
                nameTextField.setOpaque(false);
                firstRowPanel.add(nameTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setOpaque(false);
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- surnameLabel ----
                surnameLabel.setText("Apellido:");
                surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                surnameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                surnameLabel.setPreferredSize(new Dimension(120, 30));
                surnameLabel.setMaximumSize(new Dimension(70, 15));
                surnameLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(surnameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- surnameTextField ----
                surnameTextField.setPreferredSize(new Dimension(120, 30));
                surnameTextField.setMinimumSize(new Dimension(100, 30));
                surnameTextField.setMaximumSize(new Dimension(100, 30));
                surnameTextField.setOpaque(false);
                secondRowPanel.add(surnameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- emailLabel ----
                emailLabel.setText("Email:");
                emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                emailLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                emailLabel.setPreferredSize(new Dimension(120, 30));
                emailLabel.setMaximumSize(new Dimension(70, 15));
                emailLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(emailLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- emailTextField ----
                emailTextField.setPreferredSize(new Dimension(120, 30));
                emailTextField.setMinimumSize(new Dimension(100, 30));
                emailTextField.setMaximumSize(new Dimension(100, 30));
                emailTextField.setOpaque(false);
                secondRowPanel.add(emailTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoUserPanel.add(vSpacer13, new GridBagConstraints(1, 5, 1, 1, 0.0, 200.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

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

                //---- citizenshipLabel ----
                citizenshipLabel.setText("Nacionalidad:");
                citizenshipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                citizenshipLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                citizenshipLabel.setPreferredSize(new Dimension(120, 30));
                citizenshipLabel.setMaximumSize(new Dimension(70, 15));
                citizenshipLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(citizenshipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- citizenshipTextField ----
                citizenshipTextField.setPreferredSize(new Dimension(120, 30));
                citizenshipTextField.setMinimumSize(new Dimension(100, 30));
                citizenshipTextField.setMaximumSize(new Dimension(100, 30));
                citizenshipTextField.setOpaque(false);
                thirdRowPanel.add(citizenshipTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idLabel ----
                idLabel.setText("Id:");
                idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                idLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                idLabel.setPreferredSize(new Dimension(120, 30));
                idLabel.setMaximumSize(new Dimension(70, 15));
                idLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(idLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idTextField ----
                idTextField.setPreferredSize(new Dimension(120, 30));
                idTextField.setMinimumSize(new Dimension(100, 30));
                idTextField.setMaximumSize(new Dimension(100, 30));
                idTextField.setOpaque(false);
                thirdRowPanel.add(idTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(thirdRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== fourthRowPanel ========
            {
                fourthRowPanel.setPreferredSize(new Dimension(510, 30));
                fourthRowPanel.setMinimumSize(new Dimension(510, 30));
                fourthRowPanel.setMaximumSize(new Dimension(510, 30));
                fourthRowPanel.setOpaque(false);
                fourthRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowHeights = new int[] {20, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- birthDateLabel ----
                birthDateLabel.setText("Fecha de Nacimiento:");
                birthDateLabel.setPreferredSize(new Dimension(70, 15));
                birthDateLabel.setMaximumSize(new Dimension(70, 15));
                birthDateLabel.setMinimumSize(new Dimension(70, 15));
                birthDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                birthDateLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                fourthRowPanel.add(birthDateLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- birthDateTextField ----
                birthDateTextField.setPreferredSize(new Dimension(120, 30));
                birthDateTextField.setMinimumSize(new Dimension(100, 30));
                birthDateTextField.setText("dd/mm/yyyy");
                birthDateTextField.setOpaque(false);
                fourthRowPanel.add(birthDateTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idTypeLabel ----
                idTypeLabel.setText("Tipo de ID:");
                idTypeLabel.setPreferredSize(new Dimension(70, 15));
                idTypeLabel.setMaximumSize(new Dimension(70, 15));
                idTypeLabel.setMinimumSize(new Dimension(70, 15));
                idTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fourthRowPanel.add(idTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idTypeComboBox ----
                idTypeComboBox.setMinimumSize(new Dimension(100, 30));
                idTypeComboBox.setPreferredSize(new Dimension(100, 30));
                idTypeComboBox.setOpaque(false);
                fourthRowPanel.add(idTypeComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(fourthRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoUserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== updateBtnPanel ========
        {
            updateBtnPanel.setOpaque(false);
            updateBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            updateBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            updateBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createNewCustomerBtn ----
            createNewCustomerBtn.setText("+ Crear cliente");
            createNewCustomerBtn.setOpaque(false);
            updateBtnPanel.add(createNewCustomerBtn, BorderLayout.CENTER);
        }
        add(updateBtnPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JLabel titleLabel;
    private JPanel InfoUserPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel nicknameLabel;
    private JTextField nicknameTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JPanel secondRowPanel;
    private JLabel surnameLabel;
    private JTextField surnameTextField;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JPanel vSpacer13;
    private JPanel thirdRowPanel;
    private JLabel citizenshipLabel;
    private JTextField citizenshipTextField;
    private JLabel idLabel;
    private JTextField idTextField;
    private JPanel fourthRowPanel;
    private JLabel birthDateLabel;
    private JTextField birthDateTextField;
    private JLabel idTypeLabel;
    private JComboBox<EnumTipoDocumento> idTypeComboBox;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewCustomerBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
