/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_game.gamet;

import cz.vse.adv_framework.game_txt.IBag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;



/*******************************************************************************
 * Instance třídy {@code Bag} představují úložiště, do nichž si hráči
 * odkládají sebrané předměty či jejch ekvivalenty.
 * Instance třídy je definovaná jako jedináček,
 * protože celá hra vystačí s jediným batohem.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class Bag implements IBag
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Maximální kapacita batohu - v této hře dvě ruce. */
    private static final int KAPACITA = 2;


    /** Schránka na přenášené předměty. V případě této hry se ale předměty
     *  přenášejí v rukou, a proto může hráč níst současně nejvýše dva. */
    private static final Bag batoh = new Bag();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce předmětů v batohu. */
    private final Collection<Something> obsah = new ArrayList<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    static Bag getBatoh()
    {
        return batoh;
    }



    /***************************************************************************
     * Vytvoří prázdný batoh.
     * Konstruktor je soukromý, aby nebylo možno omylem vytvářet další batohy.
     */
    private Bag()
    {
        //Pro vytvoření batohu stačí prázdný konstruktor -
        //vše potřebné je vykonáno v deklaracích atributů
    }



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================

    /***************************************************************************
     * Vrátí kapacitu batohu, tj. kolik předmětů se do něj vejde.
     *
     * @return Kapacita batohu
     */
    @Override
    public int getCapacity()
    {
        return 2;
    }


    /***************************************************************************
     * Vrátí kolekci předmětů uložených v batohu.
     *
     * @return Kolekce předmětů v batohu
     */
    @Override
    public Collection<Something> getObjects()
    {
        return Collections.unmodifiableCollection(obsah);
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Inicializuje jedináčka a připraví jej pro novou hru.
     */
    static void inicializuj()
    {
        batoh.obsah.clear();
    }


    /***************************************************************************
     * Zjistí, zda batoho obsahuje předmět zadaný svým názvem.
     *
     * @param  název Název předmětu v batohu
     * @return {@code true} pokud je předmět v batohu, jinak {@code falše}
     */
    boolean obsahuje(String název) {
        Something našel = (Something)Utilities.najdi(název, obsah);
        return (našel != null);
    }


    /***************************************************************************
     * Přidá zadaný předmět do batohu.
     *
     * @param  předmět Předmět přidávaný do batohu
     * @return {@code true} pokud byl předmět do batohu vložen
     */

    boolean přidej(Something předmět) {
        boolean zvedám = (obsah.size() < KAPACITA)  &&  obsah.add(předmět);
        return zvedám;
    }


    /***************************************************************************
     * Vyjme z batohu předmět zadaný svým názvem.
     *
     * @param  název Název předmětu vybíraného z batohu
     * @return Byl-li předmět v batohu, vrátí odkaz na předmět,
     *         jinak vrátí prázdný odkaz {@code null}
     */
    Something vyjmi(String název) {
        Something našel = (Something)Utilities.najdi(název, obsah);
        if (našel != null) {
            obsah.remove(našel);
        }
        return našel;
    }


    /** {@inheritDoc} */
    @Override
    public String toString()  {
        return "Batoh obsahující " + obsah;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//          Bag instance = new Bag();
//     }
//     /** @param args Parametry příkazového řádku - nepoužité. */
//     public static void main(String... args) { test(); }
}
