/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IObjectG;
import cz.vse.adv_framework.game_txt.INamed;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;



/*******************************************************************************
 * Instance třídy {@code ButtonPaneItem} představují komponenty
 * sloužící k zobrazení jim zadaného objektu.
 * <br><br>
 * Vylepšení změnilo předka z {@code JLabel} na {@code JButton} a
 * přidalo dva další konstruktory s parametrem specifikujícím název příkazu.
 * Tím umožnilo definovat definovat prvky jako tlačítka, po jejichž stisku
 * se provede příslušná akce.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
public class ButtonPaneItem extends JButton
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název zobrazovaného prvku. */
    private final String name;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří komponentu zobrazující název zadaného předmětu
     * spolu s jeho obrázkem.
     *
     * @param object   Zobrazovaný předmět
     * @param command  Příkaz, který se zadá po stisku tlačítka
     * @param gui      GUI, jehož prostřednictvím ovládáme hru
     */
    public ButtonPaneItem(IObjectG object, String command, IMyGUI gui)
    {
        this(object, null, object.getPicture(), command, gui);
        setToolTipText(name);
    }


    /***************************************************************************
     * Vytvoří komponentu zobrazující název zadaného pojemnovaného objektu
     * (většinou místnosti).
     *
     * @param object  Zobrazovaný objekt
     * @param command Příkaz, který se zadá po stisku tlačítka
     * @param gui     GUI, jehož prostřednictvím ovládáme hru
     */
    public ButtonPaneItem(INamed object, String command, IMyGUI gui)
    {
        this(object, object.getName(), null, command, gui);
    }


    /***************************************************************************
     * Vytvoří komponentu zobrazující název zadaného pojemnovaného objektu
     * (většinou místnosti).
     *
     * @param object  Zobrazovaný objekt
     * @param name    Jméno zobrazovaného objektu
     * @param icon    Obraz zobrazovaného prvku
     * @param command Příkaz, který se zadá po stisku tlačítka
     * @param gui     GUI, jehož prostřednictvím ovládáme hru
     */
    private ButtonPaneItem(INamed object, String name, Icon icon,
                           final String command, final IMyGUI gui)
    {
        super(name, icon);
        this.name = object.getName();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gui.executeCommand(command + " " + ButtonPaneItem.this.name);
            }
        });
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název zobrazovaného objetku.
     *
     * @return Název zobrazovaného objetku
     */
    @Override
    public String getName()
    {
        return name;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
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
//        PrvekPanelu inst = new PrvekPanelu();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
