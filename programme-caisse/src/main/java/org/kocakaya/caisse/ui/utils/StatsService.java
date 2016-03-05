package org.kocakaya.caisse.ui.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.kocakaya.caisse.utils.DateUtils;
import org.kocakaya.caisse.utils.DoubleUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StatsService {

    public List<String[]> cumulCBData() {
	List<String[]> data = new ArrayList<>();
	String[] daysArray = daysOfCurrentMonth();

	for (int i = 0; i < daysArray.length; i++) {
	    String[] row = new String[4];
	    row[0] = daysArray[i];
	    row[1] = "";
	    row[2] = "";
	    row[3] = DoubleUtils.doubleToString(delta(DoubleUtils.stringToDouble(row[1]), DoubleUtils.stringToDouble(row[2])));
	}
	return data;
    }

    public String[] daysOfCurrentMonth() {
	Calendar cal = Calendar.getInstance();
	int nbOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	String[] array = new String[nbOfDays];

	for (int i = 1; i <= nbOfDays; i++) {
	    cal.set(Calendar.DAY_OF_MONTH, i);
	    array[i - 1] = DateUtils.formatDateWithFrenchFormat(cal.getTime());
	}
	return array;
    }

    private double delta(double calculatedAmount, double recoltedAmount) {
	return recoltedAmount - calculatedAmount;
    }
}
