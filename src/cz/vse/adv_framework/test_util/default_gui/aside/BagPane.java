/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IBagG;
import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IObjectG;

import java.util.ArrayList;
import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@code BagPane} představují panely,
 * v nichž se v průběhu hry zobrazuje obsah batohu hráče,
 * tj. předměty, které jsou v něm uloženy.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class BagPane extends AButtonPane<IBagG>
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
     * Vytoří nový panel pro zobrazení obsahu batohu.
     */
    BagPane()
    {
        super("Obsah batohu");
   }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o změně obsahu batohu.
     *
     * @param batoh Batoh, jehož obsah se změnil
     */
    @Override
    public void notice(IBagG batoh)
    {
        Collection<PaneItem> prvky = new ArrayList<PaneItem>();
        for (IObjectG předmět : batoh.getObjects()) {
            PaneItem pp = new PaneItem(předmět);
            prvky.add(pp);
        }
        processNotice(prvky);
    }


    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     *
     * @param hra Hra, s níž bude panel od této chvíle komunikovat
     */
    @Override
    public void inicialize(IGameG hra)
    {
        hra.addBagListener(this);
        notice(hra.getBag());    //Kdyby hráč začínal s neprázným batohem
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
//        BagPane inst = new BagPane();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
