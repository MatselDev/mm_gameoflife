package swingapplication.gui;


import swingapplication.gui.interfaces.classLinker;
import swingapplication.model.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements classLinker {

	private final static int CELLSIZE = 5;
	private final static Color bgColor = Color.BLACK;
	private final static Color fgColor = Color.GREEN;
	private final static Color gridColor = Color.BLACK;
	private final static Color[] colarray = {Color.WHITE, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.RED, Color.GREEN,
	Color.BLUE, Color.PINK, Color.LIGHT_GRAY};


	private int topBottomMargin;
	private int leftRightMargin;

	public int gameSpeed = 30;

	private World world;

	public GamePanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = (e.getY() - topBottomMargin)/CELLSIZE;
				int col = (e.getX() - leftRightMargin)/CELLSIZE;
				if(row >= world.getRows() || col >= world.getColumns()) {
					return;
				}
				boolean status = world.getCell(row, col);
				world.setCell(row, col, !status);
				repaint();

			}
		});
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->next(), 500, gameSpeed, TimeUnit.MILLISECONDS);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;

		int width = getWidth();
		int height = getHeight();

		int rows = (height - (2*topBottomMargin))/CELLSIZE;
		int columns = (width - (2*leftRightMargin))/CELLSIZE;

		leftRightMargin = ((width % CELLSIZE) + CELLSIZE)/2;
		topBottomMargin = ((height % CELLSIZE) + CELLSIZE)/2;



		if(world == null) {
			world = new World(rows, columns);
		} else if (world.getRows() != rows || world.getColumns() != columns) {
			world = new World(rows, columns);
		}

		g2.setColor(bgColor);
		g2.fillRect(0, 0, width, height);

		drawGrid(g2, width, height);

		for(int col = 0; col < columns; col++) {
			for(int row = 0; row < rows; row++) {
				boolean status = world.getCell(row, col);
				fillCell(g2, row, col, status);
			}
		}

	}

	private void fillCell(Graphics2D g2, int row, int col, boolean status) {
		Random random = new Random();
		Color color = status ? fgColor : bgColor;

		g2.setColor(color);

		int x = leftRightMargin + (col * CELLSIZE);
		int y = topBottomMargin + (row * CELLSIZE);

		g2.fillRect(x+1, y+1, CELLSIZE-2, CELLSIZE-2);
	}

	private void drawGrid(Graphics2D g2, int width, int height) {
		g2.setColor(gridColor);

		for(int x = leftRightMargin; x <= width - leftRightMargin; x += CELLSIZE) {
			g2.drawLine(x, topBottomMargin, x, height - topBottomMargin);
		}

		for(int y = topBottomMargin; y <= width - topBottomMargin; y += CELLSIZE) {
			g2.drawLine(leftRightMargin, y, width - leftRightMargin, y);
		}

	}


	public void clear() {
		world.clear();
		repaint();
	}

	public void changeSpeed(int gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public void randomize() {
		world.randomize();
		repaint();
	}

	public void next() {
		world.next();
		repaint();
	}


	public void save(File selectedfile) {
		world.getGrid(selectedfile);

	}

	public void loadGrid(File openFile) {
		world.loadGrid(openFile);
		repaint();
	}
}
