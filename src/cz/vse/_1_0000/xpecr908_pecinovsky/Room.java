/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr908_pecinovsky;

import cz.vse.adv_framework.game_txt.IObject;
import cz.vse.adv_framework.game_txt.IPlace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instance třídy {@code Room} představují prostory v demonstrační hře.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class Room implements IPlace
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Kolekce všech prostorů v dané hře. */
    private static final Collection<Room> ALL_ROOMS = new ArrayList<>();

    /** Mapa sdružující název prostoru s příslušným prostorem. */
    private static final Map<String, Room> NAME_2_ROOM = new HashMap<>();

    /** Vstupní místnost bytu odkud se dá přesunout do většiny místností. */
    private static final Room PŘEDSÍŇ = new Room("Předsíň",
            new String[] {"Koupelna", "Ložnice", "Obývák"},
            "Botník", "Deštník"
    );

    /** Koupelna spojená se záchodem. */
    private static final Room KOUPELNA = new Room("Koupelna",
            new String[] { "Předsíň" },
            "Brýle", "Umyvadlo", "Časopis"
    );

    /** Druhá nejmilejší místnost v bytě, v níž osazenstvo tráví
     *  mnohé příjemné chvilky. */
    private static final Room Ložnice = new Room("Ložnice",
            new String[] { "Kuchyň", "Předsíň" },
            "Postel", "Zrcadlo", "Župan"
   );

    /** Místnost, ve které se moc nezdržuju. */
    private static final Room Obývák = new Room("Obývák",
            new String[] { "Kuchyň", "Předsíň" },
            "Televize"
   );

    /** Moje nejoblíbenější místnost. */
    private static final Room Kuchyň = new Room("Kuchyň",
            new String[] { "Ložnice", "Obývák" },
            "Lednička", "Papír"
   );

    /** Nejčastější cíl mých výletů. */
    private static final Room Lednička = new Room("Lednička",
            new String[] {},
            "Pivo", "Pivo", "Pivo", "Salám", "Houska", "Víno", "Rum"
    );



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Proměnná udržující informaci o tom,
     *  v jaké místnosti se hráč právě nachází. */
    private static Room currentPlace;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název dané místnosti. */
    private final String name;

    /** Názvy sousedů dané místnosti na počátku hry. */
    private final String[] neighborNames;

    /** Názvy objektů nacházejících se v místnosti na počátku hry. */
    private final String[] objectNames;

    /** Aktuální sousedé dané místnosti. */
    private final Collection<Room> neighbors = new ArrayList<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí kolekci všech prostorů vyskytujících se v dané hře.
     *
     * @return Kolekce všech prostorů vyskytujících se ve hře
     */
    static Collection<Room> getAllPlaces()
    {
        return ALL_ROOMS;
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se hráč právě nachází.
     *
     * @return Odkaz na aktuální místnost
     */
    static Room getCurrentPlace()
    {
        return currentPlace;
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Uvede všechny prostory  do počátečního stavu vyžadovaného na počátku hry
     * a současně nastaví výchozí prostor, v němž je hráč na počátku hry.
     */
    static void initialize()
    {
        for (Room room : ALL_ROOMS) {
            room.initializeYourself();
        }
        currentPlace = PŘEDSÍŇ;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří prostor s daným názvem a počáteční sadou sousedů a objektů.
     *
     * @param name      Název vytvářeného prostoru
     * @param neighbors Názvy sousedů prostoru na počátku hry
     * @param objects   Názvy objektů v prostoru na počátku hry
     */
    private Room(String name, String[] neighbors, String... objects)
    {
        this.name          = name;
        this.neighborNames = neighbors;
        this.objectNames   = objects;

        ALL_ROOMS.add(this);
        NAME_2_ROOM.put(name, this);
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
        //TODO Room.getName - Metoda ještě není hotova
        throw new UnsupportedOperationException("\nMetoda ještě není hotova.");
    }


    /***************************************************************************
     * Vrátí kolekci sousedů daného prostoru, tj. kolekci prostorů,
     * do nichž je možno se z tohoto prostoru přesunout příkazem typu
     * {@link Commands.Type#MOVE Commands.Type.MOVE}.
     *
     * @return Kolekce sousedů
     */
    @Override
    public Collection<? extends IPlace> getNeighbors()
    {
        //TODO Room.getNeighbors - Metoda ještě není hotova
        throw new UnsupportedOperationException("\nMetoda ještě není hotova.");
    }


    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném prostoru.
     *
     * @return Kolekce objektů nacházejících se v daném prostoru
     */
    @Override
    public Collection<? extends IObject> getObjects()
    {
        //TODO Room.getObjects - Metoda ještě není hotova
        throw new UnsupportedOperationException("\nMetoda ještě není hotova.");
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Uvede místnost do výchozího stavu požadovaného na počátku hry.
     */
    private void initializeYourself()
    {
        neighbors.clear();
        for (String nname : neighborNames) {
            neighbors.add(NAME_2_ROOM.get(nname));
        }
        //TODO Room.initializeYourself - Metoda ještě není hotova
        throw new UnsupportedOperationException("Metoda ještě není hotova.");
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
