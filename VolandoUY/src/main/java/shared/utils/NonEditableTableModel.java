package shared.utils;

import javax.swing.table.DefaultTableModel;

//como se usa en varias tablas, para no repetir código lo dejo en una clase asi despues llamamos nomas
public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // clickea la tabla pero nunca las pueden editar
    }
}
