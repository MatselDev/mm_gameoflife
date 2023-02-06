package swingapplication.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class MainFrame extends JFrame {

	private GamePanel gamepanel = new GamePanel();
	private Toolbar toolbar = new Toolbar();
	private static final String defaultFileName = "gameoflife.gol";

	public MainFrame(String name) {
		super("mati's game of life");

		setLayout(new BorderLayout());
		add(gamepanel, BorderLayout.CENTER);
		add(toolbar, BorderLayout.NORTH);

		MenuItem openItem = new MenuItem("Open file");
		MenuItem saveItem = new MenuItem("Save");
		Menu fileMenu = new Menu("File");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		MenuBar menuBar = new MenuBar();
		setMenuBar(menuBar);
		menuBar.add(fileMenu);

		JFileChooser fileChooser = new JFileChooser();
		var filter = new FileNameExtensionFilter("Game of life files", "gol");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);

		openItem.addActionListener(e -> {
			int userOpen = fileChooser.showOpenDialog(MainFrame.this);

			if(userOpen == JFileChooser.APPROVE_OPTION) {
				File openFile = fileChooser.getSelectedFile();
				gamepanel.clear();
				gamepanel.loadGrid(openFile);
//

			}
		});

		saveItem.addActionListener(e -> {
			fileChooser.setSelectedFile(new File(defaultFileName));
			int userOption = fileChooser.showSaveDialog(MainFrame.this);
			if(userOption == JFileChooser.APPROVE_OPTION) {
				File selectedfile = fileChooser.getSelectedFile();
				gamepanel.save(selectedfile);
			}
		});

		toolbar.setRandomizer(gamepanel);
		toolbar.setClearer(gamepanel);

		MenuItem openitem = new MenuItem("Open");

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch(code) {
					case 32:
						System.out.println("Space pressed");
						gamepanel.next();
						break;
					case 8:
						System.out.println("Backspace pressed");
						gamepanel.clear();
						break;

					case 10:
						System.out.println("enter pressed");
						gamepanel.randomize();
						break;
				}
				//32 - space
				// 10 - enter
				//  8 backspace
			}
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);
		setVisible(true);

	}
}
