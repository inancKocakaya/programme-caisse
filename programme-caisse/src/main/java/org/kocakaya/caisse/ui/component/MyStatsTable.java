package org.kocakaya.caisse.ui.component;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MyStatsTable extends JTable {

    private static final long serialVersionUID = 1L;

    private String tableType;

    public static final String DAY_STATS = "day";
    public static final String MONTH_STATS = "month";
    public static final String YEAR_STATS = "year";

    public static final String TR_JOUR = "tr_jour";
    public static final String CUMUL_TR = "cumul_tr";

    public MyStatsTable(TableModel dm, String tableType) {
	super(dm);
	this.tableType = tableType;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }

    public String getTableType() {
	return tableType;
    }

    public void setTableType(String tableType) {
	this.tableType = tableType;
    }
}
