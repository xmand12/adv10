/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_framework.game_txt.IObject;
import cz.vse.adv_framework.game_txt.IPlace;

import java.util.ArrayList;
import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@literal Room2} představují prostory v demonstrační hře;
 * třídy přitom předpokládá, že správce prostorů je instancí jiné třídy.
 * <p>
  * Dvojka v názvu třídy má symbolizovat, že pro definice jednotlivých prostorů
 * a definici jejich správce potřebujeme dvě třídy, dva zdrojové kódy.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
class Room2 implements IPlace, IRoom
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název dané místnosti. */
    private final String name;

    /** Názvy sousedů dané místnosti na počátku hry. */
    private final String[] neighborNames;

    /** Názvy objektů nacházejících se v místnosti na počátku hry. */
    private final String[] objectNames;

    /** Aktuální sousedé dané místnosti. */
    private final Collection<Room2> neighbors = new ArrayList<>();

    /** Aktuální sousedé dané místnosti. */
    private final Collection<Thing> objects = new ArrayList<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří prostor s daným názvem a počáteční sadou sousedů a objektů.
     *
     * @param name      Název vytvářeného prostoru
     * @param neighbors Názvy sousedů prostoru na počátku hry
     * @param objects   Názvy objektů v prostoru na počátku hry
     */
    Room2(String name, String[] neighbors, String... objects)
    {
        this.name          = name;
        this.neighborNames = neighbors;
        this.objectNames   = objects;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název prostoru.
     *
     * @return Název prostoru
     */
    @Override
    public String getName()
    {
        return name;
    }


    /***************************************************************************
     * Vrátí kolekci sousedů daného prostoru, tj. kolekci prostorů,
     * do nichž je možno se z tohoto prostoru přesunout příkazem typu
     * {@link Commands.Type#MOVE Commands.Type.MOVE}.
     *
     * @return Kolekce sousedů
     */
    @Override
    public Collection<Room2> getNeighbors()
    {
        return neighbors;
    }


    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném prostoru.
     *
     * @return Kolekce objektů nacházejících se v daném prostoru
     */
    @Override
    public Collection<? extends IObject> getObjects()
    {
        return objects;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Uvede místnost do výchozího stavu požadovaného na počátku hry.
     * <ul>
     *   <li>Nastaví počáteční sousedy místnosti.</li>
     *   <li>Nastaví počáteční objekty v místnosti.</li>
     * </ul>
     */
    void initializeYourself()
    {
        Room2Manager manager = Room2Manager.getInstance();
        neighbors.clear();
        for (String nName : neighborNames) {
            neighbors.add(manager.getPlace(nName));
        }
        objects.clear();
        for (String objectName : objectNames) {
            Thing thing = new Thing(objectName);
            objects.add(thing);
        }
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
