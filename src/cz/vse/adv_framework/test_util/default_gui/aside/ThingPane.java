/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IObjectG;
import cz.vse.adv_framework.game_gui.IPlaceG;

import java.util.ArrayList;
import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@code ThingPane} představují panely,
 * v nichž se v průběhu hry zobrazují předměty v aktuální místnosti.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class ThingPane extends AButtonPane<IPlaceG>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový panel pro zobrazování předmětů aktuální místnosti.
     */
    ThingPane()
    {
        super("Předměty v místnosti");
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o změně předmětů v aktuální místnosti zapříčiněné
     * buď jejich přímým přidáním či odebráním, anebo změnou místnosti.
     *
     * @param room Místnost, jejíž předměty je třeba zobrazit
     */
    @Override
    public void notice(IPlaceG room)
    {
        Collection<PaneItem> items = new ArrayList<>();
        for (IObjectG object : room.getObjects()) {
            PaneItem pp = new PaneItem(object);
            items.add(pp);
        }
        processNotice(items);
    }


    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     *
     * @param hra Hra, s níž bude panel od této chvíle komunikovat
     */
    @Override
    public void inicialize(IGameG hra)
    {
        hra.addObjectListener(this);
        notice(hra.getCurrentPlace());
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
//        ThingPane inst = new ThingPane();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
