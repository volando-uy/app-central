package gui.user;

import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.*;

public class UserFrame extends JPanel {
    public UserFrame() {
        initComponents();
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton button1;
    private JButton button2;
    private JPanel ContentPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        ContentPanel = new JPanel();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(320, 300));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0x2a406c));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border
        .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax
        . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,
        12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans
        .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e.
        getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(200, 30));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 30));
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

        //======== ContentPanel ========
        {
            ContentPanel.setPreferredSize(new Dimension(200, 200));
            ContentPanel.setMinimumSize(new Dimension(200, 200));
            ContentPanel.setBackground(new Color(0x2a406c));

            GroupLayout ContentPanelLayout = new GroupLayout(ContentPanel);
            ContentPanel.setLayout(ContentPanelLayout);
            ContentPanelLayout.setHorizontalGroup(
                ContentPanelLayout.createParallelGroup()
                    .addGap(0, 640, Short.MAX_VALUE)
            );
            ContentPanelLayout.setVerticalGroup(
                ContentPanelLayout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
        }
        add(ContentPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

