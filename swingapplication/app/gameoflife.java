package swingapplication.app;

import swingapplication.gui.*;
import javax.swing.*;

//TODO  settable gamespeed
//TODO  pause button
//TODO  settings menu to change colours
//TODO  grid paintbrush
//TODO 	adjustable gridsize

public class gameoflife {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			new MainFrame("Mati's game of life");

		});

	}
}


