/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util.default_game.gamet;

import cz.vse.adv_framework.game_txt.IPlace;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import static cz.vse.adv_framework.test_util.default_game.gamet.Something.HEAVY;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;
import java.util.Set;
import java.util.TreeSet;



/*******************************************************************************
 * Instance třídy {@code Room} představují jednotlivé místnosti
 * v procházeném byte. Třída {@code Room} je současně schopna vybudovat
 * na základě zadaných parametrů cely byt z jednotlivých místností.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public enum Room implements IPlace
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
    private static final Map<String,Room> název2místnost = new HashMap<>();

//%A+ o2012p4
    private static final Set<String> nenavštívené = new TreeSet<>();
//%A-



//== VARIABLE CLASS ATTRIBUTES =================================================

    private static Room aktuálníMístnost;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    //Teprve nyní existují všechny instance i všechny potřebné kontejnery,
    //a proto lze teprve nyní inicializovat sousedy místností
    static {
        //Zapamatuji si jména všech místností malými písmeny
        for (Room m : values()) {
            název2místnost.put(m.name().toLowerCase(), m);
        }
        //Jména výchozích sousedů převedu na sousedy a ty uložím
        for (Room m : values()) {
            for (int i=0;   i < m.názvyVýchozíchSousedů.length;   i++) {
                m.výchozíSousedé[i] = název2místnost.get(
                                      m.názvyVýchozíchSousedů[i].toLowerCase());
            }
            m.názvyVýchozíchSousedů = null;  //Už nebudou potřeba
        }
        inicializuj();
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Popis významu a účelu dané místnosti. */
    private final String popis;

    /** Sousedé místnosti na začátku hry. */
    private final Room[] výchozíSousedé;

    /* Předměty v místnosti na počátku hry. */
    private final Something[] výchozíPředměty;

    /** Aktuální sousedé dané místnosti. */
    private final Collection<Room> sousedé  = new ArrayList<>();

    /** Předměty nacházející se aktuálně v místnosti. */
    private final Collection<Something>  předměty = new ArrayList<>();

    /** Pozice místnosti na plánku hry. */
    private final Point pozice;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Pomocná proměnná používaná v konstruktoru třídy
     *  pro inicializaci pole výchozíSousedé. */
    private String[] názvyVýchozíchSousedů;



//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================

//%A+ o2012p4
    /***************************************************************************
     * Vrátí kolekci doposud nenavštívených místností.
     *
     * @return Kolekce doposud nenavštívených místností
     */
    public static Collection<String> getNenavštívené()
    {
        return nenavštívené;
    }
//%A-


    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v níž se právě nachází hráč.
     *
     * @return Požadovaný odkaz
     */
    static Room getAktuálníMístnost()
    {
        return aktuálníMístnost;
    }


    /***************************************************************************
     * Nastaví zadanou místnost jako aktuální.
     *
     * @param místnost Nastavovaná aktuální místnost
     */
    static void setAktuálníMístnost(Room místnost) {
        aktuálníMístnost = místnost;
//%A+ o2012p4
        nenavštívené.remove(místnost.getName().toLowerCase());
//%A-
    }


    /***************************************************************************
     * Vrátí odkaz na místnost se zadaným názvem.
     *
     * @param název Název hledané místnosti
     * @return Požadovaný odkaz
     */
    static Room getMístnost(String název)
    {
        Room cil = název2místnost.get(název.toLowerCase());
        return cil;
    }


    /***************************************************************************
     * Vrátí kolekci odkazů na jednotlivé místnosti.
     *
     * @return Požadovaná kolekce
     */
    static Collection<Room> getMístnosti()
    {
        return new ArrayList<>(Arrays.asList(values()));
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Inicializuje místnosti do výchozího stavu pro spuštění hry,
     * tj. nastavuje jim výchozí sousedy a výchozí přítomné předměty.
     * Metoda přitom předpokládá, že jsou již přihlášeni všichni posluchači.
     */
    static void inicializuj()
    {
        for (Room room : values()) {
            room.sousedé.clear();
            room.sousedé.addAll(Arrays.asList(room.výchozíSousedé));
            room.předměty.clear();
            room.předměty.addAll(Arrays.asList(room.výchozíPředměty));
        }
        aktuálníMístnost = Předsíň;
//%A+ o2012p4
        nenavštívené.clear();
        for (Room room : values()) {
            nenavštívené.add(room.getName().toLowerCase());
        }
        nenavštívené.remove(aktuálníMístnost.getName().toLowerCase());
//%A-
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří mistnost se zadanými charakteristikami, popisem účelu místnosti,
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
        this.pozice = pozice;
        this.popis  = popis;
        this.názvyVýchozíchSousedů = sousedé;
        this.výchozíSousedé  = new Room[sousedé.length];
        this.výchozíPředměty = new Something [předměty.length];
        for (int i=0;   i < předměty.length;   i++) {
            Something předmět = new Something(předměty[i]);
            výchozíPředměty[i] = předmět;
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
//        return popis;
//    }
//

    /***************************************************************************
     * Vrátí kolekci předmětů nacházejících se v dané místnosti.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<Something> getObjects()
    {
        //Vrací nemodifikovatelnou kolekci,
        //aby interně používanou kolekci nebylo možno zvenku změnit
        return Collections.unmodifiableCollection(předměty);
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
        return new ArrayList<>(sousedé);
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Nazev instance
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
    void polož(Something předmět) {
        předměty.add(předmět);
    }


    /***************************************************************************
     * Odebere předmět zadaného názvu z místnosti.
     * Odebrat je možné pouze zvednutelné předměty.
     *
     * @param název  Název odebíraného předmětu
     * @return Byl-li předmět z místnosti odebrán, vrátí tento předmět.
     *         Nebyl-li předmět zvednutelný, vrátí předmět {@link Something#MASS}.
     *         Nebyl-li v místnosti požadovaný předmět, vrátí {@code null}.
     */
    Something zvedni(String název)
    {
        String jméno;
        Something našel = null;
        for (Something předmět : předměty) {
            jméno = předmět.getName();
            if (jméno.equalsIgnoreCase(název))  {
                if (předmět.getWeight() >= 0)  {
                    předměty.remove(předmět);
                    našel = předmět;
                } else {
                    našel = Something.MASS;
                }
                break;
            }
        }
        return našel;
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
//     }
//     /** @param args Parametry příkazového řádku - nepoužité. */
//     public static void main(String... args) { test(); }
}
