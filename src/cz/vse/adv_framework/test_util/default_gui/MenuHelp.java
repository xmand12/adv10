/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_gui;

import cz.vse.adv_framework.game_gui.IGameG;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;




/*******************************************************************************
 * Instance třídy {@code NabidkaNapoveda} přestavují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class MenuHelp extends JMenu
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final String ABOUT_STRING =
        "<html><font color=\"red\">Vzorové řešení</font><br>" +
        "grafického rozhraní adventury<br><br>" +
        "tentokrát již doplněné o nabídku s možností<br>" +
        "aktivovat nápovědu" +
        "</html>";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** GUI, pro které nabídka pracuje. */
    private final IMyGUI gui;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @param gui Grafické rozhraní, pro které instance pracuje
     */
    MenuHelp(IMyGUI gui)
    {
        super("Nápověda");
        this.gui = gui;
        this.setMnemonic( 'N' );

        creatHelpItem();
        CreateAboutItem();
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Otevře aplikační okno zobrazující obsah hlavního souboru nápovědy,
     * ze kterého vedou hyperodkazy do dalších souborů.
     * Metoda umožňuje otevřít několik instancí tohoto okna
     * a v každé si zobrazit jinou část nápovědy.
     */
    private void openHelpWindow()
    {
        JFrame      helpWindow = new JFrame("Nápověda");
        JEditorPane helpPane   = new JEditorPane();

        //Připraví konfiguraci panelu s nápovědou v okně
        helpPane.setEditable(false);
        JScrollPane editorScrollPane = new JScrollPane(helpPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        helpWindow.add(editorScrollPane);

        //Naplní panel obsahem
        IGameG game = gui.getGame();
        URL    url  = game.getHelpURL();
        try {
            helpPane.setPage(url);
        } catch (Exception e) {
            throw new RuntimeException(
                "\nNepodařilo se otevřít nápovědu na URL: " + url, e);
        }
        helpPane.addHyperlinkListener(new HLL(helpPane));
        helpWindow.pack();
        helpWindow.setVisible(true);
    }


    /***************************************************************************
     * V nabídce Nápověda vytvoří položku Nápověda,
     * po jejímž zadání se zobrazí okno s nápovědou.
     */
    private void creatHelpItem()
    {
        JMenuItem helpItem = new JMenuItem("Nápověda");
        this.add(helpItem);

        helpItem.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent ae ) {
                    openHelpWindow();
                }
            }
        );
    }


    /***************************************************************************
     * V nabídce Nápověda vytvoří položku O aplikaci, po jejímž zadání
     * se zobrazí okno se základními informacemi o dané aplikaci.
     */
    private void CreateAboutItem()
    {
        JMenuItem aboutItem = new JMenuItem("O aplikaci");
        this.add(aboutItem);

        aboutItem.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent ae ) {
                    JOptionPane.showMessageDialog(null,
                        ABOUT_STRING,
                        "Druhá vzorové řešení GUI adventury",
                        JOptionPane.PLAIN_MESSAGE);

                }
            }
        );
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Instance třídy <b><code>HLL</code></b> přestavují posluchače
     * reagující na klepnutí na hypertextový odkaz.
     *
     * @author    jméno autora
     * @author    Rudolf PECINOVSKÝ
     * @version   0.00.000, 0.0.2008
     */
    public class HLL implements HyperlinkListener
    {
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================

        JEditorPane helpPane;



    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

        /***********************************************************************
         *
         * @param helpPane
         */
        public HLL(JEditorPane helpPane)
        {
            this.helpPane = helpPane;
        }


    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================
    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================

        /***********************************************************************
         *
         */
        @Override
        public void hyperlinkUpdate(final HyperlinkEvent e) {
            URL url = e.getURL();
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    helpPane.setPage(url);
                } catch (IOException ioe) {
                    System.err.println("Netrefim na: " + url);
                }
            }
        }



    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== VNOŘENÉ A VNITŘNÍ TŘÍDY ===============================================
    //== TESTING CLASSES AND METHODS ===========================================
    //
    //    /*********************************************************************
    //     * Testovací metoda.
    //     */
    //    public static void test()
    //    {
    //    }
    //    /** @param args Parametry příkazového řádku - nepoužívané. */
    //    public static void main( String[] args )  {  test();  }
    }



//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        JOptionPane.showMessageDialog(null, ABOUT_STRING);
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main( String[] args )  {  test();  }
}
