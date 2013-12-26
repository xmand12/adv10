/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.io.IOException;
import java.io.OutputStream;



/*******************************************************************************
 * Instance třídy {@code MultiOutput} představuje proud združující sadu
 * proudů, které se navenek tváří jako jeden proud.
 * Každá operace se vždy provede s každým se sdružených proudů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class MultiOutput extends OutputStream
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Vystupní proudy, do nichž se bude paralelně zapisovat. */
    private final OutputStream[] thisStreams;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový proud zastupující skupinu zadaných sdružovaných proudů.
     *
     * @param streams Sdružované proudy
     */
    public MultiOutput(OutputStream... streams)
    {
        thisStreams = streams;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zavře všechny sdružované proudy.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException
    {
        for (OutputStream os : thisStreams) {
            os.close();
        }
    }


    /***************************************************************************
     *
     * @throws IOException
     */
    @Override
    public void flush() throws IOException
    {
        for (OutputStream os : thisStreams) {
            os.flush();
        }
    }


    /***************************************************************************
     *
     * @param b
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException
    {
        for (OutputStream os : thisStreams) {
            os.write(b);
        }
    }


    /***************************************************************************
     *
     * @param b
     * @param off
     * @param len
     * @throws IOException
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        for (OutputStream os : thisStreams) {
            os.write(b, off, len);
        }
    }


    /***************************************************************************
     * Zapíše zadaný znak do všech sdružených proudů.
     *
     * @param b   Kód zapisovaného znaku
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException
    {
        for (OutputStream os : thisStreams) {
            os.write(b);
        }
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
//        MultiOutput inst = new MultiOutput();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
