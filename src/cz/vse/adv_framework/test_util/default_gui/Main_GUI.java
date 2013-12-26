/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IUIG;
import cz.vse.adv_framework.test_util.default_game.gameg.DefaultGame;





/*******************************************************************************
 * The class {@code Main_GUI} is the main class of the project
 * that ...
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Main_GUI
{
    /***************************************************************************
     * Method starting the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        IUIG   gui  = DefaultGUI.getInstance();
        IGameG game = DefaultGame.getInstance();
        gui.startGame(game);
    }


    /** Private constructor preventing instance creation.*/
    private Main_GUI() {}
}
