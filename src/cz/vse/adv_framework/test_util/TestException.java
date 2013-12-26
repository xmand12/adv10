/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;



/*******************************************************************************
 * Instance třídy {@code TestException} představují výjimky vyhazované
 * při odhalení nesrovnalostí v průběhu testování.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
@SuppressWarnings("serial")
class TestException extends RuntimeException
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří novou výjimku bez explicitně zadané zprávy a příčíny (cause).
     * Příčinu lze dodatečně zadat zavoláním metody {@link #initCause}.
     */
    TestException()
    {
    }


    /***************************************************************************
     * Vytvoří novou výjimku se zadanou detailní zprávou,
     * avšak bez zadané příčiny (cause).
     * Příčinu lze dodatečně zadat zavoláním metody {@link #initCause}.
     *
     * @param zpráva Detailní zpráva, kterou lze následně získat zavoláním
     *               metody {@link #getMessage()}.
     */
    TestException(String zpráva) {
        super(zpráva);
    }


    /***************************************************************************
     * Vytvoří novou s explicitně zadanou příčinou a detailní zprávou
     * zadanou podle vzorce {@code (příčina==null ? null : příčina.toString())}.
     * Tento konstruktor je vhodný pro výjimky, které jsou ve skutečnosti
     * pouhými obaly (wrappers) výjimky <tt>příčina</tt>.
     *
     * @param příčina Výjimka, která typicky způsobila vyhození dané výjimky
     *                a kterou lze později získat voláním {@link #getCause()}.
     *                Hodnota {@code null} je povolena a indikuje, že příčinu
     *                vyhození dané výjimky neznáme a nebo dokonce není.
     */
    TestException(Throwable příčina)
    {
        super(příčina);
    }


    /***************************************************************************
     * Vytvoří novou výjimku s explicitně zadanou zprávou i příčinou.
     *
     * @param zpráva  Detailní zpráva, kterou lze následně získat zavoláním
     *                metody {@link #getMessage()}.
     * @param příčina Výjimka, která typicky způsobila vyození dané výjimky
     *                a kterou lze později získat voláním {@link #getCause()}.
     *                Hodnota {@code null} je povolena a indikuje, že příčinu
     *                vyhození dané výjimky neznáme a nebo dokonce není.
     */
    TestException(String zpráva, Throwable příčina) {
        super(zpráva, příčina);
    }

}
