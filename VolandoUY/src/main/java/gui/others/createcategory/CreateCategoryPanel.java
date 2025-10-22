package gui.others.createcategory;

import controllers.category.ICategoryController;
import domain.dtos.category.CategoryDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

@Setter
public class CreateCategoryPanel extends JPanel {
    
    private ICategoryController categoryController;
    
    public CreateCategoryPanel(ICategoryController categoryController) {
        this.categoryController = categoryController;
        initComponents();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    
    private void initListeners() {
        createNewCategoryBtn.addActionListener(e -> {
            try {
                String name = nicknameTextField.getText();

                CategoryDTO categoryDTO = new CategoryDTO(name);

                CategoryDTO createdCategoryDTO = categoryController.createCategory(categoryDTO);

                JOptionPane.showMessageDialog(this,
                        "Nombre: " + createdCategoryDTO.getName(),
                        "Category Registered",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la categoria: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        titleLabel = new JLabel();
        InfoCategoryPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        nicknameLabel = new JLabel();
        nicknameTextField = new JTextField();
        vSpacer13 = new JPanel(null);
        updateBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createNewCategoryBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Crear nueva categoria");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //======== InfoCategoryPanel ========
        {
            InfoCategoryPanel.setOpaque(false);
            InfoCategoryPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoCategoryPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoCategoryPanel.getLayout()).rowHeights = new int[] {0, 20, 38, 0, 0, 0, 0};
            ((GridBagLayout)InfoCategoryPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoCategoryPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoCategoryPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            vSpacer17.setOpaque(false);
            InfoCategoryPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoCategoryPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setOpaque(false);
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 380, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- nicknameLabel ----
                nicknameLabel.setText("Nombre:");
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
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoCategoryPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- vSpacer13 ----
            vSpacer13.setOpaque(false);
            InfoCategoryPanel.add(vSpacer13, new GridBagConstraints(1, 4, 1, 1, 0.0, 400.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoCategoryPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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

            //---- createNewCategoryBtn ----
            createNewCategoryBtn.setText("+ Crear categoria");
            createNewCategoryBtn.setOpaque(false);
            updateBtnPanel.add(createNewCategoryBtn, BorderLayout.CENTER);
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
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JLabel titleLabel;
    private JPanel InfoCategoryPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel nicknameLabel;
    private JTextField nicknameTextField;
    private JPanel vSpacer13;
    private JPanel updateBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createNewCategoryBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
