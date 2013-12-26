package cz.vse.adv_framework.test_util.default_game.gamet;

import  cz.vse.adv_framework.game_txt.ICommand;

import  cz.vse.adv_framework.game_txt.INamed;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Instance třídy {@code ACommand} (přesněji instance jejích potomků) mají
 * na starost interpretaci příkazů zadávaných uživatelem hrajícím danou hru.
 * Třída {@code ACommand} je rodičovskou třídou všech příkazů.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.00
 */
abstract class ACommand implements ICommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    private static final String UVÍTÁNÍ = Texts.zCELÉ_UVÍTÁNÍ;
//            "\nVítáme vás ve vašem novém bytě. Jistě máte hlad." +
//            "\nNajděte v bytě ledničku - tam vás čeká svačina." +
//            "\n\nNacházíte se v místnosti: Předsíň." +
//            "\nV místnosti se nachází: Botník, Deštník" +
//            "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna";

    private static final String NENÍ_START = Texts.zNENÍ_START;
//            "Hra neběží, lze ji spustit pouze prázdným příkazem";

    private static final String PRÁZDNÝ = Texts.zPRÁZDNÝ_PŘÍKAZ;
//          "Zadal(a) jste prázdný příkaz. Chcete-li poradit, zadejte příkaz ?";

    private static final String NEZNÁM = Texts.zNEZNÁMÝ_PŘÍKAZ;
//            "Tento příkaz neznám. Chcete-li poradit, zadejte příkaz ?";


    private static final Map<String, ICommand> název2příkaz = new HashMap<>();

    /** Teoreticky by následující příkazy stačilo vytvořit např. ve statickém
     *  inicializačním bloku. Tady je každému příkazu přiřazena konstanta
     *  pouze proto, že se mi zdálo, že by se mi to mohlo při ladění hodit. */
    private static final ACommand
            PŘÍKAZ_HELP  = new CommandHelp(),
            PŘÍKAZ_JDI   = new CommandGo(),
            PŘÍKAZ_OTEVRI= new CommandOpen(),
            PŘÍKAZ_NASAD = new CommandUse(),
            PŘÍKAZ_PODLOZ= new CommandChock(),
            PŘÍKAZ_POLOZ = new CommandPut(),
            PŘÍKAZ_PRECTI= new CommandRead(),
            PŘÍKAZ_VEZMI = new CommandPick(),
            PŘÍKAZ_ZAVRI = new CommandClose(),
            PŘÍKAZ_KONEC = new CommandEnd();



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Příznak toho, je-li hra rozehraná nebo
     *  je-li skončená a bude se startovat nová. */
    private static boolean nehrajeme = true;



