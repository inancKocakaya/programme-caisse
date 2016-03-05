package org.kocakaya.caisse.ui.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StateMessageLabelBuilder {

    private JLabel label = new JLabel();
    private int duration = 0;
    private Color color = failColor();
    private String text = "";
    private boolean isVisible = false;

    public StateMessageLabelBuilder withLabel(JLabel label) {
	this.label = label;

	return this;
    }

    public StateMessageLabelBuilder withText(String text) {
	this.text = text;

	return this;
    }

    public StateMessageLabelBuilder withVisibilty(boolean isVisible) {
	this.isVisible = isVisible;

	return this;
    }

    public StateMessageLabelBuilder withState(MessageType messageType) {
	if (MessageType.SUCCESS.equals(messageType)) {
	    this.color = successColor();
	}
	return this;
    }

    public StateMessageLabelBuilder withVisibilityInSeconds(int duration) {
	this.duration = duration;

	return this;
    }

    public void start() {
	label.setText(text);
	Font font = label.getFont();
	label.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
	label.setForeground(color);
	label.setVisible(isVisible);
	Timer t = new Timer(duration, new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		label.setVisible(!isVisible);
	    }
	});
	t.setRepeats(false);
	t.start();
    }

    public static StateMessageLabelBuilder create() {
	return new StateMessageLabelBuilder();
    }

    public JLabel get() {
	return this.label;
    }

    private static Color failColor() {
	return new Color(230, 0, 0);
    }

    private static Color successColor() {
	return new Color(0, 202, 0);
    }
}
