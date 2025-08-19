/*
 * Created by JFormDesigner on Tue Aug 19 13:19:30 UYT 2025
 */

package gui.user.registerUser;

import domain.models.user.enums.EnumTipoDocumento;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author ignac
 */
@Setter
public class RegisterUserContent extends JPanel {

    private CustomerFormListener customerFormListener;

    public RegisterUserContent() {
        initComponents();
        initListeners();
    }

    private void initListeners() {
        button1.addActionListener(e -> {

            // Extraer estos detalles de las labels y demas, estos los generé para testear
            String nickname = "Nuevo cliente" + System.currentTimeMillis();
            String name = "asd";
            String surname = "asd";
            String mail = "asd@gmail.com";
            String citizenId = "asd";
            LocalDate birthDate = LocalDate.of(2000, 1, 1);
            String id = "asd";
            EnumTipoDocumento idType = EnumTipoDocumento.CI;

            CustomerFormEvent ev = new CustomerFormEvent(this, nickname, name, surname, mail, citizenId, birthDate, id, idType);
            if (customerFormListener != null) {
                customerFormListener.formEventOccurred(ev);
            } else {
                System.out.println("No listener registered for customer form events.");
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        button1 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 740));
        setMinimumSize(new Dimension(640, 740));
        setMaximumSize(new Dimension(640, 740));
        setBackground(new Color(0xcc6600));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
        javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax
        .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
        .awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt
        .Color.red), getBorder())); addPropertyChangeListener(new java.beans.
        PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".
        equals(e.getPropertyName()))throw new RuntimeException();}});

        //---- button1 ----
        button1.setText("Crear nuevo cliente");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(120, 120, 120)
                    .addComponent(button1)
                    .addContainerGap(123, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(133, 133, 133)
                    .addComponent(button1)
                    .addContainerGap(133, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