//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    private final String název;
    private final String popis;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře..
     *
     * @return Požadovaná kolekce
     */
    static Collection<ICommand> getPříkazy()
    {
        return Collections.unmodifiableCollection(název2příkaz.values());
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zpracuje zadany prikaz a vrati text zpravy pro uzivatele.
     *
     * @param příkaz Zadavany prikaz
     * @return Textova odpoved hry na zadany prikaz
     */
    static String zpracujPříkaz(String příkaz)
    {
        příkaz = příkaz.toLowerCase().trim();
        if (nehrajeme) {
//%A+ o2012p4
            return appendMessage(start(příkaz));    //==========>
//%A-
        }
        if (příkaz.isEmpty()) {
//%A+ o2012p4
            return appendMessage(PRÁZDNÝ);          //==========>
//%A-
        }
        if (State.ROZHOVOR) {
//%A+ o2012p4
            return appendMessage(Conversation.reaguj(příkaz));    //==========>
//%A-
        }
        String[] slova = příkaz.split("\\s+");
        ICommand  povel = název2příkaz.get(slova[0]);
        if (povel == null) {
//%A+ o2012p4
            return appendMessage(NEZNÁM);           //==========>
//%A-
        }
//        if (DBG.DEBUG < 0) {
//            povel.getParametry();
//        }

        String[] parametry = new String[slova.length-1];
        System.arraycopy(slova, 1, parametry, 0, parametry.length);

        String  odpověď = povel.execute(parametry);
        odpověď = doplň(odpověď);
//%A+ o2012p4
            return appendMessage(odpověď);          //==========>
//%A-
    }

//%A+ o2012p4
    /***************************************************************************
     * @todo appendMessage - Je třeba ještě doplnit komentář
     */
    private static String appendMessage(String message)
    {
        Collection<String> set = Room.getNenavštívené();
        String appendix = "\nDosud nenavštívené místnosti: " + set + '\n';
        return message + appendix;
    }

//%A-

    /***************************************************************************
     * Oznámení o ukončení hry.
     * Příští volání metody {@link #zpracujPříkaz(String)}
     * bude opět prvním voláním v nové hře.
     */
    public static void konec() {
        nehrajeme = true;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový příkaz se zadaným názvem a popisem své funkce.
     *
     * @param název Název vytvářeného příkazu
     * @param popis Stručný popis funkce, použití a účelu příkazu
     */
    ACommand (String název, String popis)
    {
        this.název = název;
        this.popis = popis;
        název2příkaz.put(název.toLowerCase(), this);
    }


//== ABSTRACT METHODS ==========================================================

    /***************************************************************************
     * Metoda realizující reakci hry na zadaní daného příkazu.
     * Počet parametru je závislý na konkrétním příkazu, např. příkazy Konec
     * a Nápověda nemají parametry, příkazy Jdi a Vezmi mají jeden parametr
     * příkaz Podlož muže mít dva parametry atd.
     *
     * @param parametry Parametry příkazu;
     *                  jejich počet muže byt pro každý příkaz jiný
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public abstract String execute(String... parametry);



//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vraci nazev prikazu, tj. text, ktery musi hrac zadat
     * pro vyvolani daneho prikazu
     *
     * @return Nazev prikazu
     */
    @Override
    public String getName()
    {
        return název;
    }


    /***************************************************************************
     * Vraci popis prikazu s vysvetlenim jeho funkce
     * a vyznamu jednotlivych parametru.
     *
     * @return Popis prikazu
     */
    @Override
    public String getDescription()
    {
        return popis;
    }


    /***************************************************************************
     * Vrátí informaci o tom, jeli hra již skočena a lze-li proto začít novou.
     *
     * @return Je-li hra skončena, vrátí {@code true}, jinak vrací {@code false}
     */
    static boolean isSkončeno()
    {
        return nehrajeme;
    }



//+++ Přidáno pro rozšířené zadnání v předmětu 4IT115 ++++++++++++++++++++++++++
//
//    /***************************************************************************
//     * Metoda vrátí pole parametrů daného příkazu. Každý parametr je přitom
//     * schopen vrátit množinu hodnot, které v dané situaci přicházejí
//     * pro daný příkaz na pozici příslušného parametru v úvahu.
//     *
//     * @return Požadované pole parametrů
//     * @throws UnsupportedOperationException v případě, kdy příkaz tuto
//     *                                       funkci nepodporuje
//     */
//    @Override
//    public IParametr[] getParametry()
//    {
//        throw new UnsupportedOperationException();
//    }
//
//
//
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Nazev instance
     */
    @Override
    public String toString()
    {
        return název;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Vrátí text s informacemi o aktuálním stavu hry.
     *
     * @param odpověď Úvodní odpověď hry doplňovaná informacemi o stavu.
     * @return Požadovaný informační text
     */
    private static String doplň(String  odpověď)
    {
        StringBuilder sb = new StringBuilder(odpověď);
        Collection<INamed> kolekce;

        Room am = Room.getAktuálníMístnost();
        připoj(sb, "Sousedé:  ", am.getNeighbors());
        připoj(sb, "Předměty: ", am.getObjects());
        připoj(sb, "Batoh:    ", Bag.getBatoh().getObjects());

        return sb.toString();
    }


    /***************************************************************************
     * Do zadaného stringbuilderu přidá zadaný titulek
     * následovaný vyjmenovanými položkami ze zadané kolekce.
     *
     * @param sb       Doplňovaný StringBuilder
     * @param titulek  Přidávaný titulek
     * @param objekty  Kolekce přidávaných objektů
     */
    private static void připoj(StringBuilder sb, String titulek,
                        Collection<? extends INamed> objekty)
    {
        sb.append('\n').append(titulek).append(objekty);
    }


    /***************************************************************************
     * Odstartuje novou hru.
     *
     * @param příkaz Zadaný startovací příkaz
     * @return Odpověď hry = uvítání nebo zpráva o chybě
     */
    private static String start(String příkaz)
    {
        if (příkaz.isEmpty()) {
            Room.inicializuj();
            Bag .inicializuj();
            nehrajeme = false;
            return UVÍTÁNÍ;                             //==========>
        } else {
            return "Hra neběží, lze ji spustit pouze prázdným příkazem.";
        }
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        ACommand x = new ACommand();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
