/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;

import javax.swing.Box;
import javax.swing.BoxLayout;



/*******************************************************************************
 * Instance třídy {@code RoomButtonPane} představují panely,
 * v nichž se v průběhu hry zobrazují předměty v aktuální místnosti
 * a sousedé této místnosti
 * <br><br>
 * Třída je oproti třídě {@code RoomPane} upravena tak,
 * aby pracovala s panely používajícími tlačítka.
 * K tomu bylo třeba přidat atributy pro zapamatování si příkazu k položení
 * předmětu a také GUI, jehož prostřednictvím bude příkaz hře zadán.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
public class RoomButtonPane extends Box
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Panel zobrazující předměty v aktuální místnosti. */
    private final ThingButtonPane objectPane;

    /** Panel zobrazující předměty v aktuální místnosti. */
    private final NeighborButtonPane neighborPane;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový panel obsahující panely předmětů a sousedů
     * aktuální místnosti.
      *
     * @param gui  GUI, prostřednictvím nějž je hra ovládána
    */
    public RoomButtonPane(IMyGUI gui)
    {
        super(BoxLayout.Y_AXIS);
        objectPane    = new ThingButtonPane(gui);
        neighborPane  = new NeighborButtonPane(gui);

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
    public void inicialize(IGameG game)
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
//        PanelMístností inst = new PanelMístností();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
