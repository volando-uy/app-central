package gui.user.registerAirline;

import controllers.user.IUserController;
import controllers.utils.IUtilsController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.File;

@Setter
public class RegisterAirlinePanel extends JPanel {
    
    IUserController userController;
    IUtilsController utilsController;
    
    File selectedImageFile = null;
    
    public RegisterAirlinePanel(IUserController userController, IUtilsController utilsController) {
        this.userController = userController;
        this.utilsController = utilsController;

        initComponents();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    
    private void initListeners() {
        createNewAirlineBtn.addActionListener(e -> {
            try {
                String nickname = nicknameTextField.getText();
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                String email = mailTextField.getText();
                String web = webTextField.getText();
                String password  = String.valueOf(passwordField.getPassword());
                if (!password.equals(String.valueOf(confirmPasswordField.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Upload image
                String imagePath = null;
                if (selectedImageFile != null) {
                    imagePath = utilsController.uploadImage(selectedImageFile, "users/airlines/" + nickname);
                }

                BaseAirlineDTO baseAirlineDTO = new BaseAirlineDTO(
                        nickname,
                        name,
                        email,
                        password,
                        imagePath,
                        description,
                        web
                );

                BaseAirlineDTO createdAirlineDTO = userController.registerAirline(baseAirlineDTO);

                JOptionPane.showMessageDialog(this,
                        "Nickname: " + createdAirlineDTO.getNickname() +
                                "\nName: " + createdAirlineDTO.getName() +
                                "\nEmail: " + createdAirlineDTO.getMail() +
                                "\nDescription: " + createdAirlineDTO.getDescription() +
                                (!web.isEmpty() ? ("\nWeb: " + createdAirlineDTO.getWeb()) : ""),
                        "User Registered",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la aerolínea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        uploadImageBtn.addActionListener(e -> {
            // Open a new FileChooser in a new window
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                selectedImageFile = fileChooser.getSelectedFile();
                // Set the image path to the label
                uploadedImageLabel.setText(selectedImageFile.getName());
            }
        });
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
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
        vSpacer13 = new JPanel(null);
        secondRowPanel = new JPanel();
        mailLabel = new JLabel();
        mailTextField = new JTextField();
        webLabel = new JLabel();
        webTextField = new JTextField();
        thirdRowPanel = new JPanel();
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        fourthRowPanel = new JPanel();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        confirmPasswordLabel = new JLabel();
        confirmPasswordField = new JPasswordField();
        fifthRowPanel = new JPanel();
        uploadImageLabel = new JLabel();
        uploadImageBtn = new JButton();
        uploadedImageLabel = new JLabel();
        updateBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createNewAirlineBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
        0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
        .BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.
        red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
        beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Crear nueva aerolinea");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setOpaque(false);
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoUserPanel.add(vSpacer13, new GridBagConstraints(1, 5, 1, 1, 0.0, 400.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
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

                //---- mailLabel ----
                mailLabel.setText("Email:");
                mailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                mailLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                mailLabel.setPreferredSize(new Dimension(120, 30));
                mailLabel.setMaximumSize(new Dimension(70, 15));
                mailLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(mailLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- mailTextField ----
                mailTextField.setPreferredSize(new Dimension(120, 30));
                mailTextField.setMinimumSize(new Dimension(100, 30));
                mailTextField.setMaximumSize(new Dimension(100, 30));
                mailTextField.setOpaque(false);
                secondRowPanel.add(mailTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- webLabel ----
                webLabel.setText("Web:");
                webLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                webLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                webLabel.setPreferredSize(new Dimension(120, 30));
                webLabel.setMaximumSize(new Dimension(70, 15));
                webLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(webLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- webTextField ----
                webTextField.setPreferredSize(new Dimension(120, 30));
                webTextField.setMinimumSize(new Dimension(100, 30));
                webTextField.setMaximumSize(new Dimension(100, 30));
                webTextField.setOpaque(false);
                secondRowPanel.add(webTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setOpaque(false);
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 380, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- descriptionLabel ----
                descriptionLabel.setText("Descripcion:");
                descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                descriptionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                descriptionLabel.setPreferredSize(new Dimension(120, 30));
                descriptionLabel.setMaximumSize(new Dimension(70, 15));
                descriptionLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(descriptionLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- descriptionTextField ----
                descriptionTextField.setPreferredSize(new Dimension(120, 30));
                descriptionTextField.setMinimumSize(new Dimension(100, 30));
                descriptionTextField.setMaximumSize(new Dimension(100, 30));
                descriptionTextField.setOpaque(false);
                thirdRowPanel.add(descriptionTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(thirdRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== fourthRowPanel ========
            {
                fourthRowPanel.setPreferredSize(new Dimension(510, 30));
                fourthRowPanel.setMinimumSize(new Dimension(510, 30));
                fourthRowPanel.setMaximumSize(new Dimension(510, 510));
                fourthRowPanel.setOpaque(false);
                fourthRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)fourthRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)fourthRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- passwordLabel ----
                passwordLabel.setText("Contrase\u00f1a:");
                passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                passwordLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                passwordLabel.setPreferredSize(new Dimension(120, 30));
                passwordLabel.setMaximumSize(new Dimension(70, 15));
                passwordLabel.setMinimumSize(new Dimension(70, 15));
                fourthRowPanel.add(passwordLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- passwordField ----
                passwordField.setPreferredSize(new Dimension(120, 30));
                passwordField.setMinimumSize(new Dimension(100, 30));
                passwordField.setMaximumSize(new Dimension(100, 30));
                passwordField.setOpaque(false);
                fourthRowPanel.add(passwordField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- confirmPasswordLabel ----
                confirmPasswordLabel.setText("Confirmar contrase\u00f1a:");
                confirmPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                confirmPasswordLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                confirmPasswordLabel.setPreferredSize(new Dimension(120, 30));
                confirmPasswordLabel.setMaximumSize(new Dimension(70, 15));
                confirmPasswordLabel.setMinimumSize(new Dimension(70, 15));
                fourthRowPanel.add(confirmPasswordLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- confirmPasswordField ----
                confirmPasswordField.setPreferredSize(new Dimension(120, 30));
                confirmPasswordField.setMinimumSize(new Dimension(100, 30));
                confirmPasswordField.setMaximumSize(new Dimension(100, 30));
                confirmPasswordField.setOpaque(false);
                fourthRowPanel.add(confirmPasswordField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(fourthRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== fifthRowPanel ========
            {
                fifthRowPanel.setPreferredSize(new Dimension(510, 30));
                fifthRowPanel.setMinimumSize(new Dimension(510, 30));
                fifthRowPanel.setMaximumSize(new Dimension(510, 510));
                fifthRowPanel.setOpaque(false);
                fifthRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)fifthRowPanel.getLayout()).columnWidths = new int[] {163, 78, 0, 0};
                ((GridBagLayout)fifthRowPanel.getLayout()).rowHeights = new int[] {30, 0, 0};
                ((GridBagLayout)fifthRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)fifthRowPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                //---- uploadImageLabel ----
                uploadImageLabel.setText("Subir una imagen:");
                uploadImageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                uploadImageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                uploadImageLabel.setPreferredSize(new Dimension(120, 30));
                uploadImageLabel.setMaximumSize(new Dimension(70, 15));
                uploadImageLabel.setMinimumSize(new Dimension(70, 15));
                fifthRowPanel.add(uploadImageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- uploadImageBtn ----
                uploadImageBtn.setText("Nueva");
                fifthRowPanel.add(uploadImageBtn, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- uploadedImageLabel ----
                uploadedImageLabel.setText("Subir una imagen:");
                uploadedImageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                uploadedImageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                uploadedImageLabel.setPreferredSize(new Dimension(120, 30));
                uploadedImageLabel.setMaximumSize(new Dimension(70, 15));
                uploadedImageLabel.setMinimumSize(new Dimension(70, 15));
                fifthRowPanel.add(uploadedImageLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(fifthRowPanel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
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

            //---- createNewAirlineBtn ----
            createNewAirlineBtn.setText("+ Crear aerolinea");
            createNewAirlineBtn.setOpaque(false);
            updateBtnPanel.add(createNewAirlineBtn, BorderLayout.CENTER);
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
    // Generated using JFormDesigner Evaluation license - asd
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
    private JPanel vSpacer13;
    private JPanel secondRowPanel;
    private JLabel mailLabel;
    private JTextField mailTextField;
    private JLabel webLabel;
    private JTextField webTextField;
    private JPanel thirdRowPanel;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JPanel fourthRowPanel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;
    private JPanel fifthRowPanel;
    private JLabel uploadImageLabel;
    private JButton uploadImageBtn;
    private JLabel uploadedImageLabel;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewAirlineBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
