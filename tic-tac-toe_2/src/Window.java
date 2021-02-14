import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;


public class Window extends JFrame {

    private static final int WIDTH = 610;
    private static final int HEIGHT = 610;

    private final BoardHandler boardHandler;
    private State statesTree;
    private final GameHandler gameHandler;
    private final int player;
    private final int AI;
    private static boolean machinesTurnFirst = true;

    private final Panel panel;
    private BufferedImage imageBackground, imageX, imageO;
    private Point[] cells;
    private static final int DISTANCE = 100;


    private Window(BoardHandler boardHandler, State statesTree, GameHandler gameHandler, int player, int ai) {
        this.boardHandler = boardHandler;
        this.statesTree = statesTree;
        this.gameHandler = gameHandler;
        this.player = player;
        this.AI = ai;
        if (ai == 0) {
            machinesTurnFirst = false;
        }
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
    }

    private void loadCells () {
        cells = new Point[9];

        cells[0] = new Point(100, 100);
        cells[1] = new Point(305, 100);
        cells[2] = new Point(510, 100);
        cells[3] = new Point(100, 305);
        cells[4] = new Point(305, 305);
        cells[5] = new Point(510, 305);
        cells[6] = new Point(100, 510);
        cells[7] = new Point(305, 510);
        cells[8] = new Point(510, 510);
    }

    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Panel createPanel () {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }

    private void loadImages () {
        imageBackground = getImage("board");
        imageX = getImage("close");
        imageO = getImage("circle-ring");
    }

    private static BufferedImage getImage (String path) {

        BufferedImage image;

        try {
            path = "Assets" + File.separator + path + ".png";
            image = ImageIO.read(Window.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Image not loaded.");
        }

        return image;
    }

    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paintTicTacToe((Graphics2D) graphics);
        }

        private void paintTicTacToe (Graphics2D graphics2D) {
            setProperties(graphics2D);
            paintBoard(graphics2D);
            paintWinner(graphics2D);
        }

        private void setProperties (Graphics2D g) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(imageBackground, 0, 0, null);

            g.drawString("", 0, 0);
        }

        private void paintBoard (Graphics2D graphics2D) {
            if (machinesTurnFirst) {
                statesTree = gameHandler.machinesTurnForWindow(statesTree, AI);
                machinesTurnFirst = false;
            }
                Board board = statesTree.getCurrentState();
                //BoardHandler boardHandler = new BoardHandler(board.getRowSize());
                Board board1 = boardHandler.getBoardCopy(board);
                Stack<Move> moveStack = board1.getMoveStack();

                int offset = 35;

                while (!moveStack.isEmpty()) {
                    Move move = moveStack.pop();
                    if (move.getToken() == 1) {
                        graphics2D.drawImage(imageX, offset + (200 * move.getColumn()), offset + (200 * move.getRow()), null);
                    } else {
                        graphics2D.drawImage(imageO, offset + (200 * move.getColumn()), offset + (200 * move.getRow()), null);
                    }
                }

        }

        private void paintWinner (Graphics2D graphics2D) {
                graphics2D.setColor(new Color(255, 255, 255));
                graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 50));

                String s;
                Board board = boardHandler.getCurrentBoardCopy();
                boolean xWon = board.hasWon(1);
                boolean oWon = board.hasWon(0);
                boolean draw = board.hasDrawn();

                if (draw) {
                    s = "Draw";
                } else {
                    if (oWon) {
                        s = "O Wins!";
                    }
                    else if (xWon){
                        s = "X Wins!";
                    }
                    else {
                        s = "";
                    }

                }

                graphics2D.drawString(s, 300 - getFontMetrics(graphics2D.getFont()).stringWidth(s)/2, 315);

        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);
            playMove(e);

        }

        private void playMove (MouseEvent mouseEvent) {
            int move = getMove(mouseEvent.getPoint());
            if (!boardHandler.isGameOver() && move != -1) {
                    statesTree = gameHandler.humansTurnForWindow(statesTree, player, move / 3, move % 3);
                    if (boardHandler.isGameOver()) {
                        return;
                    }
                    statesTree = gameHandler.machinesTurnForWindow(statesTree, AI);
            }

            panel.repaint();
        }

        private int getMove (Point point) {

            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }

        private double distance (Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff*xDiff;
            double yDiffSquared = yDiff*yDiff;

            return Math.sqrt(xDiffSquared+yDiffSquared);
        }
    }

    public static void main(String[] args) {

        BoardHandler boardHandler = new BoardHandler(3);
        State statesTree = new State(boardHandler.getCurrentBoardCopy(), new HashSet<>(), -100);
        StateMoveBoardHandler stateMoveBoardHandler = new StateMoveBoardHandler(boardHandler);
        MoveHandler moveHandler = new MoveHandler(boardHandler);
        GameHandler gameHandler = new GameHandler(boardHandler, stateMoveBoardHandler, new Scanner(System.in));


        moveHandler.generateMoveTree(statesTree, true, 0, 1);

        int machine;
        int human;
        System.out.println("Game Mode: Player vs. AI");
        if (args[0].equals("0")) {
            machine = 1;
            human = 0;
        }
        else {
            machine = 0;
            human = 1;
        }

        SwingUtilities.invokeLater(() -> new Window(boardHandler, statesTree, gameHandler, human, machine));


    }

}
