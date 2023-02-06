package swingapplication.gui;

import swingapplication.gui.interfaces.classLinker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar {
	private classLinker randomLink;
	private classLinker clearLink;

	public Toolbar() {

		setLayout(new BorderLayout());
		JButton randomiser = new JButton("Randomise");
		JButton clearer = new JButton ("Clear");

		clearer.addActionListener(e-> clearLink.clear());
		randomiser.addActionListener(e-> randomLink.randomize());
		add(randomiser, BorderLayout.EAST);
		add(clearer, BorderLayout.WEST);
	}

	public void setRandomizer(classLinker randomizer) {
		this.randomLink = randomizer;
	}

	public void setClearer(classLinker clearer) {
		this.clearLink = clearer;
	}
}
