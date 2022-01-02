package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Klasa odpowiedzialna za odczytywanie paramertów poziomów z pliku txt */
public class Leveler {
    private String path;
    /** Klasa typu Properties do wyciągania wartości z pliku txt */
    static Properties proper = new Properties();

    /** Metoda wczytująca dane z pliku txt */
    public Leveler(String pathToTxT) throws IOException {
        path = pathToTxT;
        InputStream propertiesFile = new FileInputStream(path);
        proper.load(propertiesFile);
        propertiesFile.close();
    }
    /** Metoda zwracająca EnemyNumber */
    public int getEnemyNumber() { return Integer.parseInt(proper.getProperty("enemyNumber"));}
    /** Metoda zwracająca ColorEnemy */
    public String getColorEnemy() {return proper.getProperty("colorEnemy");}
    /** Metoda zwracająca enemyPoints */
    public int getEnemyPoints() { return Integer.parseInt(proper.getProperty("enemyPoints")); }
    /** Metoda zwracająca ColorBackground */
    public String getColorBackground() {return proper.getProperty("colorBackground");}
    public void setPath(String pathToTxT){
        path=pathToTxT;
    }
}