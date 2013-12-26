/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



/*******************************************************************************
 * Instance třídy {@code MenuGame} představují nabídku umožňující nastavovat
 * různé možnosti hry, mezi nimi:
 * <ul>
 *   <li>Zobrazení plánku hry</li>
 *   <li>Spuštění zadané hry</li>
 *   <li>Předčasné ukončení hry</li>
 *   <li>Ukončení celé aplikace</li>
 * </ul>
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class MenuGame extends JMenu
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, pro které nabídka pracuje. */
    private final IMyGUI gui;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @param gui
     */
    MenuGame(IMyGUI gui)
    {
        super("Hra");
        setMnemonic('H');
        this.gui = gui;

        showMapCommand();
        playAgainCommand();
        choseNewGameCommand();
        endoOfGameCommand();
        endOfApplicationCommand();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Nastaví, zda se bude či nebude zobrazovat plánek hry
     * s pohybujícím se kurzorem představujícím hráče, který zobrazí místnost,
     * ve které místnosti se hráč právě nachází.
     */
    private void showMapCommand()
    {
        final JCheckBoxMenuItem showMapItem=  new JCheckBoxMenuItem(
                                               "Zobrazovat plánek hry", true);
        showMapItem.setMnemonic('P');
//        gui.setGameMapVisible(gui.isGameMapVisible());
        this.add(showMapItem);

        showMapItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                gui.setGameMapVisible(showMapItem.getState());
            }
        });
    }


    /***************************************************************************
     * Spustí aktuální hru znovu od začátku.
     */
    private void playAgainCommand()
    {
        JMenuItem playAgainItem = new JMenuItem("Hrát znovu stejnou hru");
        playAgainItem.setMnemonic('Z');
        this.add(playAgainItem);

        playAgainItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IGameG game = stopGame();
                gui.startGame(game);
            }
        });
    }


    /***************************************************************************
     * Nabídne seznam her, z nichž si může hráč vybrat, která se spustí.
     */
    private void choseNewGameCommand()
    {
        JMenuItem enterNewGameItem = new JMenuItem("Zadat další hru");
        enterNewGameItem.setMnemonic('d');
        this.add(enterNewGameItem);
        enterNewGameItem.setEnabled(false);
    }


    /***************************************************************************
     * Ukončí právě hranou hru.
     */
    private void endoOfGameCommand()
    {
        JMenuItem stopGameItem = new JMenuItem("Ukončit hru");
        stopGameItem.setMnemonic('U');
        this.add(stopGameItem);

        stopGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IGameG hra = stopGame();
            }
        });
    }


    /***************************************************************************
     * Ukončí celou aplikaci.
     */
    private void endOfApplicationCommand()
    {
        JMenuItem endOfApplicationItem = new JMenuItem("Konec aplikace");
        endOfApplicationItem.setMnemonic('K');
        this.add(endOfApplicationItem);

        endOfApplicationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stopGame();
                System.exit(0);
            }
        });
    }


    /***************************************************************************
     * Zjistí, která hra je právě ovládána, a pokud ještě není ukončena,
     * tak ji ukončí.
     *
     * @return Odkaz na právě ukončenou hru
     */
    private IGameG stopGame()
    {
        IGameG game = gui.getGame();
        if ((game != null) && (game.isAlive())) {
            game.stop();
        }
        return game;
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        MenuGame inst = new MenuGame();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
