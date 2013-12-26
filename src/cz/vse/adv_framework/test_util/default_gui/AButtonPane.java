/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IListener;

import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;



/*******************************************************************************
 * Instance třídy {@code AButtonPane} představují panely,
 * v nichž jsou umístěny komponenty zobrazující jednotlivé vložené prvky.
 *
 * @param <T> Typ informátora, z nějž může oslovený posluchač získat informace
 *            potřebné pro zpracování obdrženého hlášení
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public abstract class AButtonPane <T> extends Box
       implements IListener<T>
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Velikost mezery vkládané mezi jednotlivé prvky. */
    private static final int SPACE = 10;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Titulek oznamující obsah okna. */
    private final JLabel titleLabel;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří základ panelu prvků nadepsaného zadaným titulkem.
     *
     * @param title Označení vytvářeného panelu prvků
     */
    public AButtonPane(String title)
    {
        super(BoxLayout.Y_AXIS);
        titleLabel = new JLabel(
            "<html><font color=\"red\">" + title +
            "</font><hr></html>");
    }



//== ABSTRACT METHODS ==========================================================

    /***************************************************************************
     * Hlášeni o vzniku události, na kterou posluchač čeká.
     *
     * @param informer Objekt, od nějž může oslovený objekt
     *                 získat potřebné informace
     */
    @Override
    public abstract void notice(T informer);


    /***************************************************************************
     * Inicializuje panel pro práci s novou hrou.
     * Při ní se panel musí přihlásit u hry jako posluchač jistého druhu objektů
     * a zobrazit příslušné objekty na počátku hry.
     *
     * @param hra Hra, s níž bude panel od této chvíle komunikovat
     */
    abstract public void inicialize(IGameG hra);



//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Závěrečná fáze zpracování obdrženého hlášení poté,
     * co oslovený objekt připravil kolekci zobrazovaných objektů.
     * Metoda vyčistí panel a naplní jej objekty obdrženými v parametru.
     *
     * @param items Prvky, které je v panelu třeba zobrazit.
     *
     * Kolekce má definované prvky typu {@code <? extends JComponent>} proto,
     * aby bylo možno jednouše přejít od návěští, tj. od prvků typu
     * {@link PaneItem}, které jsou potomky typu {@link JLabel},
     * k tlačítkům, tj. k prvkům typu {@link ButtonPaneItem},
     * které jsou potomky typu {@link javax.swing.JButton}.
     * Typ {@link JComponent} je totiž jejich nejbližším společným předkem.
     */
    public void processNotice(Collection<? extends JComponent> items)
    {
        this.removeAll();
        this.add(titleLabel);
        for (JComponent item : items) {
            this.add(item);
            this.add(Box.createVerticalStrut(SPACE)); //Oddělovací mezera
        }
        this.add(Box.createVerticalStrut(SPACE));
        this.repaint();
        this.revalidate();
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
//        AButtonPane inst = new AButtonPane();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
