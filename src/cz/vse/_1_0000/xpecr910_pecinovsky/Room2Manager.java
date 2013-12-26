/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;



/*******************************************************************************
 * Instance třídy {@literal Room2Manager} funguje jako správce prostorů hry.
 * <p>
 * Dvojka v názvu třídy má symbolizovat, že pro definice jednotlivých prostorů
 * a definici jejich správce potřebujeme dvě třídy, dva zdrojové kódy.
 * Protože všechny prostory spravuje jediný správce,
 * je instance definována jako jedináček.
 * </p>
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
class Room2Manager
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final Room2Manager SINGLETON = new Room2Manager();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Kolekce všech prostorů v dané hře. */
    private final ListINamed<Room2> allRooms = new ListINamed<>();

//    //Verze používající mapu
//    /** Mapa sdružující název prostoru s příslušným prostorem. */
//    private final Map<String, Room2> name2room = new HashMap<>();

    //Verze používající seznam pojmenovaných
    //Nepotřebuje zavádět další atribut, protože vystačí s allRooms

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
     * Vrátí odkaz na správce prostorů.
     * @return Odkaz na správce prostorů
     */
    public static Room2Manager getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     * Vytvoří správce prostorů a inicializuje jej.
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

        add("_TEST_10_",
            new String[] { "Předsíň" },
            "BAG_FULL");

        startingRoom2 = allRooms.get(0);
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci všech prostorů vyskytujících se v dané hře.
     *
     * @return Kolekce všech prostorů vyskytujících se ve hře
     */
    ListINamed<Room2> getAllPlaces()
    {
        return allRooms;
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se hráč právě nachází.
     *
     * @return Odkaz na aktuální místnost
     */
    Room2 getCurrentPlace()
    {
        return currentPlace;
    }


    /***************************************************************************
     * Nastaví aktuální prostor, v němž se vyskytuje hráč.
     *
     * @param currentPlace Nastavovaný prostor
     */
    public void setCurrentPlace(Room2 currentPlace)
    {
        this.currentPlace = currentPlace;
    }


    /***************************************************************************
     * Vrátí odkaz na prostor se zadaným názvem.
     *
     * @param roomName Název požadovaného prostoru
     * @return Odkaz na prostor se zadaným názvem
     */
    Room2 getPlace(String roomName)
    {
//    //Verze používající mapu
//        return name2room.get(roomName);

        //Verze používající seznam pojmenovaných
        return allRooms.getINamed(roomName);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Uvede všechny prostory  do počátečního stavu vyžadovaného na počátku hry
     * a současně nastaví výchozí prostor, v němž je hráč na počátku hry.
     */
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
     * Vytvoří prostor se zadaným názvem a názvy počátečních sousedů
     * a uložených objektů a přidá tento prostor mezi spravované.
     *
     * @param name      Název vytvářeného prostoru
     * @param neighbors Názvy počátečních sousedů
     * @param objects   Názvy objektů v prostoru na počátku hry
     */
    private void add(String name, String[] neighbors, String... objects)
    {
        Room2 room = new Room2(name, neighbors, objects);
        allRooms .add(room);

//    //Verze používající mapu
//        name2room.put(name, room);

        //Verze používající seznam pojmenovaných
        //Vše už je vyřešeno přidáním do allRooms
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
