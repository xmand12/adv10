/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.util;



////////////////////////////////////////////////////////////////////////////////
//%P-  +++++ End of ignored starting text - place for imports +++++

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.util.Locale;

//%X+ xxxxx Start of the overjumped text with declaration of the envelope xxxxx
/*******************************************************************************
 *<pre>
 * Previous:  No - this is a newly defined class
 *              Ttt in the project Ppp
 * Following: No
 *
 * Project  Ppp
 *   + Aded
 *   - Removed
 *   ~ Changed
 *
 *</pre>
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 6.03.2017 — 2010-11-08
 */
//public class IndentingPrintStream_000_
//{    private IndentingPrintStream_000_() {}   static
////////////////////////////////////////////////////////////////////////////////
//%X- ----- End of the overjumped text with declaration of the envelope -----


/*******************************************************************************
 * Instance třídy {@code IndentingPrintStream} představují dekorátory,
 * které "ozdobí" dekorované výstupní proudy schopností
 * zapisovat vystupující informace tak, aby vynikla požadovaná hierarchie.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 6.03.2017 — 2010-11-08
 */
public class IndentingPrintStream extends PrintStream
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Implicitní odsazení pokračovacích řádků. */
    private static String defaultIndentIncrement = "|  ";



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Řetězec vkládaný při další úrovni zanoření na konec řetězce
     *  {@link #indent} a po vynoření opět odebíraný. */
    private String indentIncrement = defaultIndentIncrement;

    /** Řetězec vkládaný na počátek každého tištěného řádku. */
    private String indent = "";



//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí implicitní řetězec vkládaný několikrát na počátek každého
     * tištěného řádku, přičemž počet vložení je shodný s úrovní vnoření.
     *
     * @return Implicitní odsazovací řetězec
     */
    public static String getDefaultIndentIncrement()
    {
        return defaultIndentIncrement;
    }


    /***************************************************************************
     * Nastaví implicitní řetězec vkládaný několikrát na počátek každého
     * tištěného řádku, přičemž počet vložení je shodný s úrovní vnoření.
     *
     * @param defaultIncrement Implicitní odsazovací řetězec
     */
    public static void setDefaultIndentIncrement(String defaultIncrement)
    {
        defaultIndentIncrement = defaultIncrement;
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí zadaný výstupní proud dekorovaný o schopnost řízeně odsazovat
     * vystupující text a vytvořený dekorovaný proud vrátí.
     * Pro vytvořený proud bude použito kódování UTF-8.
     *
     * @param stream  Dekorovaný proud
     * @return Standardní výstupní proud dekorovaný o schopnost odsazování
     */
    public static IndentingPrintStream indentifyPrintStream(
                                               OutputStream stream)
    {
        return indentifyPrintStream(System.out, "UTF-8");
    }


    /***************************************************************************
     * Vrátí zadaný výstupní proud dekorovaný o schopnost řízeně odsazovat
     * vystupující text a vytvořený dekorovaný proud vrátí.
     *
     * @param stream   Dekorovaný proud
     * @param codepage Kódová stránka vytvořeného proudu
     * @return Standardní výstupní proud dekorovaný o schopnost odsazování
     */
    public static IndentingPrintStream indentifyPrintStream(
                                       OutputStream stream, String codepage)
    {
        IndentingPrintStream ips;
        try {
            ips = new IndentingPrintStream(stream, codepage);
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(
                "\nZadaná kódová stránka není podporována: " + codepage, ex );
        }
        return ips;
    }


    /***************************************************************************
     * Vytvoří instanci dekorující zadaný výstupní proud
     * schopností odsazování.
     *
     * @param codepage Kódová stránka vytvořeného proudu
     * @param stream   Dekorovaný výstupní proud
     * @throws UnsupportedEncodingException Při zadání nepodporovaného kódování
     */
    protected IndentingPrintStream(OutputStream stream, String codepage)
              throws UnsupportedEncodingException
    {
        //Pro začátek nastavuji za obalovaný proud standardní výstup,
        //protože něco se tam dát musí
        super(System.out, true, codepage);

        //Nastavím obalovaný proud na vytvořenou instanci své vnitřní třídy,
        //protože má rodičovská třída PrintStream řeší některé reakce
        //na odřídkování sama, a já na ně přitom potřebuji reagovat po svém -
        //tak nastavím reakci do vnitřní třídy.
        //Posloupnost volání je nyní:
        // Já -> PrintStream -> FilterOutputStream -> IndentingOutputStream
        //    (to je má vnitřní třída) -> FilterOutputStream -> stream
        out = new IndentingOutputStream(stream);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí řetězec vkládaný několikrát na počátek každého tištěného řádku,
     * přičemž počet vložení je shodný s úrovní vnoření.
     *
     * @return Aktuální odsazovací řetězec
     */
    public String getIndentIncrement()
    {
        return indentIncrement;
    }


    /***************************************************************************
     * Nastaví  řetězec vkládaný několikrát na počátek každého tištěného řádku,
     * přičemž počet vložení je shodný s úrovní vnoření.
     *
     * @param indentIncrement Zadávaný odsazovací řetězec
     */
    public void setIndentIncrement(String indentIncrement)
    {
        this.indentIncrement = indentIncrement;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá jednu úroveň odsazení a odřádkuje.
     * Další řádek tak bude o jedno odsazení bohatší.
     *
     * @return Vrátí odkaz na sebe, aby bylo možno příkazy řetězit
     */
    public IndentingPrintStream indent()
    {
        indent += indentIncrement;
        println();
        return this;
    }


    /***************************************************************************
     * Odebere jednu úroveň odsazení a odřádkuje.
     * Další řádek tak bude o jedno odsazení chudší.
     *
     * @return Vrátí odkaz na sebe, aby bylo možno příkazy řetězit
     */
    public IndentingPrintStream outdent()
    {
        if (indent.length() < indentIncrement.length()) {
            throw new RuntimeException(
                "\nByla přijata přespočetná žádost o přisazení");
        }
        indent = indent.substring(0, indent.length()- indentIncrement.length());
        println();
        return this;
    }



//== Inherited methods with casted return values ===============================

    /**********************************************************************
     * {@inheritDoc}
     *
     * @param csq   Připojovaná posloupnost znaků
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream append(CharSequence csq)
    {
        super.append(csq);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param csq   Připojovaná posloupnost znaků
     * @param start Index prvního přidávaného znaku
     * @param end   Index znaku za posledním přidávaným znakem
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream append(CharSequence csq, int start, int end)
    {
        super.append(csq, start, end);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param c Přidávaný znak
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream append(char c)
    {
        super.append(c);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param format Formát vraceného textu
     * @param args   Parametry, jejichž hodnoty se budou tisknout
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream format(String format, Object... args)
    {
        super.format(format, args);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param l      Použité {@link Locale}
     * @param format Formát vraceného textu
     * @param args   Parametry, jejichž hodnoty se budou tisknout
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream format(Locale l, String format, Object... args)
    {
        super.format(l, format, args);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param format Formát vraceného textu
     * @param args   Parametry, jejichž hodnoty se budou tisknout
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
     */
    @Override
    public IndentingPrintStream printf(String format, Object... args)
    {
        super.printf(format, args);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param l      Použité {@link Locale}
     * @param format Formát vraceného textu
     * @param args   Parametry, jejichž hodnoty se budou tisknout
     * @return Odkaz na svoji instanci, aby bylo možno příkazy řetězit
    */
    @Override
    public IndentingPrintStream printf(Locale l, String format, Object... args)
    {
        super.printf(l, format, args);
        return this;    //Efektivnější než přetypování vrácené hodnoty
    }


    /**********************************************************************
     * {@inheritDoc}
     *
     * @param b Zapisovaný znak
     */
    @Override
    public void write(int b)
    {
        super.write(b);
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /**************************************************************************
     * Instance třídy {@code IndentingOutputStream} představují dekorátory
     * doplňující u dekorovaného proudu operaci odřádkování
     * o vypsání odsazovacího řetězce
     * zpřehledňujícího hierarchickou strukturu vypisovaného textu.
     */
    private class IndentingOutputStream extends FilterOutputStream
    {
        /***********************************************************************
         * Vytvoří objekt dekorující zadaný výstupní proud.
         *
         * @param stream
         */
        IndentingOutputStream(OutputStream stream)
        {
            super(stream);
        }


        /***********************************************************************
         * Vypíše zadaný znak, přičemž za odřádkování automaticky doplní
         * počáteční odsazovací řetězec.
         * @param b
         * @throws IOException
         */
        @Override
        public void write(int b) throws IOException
        {
            super.write(b);
            if (b == '\n') {
                super.write(indent.getBytes());
            }
        }
    }



//== TESTING CLASSES AND METHODS ===============================================
//%U=}
//%E# ---- Start of the overjumped text with closing of the envelope ----
////////////////////////////////////////////////////////////////////////////////
//== QUCK TESTS ================================================================

}
