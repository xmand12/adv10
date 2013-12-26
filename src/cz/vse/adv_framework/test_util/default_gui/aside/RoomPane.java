/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;

import javax.swing.Box;
import javax.swing.BoxLayout;



/*******************************************************************************
 * Instance třídy {@code RoomPane} představují panely,
 * v nichž se v průběhu hry zobrazují předměty v aktuální místnosti
 * a sousedé této místnosti
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
public class RoomPane extends Box
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Panel zobrazující předměty v aktuální místnosti. */
    private final ThingPane objectPane = new ThingPane();

    /** Panel zobrazující předměty v aktuální místnosti. */
    private final NeighborPane neighborPane = new NeighborPane();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový panel obsahující panely předmětů a sousedů
     * aktuální místnosti.
     */
    public RoomPane()
    {
        super(BoxLayout.Y_AXIS);
        add(objectPane);
        add(Box.createGlue());  //Odtlačí oba panely ke krajům pole
        add(neighborPane);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     *
     * @param game Hra, s níž bude panel od této chvíle komunikovat
     */
    public void inicializuj(IGameG game)
    {
        objectPane   .inicialize(game);
        neighborPane .inicialize(game);
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
//        RoomPane inst = new RoomPane();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
