package org.kocakaya.caisse.ui.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitleMessageBuilder {

    private JLabel label = new JLabel();
    private String text = "";
    private boolean texUppercase = false;

    public static TitleMessageBuilder create() {
	return new TitleMessageBuilder();
    }

    public TitleMessageBuilder withText(String text) {
	this.text = text;

	return this;
    }

    public TitleMessageBuilder withTextToUpperCase() {
	texUppercase = true;

	return this;
    }

    public JLabel get() {
	if (texUppercase) {
	    text = text.toUpperCase();
	}
	label.setText(text);
	label.setForeground(standardColorForTitle());
	label.setFont(new Font("Serif", Font.PLAIN, 26));
	label.setHorizontalAlignment(SwingConstants.CENTER);
	return this.label;
    }

    private Color standardColorForTitle() {
	return new Color(0, 0, 255);
    }
}
