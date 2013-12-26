/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IPlaceG;

import java.util.ArrayList;
import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@code PanelSousedů} představují panely,
 * v nichž se v průběhu hry zobrazují sousedé aktuální místnosti,
 * tj místností, do nichž je možno z aktuální mísntosti přímo přejít.
 * <br><br>
 * Třída je upravena tak, aby její instance uměly pracovat s prvky
 * definovanými jako tlačítka, jejichž stiskem lze hráče přesunout
 * do příslušné místnosti.
 * K tomu bylo třeba přidat atributy pro zapamatování si příkazu k přejítí
 * a také GUI, jehož prostřednictvím bude příkaz hře zadán.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class NeighborButtonPane extends AButtonPane<IPlaceG>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, prostřednictvím nějž je hra ovládána. */
    private final IMyGUI gui;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Příkaz, jímž se v aktuálně zpracovávané hře
     *  přesouvá do zadané místnosti. */
    private String commandMove;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový panel pro zobrazování sousedů aktuální místnosti.
     *
     * @param gui  GUI, prostřednictvím nějž je hra ovládána
     */
    NeighborButtonPane(IMyGUI gui)
    {
        super("Aktuální sousedé");
        this.gui = gui;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o změně sousedů aktuální místnosti zapříčiněné
     * buď jejich přímým přidáním či odebráním, anebo změnou místnosti.
     *
     * @param aktuální Místnost, jejíž sousedy je třeba zobrazit
     */
    @Override
    public void notice(IPlaceG aktuální)
    {
        Collection<ButtonPaneItem> prvky = new ArrayList<ButtonPaneItem>();
        for (IPlaceG místnost : aktuální.getNeighbors()) {
            ButtonPaneItem pp = new ButtonPaneItem(místnost, commandMove, gui);
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
        commandMove = hra.getBasicCommands().MOVE_CMD_NAME;
        hra.addNeighborsListener(this);
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
//        PanelSousedů inst = new PanelSousedů();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
