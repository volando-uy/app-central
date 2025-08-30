package gui.user.updateUser;

import controllers.user.IUserController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.enums.EnumTipoDocumento;
import lombok.Setter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

@Setter
public class UpdateUserPanel extends JPanel {

    IUserController userController;
    UserDTO selectedUser;
    
    private boolean isLoadingUsers = false;
    
    List<UserDTO> users = new ArrayList<>();

    public UpdateUserPanel(IUserController uController) {
        userController = uController;
        initComponents();
        loadUserList();
        initListeners();
        initPlaceholderForTextField(variableTextField4, "dd/mm/yyyy");
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadUserList() {
        isLoadingUsers = true;
        userComboBox.removeAllItems();

        List<UserDTO> usersDTOs = userController.getAllUsers();
        for (UserDTO user : usersDTOs) {
            userComboBox.addItem(user.getName() + " (" + user.getNickname() + ")");
        }
        this.users = usersDTOs;
        if (!users.isEmpty()) {
            userComboBox.setSelectedIndex(0);
        }
        isLoadingUsers = false;
    }

    private String getSelectedUserNickname() {
        Integer selectedUserIndex = userComboBox.getSelectedIndex();
        UserDTO selectedUser = users.get(selectedUserIndex);
        return selectedUser.getNickname();
    }

    private void initListeners() {
        // Add action listener to the JLabel
        reloadUsersBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadUserList();
            }
        });

        userComboBox.addActionListener(e -> {
            if (isLoadingUsers) return;
            try {
                String selectedNickname = getSelectedUserNickname();
                // If user doesn't exist, throw exception
                selectedUser = userController.getUserByNickname(selectedNickname);
                nameTextField.setText(selectedUser.getName());
                if (selectedUser instanceof AirlineDTO selectedUser) {
                    variableLabel1.setText("Descripción:");
                    variableTextField1.setText(selectedUser.getDescription());
                    variableLabel2.setText("Web:");
                    variableTextField2.setText(selectedUser.getWeb());

                    variableLabel1.setEnabled(true);
                    variableTextField1.setEnabled(true);
                    variableLabel2.setEnabled(true);
                    variableTextField2.setEnabled(true);


                    variableLabel3.setEnabled(false);
                    variableTextField3.setEnabled(false);
                    variableTextField3.setText("");
                    variableLabel4.setEnabled(false);
                    variableTextField4.setEnabled(false);
                    variableTextField4.setText("");
                    idTypeLabel.setEnabled(false);
                    idTypeComboBox.setEnabled(false);
                } else if (selectedUser instanceof CustomerDTO selectedUser) {
                    variableLabel1.setText("Apellido:");
                    variableTextField1.setText(selectedUser.getSurname());
                    variableLabel2.setText("Nacionalidad:");
                    variableTextField2.setText(selectedUser.getCitizenship());
                    variableLabel3.setText("ID:");
                    variableTextField3.setText(selectedUser.getId());
                    variableLabel4.setText("Fecha de nacimiento:");
                    variableTextField4.setText(selectedUser.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    idTypeLabel.setText("Tipo de ID:");
                    idTypeComboBox.setModel(new DefaultComboBoxModel<>(EnumTipoDocumento.values()));
                    idTypeComboBox.setSelectedItem(selectedUser.getIdType());

                    nameLabel.setEnabled(true);
                    nameTextField.setEnabled(true);
                    variableLabel1.setEnabled(true);
                    variableTextField1.setEnabled(true);
                    variableLabel2.setEnabled(true);
                    variableTextField2.setEnabled(true);
                    variableLabel3.setEnabled(true);
                    variableTextField3.setEnabled(true);
                    variableLabel4.setEnabled(true);
                    variableTextField4.setEnabled(true);
                    idTypeLabel.setEnabled(true);
                    idTypeComboBox.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Error del sistema", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateUserBtn.addActionListener(e -> {
            try {
                if (selectedUser == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, carga un usuario primero", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String name = nameTextField.getText();
                String variable1 = variableTextField1.getText();
                String variable2 = variableTextField2.getText();
                String variable3 = variableTextField3.getText();
                String variable4 = variableTextField4.getText();

                if (selectedUser instanceof AirlineDTO) {
                    AirlineDTO airline = (AirlineDTO) selectedUser;
                    airline.setName(name);
                    airline.setDescription(variable1);
                    airline.setWeb(variable2);
                    userController.updateUser(airline.getNickname(), airline);
                } else if (selectedUser instanceof CustomerDTO) {
                    CustomerDTO customer = (CustomerDTO) selectedUser;
                    customer.setName(name);
                    customer.setSurname(variable1);
                    customer.setCitizenship(variable2);
                    customer.setId(variable3);
                    customer.setBirthDate(LocalDate.parse(variable4, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    customer.setIdType((EnumTipoDocumento) idTypeComboBox.getSelectedItem());
                    userController.updateUser(customer.getNickname(), customer);
                } else {
                    JOptionPane.showMessageDialog(this, "Error del sistema", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initPlaceholderForTextField(JTextField textField, String placeholder) {

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
        // Generated using JFormDesigner Evaluation license - Nahuel
        titleLabel = new JLabel();
        vSpacer18 = new JPanel(null);
        selectUserPanel = new JPanel();
        userLabel = new JLabel();
        userComboBox = new JComboBox<>();
        reloadUsersBtn = new JLabel();
        InfoUserPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        vSpacer14 = new JPanel(null);
        firstRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        variableLabel1 = new JLabel();
        variableTextField1 = new JTextField();
        secondRowPanel = new JPanel();
        variableLabel2 = new JLabel();
        variableTextField2 = new JTextField();
        variableLabel3 = new JLabel();
        variableTextField3 = new JTextField();
        thirdRowPanel = new JPanel();
        variableLabel4 = new JLabel();
        variableTextField4 = new JTextField();
        idTypeLabel = new JLabel();
        idTypeComboBox = new JComboBox<>();
        vSpacer13 = new JPanel(null);
        updateBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        updateUserBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
        new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion"
        , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
        , new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 )
        ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Modificar usuario");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //---- vSpacer18 ----
        vSpacer18.setMinimumSize(new Dimension(12, 70));
        vSpacer18.setPreferredSize(new Dimension(10, 100));
        vSpacer18.setOpaque(false);
        add(vSpacer18, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== selectUserPanel ========
        {
            selectUserPanel.setOpaque(false);
            selectUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)selectUserPanel.getLayout()).columnWidths = new int[] {0, 0, 14, 0, 0};
            ((GridBagLayout)selectUserPanel.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)selectUserPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)selectUserPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- userLabel ----
            userLabel.setText("Selecciona usuario:");
            selectUserPanel.add(userLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 10), 0, 0));

            //---- userComboBox ----
            userComboBox.setMinimumSize(new Dimension(100, 30));
            userComboBox.setPreferredSize(new Dimension(100, 30));
            userComboBox.setOpaque(false);
            selectUserPanel.add(userComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- reloadUsersBtn ----
            reloadUsersBtn.setText("\ud83d\udd04");
            reloadUsersBtn.setPreferredSize(new Dimension(34, 34));
            reloadUsersBtn.setMaximumSize(new Dimension(34, 34));
            reloadUsersBtn.setMinimumSize(new Dimension(34, 34));
            reloadUsersBtn.setBorder(null);
            reloadUsersBtn.setHorizontalAlignment(SwingConstants.CENTER);
            reloadUsersBtn.setHorizontalTextPosition(SwingConstants.CENTER);
            reloadUsersBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            selectUserPanel.add(reloadUsersBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(selectUserPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setOpaque(false);
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 0, 20, 38, 104, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoUserPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoUserPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer14 ----
            vSpacer14.setOpaque(false);
            InfoUserPanel.add(vSpacer14, new GridBagConstraints(1, 1, 1, 1, 0.0, 200.0,
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

                //---- variableLabel1 ----
                variableLabel1.setText("Nacionalidad:");
                variableLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
                variableLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
                variableLabel1.setPreferredSize(new Dimension(120, 30));
                variableLabel1.setMaximumSize(new Dimension(70, 15));
                variableLabel1.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(variableLabel1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- variableTextField1 ----
                variableTextField1.setPreferredSize(new Dimension(120, 30));
                variableTextField1.setMinimumSize(new Dimension(100, 30));
                variableTextField1.setMaximumSize(new Dimension(100, 30));
                variableTextField1.setOpaque(false);
                firstRowPanel.add(variableTextField1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(firstRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
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

                //---- variableLabel2 ----
                variableLabel2.setText("Apellido:");
                variableLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
                variableLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
                variableLabel2.setPreferredSize(new Dimension(120, 30));
                variableLabel2.setMaximumSize(new Dimension(70, 15));
                variableLabel2.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(variableLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- variableTextField2 ----
                variableTextField2.setPreferredSize(new Dimension(120, 30));
                variableTextField2.setMinimumSize(new Dimension(100, 30));
                variableTextField2.setMaximumSize(new Dimension(100, 30));
                variableTextField2.setOpaque(false);
                secondRowPanel.add(variableTextField2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- variableLabel3 ----
                variableLabel3.setText("ID:");
                variableLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
                variableLabel3.setHorizontalTextPosition(SwingConstants.RIGHT);
                variableLabel3.setPreferredSize(new Dimension(120, 30));
                variableLabel3.setMaximumSize(new Dimension(70, 15));
                variableLabel3.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(variableLabel3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- variableTextField3 ----
                variableTextField3.setPreferredSize(new Dimension(120, 30));
                variableTextField3.setMinimumSize(new Dimension(100, 30));
                variableTextField3.setMaximumSize(new Dimension(100, 30));
                variableTextField3.setOpaque(false);
                secondRowPanel.add(variableTextField3, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 30));
                thirdRowPanel.setOpaque(false);
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {20, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- variableLabel4 ----
                variableLabel4.setText("Fecha de Nacimiento:");
                variableLabel4.setPreferredSize(new Dimension(70, 15));
                variableLabel4.setMaximumSize(new Dimension(70, 15));
                variableLabel4.setMinimumSize(new Dimension(70, 15));
                variableLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
                variableLabel4.setHorizontalTextPosition(SwingConstants.RIGHT);
                thirdRowPanel.add(variableLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- variableTextField4 ----
                variableTextField4.setPreferredSize(new Dimension(100, 30));
                variableTextField4.setMinimumSize(new Dimension(100, 30));
                variableTextField4.setText("dd/mm/yyyy");
                variableTextField4.setOpaque(false);
                thirdRowPanel.add(variableTextField4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idTypeLabel ----
                idTypeLabel.setText("Tipo de ID:");
                idTypeLabel.setPreferredSize(new Dimension(70, 15));
                idTypeLabel.setMaximumSize(new Dimension(70, 15));
                idTypeLabel.setMinimumSize(new Dimension(70, 15));
                idTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                thirdRowPanel.add(idTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- idTypeComboBox ----
                idTypeComboBox.setMinimumSize(new Dimension(100, 30));
                idTypeComboBox.setPreferredSize(new Dimension(100, 30));
                idTypeComboBox.setOpaque(false);
                thirdRowPanel.add(idTypeComboBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(thirdRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoUserPanel.add(vSpacer13, new GridBagConstraints(1, 5, 1, 1, 0.0, 200.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoUserPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
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

            //---- updateUserBtn ----
            updateUserBtn.setText("\u27f3 Modificar usuario");
            updateUserBtn.setOpaque(false);
            updateBtnPanel.add(updateUserBtn, BorderLayout.CENTER);
        }
        add(updateBtnPanel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        vSpacer19.setOpaque(false);
        add(vSpacer19, new GridBagConstraints(0, 5, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JLabel titleLabel;
    private JPanel vSpacer18;
    private JPanel selectUserPanel;
    private JLabel userLabel;
    private JComboBox<String> userComboBox;
    private JLabel reloadUsersBtn;
    private JPanel InfoUserPanel;
    private JPanel hSpacer5;
    private JPanel hSpacer6;
    private JPanel vSpacer14;
    private JPanel firstRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel variableLabel1;
    private JTextField variableTextField1;
    private JPanel secondRowPanel;
    private JLabel variableLabel2;
    private JTextField variableTextField2;
    private JLabel variableLabel3;
    private JTextField variableTextField3;
    private JPanel thirdRowPanel;
    private JLabel variableLabel4;
    private JTextField variableTextField4;
    private JLabel idTypeLabel;
    private JComboBox<EnumTipoDocumento> idTypeComboBox;
    private JPanel vSpacer13;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton updateUserBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
