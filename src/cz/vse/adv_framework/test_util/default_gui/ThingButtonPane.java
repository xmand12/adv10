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
 * Instance třídy {@code PanelPředmětů} představují panely,
 * v nichž se v průběhu hry zobrazují předměty v aktuální místnosti.
 * <br><br>
 * Třída je upravena tak, aby její instance uměly pracovat s prvky
 * definovanými jako tlačítka, jejichž stiskem lze daný předmět zvednout.
 * K tomu bylo třeba přidat atributy pro zapamatování si příkazu ke zvednutí
 * předmětu a také GUI, jehož prostřednictvím bude příkaz hře zadán.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class ThingButtonPane extends AButtonPane<IPlaceG>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, prostřednictvím nějž je hra ovládána. */
    private final IMyGUI gui;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Příkaz, jímž se v aktuálně zpracovávané hře zvedá předmět. */
    private String commandPick;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový panel pro zobrazování předmětů aktuální místnosti.
     *
     * @param gui  GUI, prostřednictvím nějž je hra ovládána
     */
    ThingButtonPane(IMyGUI gui)
    {
        super("Předměty v místnosti");
        this.gui = gui;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Hlášeni o změně předmětů v aktuální místnosti zapříčiněné
     * buď jejich přímým přidáním či odebráním, anebo změnou místnosti.
     *
     * @param místnost Místnost, jejíž předměty je třeba zobrazit
     */
    @Override
    public void notice(IPlaceG místnost)
    {
        Collection<ButtonPaneItem> prvky = new ArrayList<>();
        for (IObjectG předmět : místnost.getObjects()) {
            ButtonPaneItem pp = new ButtonPaneItem(předmět, commandPick, gui);
            prvky.add(pp);
        }
        processNotice(prvky);
    }


    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     *
     * @param game Hra, s níž bude panel od této chvíle komunikovat
     */
    @Override
    public void inicialize(IGameG game)
    {
        commandPick = game.getBasicCommands().PICK_UP_CMD_NAME;
        game.addObjectListener(this);
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
//        PanelPředmětů inst = new PanelPředmětů();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
