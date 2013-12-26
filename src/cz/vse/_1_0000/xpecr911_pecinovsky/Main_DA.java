/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr911_pecinovsky;

import java.awt.Component;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



/*******************************************************************************
 * Třída {@code Main_DA} je hlavní třídou projektu s textovou hrou.
 * Spouští komunikaci s uživatelem, který si chce zahrát hru.
 * <p />
 * Oproti třídě {@code Main_D} se liší tím, že třídy objektů
 * zprostředkovávajících komunikaci jsou definovány jako anonymní.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Main_DA
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================

    private static IDialogTool dialogTool;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Metoda spouštějící aplikaci a tím i komunikaci s uživatelem.
     *
     * @param args Parametry příkazového řádku ovlivňující způsob komunikace:
     *             <br/>
     * <b><tt>-con</tt></b> spouští komunikaci prostřednictvím
     *    standardního vstupu a výstupu (konzole)
     * <b><tt>-jop</tt></b> nebo nezadání žádného parametru spouští komunikaci
     *    prostřednictvím statických metod třídy {@link JOptionPane}.
     */
    public static void main(String[] args)
    {
        //Nebylo-li zadáno nic, tváříme se, že bylo zadáno -jop
        if (args.length == 0) {
            args = new String[] { "-con" };
        }

        switch (args[0])
        {
            case "-con":
                dialogTool = new IDialogTool()
                {
                    Scanner  scanner = new Scanner(System.in);

                    /** Pošle uživateli zadanou zprávu
                     *  a převezme od něj další příkaz. */
                    @Override public String askCommand(String message) {
                        sendMessage(message);
                        return scanner.nextLine();
                    }

                    /** Pošle uživateli zadanou zprávu. */
                    @Override public void sendMessage(String message) {
                        System.out.println(message);
                    }

                    /** Zjistí binární (Ano/Ne) odpověď uživatele
                     *  na zadanou otázku. */
                    @Override public boolean wantContinue() {
                        String answer = askCommand(
                                        "Chcete si zahrát ještě jednou (A/N)?");
                        answer = answer.trim().toUpperCase();
                        return (answer.charAt(0) == 'A');
                    }
                };
                break;

            case "-jop":
                dialogTool = new IDialogTool()
                {
                    Component PARENT;

                    /** Inicializační blok vytvoří rodičovskou
                     *  komponentu definující umístění dialogových oken.
                     *  V anonymních třídách je to jediná způsob,
                     * jak definovat kód konstruktoru. */
                    {
                        PARENT = new JFrame();
                        PARENT.setLocation(100, 100);
                        PARENT.setVisible(true);
                    }

                    /** Pošle uživateli zadanou zprávu
                     *  a převezme od něj další příkaz. */
                    @Override public String askCommand(String message) {
                        return JOptionPane.showInputDialog(PARENT, message);
                    }

                    /** Pošle uživateli zadanou zprávu. */
                    @Override public void sendMessage(String message) {
                        JOptionPane.showMessageDialog(PARENT, message);
                    }

                    /** Zjistí binární (Ano/Ne) odpověď uživatele
                     *  na zadanou otázku. */
                    @Override public boolean wantContinue() {
                        int answer = JOptionPane.showConfirmDialog(PARENT,
                                     "Chcete si zahrát ještě jednou?");
                        return (answer == 0);
                    }
                };
                break;

            default:
                throw new IllegalArgumentException(
                        "\nZadaná volba není podporována: " + args[0]);
        }

        //Spustí komunikaci s uživatelem prostřednictvím zadaného prostředku
        dialog(dialogTool);

        System.exit(0);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    private Main_DA()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Komunikuje s uživatelem prostřednictvím zadaného prostředku.
     *
     * @param dialogTool Prostředek pro komunikaci s uživatelem
     */
    private static void dialog(IDialogTool dialogTool)
    {
        do {
            playGame(dialogTool);
        } while(dialogTool.wantContinue());
    }


    /***************************************************************************
     * Komunikuje s uživatelem prostřednictvím zadaného prostředku.
     *
     * @param dialogTool Prostředek pro komunikaci s uživatelem
     */
    private static void playGame(IDialogTool dialogTool)
    {
        DemoGame game    = DemoGame.getInstance();
        String   command = "";

        for (;;) {
            String answer = game.executeCommand(command);
            if (! game.isAlive()) {
                dialogTool.sendMessage(answer);
                break;
            }
            command = dialogTool.askCommand(answer);
        }
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Instance interfejsu {@code IDialog} představují prostředky,
     * které je možno využít ke komunikaci s uživatelem.
     */
    private static interface IDialogTool
    {
        /** Pošle uživateli zadanou zprávu a převezme od něj další příkaz.
         * @param message Posílaná zpráva
         * @return Uživatelem zadaný příkaz
         */
        public String askCommand (String message);

        /** Pošle uživateli zadanou zprávu.
         * @param message Posílaná zpráva
         */
        public void sendMessage(String message);

        /** Zjistí, jestli si chce uživatel zahrát ještě jednou.
         * @return Chce-li si uživatel znovu zahrát, vrátí {@code true},
         *         jinak vrátí {@code false}
         */
        public boolean wantContinue();
    }



//== TESTING CLASSES AND METHODS ===============================================
}
