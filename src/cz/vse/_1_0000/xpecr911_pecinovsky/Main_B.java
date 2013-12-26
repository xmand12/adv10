/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr911_pecinovsky;

import java.awt.Component;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Třída {@code Main_B} je hlavní třídou projektu s textovou hrou.
 * Spouští komunikaci s uživatelem, který si chce zahrát hru.
 * <p />
 * Oproti třídě {@code Main_A} umožňuje specifikovat,
 * jakým způsobem bude aplikace s uživatelem komunikovat.
 * Prostřednictvím parametrů příkazového řádku můžeme zadat,
 * zda bude komunikace probíhat prostřednictvím standardního vstupu a výstupu,
 * anebo prostřednictvím statických metod třídy {@link JOptionPane}.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Main_B
{
    /***************************************************************************
     * Metoda spouštějící aplikaci a tím i komunikaci s uživatelem.
     *
     * @param args Parametry příkazového řádku ovlivňující způsob komunikace:
     *             <br/>
     * <b><tt>-text</tt></b> spouští komunikaci prostřednictvím
     *    standardního vstupu a výstupu
     * <b><tt>-jop</tt></b> nebo nezadání žádného parametru spouští komunikaci
     *    prostřednictvím statických metod třídy {@link JOptionPane}.
     */
    public static void main(String[] args)
    {
        //Nebylo-li zadáno nic, tváříme se, že bylo zadáno -jop
        if (args.length == 0) {
            args = new String[] { "-jop" };
        }

        switch (args[0])
        {
            case "-con":
                conDialog();
                break;

            case "-jop":
                jopDialog();
                break;

            default:
                throw new IllegalArgumentException(
                        "\nZadaná volba není podporována: " + args[0]);
        }
        System.exit(0);
    }


    /***************************************************************************
     * Komunikuje s uživatelem prostřednictvím standardního
     * (konzolového) vstupu a výstupu.
     */
    static void conDialog()
    {
        Scanner  scanner = new Scanner(System.in);

        DemoGame game    = DemoGame.getInstance();
        String   command = "";

        for (;;) {
            String answer = game.executeCommand(command);
            if (! game.isAlive()) {
                System.out.println(answer);
                break;
            }
            System.out.println(answer);
            command =  scanner.nextLine();
        }
    }


    /***************************************************************************
     * Komunikuje s uživatelem prostřednictvím statických metod
     * třídy {@link JOptionPane}.
     */
    static void jopDialog()
    {
        Component PARENT = new JFrame();
        PARENT.setLocation(100, 100);
        PARENT.setVisible(true);

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
    }


    /** Private constructor preventing instance creation.*/
    private Main_B() {}
}
