package gui.others;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.user.IUserController;
import gui.others.createCategory.CreateCategoryPanel;
import gui.others.createCity.CreateCityPanel;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;
import gui.user.updateUser.UpdateUserPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OtherPanel extends JPanel {

    private MouseListener createCategoryPanelListener;
    private MouseListener createCityPanelListener;

    private ICategoryController categoryController;
    private ICityController cityController;
    
    private JPanel contentPanel;

    public OtherPanel(ICategoryController categoryController, ICityController cityController) {
        this.categoryController = categoryController;
        this.cityController = cityController;
        initComponents();
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        // Listener para el botón de registro de categorías
        createCategoryPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo CreateCategoryPanel
                    if (contentPanel instanceof CreateCategoryPanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Create Category button clicked");
                // Crear el nuevo contentPanel con el contenido de creación de categoría
                contentPanel = new CreateCategoryPanel(categoryController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        // Listener para el botón de registro de aerolínea
        createCityPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo CreateCategoryPanel
                    if (contentPanel instanceof RegisterAirlinePanel) {
                        return;
                    }
                    remove(contentPanel);
                }
                System.out.println("Create City button clicked");
                // Crear el nuevo contentPanel con el contenido de registro de aerolínea
                contentPanel = new CreateCityPanel(cityController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        createCategoryBtn.addMouseListener(createCategoryPanelListener);
        createCityBtn.addMouseListener(createCityPanelListener);
    }
  
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton createCityBtn;
    private JButton createCategoryBtn;
    private JButton noBtn1;
    private JButton noBtn2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        createCityBtn = new JButton();
        createCategoryBtn = new JButton();
        noBtn1 = new JButton();
        noBtn2 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border
        .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax. swing .border . TitledBorder. CENTER ,javax
        . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,
        12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans
        .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er" .equals ( e.
        getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- createCityBtn ----
            createCityBtn.setText("Crear Ciudad");
            NavPanel.add(createCityBtn);

            //---- createCategoryBtn ----
            createCategoryBtn.setText("Crear Categoria");
            NavPanel.add(createCategoryBtn);

            //---- noBtn1 ----
            noBtn1.setText("-----");
            NavPanel.add(noBtn1);

            //---- noBtn2 ----
            noBtn2.setText("-----");
            NavPanel.add(noBtn2);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

