package gameLogic;

import gui.GameFrame;
import spaceInvaders.Game;

import java.io.IOException;
import java.util.Random;

/** Klasa odpowiadajaca za animacje */
public class Animation implements Runnable {

    /** Atrybut klasy Game */
    final private Game game;
    /** Atrybut klasy GameFrame */
    final private GameFrame gameFrame;
    /** Atrubut klasy Thread */
    private Thread kicker;
    /** Atrybut klasy Physics */
    private final Physics physics;

    /** Konstruktor klasy Animation */
    public Animation(GameFrame gameFrame, Game game) {
        this.gameFrame = gameFrame;
        this.game = game;
        this.gameFrame.setFocusable(true);
        this.gameFrame.setFocusTraversalKeysEnabled(false);
        this.physics = new Physics(game);
    }
    /**Metoda ustawiajaca watek */
    public void setKicker(Thread kicker) {
        this.kicker = kicker;
    }
    /** Metoda odpowiadajaca za wykonywanie sie animacji */
    @Override
    public void run() {
        GameObjectList gameEnemyList = gameFrame.getGameEnemyList();
        GameObjectList gameCannonBulletList = gameFrame.getGameCannonBulletList();
        GameObjectList gameEnemyBulletList = gameFrame.getGameEnemyBulletList();

        int counter=0;

        float dX = 0.015f;
        float dY = 0.015f;
        while (kicker == Thread.currentThread()) {
            try {Thread.sleep(60);}
            catch (InterruptedException ignore) {}
            float valueXRightEnemy = 0;
            float valueXLeftEnemy = 1;
            float widthRightEnemy = 0;
            float helpfulY = 0;
            for (MovingObject shape : gameEnemyList) {
                if(shape.getX() > valueXRightEnemy) {
                    valueXRightEnemy = shape.getX();
                    widthRightEnemy = shape.getWidth();}
                if(shape.getX() < valueXLeftEnemy) {
                    valueXLeftEnemy = shape.getX();}
            }
            if ((valueXRightEnemy + dX) >= (1f - widthRightEnemy) || valueXLeftEnemy + dX <= 0f) {
                dX = -dX;
                helpfulY = dY;
            }
            for (MovingObject shape : gameEnemyList) {
                shape.setX(shape.getX() + dX);
                shape.setY(shape.getY() + helpfulY);
            }

            for(MovingObject bullet : gameCannonBulletList) {
                float temp = (float) (bullet.getY() - 0.02);
                bullet.setY(temp);
            }

            for(MovingObject bullet : gameEnemyBulletList) {
                float temp = (float) (bullet.getY() + 0.02);
                bullet.setY(temp);
            }

            if(gameEnemyList.isEmpty()) {
                game.getLeveler().setPath(game.getConfiger().getPathLevel2());
                try {
                    game.getLeveler().getInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                if(counter==15) {
                    Random rand = new Random();
                    gameEnemyList.get(rand.nextInt(gameEnemyList.size())).fire(gameEnemyBulletList, game.getConfiger().getBulletWidth(), game.getConfiger().getBulletHeight());
                    counter = 0;
                }
            }

            gameFrame.setScore(game.getPlayer().getPoints());
            gameFrame.setLives(game.getCannon().getLives());

            gameCannonBulletList.removeIf( bullet -> ( (bullet.getY()+bullet.getHeight()) <= 0.05f) );
            gameEnemyBulletList.removeIf( bullet -> ( (bullet.getY()) >= 0.95f) );

            physics.collisionEnemy(gameEnemyList,gameCannonBulletList);
            physics.collisionCannon(game.getCannon(),gameEnemyBulletList);

            counter+=1;

            if(game.getCannon().getLives()==0) {
                game.stopAnimation();
                game.GameOver();
            }

            gameFrame.getGameCanvas().repaint();
        }
    }
}
