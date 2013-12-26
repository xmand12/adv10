/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_game.gameg;

import cz.vse.adv_framework.game_gui.IBagG;
import cz.vse.adv_framework.game_gui.IListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;



/*******************************************************************************
 * Instance třídy {@code Bag} představují úložiště, do nichž si hráči
 * odkládají sebrané předměty či jejich ekvivalenty.
 * Instance třídy je definovaná jako jedináček,
 * protože celá hra vystačí s jediným batohem.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class Bag implements IBagG
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Maximální kapacita batohu - v této hře dvě ruce. */
    private static final int CAPACITY = 2;

    /** Schránka na přenášené předměty. V případě této hry se ale předměty
     *  přenášejí v rukou, a proto může hráč nést současně nejvýše dva. */
    private static final Bag SINGLETON = new Bag();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce předmětů v batohu. */
    private final Collection<Thing> content = new ArrayList<>();

    /** Posluchači očekávající oznámení o změně předmětů v batohu. */
    private final Collection<IListener<IBagG>> listeners = new ArrayList<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    static Bag getInstance()
    {
        return SINGLETON;
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
    public Collection<Thing> getObjects()
    {
        return Collections.unmodifiableCollection(content);
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Inicializuje jedináčka a připraví jej pro novou hru.
     */
    static void initializeBag()
    {
        SINGLETON.content.clear();
        SINGLETON.noticeAll();
    }


    /***************************************************************************
     * Zjistí, zda batoh obsahuje předmět zadaný svým názvem.
     *
     * @param  objectName Název předmětu v batohu
     * @return Je-li předmět v batohu, vrátí {@code true}, jinak {@code false}
     */
    boolean contains(String objectName) {
        Thing thing = (Thing)Utilities.find(objectName, content);
        return (thing != null);
    }


    /***************************************************************************
     * Přidá zadaný předmět do batohu.
     *
     * @param  object Předmět přidávaný do batohu
     * @return {@code true} pokud byl předmět do batohu vložen
     */

    boolean add(Thing object) {
        boolean adding = (content.size() < CAPACITY)  &&  content.add(object);
        if (adding) {
            noticeAll();
        }
        return  adding;
    }


    /***************************************************************************
     * Vyjme z batohu předmět zadaný svým názvem.
     *
     * @param  name Název předmětu vybíraného z batohu
     * @return Byl-li předmět v batohu, vrátí odkaz na předmět,
     *         jinak vrátí prázdný odkaz {@code null}
     */
    Thing remove(String name) {
        Thing found = (Thing)Utilities.find(name, content);
        if (found != null) {
            content.remove(found);
            noticeAll();
        }
        return found;
    }


    /** {@inheritDoc} */
    @Override
    public String toString()  {
        return "Batoh obsahující " + content;
    }



//+++ Přidáno pro rozšířené zadnání v předmětu 4IT115 ++++++++++++++++++++++++++

    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o výskytu očekávané události
     *
     * @param listener Přihlašovaný posluchač
     */
    public void addBagListener(IListener<IBagG> listener)
    {
        listeners.add(listener);
        listener.notice(this);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o výskytu očekávané události
     *
     * @param listener Odhlašovaný posluchač
     */
    public void removeBagListener(IListener<IBagG> listener)
    {
        listeners.remove(listener);
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Obvolá všechny přihlášené posluchače, čímž jím oznámí,
     * že se obsah batohu změnil.
     */
    private void noticeAll()
    {
        for (IListener<IBagG> posluchač : listeners) {
            posluchač.notice(this);
        }
    }



//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
}
