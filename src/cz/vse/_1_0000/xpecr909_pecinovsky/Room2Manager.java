/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_du.I08T_SpaceManagerT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*******************************************************************************
 * Instances of class {@literal Room2Manager} represent ...
 * <p>
  * Dvojka v názvu třídy má symbolizovat, že pro definice jednotlivých prostorů
 * a definici jejich správce potřebujeme dvě třídy, dva zdrojové kódy.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
class Room2Manager implements IRoomManager,
                              I08T_SpaceManagerT
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final Room2Manager SINGLETON = new Room2Manager();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce všech prostorů v dané hře. */
    private final List<Room2> allRooms = new ArrayList<>();

    /** Mapa sdružující název prostoru s příslušným prostorem. */
    private final Map<String, Room2> name2room = new HashMap<>();

    /** Místnost, v níž hra začíná. */
    private final Room2 startingRoom2;


//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Proměnná udržující informaci o tom,
     *  v jaké místnosti se hráč právě nachází. */
    private Room2 currentPlace;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *
     * @return
     */
    public static Room2Manager getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     *
     */
    private Room2Manager()
    {
        add("Předsíň",
            new String[] {"Koupelna", "Ložnice", "Obývák"},
            "Botník", "Deštník");

        add("Koupelna",
            new String[] { "Předsíň" },
            "Brýle", "Umyvadlo", "Časopis");

        add("Ložnice",
            new String[] { "Kuchyň", "Předsíň" },
            "Postel", "Zrcadlo", "Župan");

        add("Obývák",
            new String[] { "Kuchyň", "Předsíň" },
            "Televize");

        add("Kuchyň",
            new String[] { "Ložnice", "Obývák" },
            "Lednička", "Papír");

        add("Lednička",
            new String[] {},
            "Pivo", "Pivo", "Pivo", "Salám", "Houska", "Víno", "Rum");

        startingRoom2 = allRooms.get(0);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech prostorů vyskytujících se v dané hře.
     *
     * @return Kolekce všech prostorů vyskytujících se ve hře
     */
    @Override
    public
    Collection<Room2> getAllPlaces()
    {
        return allRooms;
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se hráč právě nachází.
     *
     * @return Odkaz na aktuální místnost
     */
    @Override
    public
    Room2 getCurrentPlace()
    {
        return currentPlace;
    }


    /***************************************************************************
     * Vrátí odkaz na prostor se zadaným názvem.
     *
     * @param roomName
     * @return Odkaz na prostor se zadaným názvem
     */
    @Override
    public
    Room2 getPlace(String roomName)
    {
        return name2room.get(roomName);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Uvede všechny prostory  do počátečního stavu vyžadovaného na počátku hry
     * a současně nastaví výchozí prostor, v němž je hráč na počátku hry.
     */
    @Override
    public
    void initialize()
    {
        for (Room2 room : allRooms) {
            room.initializeYourself();
        }
        currentPlace = startingRoom2;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     *
     * @param name
     * @param neighbors
     * @param objects
     */
    private void add(String name, String[] neighbors, String... objects)
    {
        Room2 room = new Room2(name, neighbors, objects);

        allRooms .add(room);
        name2room.put(name, room);
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
