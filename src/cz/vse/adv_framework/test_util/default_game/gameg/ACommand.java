package cz.vse.adv_framework.test_util.default_game.gameg;

import cz.vse.adv_framework.game_gui.IListener;

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

    protected static final String INVITATION = Texts.zCELÉ_UVÍTÁNÍ;
//            "\nVítáme vás ve vašem novém bytě. Jistě máte hlad." +
//            "\nNajděte v bytě ledničku - tam vás čeká svačina." +
//            "\n\nNacházíte se v místnosti: Předsíň." +
//            "\nV místnosti se nachází: Botník, Deštník" +
//            "\nMůžete se přesunout do místností: Ložnice, Obývák, Koupelna";

    private static final String NENÍ_START = Texts.zNENÍ_START;
//            "Hra neběží, lze ji spustit pouze prázdným příkazem";

    private static final String CMD_IS_EMPTY = Texts.zPRÁZDNÝ_PŘÍKAZ;
//          "Zadal(a) jste prázdný příkaz. Chcete-li poradit, zadejte příkaz ?";

    private static final String CMD_NOT_KNOWN = Texts.zNEZNÁMÝ_PŘÍKAZ;
//            "Tento příkaz neznám. Chcete-li poradit, zadejte příkaz ?";


    private static final Map<String, ACommand> NAME_2_COMMAND = new HashMap<>();

    /** Teoreticky by následující příkazy stačilo vytvořit např. ve statickém
     *  inicializačním bloku. Tady je každému příkazu přiřazena konstanta
     *  pouze proto, že se mi zdálo, že by se mi to mohlo při ladění hodit. */
    private static final ACommand
            COMMAND_HELP = new CommandHelp(),
            COMMAND_GO   = new CommandGo(),
            COMMAND_OPEN = new CommandOpen(),
            COMMAND_USE  = new CommandUse(),
            COMMAND_CHOCK= new CommandChock(),
            COMMAND_PUT  = new CommandPut(),
            COMMAND_READ = new CommandRead(),
            COMMAND_PICK = new CommandPick(),
            COMMAND_CLOSE= new CommandClose(),
            COMMAND_END  = new CommandEnd();



//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Příznak toho, je-li hra rozehraná nebo
     *  je-li skončená a bude se startovat nová. */
    private static boolean isAlive = false;



//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název daného příkazu. */
    private final String commandName;

    /** Stručný popis daného příkazu. */
    private final String description;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí informaci o tom, jeli hra již skončena a lze-li proto začít novou.
     *
     * @return Je-li hra skončena, vrátí {@code true}, jinak vrací {@code false}
     */
    static boolean isAlive()
    {
        return isAlive;
    }


    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    public static Collection<ACommand> getCommands()
    {
        return Collections.unmodifiableCollection(NAME_2_COMMAND.values());
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param command Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    public static String executeCommand(String command)
    {
        command = command.toLowerCase().trim();
        if (! isAlive) {
            return start(command);                       //==========>
        }
        if (command.isEmpty()) {
            return CMD_IS_EMPTY;                             //==========>
        }
        if (State.DIALOG) {
            return Dialog.reaguj(command);         //==========>
        }
        String[] words       = command.split("\\s+");
        ICommand commandName = NAME_2_COMMAND.get(words[0]);
        if (commandName == null) {
            return CMD_NOT_KNOWN;                              //==========>
        }
//        if (DBG.DEBUG < 0) {
//            povel.getParametry();
//        }

        String[] parameters = new String[words.length-1];
        System.arraycopy(words, 1, parameters, 0, parameters.length);

        String  odpověď = commandName.execute(parameters);
        odpověď = addContext(odpověď);
        return  odpověď;
    }


    /***************************************************************************
     * Oznámení o ukončení hry.
     * Příští volání metody {@link #executeCommand(String)}
     * bude opět prvním voláním v nové hře.
     */
    public static void stop() {
        isAlive = false;
    }



//+++ Přidáno pro rozšířené zadnání v předmětu 4IT115 ++++++++++++++++++++++++++

    /***************************************************************************
     * Přidá zadaného posluchače do seznamu posluchačů,
     * kterým hra oznamuje začátek a konec rozhovoru.
     *
     * @param listener Přihlašovaný posluchač
     */
    public static void addDialogListener(IListener<Boolean> listener)
    {
        Dialog.addDialogListener(listener);
    }


    /***************************************************************************
     * Odebere zadaného posluchače ze seznamu posluchačů,
     * kterým hra oznamuje začátek a stop rozhovoru.
     *
     * @param listener Odhlašovaný posluchač
     */
    public static void removeDialogListener(IListener<Boolean> listener)
    {
        Dialog.removeDialogListener(listener);
    }


    /***************************************************************************
     * Inicializuje všechny příkazy,
     * tj. nechá je uvést případné nastavované příznaky do počátečního stavu.
     */
    public static void initializeCommands()
    {
        State.initialize();
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový příkaz se zadaným názvem a popisem své funkce.
     *
     * @param commandName Název vytvářeného příkazu
     * @param description Stručný popis funkce, použití a účelu příkazu
     */
    public ACommand (String commandName, String description)
    {
        this.commandName = commandName;
        this.description = description;
        NAME_2_COMMAND.put(commandName.toLowerCase(), this);
    }


//== ABSTRACT METHODS ==========================================================

    /***************************************************************************
     * Metoda realizující reakci hry na zadaní daného příkazu.
     * Počet parametru je závislý na konkrétním příkazu, např. příkazy Konec
     * a Nápověda nemají parametry, příkazy Jdi a Vezmi mají jeden parametr
     * příkaz Podlož muže mít dva parametry atd.
     *
     * @param parameters Parametry příkazu;
     *                   jejich počet muže byt pro každý příkaz jiný
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public abstract String execute(String... parameters);



//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název příkazu, tj. text, který musí hráč zadat
     * pro vyvolaní daného příkazu.
     *
     * @return Název příkazu
     */
    @Override
    public String getName()
    {
        return commandName;
    }


    /***************************************************************************
     * Vrátí popis příkazu s vysvětlením jeho funkce
     * a významu jednotlivých parametru.
     *
     * @return Popis příkazu
     */
    @Override
    public String getDescription()
    {
        return description;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Název instance
     */
    @Override
    public String toString()
    {
        return commandName;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Vrátí text s informacemi o aktuálním stavu hry.
     *
     * @param answer Úvodní odpověď hry doplňovaná informacemi o stavu.
     * @return Požadovaný informační text
     */
    private static String addContext(String  answer)
    {
        StringBuilder sb = new StringBuilder(answer);
        Collection<INamed> kolekce;

        Room am = Room.getCurrentRoom();
        addListOf(sb, "Sousedé:  ", am.getNeighbors());
        addListOf(sb, "Předměty: ", am.getObjects());
        addListOf(sb, "Batoh:    ", Bag.getInstance().getObjects());

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
    private static void addListOf(StringBuilder sb, String titulek,
                        Collection<? extends INamed> objekty)
    {
        sb.append('\n').append(titulek).append(objekty);
    }


    /***************************************************************************
     * Odstartuje novou hru.
     *
     * @param command Zadaný startovací příkaz
     * @return Odpověď hry = uvítání nebo zpráva o chybě
     */
    private static String start(String command)
    {
        if (command.isEmpty()) {
            ACommand.initializeCommands();
            Room    .initializeRooms();
            Bag     .initializeBag();
            isAlive = true;
            return INVITATION;                             //==========>
        } else {
            return "Prvním příkazem není startovací příkaz.";
        }
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
