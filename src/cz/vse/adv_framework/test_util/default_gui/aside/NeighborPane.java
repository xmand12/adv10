/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IPlaceG;

import java.util.ArrayList;
import java.util.Collection;




/*******************************************************************************
 * Instance třídy {@code NeighborPane} představují panely,
 * v nichž se v průběhu hry zobrazují sousedé aktuální místnosti,
 * tj místností, do nichž je možno z aktuální mísntosti přímo přejít.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class NeighborPane extends AButtonPane<IPlaceG>
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
     * Vytvoří nový panel pro zobrazování sousedů aktuální místnosti.
     */
    NeighborPane()
    {
        super("Aktuální sousedé");
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o změně sousedů aktuální místnosti zapříčiněné
     * buď jejich přímým přidáním či odebráním, anebo změnou místnosti.
     *
     * @param current Místnost, jejíž sousedy je třeba zobrazit
     */
    @Override
    public void notice(IPlaceG current)
    {
        Collection<PaneItem> items = new ArrayList<>();
        for (IPlaceG room : current.getNeighbors()) {
            PaneItem pp = new PaneItem(room);
            items.add(pp);
        }
        processNotice(items);
    }


    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     *
     * @param game Hra, s níž bude panel od této chvíle komunikovat
     */
    @Override
    public void inicialize(IGameG game)
    {
        game.addNeighborsListener(this);
        notice(game.getCurrentPlace());
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
//        NeighborPane inst = new NeighborPane();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
