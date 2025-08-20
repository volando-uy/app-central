package gui.user.registerAirline;

import controllers.user.IUserController;
import domain.dtos.user.AirlineDTO;
import lombok.Setter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@Setter
public class RegisterAirlinePanel extends JPanel {
    
    IUserController userController;
    
    public RegisterAirlinePanel(IUserController uController) {
        userController = uController;

        initComponents();
        // initComponentsManually();
        initListeners();
    }
    
    private void initListeners() {
        createNewAirlineBtn.addActionListener(e -> {
            try {
                String nickname = nicknameTextField.getText();
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                String email = emailTextField.getText();
                String web = webTextLabel.getText();

                AirlineDTO airlineDTO = new AirlineDTO(nickname, name, email, description, web);

                AirlineDTO createdAirlineDTO = userController.registerAirline(airlineDTO);

                JOptionPane.showMessageDialog(this,
                        "Nickname: " + createdAirlineDTO.getNickname() +
                                "\nName: " + createdAirlineDTO.getName() +
                                "\nEmail: " + createdAirlineDTO.getMail() +
                                "\nDescription: " + createdAirlineDTO.getDescription() +
                                (web.isEmpty() ? ("\nWeb: " + createdAirlineDTO.getWeb()) : ""),
                        "User Registered",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la aerolínea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        createNewAirlineBtn = new JButton();
        nicknameLabel = new JLabel();
        nicknameTextField = new JTextField();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        descriptionTextField = new JTextField();
        descriptionLabel = new JLabel();
        emailLabel = new JLabel();
        emailTextField = new JTextField();
        webLabel = new JLabel();
        webTextLabel = new JTextField();

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
        EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing
        .border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))
        throw new RuntimeException();}});

        //---- createNewAirlineBtn ----
        createNewAirlineBtn.setText("Crear nueva aerolinea");

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

        //---- descriptionTextField ----
        descriptionTextField.setPreferredSize(new Dimension(100, 30));
        descriptionTextField.setMinimumSize(new Dimension(100, 30));

        //---- descriptionLabel ----
        descriptionLabel.setText("Descripci\u00f3n:");

        //---- emailLabel ----
        emailLabel.setText("Email:");

        //---- emailTextField ----
        emailTextField.setPreferredSize(new Dimension(100, 30));
        emailTextField.setMinimumSize(new Dimension(100, 30));

        //---- webLabel ----
        webLabel.setText("Web:");

        //---- webTextLabel ----
        webTextLabel.setPreferredSize(new Dimension(100, 30));
        webTextLabel.setMinimumSize(new Dimension(100, 30));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(235, 235, 235)
                            .addComponent(createNewAirlineBtn))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(126, 126, 126)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(descriptionLabel)
                                    .addGap(6, 6, 6)
                                    .addComponent(descriptionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(nicknameLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nicknameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(webLabel)
                                    .addGap(6, 6, 6)
                                    .addComponent(webTextLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addGap(73, 73, 73)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(nameLabel)
                                    .addGap(6, 6, 6)
                                    .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(emailLabel)
                                    .addGap(6, 6, 6)
                                    .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
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
                        .addComponent(descriptionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(descriptionLabel)
                                .addComponent(emailLabel))))
                    .addGap(40, 40, 40)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(webTextLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(webLabel)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
                    .addComponent(createNewAirlineBtn)
                    .addGap(30, 30, 30))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JButton createNewAirlineBtn;
    private JLabel nicknameLabel;
    private JTextField nicknameTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JLabel descriptionLabel;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel webLabel;
    private JTextField webTextLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
