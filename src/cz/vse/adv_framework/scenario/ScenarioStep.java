/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.scenario;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.utilities.DBG_Logger;

import cz.vse.adv_framework.utilities.Util;

import java.util.Arrays;


import static cz.vse.adv_framework.utilities.CompareIgnoreCase.CIC;
import static cz.vse.adv_framework.utilities.FormatStrings.*;
import static cz.vse.adv_framework.utilities.Util.*;



/*******************************************************************************
 * Třída {@code ScenarioStep} slouží jako přepravka k uchovávání informaci
 * o jednotlivých zadávaných příkazech scénáře
 * a o očekávaných stavech programu po jejich provedení.
 * <p>
 * Kroky scénáře obsahují informace sloužící k ověření
 * správné reakce hry na příkaz zadávaný v příslušném kroku scénáře.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public class ScenarioStep implements Cloneable
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Zpráva v kroku scénáře, u níž se nebude testovat shoda
     *  se zprávou vrácenou hrou v reakci na zadaný příkaz.  */
    public static final String IGNORED_MESSAGE = "¤¤¤ TEST ¤¤¤";

    /** Loger, prostřednictvím nějž zaznamenáváme veškeré informace. */
    protected final static DBG_Logger DBG = DBG_Logger.getInstance();



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Index posledního vytvořeného kroku scénáře -
     *  z něj se odvozuje index příštího kroku. */
    private static int lastIndex = 0;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== KONSTANTNÍ ATRIBUTY INSTANCI ==============================================

    /** Typ daného kroku scénáře. */
    public final TypeOfStep typeOfStep;

    /** Zadávaný příkaz. */
    public final String command;

    /** Zpráva vydaná v reakci na zadanou akci; obsahuje-li {@code null},
     *  nepočítá se s ověřováním reakce hry. */
    public final String message;

    /** Prostor, v němž se bude hráč nacházet po vykonaní příkazu. */
    public final String place;

    /** Názvy prostorů, které bezprostředně sousedí s prostorem,
     *  v němž se bude hráč nacházet po vykonaní příkazu,
     *  a které jsou proto z tohoto prostoru přímo dostupné. */
    public final String[] neighbors;

    /** Názvy objektů aktuálně se nacházejících v prostoru,
     *  v němž se bude hráč nacházet po vykonaní příkazu. */
    public final String[] objects;

    /** Názvy objektů v batohu po vykonání příkazu. */
    public final String[] bag;



//== PROMĚNNÉ ATRIBUTY INSTANCI ================================================

    /** Index daného kroku scénáře - typicky jeho pořadí v rámci scénáře. */
    public int index;



