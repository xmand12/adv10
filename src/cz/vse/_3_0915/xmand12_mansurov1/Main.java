
package cz.vse._3_0915.xmand12_mansurov1;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*******************************************************************************
 Třída {@code Main} je hlavní třídou projektu s textovou hrou.
 * Spouští komunikaci s uživatelem, který si chce zahrát hru.
 * Zobecní metodu pro komunikaci s uživatelem,
 * která se nyní po skončení hry uživatele zeptá,
 * jestli si nechce zahrát ještě jendou, a pokud chce,
 * tak spustí hru znovu.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public class Main
{
    /***************************************************************************
     * Metoda strtujici aplikaci
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Aplikace byla spuštěna");
        if (args.length == 0) {
            winIO();
        } else {
            switch (args[0]) {
                case "-win":
                    winIO();
                    break;

                default:
                    throw new RuntimeException(
                            "\nNeznámý argument " + args[0]);
            }
        }
        System.out.println("Aplikace byla dokončena");
        System.exit(0);
    }


    /**
     * Metoda zprostředkovávající komunikaci s uživatelem
     * prostřednictvím oken.
     * @throws HeadlessException
     */
    private static void winIO() throws HeadlessException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(-1000, 500);
        frame.setVisible(true);

        Game game = Game.getInstance();
        String command = "";
        for (;;) {

            String answer = game.executeCommand(command);
            System.out.println(answer);
            if (!game.isAlive()) {
                JOptionPane.showMessageDialog(frame, answer);
                break;
            }
            command = JOptionPane.showInputDialog(frame, answer);
        }
    }

    /** Private constructor preventing instance creation.*/
    private Main() {}
}
