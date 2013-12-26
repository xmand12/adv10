/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;



/*******************************************************************************
 * Knihovní třída {@code DBG} poskytuje metody pro podmíněny výstup
 * ladicích tisku do předem zadaného proudu, kterým muže byt standardní výstup,
 * standardní chybový výstup, libovolný proud typu {@link PrintStream}
 * nebo soubor, který se v případe potřeby nejprve vytvoří.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public final class DBG_
{
//    static { Systém.out.println("CLASS - DBG - START"); }
//    { Systém.out.println("INSTANCE - DBG - START"); }
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Konstanta vyhodnotitelná v době překladu, jejiž hodnota ovlivňuje,
     *  zda se budou požadované tisky provádět nebo ne. */
    public static final int DEBUG = 1;


//== VARIABLE CLASS ATTRIBUTES =================================================

    private static PrintStream out = System.out;


//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== KONSTANTNÍ ATRIBUTY INSTANCI ==============================================
//== PROMĚNNÉ ATRIBUTY INSTANCI ================================================
//== PŘÍSTUPOVÉ METODY ATRIBUTU TŘÍDY ==========================================

    /***************************************************************************
     * Nastaví výstup do zadaného tiskového proudu.
     *
     * @param ps
     */
    public static void setOut(PrintStream ps) {
        out = ps;
    }


    /***************************************************************************
     * Je-li jako parametr zadaná hodnota <b>{@code out}</b> nebo
     * <b>{@code err}</b> nastaví výstup do zadaného systémového proudu;
     * Jiná podoba řetězce definuje cestu k souboru, do nějž se ma výstup
     * poslat. Pokud tento soubor neexistuje, metoda se jej pokusí vytvořit.
     *
     * @param proud
     */
    public static void setOut(String proud) {
        if (proud.equals("out")) {
            out = System.out;
        }
        else if (proud.equals("err")) {
            out = System.err;
        }
        else {
            File soubor = new File(proud);
            try {
                out = new PrintStream(soubor);
            } catch (FileNotFoundException ex1) {
                try {
                    soubor.createNewFile();
                } catch (IOException ex2) {
                    throw new RuntimeException("\nSoubor " + soubor +
                        " se nepodařilo najít ani vytvořit", ex2);
                }
            }
        }
    }


    /***************************************************************************
     * Vrátí proud, do nějž odcházejí ladící tisky
     *
     * @return Proud, do nějž odcházejí ladící tisky
     */
    public static PrintStream getPrintStream() {
        return out;
    }


//== NESOUKROMÉ METODY TŘÍDY ===================================================

    /***************************************************************************
     * Je-li <b>{@code DEBUG > 0}</b>, vytiskne požadovaný text
     * bez závěrečného odřádkování.
     *
     * @param text Tištěný text
     */
    public static void pr(String text) {
        if (DEBUG > 0) {
            out.print(text);
        }
    }


    /***************************************************************************
     * Je-li <b>{@code DEBUG >= hladina}</b>, vytiskne požadovaný text
     * bez závěrečného odřádkování.
     * @param level
     * @param text
     */
    public static void pr(int level, String text) {
        if (DEBUG >= level) {
            out.print(text);
        }
    }


    /***************************************************************************
     * Je-li <b>{@code DEBUG > 0}</b>, vytiskne požadovaný text
     * v zadaném formátu.
     * @param format
     * @param parametry
     */
    public static void prf(String format, Object... parametry) {
        if (DEBUG > 0) {
            out.printf(format, parametry);
        }
    }


    /***************************************************************************
     * Je-li <b>{@code DEBUG >= hladina}</b>, vytiskne požadovaný text
      * v zadaném formátu.
     * @param level
     * @param format
     * @param parametry
     */
    public static void prf(int level, String format, Object... parametry) {
        if (DEBUG >= level) {
            out.printf(format, parametry);
        }
    }


    /***************************************************************************
     * Je-li <b>{@code DEBUG > 0}</b>, vytiskne požadovaný text
     * a odřádkuje.
     *
     * @param text
     */
    public static void prln(String text) {
        if (DEBUG > 0) {
            out.println(text);
        }
    }


    /***************************************************************************
     * Je-li <b>{@code DEBUG >= hladina}</b>, vytiskne požadovaný text
     * a odřádkuje.
     *
     * @param level
     * @param text
     */
    public static void prln(int level, String text) {
        if (DEBUG >= level) {
            out.println(text);
        }
    }


    /***************************************************************************
     * Počká zadaný počet milisekund.
     * Na přerusení nijak zvlášť nereaguje - proste skonči dřív.
     *
     * @param miliseconds   Počet milisekund, po nez se ma čekat.
     */
    public static void wait(int miliseconds)
    {
        try {
            Thread.sleep(miliseconds);
        }catch(InterruptedException e) {
            throw new RuntimeException("Čekání bylo přerušeno", e);
        }
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================
    private DBG_() {/**/}

//== ABSTRACT METHODS ==========================================================
//== NESOUKROMÉ METODY INSTANCI ================================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCI ========================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTY =====================================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        DBG x = new DBG();
//    }
//    /** @param args Parametry příkazového radku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
