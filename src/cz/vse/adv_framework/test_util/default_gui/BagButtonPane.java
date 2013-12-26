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
 * Instance třídy {@code BagButtonPane} představují panely,
 * v nichž se v průběhu hry zobrazuje obsah batohu hráče,
 * tj. předměty, které jsou v něm uloženy.
 * <br><br>
 * Třída je upravena tak, aby její instance uměly pracovat s prvky
 * definovanými jako tlačítka, jejichž stiskem lze daný předmět položit.
 * K tomu bylo třeba přidat atributy pro zapamatování si příkazu k položení
 * předmětu a také GUI, jehož prostřednictvím bude příkaz hře zadán.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class BagButtonPane extends AButtonPane<IBagG>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, prostřednictvím nějž je hra ovládána. */
    private final IMyGUI gui;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Příkaz, jímž se v aktuálně zpracovávané hře pokládá předmět. */
    private String příkazPolož;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytoří nový panel pro zobrazení obsahu batohu, přičemž předpokládá,
     * že předměty v batohu budou na panelu zobrazovány jako talčítka,
     * jejichž stiskem můžeme daný předmět položit.
     *
     * @param gui  GUI, prostřednictvím nějž je hra ovládána
     */
    BagButtonPane(IMyGUI gui)
    {
        super("Obsah batohu");
        this.gui = gui;
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
        Collection<ButtonPaneItem> prvky = new ArrayList<>();
        for (IObjectG předmět : batoh.getObjects()) {
            ButtonPaneItem pp = new ButtonPaneItem(předmět, příkazPolož, gui);
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
        příkazPolož = hra.getBasicCommands().PUT_DOWN_CMD_NAME;
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
//        PanelBatohu inst = new PanelBatohu();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
