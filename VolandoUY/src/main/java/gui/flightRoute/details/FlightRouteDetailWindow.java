/*
 * Created by JFormDesigner on Thu Sep 04 14:52:43 UYT 2025
 */

package gui.flightRoute.details;

import java.awt.*;
import java.util.Collections;
import javax.swing.*;

import domain.dtos.flightRoute.FlightRouteDTO;

/**
 * @author Nahu
 */
public class FlightRouteDetailWindow extends JFrame {
    public FlightRouteDetailWindow(FlightRouteDTO route) {
        initComponents();

        if (route != null) {
            detalle1.setText("Ruta: " + safe(route.getName()));
            detalle2.setText("Origen: " + safe(route.getOriginCityName()));
            detalle3.setText("Destino: " + safe(route.getDestinationCityName()));
            detalle4.setText("Aerolínea: " + safe(route.getAirlineNickname()));
            detalle5.setText("Categorías: " + String.join(", ", route.getCategories() != null ? route.getCategories() : Collections.emptyList()));
            detalle6.setText("Vuelos: " + String.join(", ", route.getFlightsNames() != null ? route.getFlightsNames() : Collections.emptyList()));
        }

        setTitle("Detalle de Ruta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private String safe(String text) {
        return text != null ? text : "";
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        detalle1 = new JLabel();
        detalle2 = new JLabel();
        detalle3 = new JLabel();
        detalle4 = new JLabel();
        detalle5 = new JLabel();
        detalle6 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(detalle1);
        detalle1.setBounds(40, 15, 300, 40);
        contentPane.add(detalle2);
        detalle2.setBounds(40, 60, 300, 40);
        contentPane.add(detalle3);
        detalle3.setBounds(40, 105, 300, 40);
        contentPane.add(detalle4);
        detalle4.setBounds(40, 150, 300, 40);
        contentPane.add(detalle5);
        detalle5.setBounds(40, 195, 300, 40);
        contentPane.add(detalle6);
        detalle6.setBounds(40, 240, 300, 40);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JLabel detalle1;
    private JLabel detalle2;
    private JLabel detalle3;
    private JLabel detalle4;
    private JLabel detalle5;
    private JLabel detalle6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}