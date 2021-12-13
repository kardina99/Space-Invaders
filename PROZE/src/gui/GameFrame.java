package gui;
import gameLogic.GameObjectList;
import spaceInvaders.Game;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Klasa odpowiadajaca za okno gry */
public class GameFrame extends Frame {

    private final Game game;
    private final GameObjectList gameObjectList;
    private final GameCanvas gameCanvas;

    /**
     * Metoda zaracająca obiekt klasy GameCanvas
     */
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    /**
     * Konstruktor klasy GameFrame
     */
    public GameFrame(Game game) {
        super("Space Invaders");
        this.game = game;
        gameObjectList = new GameObjectList();

        gameObjectList.add(new MyShape(0.05f, 0.1f, 0.05f, 0.05f, Color.green));
        gameObjectList.add(new MyShape(0.1f, 0.1f, 0.05f, 0.05f, Color.red));
        gameObjectList.add(new MyShape(0.15f, 0.1f, 0.05f, 0.05f, Color.blue));

        setLayout(new BorderLayout());

        final int[] points = {0};

        Panel panelTop = new Panel(new BorderLayout());
        Panel panelButton = new Panel(new FlowLayout());
        Panel panelBottom = new Panel(new BorderLayout());
        Panel panelCanvas = new Panel(new BorderLayout());
        Panel panelPoints = new Panel(new FlowLayout());
        Panel panelLives = new Panel(new FlowLayout());

        Label pointsLabel = new Label("Points:");
        Label pointsAmount = new Label(Integer.toString(points[0]));
        Label livesLabel = new Label("Lives left:");
        Label livesAmount = new Label("0");

        Button pauseButton = new Button("Start");
        Button exitButton = new Button("Exit");

        exitButton.addActionListener(e -> System.exit(1));
        pauseButton.addActionListener(e -> {
            if (game.getAnimation() == null) {
                game.startAnimation();
                pauseButton.setLabel("Pause");
            } else {
                game.stopAnimation();
                pauseButton.setLabel("Start");
            }
            pack();
        });

        panelButton.add(pauseButton);
        panelButton.add(exitButton);

        panelPoints.add(pointsLabel);
        panelPoints.add(pointsAmount);

        panelLives.add(livesLabel);
        panelLives.add(livesAmount);

        panelTop.add(panelButton, BorderLayout.EAST);
        panelTop.add(panelPoints, BorderLayout.WEST);

        panelCanvas.add(gameCanvas = new GameCanvas(Color.blue, gameObjectList), BorderLayout.CENTER);

        panelBottom.add(panelLives, BorderLayout.WEST);

        add(panelTop, BorderLayout.NORTH);
        add(panelCanvas, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameCanvas.setPreferredSize(gameCanvas.getSize());
                System.out.println("componentResized");
            }
        });

        pack();
    }
/** Metoda zwracajaca obiekt klasy GameObjectList */
    public GameObjectList getGameObjectList() { return gameObjectList; }
}