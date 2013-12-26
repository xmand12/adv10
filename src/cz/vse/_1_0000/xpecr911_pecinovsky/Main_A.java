/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr911_pecinovsky;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Třída {@code Main_A} je hlavní třídou projektu s textovou hrou.
 * Spouští komunikaci s uživatelem, který si chce zahrát hru.
 * <p />
 * Komunikace probíhá prostřednictvím statických metod
 * třídy {@link JOptionPane}.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Main_A
{
    private static final Component PARENT = new JFrame();
    static {
        PARENT.setLocation(100, 100);
        PARENT.setVisible(true);
    }



    /***************************************************************************
     * Metoda spouštějící aplikaci a tím i komunikaci s uživatelem.
     * Komunikace probíhá prostřednictvím statických metod
     * třídy {@link JOptionPane}.
     *
     * @param args Parametry příkazového řádku – nepoužívají se
     */
    public static void main(String[] args)
    {
        DemoGame game    = DemoGame.getInstance();
        String   command = "";

        for (;;) {
            String answer = game.executeCommand(command);
            if (! game.isAlive()) {
                JOptionPane.showMessageDialog(PARENT, answer);
                break;
            }
            command = JOptionPane.showInputDialog(PARENT, answer);
        }
        System.exit(0);
    }



    /** Private constructor preventing instance creation.*/
    private Main_A() {}
}
