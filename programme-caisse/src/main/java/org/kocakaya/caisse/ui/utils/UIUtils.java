package org.kocakaya.caisse.ui.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.JTextComponent;

import org.kocakaya.caisse.utils.ValidatorUtils;

public class UIUtils {

    public static List<JTextComponent> checkIsNullOrEmpty(JTextComponent... components) {
	List<JTextComponent> invalidComponents = new ArrayList<>();
	for (JTextComponent component : components) {
	    if (component.getText() == null || component.getText().equals("")) {
		invalidComponents.add(component);
	    }
	}
	return invalidComponents;
    }
    
    public static List<JTextComponent> checkIsNumeric(JTextComponent... components){
	List<JTextComponent> invalidComponents = new ArrayList<>();
	for (JTextComponent component : components){
	    if (!ValidatorUtils.validateIsNumericWithFrenchFormat(component.getText())){
		invalidComponents.add(component);
	    }
	}
	return invalidComponents;
    }

    public static Color classicGrayColorForCalculatedValue() {
	return new Color(224, 224, 224);
    }
    
    public static Color colorByAmount(Double amount){
	if (amount < 0){
	    return Color.RED;
	} else if (amount == 0) {
	    return Color.BLACK;
	} else{
	    return Color.BLUE;
	}
    }
}