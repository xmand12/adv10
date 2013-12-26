/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;



/*******************************************************************************
 * Instance třídy {@code LogWindow} představují okna,
 * do nichž se na požádání vypisují zprávy žurnálu.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class LogWindow
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Okno, v němž se bude obsah žurnálu zobraovat. */
    private final JFrame logFrame = new JFrame("Žurnál");

    /** Textové pole, , v němž se bude obsah žurnálu zobraovat. */
    private final JTextArea textArea = new JTextArea(16, 64);

    /** Dokument s obsahem zobrazovaným v textovém poli. */
    private final Document document;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     */
    public LogWindow()
    {
        document = textArea.getDocument();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        logFrame.add(scrollPane);
        logFrame.pack();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí informaci o viditelnosti okna.
     *
     * @return Je-li okno viditelné, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    public boolean isVisible()
    {
        return logFrame.isVisible();
    }


    /***************************************************************************
     * Nastaví viditelnost okna.
     *
     * @param visible Nastavovaná viditelnost
     */
    public void setVisible(boolean visible)
    {
        logFrame.setVisible(visible);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá zadaný text na další řádek za konec zobrazované textu
     * a přesune za něj kurzor, aby byl text viditelný.
     *
     * @param text Přidávaný text
     */
    public void append(String text)
    {
        int behindEndIndex = document.getLength();
        try {
            document.insertString(behindEndIndex, text, null);
        } catch (BadLocationException ex) {
            throw new RuntimeException(
                    "\nŠpatně spočtená délka žurnálu", ex);
        }
        textArea.setCaretPosition(document.getLength());
    }


    /***************************************************************************
     * Smaže obsah okna.
     *
     */
    public void erase()
    {
        textArea.setText("");
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
//        LogWindow inst = new LogWindow();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
