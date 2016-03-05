package org.kocakaya.caisse.ui.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.kocakaya.caisse.utils.ValidatorUtils;

public class MySalesTable extends JTable {

    private static final long serialVersionUID = 1L;

    public MySalesTable(TableModel dm) {
	super(dm);
    }

    public void alignData() {
	DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
	defaultTableCellRenderer.setHorizontalAlignment(JLabel.LEFT);
	for (int i = 0; i < this.getColumnCount(); i++) {
	    this.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
	}
    }

    public void addRow() {
	MyDefaultModelTable model = (MyDefaultModelTable) this.getModel();
	model.addRow(new String[model.columnsName.length]);
    }

    public void removeRow() {
	MyDefaultModelTable model = (MyDefaultModelTable) this.getModel();
	int selectedRow = this.getSelectedRow();
	if (selectedRow != -1) {
	    model.removeRow(this.getSelectedRow());
	}
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	Component component = super.prepareRenderer(renderer, row, column);
	   String value = (String) getModel().getValueAt(row, column + 1);
	if (value != null && !"".equals(value)) {
	    if (ValidatorUtils.validateIsNumericWithFrenchFormat(value)) {
		component.setBackground(getAppropriateColor(component, row));
	    } else {
		component.setBackground(Color.PINK);
		component.setForeground(Color.BLACK);
	    }
	} else {
	    component.setBackground(getAppropriateColor(component, row));
	}
	return component;
    }

    private Color getAppropriateColor(Component component, int row) {
	if (isRowSelected(row)) {
	    return selectedColor();
	} else {
	    if (row % 2 == 0) {
		return evenColor();
	    } else {
		return oddColor();
	    }
	}
    }

    // Pair
    private Color evenColor() {
	return new Color(255, 255, 255);
    }

    private Color oddColor() {
	return new Color(242, 242, 242);
    }

    private Color selectedColor() {
	return new Color(57, 105, 138);
    }
}