//== NESOUKROMÉ METODY TŘÍDY ===================================================

    /***************************************************************************
     * Vrátí hodnotu, na níž bude navazovat index následně vytvořeného kroku.
     *
     * @return Požadovaná hodnota
     */
    public static int getLastIndex()
    {
        return lastIndex;
    }


    /***************************************************************************
     * Nastaví index příštího kroku a vrátí
     * index posledního vytvořeného kroku.
     *
     * @param next Index příštího kroku
     * @return Index posledního vytvořeného kroku
     */
    public static int setIndex(int next)
    {
        int   ret = lastIndex;
        lastIndex = next - 1;
        return ret;
    }


    /***************************************************************************
     * Vynuluje počítaný index kroků testu =>
     * nebude-li zadáno jinak, bude příští vytvořený krok označen jako první.
     */
    public static void clearIndex()
    {
        lastIndex = 0;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci založenou na aktuálním stavu zadané hry.
     * Index tohoto kroku bude o jedničku větší než index kroku vytvořeného
     * před ním, přesněji než poslední zapamatovaný index.
     *
     * @param command  Zadaný příkaz
     * @param message  Zprava vydaná v reakci na zadaný příkaz
     * @param game     Hra, z jejíhož aktuálního stavu se vytváří krok
     */
    public ScenarioStep(String command,  String message,  IGame game)
    {
        this(++lastIndex, TypeOfStep.tsNOT_SET, command, message,
             game.getCurrentPlace().getName(),
             Util.colINamed2arrString(game.getCurrentPlace().getNeighbors()),
             Util.colINamed2arrString(game.getCurrentPlace().getObjects()),
             Util.colINamed2arrString(game.getBag().getObjects()));
    }


    /***************************************************************************
     * Vytvoří krok umožňující otestování správné funkce hry.
     * Index tohoto kroku bude o jedničku větší než index kroku vytvořeného
     * před ním, přesněji než poslední zapamatovaný index.
     *
     * @param typeOfStep O který typ kroku se jedná
     * @param command    Zadaný příkaz
     * @param message    Zprava vydaná v reakci na zadaný příka
     * @param place      Název prostoru, v němž bude hráč vykonání příkazu
     * @param neighbors  Názvy prostorů, kam se je odsud možno přesunout
     * @param objects    Názvy objektů v aktuálním prostoru
     * @param bag        Názvy objektů v batohu
     */
    public ScenarioStep(TypeOfStep typeOfStep, String command, String message,
                        String   place,
                        String[] neighbors,  String[] objects,  String[] bag)
    {
        this(++lastIndex, typeOfStep,
             command, message, place, neighbors, objects, bag);
    }


    /***************************************************************************
     * Vytvoří krok umožňující otestování správné funkce hry,
     * přičemž tento krok bude mít zadaný index, na nějž budou navazovat
     * indexy následně vytořených kroků bez explicitně zadaného indexu.
     *
     * @param index      Číslo, které by mělo určovat pořadí daného kroku
       *                 v rámci jeho scénáře
     * @param typeOfStep O který typ kroku se jedná
     * @param command    Zadaný příkaz
     * @param message    Zprava vydaná v reakci na zadaný příkaz
     * @param space      Název prostoru, v němž bude hráč vykonání příkazu
     * @param neighbors  Názvy prostorů, kam se je odsud možno přesunout
     * @param objects    Názvy objektů v aktuálním prostoru
     * @param bag        Názvy objektů v batohu
     */
    public ScenarioStep(int     index,      TypeOfStep typeOfStep,
                       String   command,    String   message,  String   space,
                       String[] neighbors,  String[] objects,  String[] bag)
    {
        if (invalidText(message)           ||

            ((typeOfStep != TypeOfStep.tsNOT_START)  &&
             ((command == null)               ||
              invalidText(message, space)     ||
              invalidText(neighbors, objects, bag))  ))
        {
            String error =
                    "Při konsturkci kroku scénáře byla vyhozena výjimka " +
                    "způsobená pravděpodobně tím," +
                  "\nže některý ze zadávaných řetězců je prázdný nebo " +
                    "byl místo něj zadán prázdný odkaz." +
                  "\nZadáno:";
            String notification = podpis(index, typeOfStep, error, command,
                                         message, space,
                                         neighbors, objects, bag);
            DBG.severe(notification);
            throw new IllegalArgumentException(notification);
        }
        this.index     = lastIndex = index;
        this.command   = command;
        this.message   = message;
        this.place     = space;
        this.neighbors = neighbors;    Arrays.sort(neighbors, CIC);
        this.objects   = objects;      Arrays.sort(objects,   CIC);
        this.bag       = bag;          Arrays.sort(bag,       CIC);
        this.typeOfStep= typeOfStep;
    }


    /***************************************************************************
     * Vytvoří další krok scénáře NEsloužícího k testování funkce hry,
     * ale funkce některých doprovodných programů či dodržení kontraktu.
     *
     * @param index   Pořadí daného kroku v rámci scénáře
     * @param game    Hra, jejíž stav scénách popisuje
     * @param command Příkaz, po jehož zadání se hra dostala do daného stavu
     * @param message Zpráva, kterou hra vypsala v odpovědi na zadaný příkaz
     */
    public ScenarioStep(int index, IGame game, String command, String message)
    {
        this(index, TypeOfStep.tsUNKNOWN, command, message,
             game.getCurrentPlace().getName(),
             Util.colINamed2arrString(game.getCurrentPlace().getNeighbors()),
             Util.colINamed2arrString(game.getCurrentPlace().getObjects()),
             Util.colINamed2arrString(game.getBag().getObjects()));
    }


    /***************************************************************************
     * Vytvoří další krok zjednodušeného scénáře NEsloužícího k testování,
     * ale pouze k demonstraci funkce hry a jejího rozhraní, přičemž jeho index
     * bude o jedničku větší než index kroku vytvořeného před ním.
     *
     * @param command Zadaný příkaz
     */
    public ScenarioStep(String command)
    {
        this(++lastIndex, command);
    }


    /***************************************************************************
     * Vytvoří krok zjednodušeného scénáře NEsloužícího k testování,
     * ale pouze k demonstraci funkce hry a jejího rozhraní,
     * přičemž tento krok bude mít zadaný index, na nějž budou navazovat
     * indexy následně vytvořených kroků bez explicitně zadaných indexů.
     *
     * @param index   Číslo, které by mělo určovat pořadí daného kroku
     *                v rámci jeho scénáře
     * @param command Zadaný příkaz
     */
    public ScenarioStep(int index, String command)
    {
        if (command == null)  {
            throw new NullPointerException(
                      "\nAkce musí být platný neprázdný řetězec");
        }
        this.index     = lastIndex = index;
        this.command   = command;
        this.typeOfStep= TypeOfStep.tsDEMO;

        this.message   = null;
        this.place     = null;
        this.neighbors = null;
        this.objects   = null;
        this.bag       = null;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí hodnotu indexu daného kroku.
     *
     * @return Hodnota indexu daného kroku
     */
    public int getIndex()
    {
        return index;
    }


    /***************************************************************************
     * Nastaví novou hodnotu indexu daného kroku.
     *
     * @param index Nastavovaná hodnota indexu
     */
    public void setNewIndex(int index)
    {
        this.index = index;
    }


    /***************************************************************************
     * Vrátí text příkazu zadávaného v daném kroku scénáře.
     *
     * @return Zadávaný příkaz
     */
    public String getCommand()
    {
        return command;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Podrobný podpis daného kroku scénáře;
     * obsahuje-li atribut {@link #message} hodnotu {@code null},
     * vypíše se pouze index kroku a zadaný příkaz.
     *
     * @return Textový podpis dané instance
     */
    @Override
    public String toString()
    {
        String ret =
            "\n" + twoDigits(index) + ".krok:  " + A_ + command   + _Z +
            (
//            TypeOfStep.tsDEMO.equals(typeOfStep)  ?  ""  :
                podpis(
                index, typeOfStep, "Očekávaný stav po provedení akce:",
                command, message, place, neighbors, objects, bag)
           ) +
            "\n";
        return ret;
    }


    /***************************************************************************
     * Vrátí řetězec obsahující příkaz realizovaný daným krokem
     * následovaný zprávou, kterou hra vrátí jako odpověď.
     *
     * @return  Požadovaný text
     */
    public String commandAndMessage()
    {
        return "\n" + command + "\n" + message + "\n";
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Vrátí dvojznakový řetězec představující zadané jedno- až dvojmístné
     * číslo; je-li zadané číslo menši nez 10, vloží před číslici mezeru.
     * Nekontroluje, zda je číslo nezáporné a menši než 100.
     *
     * @param  i Číslo převáděné na řetězec
     * @return Požadovaný řetězec
     */
    private static String twoDigits(int i)
    {
        return ((i > 9) ? "" : " ") + i;
    }

    /***************************************************************************
     * Vrátí textový podpis kroku se zadanými parametry.
     *
     * @param index       Číslo, které by mělo určovat pořadí daného kroku
     *                    v rámci jeho scénáře
     * @param prologue    Text, kterým se uvede celý výpis
     * @param command     Zadaný příkaz
     * @param message     Zprava vydaná v reakci na zadaný příka
     * @param place       Název prostoru, v němž bude hráč vykonání příkazu
     * @param neighbors   Názvy prostorů, kam se je odsud možno přesunout
     * @param objects     Názvy objektů v aktuálním prostoru
     * @param bag         Názvy objektů v batohu
     * @param typeOfStep  O který typ kroku se jedná
     * @return  Textový podpis kroku se zadanými parametry
     */
    private static String podpis(int      index,    TypeOfStep typeOfStep,
            String   prologue,   String   command,  String   message,
            String   place,
            String[] neighbors,  String[] objects,  String[] bag)
    {
        return  N_LINE_N + prologue +
                "\nTyp kroku: " + typeOfStep +
                "\nPříkaz:    " + command +
                "\nProstor:   " + place +
                "\nVýchody:   " + arr2String(neighbors) +
                "\nPředměty:  " + arr2String(objects ) +
                "\nBatoh:     " + arr2String(bag     ) +
                N_LINE +
                "\nZpráva:"     +
                N_CIRCUMFLEXES_N +
                message   +
                N_LINE;
    }


//== SOUKROMÉ A POMOCNÉ METODY INSTANCI ========================================

    /***************************************************************************
     * Zjistí, zda zadaný parametr je neprázdný řetězec.
     *
     * @param text Testovaný řetězce
     * @return Je-li parametrem prázdný odkaz {@code null}, prázdný řetězec
     *         nebo obsahuje-li pouze bílé znaky, vrátí {@code true},
     *         v opačném případě vrátí {@code false}.
     */
    private static boolean invalid(String text)
    {
        return (text == null)  ||  "".equals(text.trim());
    }


    /***************************************************************************
     * Zjistí, zda jsou všechny parametry neprázdné řetězce
     * obsahující alespoň jeden nebílý znak.
     *
     * @param texts Testované řetězce
     * @return Poruší-li některý z parametrů uvedenou podmínku, vrátí
     *         {@code true}, v opačném případě vrátí {@code false}.
     */
    private static boolean invalidText(String... texts)
    {
        for (String text : texts) {
            if (invalid(text)) {
                return true;
            }
        }
        return false;
    }


    /***************************************************************************
     * Zjistí, zda všechna zadané pole textových řetězců obsahují
     * pouze neprázdné řetězce.
     *
     * @param sets Testovaná pole řetězců
     * @return Poruší-li některý z řetězců uvedenou podmínku, vrátí
     *         {@code true}, v opačném případě vrátí {@code false}.
     */
    private static boolean invalidText(String[]... sets)
    {
        for (String[] set : sets) {
            if (set == null) {
                return true;
            }
            for (String text : set) {
                if (invalid(text)) {
                    return true;
                }
            }
        }
        return false;
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
}
