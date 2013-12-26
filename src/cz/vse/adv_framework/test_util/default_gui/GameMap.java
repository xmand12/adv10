/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IListener;
import cz.vse.adv_framework.game_gui.IPlaceG;
import cz.vse.adv_framework.game_gui.IUIG;

import java.awt.Point;
import java.awt.Rectangle;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



/*******************************************************************************
 * Instance třídy {@code GameMap} přestavují aplikační okno
 * zobrazující plánek hry a na něm kurzor představující hráče.
 * Z pozice kurzoru je možno odvodit pozici hráče.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class GameMap implements IListener<IPlaceG>
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final String MAP_TITLE = "Plánek hry";

    /** instance jedináčka. */
    private static final GameMap SINGLETON = new GameMap();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Okno zobrazující plánek hry. */
    private final JFrame mapFrame;

    /** Návěští s obrázkem plánku hry. */
    private final JLabel mapLabel;

    /** Návěští s obrázkem kurzoru představujícího hráče. */
    private final JLabel playerLabel;

    /** Obrázek kurzoru představujícího hráče. */
    private final ImageIcon playerIcon;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Obrázek s plánkem hry. */
    private ImageIcon mapIcon;

    /** Aktuálně ovládaná hra. */
    private IGameG controlledGame;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci jedináčka.
     *
     * @return Instance jedináčka
     */
    public static GameMap getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří aplikační okno s plánkem hry a kurzorem hráče,
     * který se po tomto plánku pohybuje.
     *
     * @param gui GUI, pro něž bude aplikační okno pracovat
     */
    private GameMap()
    {
        mapFrame = new JFrame(MAP_TITLE);
        mapFrame.setLayout(null);
        mapLabel = new JLabel();

        playerIcon  = DefaultGUI.getInstance().getGame().getPlayer();
        playerLabel = new JLabel(playerIcon);
        playerLabel.setBounds(0, 0,
                    playerIcon.getIconWidth(), playerIcon.getIconHeight());

        //Později přidaná návěští se umísťují pod návěští přidaná dříve
        mapFrame.add(playerLabel);
        mapFrame.add(mapLabel);
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Nastaví viditelnost okna s plánkem hry.
     *
     * @param visible Má-li být plánek viditelný ({@code true} + má být)
     */
    public void setVisible(boolean visible)
    {
        mapFrame.setVisible(visible);
    }


//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášení o změně aktuální místnosti
     *
     * @param currentRoom Nová aktuální místnost
     */
    @Override
    public void notice(IPlaceG currentRoom)
    {
        placePlayerIcon();
    }


    /***************************************************************************
     * Inicializuje komunikaci se zadanou hrou.
     *
     * @param game  Hra, s níž bude GUI komunikovat
     */
    void initialize(IGameG game)
    {
        DefaultGUI gui = DefaultGUI.getInstance();
        this.controlledGame = game;

        //Nastaví obrázek s plánkem
        mapIcon = game.getMap();
        mapLabel.setIcon(mapIcon);

        int w = mapIcon.getIconWidth();
        int h = mapIcon.getIconHeight();
        mapLabel.setBounds(4, 4, w, h);
        mapFrame.setSize(w + 16, h + 36);

        //Nastavení pozice plánku pod aplikačním oknem
        Rectangle rctGUI = gui.getArea();
        mapFrame.setLocation(rctGUI.x,  rctGUI.y + rctGUI.height);

        game.addNeighborsListener(this);

        placePlayerIcon();
        boolean zobrazit = gui.isGameMapVisible();
        setVisible(zobrazit);
    }


    /***************************************************************************
     *
     */
    final void placePlayerIcon()
    {
        Point playerPosition = controlledGame.getCurrentPlace().getPosition();
        playerLabel.setLocation(playerPosition);
        mapFrame.setVisible(true);
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        DialogSPlankem inst = new DialogSPlankem();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
