/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_game.gameg;

import cz.vse.adv_framework.game_gui.IListener;
import cz.vse.adv_framework.game_gui.IPlaceG;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
////%A+ o2012p4
//import java.util.Set;
//import java.util.TreeSet;
////%A-


import static cz.vse.adv_framework.test_util.default_game.gameg.Thing.HEAVY;
import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code Room} představují jednotlivé místnosti
 * v procházeném byte. Třída {@code Room} je současně schopna vybudovat
 * na základě zadaných parametrů cely byt z jednotlivých místností.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public enum Room implements IPlaceG
{
//== HODNOTY VÝČTOVÉHO TYPU ====================================================

    /** Vstupní místnost bytu odkud  se dá přesunout do většiny místností. */
    Předsíň (new Point(550, 300),
        "Vstupní místnost bytu odkud  se dá přesunout do většiny místností.",
        new String[] { LOŽNICE, OBÝVÁK, KOUPELNA },
        HEAVY+BOTNÍK, DEŠTNÍK
//        new String[] { "Ložnice", "Obývák", "Koupelna" },
//        HEAVY+"Botník", "Deštník"
    ),

    /** Koupelna spojená se záchodem. */
    Koupelna (new Point(570, 100),
        "Koupelna spojená se záchodem.",
        new String[] { PŘEDSÍŇ },
        BRÝLE, HEAVY+UMYVADLO, ČASOPIS
//        new String[] { "Předsíň" },
//        "Brýle", HEAVY+"Umyvadlo", "Časopis"
    ),

    /** Druhá nejmilejší místnost v bytě, v níž osazenstvo tráví
     *  mnohé příjemné chvilky. */
    Ložnice (new Point(460, 100),
        "Druhá nejmilejší místnost v bytě, v níž osazenstvo tráví " +
        "mnohé příjemné chvilky.",
        new String[] { KUCHYŇ, PŘEDSÍŇ },
        HEAVY+POSTEL, HEAVY+ZRCADLO, ŽUPAN
//        new String[] { "Kuchyň", "Předsíň" },
//        HEAVY+"Postel", HEAVY+"Zrcadlo", "Župan"
    ),

    /** Místnost, ve které se moc nezdržuju. */
    Obývák (new Point(200, 300),
        "Místnost, ve které se moc nezdržuju.",
        new String[] { KUCHYŇ, PŘEDSÍŇ },
        HEAVY+TELEVIZE
//        new String[] { "Kuchyň", "Předsíň" },
//        HEAVY+"Televize"
    ),

    /** Moje nejoblíbenější mistnost. */
    Kuchyň (new Point(200, 100),
        "Moje nejoblíbenější místnost.",
        new String[] { LOŽNICE, OBÝVÁK },
        HEAVY+LEDNIČKA, PAPÍR
//        new String[] { "Ložnice", "Obývák" },
//        HEAVY+"Lednička", "Papír"
    ),

    /** Nejčastější cíl mých výletů. */
    Lednička (new Point(30, 30),
        "Nejčastější cíl mých výletů.",
        new String[] {},
        "@"+PIVO, "@"+PIVO, "@"+PIVO, SALÁM, HOUSKA, "@"+VÍNO, "@"+RUM
//       new String[] {},
//        "@Pivo", "@Pivo", "@Pivo", "Salám", "Houska", "@Víno", "@Rum"
    );



//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Mapa sloužící k převodu názvu místnosti na odpovídající místnost. */
    private static final Map<String,Room> NAME_2_ROOM = new HashMap<>();

    /** Posluchači očekávající oznámení změně předmětů v místnosti. */
    private static final List<IListener<IPlaceG>> OBJECT_LISTENERS =
                                                  new ArrayList<>();

    /** Posluchači očekávající oznámení změně sousedů místnosti. */
    private static final List<IListener<IPlaceG>> NEIGHBOR_LISTENERS =
                                                  new ArrayList<>();

////%A+ o2012p4
//    private static final Set<String> nenavštívené = new TreeSet<>();
////%A-



//== VARIABLE CLASS ATTRIBUTES =================================================

    private static Room currentPlace;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    //Teprve nyní existují všechny instance i všechny potřebné kontejnery,
    //a proto lze teprve nyní inicializovat sousedy místností
    static {
        //Zapamatuji si jména všech místností malými písmeny
        for (Room m : values()) {
            NAME_2_ROOM.put(m.name().toLowerCase(), m);
        }
        //Jména výchozích sousedů převedu na sousedy a ty uložím
        for (Room m : values()) {
            for (int i=0;   i < m.initialNeigborNames.length;   i++) {
                m.initialNeighbors[i] = NAME_2_ROOM.get(
                                      m.initialNeigborNames[i].toLowerCase());
            }
            m.initialNeigborNames = null;  //Už nebudou potřeba
        }
        initializeRooms();
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Popis významu a účelu dané místnosti. */
    private final String description;

    /** Sousedé místnosti na začátku hry. */
    private final Room[] initialNeighbors;

    /* Předměty v místnosti na počátku hry. */
    private final Thing[] initialObjects;

    /** Aktuální sousedé dané místnosti. */
    private final Collection<Room> neighbors  = new ArrayList<>();

    /** Předměty nacházející se aktuálně v místnosti. */
    private final Collection<Thing> objects = new ArrayList<>();

    /** Pozice místnosti na plánku hry. */
    private final Point position;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Pomocná proměnná používaná v konstruktoru třídy
     *  pro inicializaci pole výchozíSousedé. */
    private String[] initialNeigborNames;



//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================
////%A+ o2012p4
//
//    /***************************************************************************
//     * Vrátí kolekci doposud nenavštívených místností.
//     *
//     * @return Kolekce doposud nenavštívených místností
//     */
//    public static Collection<String> getNenavštívené()
//    {
//        return nenavštívené;
//    }
//
////%A-

    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se právě nachází hráč.
     *
     * @return Požadovaný odkaz
     */
    static Room getCurrentRoom()
    {
        return currentPlace;
    }


    /***************************************************************************
     * Nastaví zadanou místnost jako aktuální.
     *
     * @param room Nastavovaná aktuální místnost
     */
    static void setCurrentRoom(Room room) {
        currentPlace = room;
        noticeAll();
////%A+ o2012p4
//        nenavštívené.remove(místnost.getName().toLowerCase());
////%A-
    }


    /***************************************************************************
     * Vrátí odkaz na místnost se zadaným názvem.
     *
     * @param name Název hledané místnosti
     * @return Požadovaný odkaz
     */
    static Room getRoom(String name)
    {
        Room cil = NAME_2_ROOM.get(name.toLowerCase());
        return cil;
    }


    /***************************************************************************
     * Vrátí kolekci odkazů na jednotlivé místnosti.
     *
     * @return Požadovaná kolekce
     */
    static Collection<Room> getRooms()
    {
        return new ArrayList<>(Arrays.asList(values()));
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Inicializuje místnosti do výchozího stavu pro spuštění hry,
     * tj. nastavuje jim výchozí sousedy a výchozí přítomné předměty.
     * Metoda přitom předpokládá, že jsou již přihlášeni všichni posluchači.
     */
    static void initializeRooms()
    {
        for (Room room : values()) {
            room.neighbors.clear();
            room.neighbors.addAll(Arrays.asList(room.initialNeighbors));
            room.objects.clear();
            room.objects.addAll(Arrays.asList(room.initialObjects));
        }
        currentPlace = Předsíň;
        noticeAll();
////%A+ o2012p4
//        nenavštívené.clear();
//        for (Room room : values()) {
//            nenavštívené.add(room.getName().toLowerCase());
//        }
//        nenavštívené.remove(aktuálníMístnost.getName().toLowerCase());
////%A-
    }


    /***************************************************************************
     * Obvolá všechny přihlášené posluchače zadaného stavu,
     * tj. posluchače předmětů i posluchače sousedů,
     * a tím jim oznámí, že se množina sledovaných objektů změnila.
     */
    static void noticeAll()
    {
        obvolejPosluchače(OBJECT_LISTENERS);
        obvolejPosluchače(NEIGHBOR_LISTENERS);
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny předmětů v místnosti
     *
     * @param listener Přihlašovaný posluchač
     */
    static void addObjectListener(IListener<IPlaceG> listener)
    {
        OBJECT_LISTENERS.add(listener);
        listener.notice(getCurrentRoom());
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny předmětů v místnosti
     *
     * @param listener Odhlašovaný posluchač
     */
    static void removeObjectListener(IListener<IPlaceG> listener)
    {
        OBJECT_LISTENERS.remove(listener);
    }


    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * které informuje o změně množiny sousedů místnosti
     *
     * @param listener Přihlašovaný posluchač
     */
    static void addNeighborsListener(IListener<IPlaceG> listener)
    {
        NEIGHBOR_LISTENERS.add(listener);
        listener.notice(getCurrentRoom());
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * které informuje o změně množiny sousedů místnosti
     *
     * @param listener Odhlašovaný posluchač
     */
    static void removeNeighborsListener(IListener<IPlaceG> listener)
    {
        NEIGHBOR_LISTENERS.remove(listener);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří místnost se zadanými charakteristikami, popisem účelu místnosti,
     * pozicí případného návštěvníka (= pozicí na plánku),
     * seznamem možných sousedů (kam všude se dá z místnosti jít) a
     * seznamem v místnosti se vyskytujících předmětů.
     *
     * @param pozice   Pozice hráče na plánku bytu při pobytu v dané místnosti
     * @param popis    Popis účelu dané místnosti
     * @param sousedé  Pole s názvy sousedů dané místnosti, tj. s názvy
     *                 místností, do nichž se dá z dané místnosti přejít.
     * @param předměty Názvy předmětů, které se v místnosti vyskytují.
     *                 Konstruktor nechá tyto předměty vyrobit.
     */
    private Room(Point pozice, String popis,
                     String[] sousedé, String... předměty)
    {
        this.position = pozice;
        this.description  = popis;
        this.initialNeigborNames = sousedé;
        this.initialNeighbors  = new Room[sousedé.length];
        this.initialObjects = new Thing [předměty.length];
        for (int i=0;   i < předměty.length;   i++) {
            Thing předmět = new Thing(předměty[i]);
            initialObjects[i] = předmět;
        }
    }



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================

    /***************************************************************************
     * Vrátí název místnosti.
     *
     * @return Název místnosti
     */
    @Override
    public String getName()
    {
        return name();
    }

//
//    /***************************************************************************
//     * Vrátí popis místnosti, který danou místnost stručné charakterizuje.
//     *
//     * @return Pozadovany popis
//     */
//    @Override
//    public String getDescription()
//    {
//        return description;
//    }
//

    /***************************************************************************
     * Vrátí kolekci předmětů nacházejících se v dané místnosti.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<Thing> getObjects()
    {
        //Vrací nemodifikovatelnou kolekci,
        //aby interně používanou kolekci nebylo možno zvenku změnit
        return Collections.unmodifiableCollection(objects);
    }


    /***************************************************************************
     * Vrátí kolekci místností, do nichž je možno se z teto místnosti přesunout.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<Room> getNeighbors()
    {
        //Vrací "kopii" požadované kolekce,
        //aby interně používanou kolekci nebylo možno zvenku změnit
        return new ArrayList<>(neighbors);
    }


    /***************************************************************************
     * Vrátí pozici místnosti na plánu hry,
     * přesněji pozici hráče nacházejícího se v dané místnosti.
     * Na této pozici bude hráč při pobytu v dané místnosti zobrazen.
     *
     * @return Požadovaná pozice
     */
    @Override
    public Point getPosition()
    {
        return position;
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Název instance
     */
    @Override
    public String toString()
    {
        return name();
    }


    /***************************************************************************
     * Odloží zadaný předmět v místnosti.
     *
     * @param  předmět Předmět odkládaný v místnosti
     */
    void polož(Thing předmět) {
        objects.add(předmět);
        obvolejPosluchače(OBJECT_LISTENERS);
    }


    /***************************************************************************
     * Odebere předmět zadaného názvu z místnosti.
     * Odebrat je možné pouze zvednutelné předměty.
     *
     * @param název  Název odebíraného předmětu
     * @return Byl-li předmět z místnosti odebrán, vrátí tento předmět.
     *         Nebyl-li předmět zvednutelný, vrátí {@link Thing#MASS}.
     *         Nebyl-li v místnosti požadovaný předmět, vrátí {@code null}.
     */
    Thing zvedni(String název)
    {
        String jméno;
        Thing našel = null;
        for (Thing předmět : objects) {
            jméno = předmět.getName();
            if (jméno.equalsIgnoreCase(název))  {
                if (předmět.getWeight() >= 0)  {
                    objects.remove(předmět);
                    obvolejPosluchače(OBJECT_LISTENERS);
                    našel = předmět;
                } else {
                    našel = Thing.MASS;
                }
                break;
            }
        }
        return našel;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Obvolá všechny přihlášené posluchače zadaného stavu, čímž jím oznámí,
     * že se množina sledovaných objektů (možná) změnila.
     *
     * @param posluchači Seznam obvolávaných posluchačů
     */
    private static void obvolejPosluchače(
                        Collection<IListener<IPlaceG>> posluchači)
    {
        for ( IListener<IPlaceG> posluchač : posluchači) {
            posluchač.notice(currentPlace);
        }
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
}
