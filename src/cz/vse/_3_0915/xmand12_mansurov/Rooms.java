package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.IPlace;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * Instance třídy {@literal Rooms} představují prostory v hře.
 *
 * @author  Daulet Mansurov
 * @version 7.4
 */
public class Rooms implements IPlace, Serializable
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Kolekce všech prostorů v dané hře. */
    private static final ListINamed<Rooms> ALL_ROOMS = new ListINamed<>();

    /** Mapa sdružující název prostoru s příslušným prostorem. */
    private static final Map<String, Rooms> NAME_2_ROOM = new HashMap<>();

    private static final Rooms JESKYNĚ = new Rooms("Jeskyně",
            new String[] {"Džungle"},
            "Lopata", "Provaz", "Láhev"
    );

    private static final Rooms DŽUNGLE = new Rooms("Džungle",
            new String[] { "Město", "Jeskyně" },
            "#Palma", "#Kamen", "@Vrah"
    );


    private static final Rooms MĚSTO = new Rooms("Město",
            new String[] { "Džungle", "Taverna", "Rezidence", "Dok", "Přistav" }
    );

    private static final Rooms TAVERNA = new Rooms("Taverna",
            new String[] { "Město" },
            "@Opilec", "@Čišník", "@Barmen"
    );

    private static final Rooms REZIDENCE = new Rooms("Rezidence",
            new String[] { "Město" },
            "@Gubernátor", "Soška", "10000"
   );


    private static final Rooms DOK = new Rooms("Dok",
            new String[] { "Město" },
            "@Prodavač", "Květiny"
   );


    private static final Rooms PŘISTAV = new Rooms("Přistav",
            new String[] {"Město", "Loď"},
            "Provaz", "Veslo"
    );

    private static final Rooms LOĎ = new Rooms("Loď",
            new String[] {"Přistav", "Břeh"},
            "#Sud", "Rum", "#Dělo", "#Sirka"
    );

    private static final Rooms BŘEH = new Rooms("Břeh",
            new String[] {"Loď", "Vodopád"},
            "Krab", "Ulita"
    );

    private static final Rooms VODOPÁD = new Rooms("Vodopád",
            new String[] {"Břeh"},
            "Hůl"
    );

    private static final Rooms TRUHLICE = new Rooms("Truhlice",
            new String[] {},
            "Deník"
    );

    private static final Rooms _TEST_10_ = new Rooms("_TEST_10_",
            new String[] {"Jeskyně"},
            "BAG_FULL"
    );

    private static final Rooms RESTART = new Rooms("Restart",
            new String[] {"Jeskyně"}
    );

//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Proměnná udržující informaci o tom,
     *  v jaké místnosti se hráč právě nachází. */
    private static Rooms currentPlace;

//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název dané místnosti. */
    private final String name;

    /** Názvy sousedů dané místnosti na počátku hry. */
    private final String[] neighborNames;

    /** Názvy objektů nacházejících se v místnosti na počátku hry. */
    private final String[] objectNames;

    /** Aktuální sousedé dané místnosti. */
    private final ListINamed<Rooms> neighbors = new ListINamed<>();

    /** Aktuální sousedé dané místnosti. */
    private final ListINamed<Things> objects = new ListINamed<>();

//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí kolekci všech prostorů vyskytujících se v dané hře.
     *
     * @return Kolekce všech prostorů vyskytujících se ve hře
     */
    static ListINamed<Rooms> getAllPlaces()
    {
        return ALL_ROOMS;
    }

    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se hráč právě nachází.
     *
     * @return Odkaz na aktuální místnost
     */
    static Rooms getCurrentPlace()
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
        for (Rooms room : ALL_ROOMS) {
            room.initializeYourself();
        }
        currentPlace = JESKYNĚ;
    }

    public static void loadRoomsFrom(Map<String, Rooms> newRoomsMap) {
        for (String key : NAME_2_ROOM.keySet()) {
            NAME_2_ROOM.put(key, newRoomsMap.get(key));
        }
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
    private Rooms(String name, String[] neighbors, String... objects)
    {
        this.name          = name;
        this.neighborNames = neighbors;
        this.objectNames   = objects;

        ALL_ROOMS.add(this);
        NAME_2_ROOM.put(name, this);

    }

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
    public ListINamed<Rooms> getNeighbors()
    {
        return neighbors;
    }

    public void setCurrentPlace(Rooms newPlace){
        currentPlace = newPlace;
    }

    public void add(Things thing){
        objects.add(thing);
    }

    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném prostoru.
     *
     * @return Kolekce objektů nacházejících se v daném prostoru
     */
    @Override
    public ListINamed<Things> getObjects()
    {
        return objects;
    }

    /**
     * Metoda odtranujici predmet z aktualniho prostoru
     * @param thingToRemove
     */
    public void removeObject(Things thingToRemove){
        objects.remove(thingToRemove);
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
        objects.clear();
        for (String nname : objectNames) {
            objects.add(new Things(nname));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getName();
    }
}
