/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr911_pecinovsky;

import cz.vse.adv_framework.game_txt.IBag;



/*******************************************************************************
 * Instance třídy {@code Hands} představuje batoh.
 * Je definována jako jedináček.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Hands implements IBag
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Kapacita batohu – dvě ruce. */
    private static final int CAPACITY = 2;

    /** Jediná instance třídy (jedináček). */
    private static final Hands INSTANCE = new Hands();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce s obsahem batohu. */
    private final ListINamed<Thing> objects = new ListINamed<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Váha objektů obsažených v batohu. Z obsahu tohoto atributu se odvodí,
     *  nakolik je aktuálně naplněna kapacita batohu. */
    private int content = 0;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí jedinou instanci třídy – jedináčka.
     *
     * @return Jediná instance třídy
     */
    public static Hands getInstance()
    {
        return INSTANCE;
    }


    /***************************************************************************
     * Vytvoří instanci batohu.
     */
    private Hands()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kapacitu batohu, tj. součet vah objektů,
     * které je možno současně uložit do batohu.
     *
     * @return Kapacita batohu
     */
    @Override
    public int getCapacity()
    {
        return CAPACITY;
    }


    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném prostoru.
     *
     * @return Kolekce objektů nacházejících se v daném prostoru
     */
    @Override
    public ListINamed<Thing> getObjects()
    {
        return objects;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přidá do kontejneru zadaný objekt a vrátí informaci o tom,
     * zda se jej podařilo přidat.
     *
     * @param thing Přidávaný objekt
     * @return Byl-li objekt skutečně přidán, vrátí {@code true},
     *         pokud se již do batohu nevešel, vrátí {@code false}
     */
    public boolean add(Thing thing)
    {
        int thingWeight = thing.getWeight();
        int newContent  = content + thingWeight;
        if (newContent <= CAPACITY) {
            objects.add(thing);
            content = newContent;
            return true;
        }
        return false;
    }


    /***************************************************************************
     * Přidá do kontejneru zadaný objekt a vrátí informaci o tom,
     * zda se jej podařilo přidat.
     *
     * @param thing Přidávaný objekt
     * @return Byl-li objekt skutečně přidán, vrátí {@code true},
     *         pokud se již do batohu nevešel, vrátí {@code false}
     */
    public Thing remove(String thingName)
    {
        Thing thing = objects.getINamed(thingName);
        if (thing != null) {
            objects.remove(thing);
            content -= thing.getWeight();
        }
        return thing;
    }


    /***************************************************************************
     * Inicializuje batoh tím, že jej naplní výchozí sadou objektů –
     * v této hře jej pouze vyprázdní.
     */
    void initialize()
    {
        objects.clear();
        content = 0;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
