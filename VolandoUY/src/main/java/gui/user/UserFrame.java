package gui.user;

import controllers.user.IUserController;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import gui.user.registerUser.CustomerFormEvent;
import gui.user.registerUser.CustomerFormListener;
import gui.user.registerUser.RegisterUserContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

public class UserFrame extends JPanel {

    private MouseListener registerUserPanelListener;
    private MouseListener updateUserPanelListener;
    private MouseListener getUserPanelListener;

    private IUserController userController;
    
    private JPanel contentPanel;

    public UserFrame(IUserController uController) {
        userController = uController;
        initComponents();
        initListeners();
    }

    private void initListeners() {
        registerUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Register User button clicked");
                // Clear the current content panel and add the RegisterUserContent
                if (contentPanel != null) {
                    remove(contentPanel);
                }
                contentPanel = createRegisterUserContent();
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        updateUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Update User button clicked");
                // Implement the logic to update a user
            }
        };

        getUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Get User button clicked");
                // Implement the logic to get a user
            }
        };
        button1.addMouseListener(registerUserPanelListener);
        button2.addMouseListener(updateUserPanelListener);
        //button3.addMouseListener(getUserListener);
    }

    private void registerUserListener() {

    }

    private JPanel createRegisterUserContent() {
        RegisterUserContent registerUserContent = new RegisterUserContent();
        registerUserContent.setCustomerFormListener(e -> {
            String nickname = e.getNickname();
            String name = e.getName();
            String surname = e.getSurname();
            String mail = e.getMail();
            String citizenId = e.getCitizenship();
            LocalDate birthDate = e.getBirthDate();
            String id = e.getId();
            EnumTipoDocumento idType = e.getIdType();

            System.out.println("Registering user with details: " +
                    "\nNickname: " + nickname +
                    "\nName: " + name +
                    "\nSurname: " + surname +
                    "\nMail: " + mail +
                    "\nCitizen ID: " + citizenId +
                    "\nBirth Date: " + birthDate +
                    "\nID: " + id +
                    "\nID Type: " + idType);

            CustomerDTO customerDTO = new CustomerDTO(
                    nickname,
                    name,
                    surname,
                    mail,
                    citizenId,
                    birthDate,
                    id,
                    idType
            );

            userController.registerCustomer(customerDTO);

        });
        return registerUserContent;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(320, 300));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0x2a406c));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing
        . border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder
        . CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .
        awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder( )) )
        ;  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} )
        ;
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x333333));

            //---- button1 ----
            button1.setText("text");

            //---- button2 ----
            button2.setText("text");

            GroupLayout NavPanelLayout = new GroupLayout(NavPanel);
            NavPanel.setLayout(NavPanelLayout);
            NavPanelLayout.setHorizontalGroup(
                NavPanelLayout.createParallelGroup()
                    .addGroup(NavPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(button1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(320, Short.MAX_VALUE))
            );
            NavPanelLayout.setVerticalGroup(
                NavPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, NavPanelLayout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(NavPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(button1)
                            .addComponent(button2))
                        .addContainerGap())
            );
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

