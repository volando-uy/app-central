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
        try {
            setBorder(null);
        } catch (Exception ignored) {
        }
    }
    
    private void initListeners() {
        createNewAirlineBtn.addActionListener(e -> {
            try {
                String nickname = nicknameTextField.getText();
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                String email = mailTextField.getText();
                String web = webTextField.getText();

                AirlineDTO airlineDTO = new AirlineDTO(nickname, name, email, description, web);

                AirlineDTO createdAirlineDTO = userController.registerAirline(airlineDTO);

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
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
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
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        vSpacer13 = new JPanel(null);
        thirdRowPanel = new JPanel();
        mailLabel = new JLabel();
        mailTextField = new JTextField();
        webLabel = new JLabel();
        webTextField = new JTextField();
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
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
        . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing
        . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
        Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
        ) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
        public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName (
        ) )) throw new RuntimeException( ); }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            InfoUserPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
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
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 380, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
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
                secondRowPanel.add(descriptionTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
            InfoUserPanel.add(vSpacer13, new GridBagConstraints(1, 4, 1, 1, 0.0, 400.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- mailLabel ----
                mailLabel.setText("Email:");
                mailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                mailLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                mailLabel.setPreferredSize(new Dimension(120, 30));
                mailLabel.setMaximumSize(new Dimension(70, 15));
                mailLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(mailLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- mailTextField ----
                mailTextField.setPreferredSize(new Dimension(120, 30));
                mailTextField.setMinimumSize(new Dimension(100, 30));
                mailTextField.setMaximumSize(new Dimension(100, 30));
                thirdRowPanel.add(mailTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- webLabel ----
                webLabel.setText("Web:");
                webLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                webLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                webLabel.setPreferredSize(new Dimension(120, 30));
                webLabel.setMaximumSize(new Dimension(70, 15));
                webLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(webLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- webTextField ----
                webTextField.setPreferredSize(new Dimension(120, 30));
                webTextField.setMinimumSize(new Dimension(100, 30));
                webTextField.setMaximumSize(new Dimension(100, 30));
                thirdRowPanel.add(webTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(thirdRowPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoUserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== updateBtnPanel ========
        {
            updateBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            updateBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            updateBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createNewAirlineBtn ----
            createNewAirlineBtn.setText("Crear nueva aerolinea");
            updateBtnPanel.add(createNewAirlineBtn, BorderLayout.CENTER);
        }
        add(updateBtnPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        add(vSpacer19, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
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
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JPanel vSpacer13;
    private JPanel thirdRowPanel;
    private JLabel mailLabel;
    private JTextField mailTextField;
    private JLabel webLabel;
    private JTextField webTextField;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewAirlineBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
