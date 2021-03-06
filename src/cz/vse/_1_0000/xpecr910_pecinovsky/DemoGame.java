/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;

import cz.vse.adv_framework.game_txt.Commands;
import cz.vse.adv_framework.game_txt.IBag;
import cz.vse.adv_framework.game_txt.ICommand;
import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.game_txt.IPlace;

import cz.vse.adv_framework.scenario.AScenarioManager;

import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@code DemoGame} představuje vzorovou verzi textové hry.
 * Instance této třídy mají na starosti logiku hry.
 * Jsou schopny akceptovat jednotlivé příkazy a poskytovat informace
 * o průběžném stavu hry a jejích součástí.
 * <p />
 * Třída hry musí být definována jako jedináček (singleton)
 * a kromě metod deklarovaných v tomto rozhraní musí definovat
 * statickou tovární metodu {@code getInstance()}.
 * Splnění této podmínky nemůže prověřit překladač,
 * ale prověří ji až následné testy hry.
 * <p />
 * Instance třídy {@code EmptyGame} představují prototypy instancí hry,
 * které ještě nejsou schopny plnohodnotného spuštění a slouží pouze
 * ke kompletaci těch vlastností správce scénářů, které bude v budoucnu
 * plnit ve spolupráci s plnohodnotnou hrou.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class DemoGame implements IGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na instanci správce prostorů.
     *  Je-li správcem prostorů třída prostorů, použije se adaptér. */
    static final Room2Manager ROOM_MANAGER = Room2Manager.getInstance();

    /** Odkaz na instanci správce příkazů.
     *  Je-li správcem příkazů třída příkazů, použije se adaptér. */
    static final Command2Manager COMMAND_MANAGER =
                                 Command2Manager.getInstance();

    /** Přepravka s názvy povinných příkazů. */
    private static final Commands BASIC_COMMANDS =
                     new Commands("Jdi", "Polož", "Vezmi", "?", "Konec");

    /** Odkaz na jedinou instanci (jedináčka) této hry. */
    private static final DemoGame GAME = new DemoGame();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Proměnná udržující informaci o tom, je-li hra právě spuštěná. */
    private boolean isAlive = false;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vracející odkaz na jedninou existující instanci dané hry.
     *
     * @return Instance dané hry
     */
    public static DemoGame getInstance()
    {
        return GAME;
    }


    /***************************************************************************
     * Soukromý konstruktor definující jedinou instanci.
     * Protože je soukromý, musí být definován, i když má prázdné tělo.
     */
    private DemoGame()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí jméno autora programu ,
     * ve formátu zadaném v rozhraní {@link cz.vse.IAuthor}.
     *
     * @return Jméno autora/autorky programu ve tvaru PŘÍJMENÍ Křestní
     */
    @Override
    public String getAuthorName()
    {
        return getScenarioManager().getAuthorName();
    }


    /***************************************************************************
     * Vrátí xname autora/autorky programu
     * ve formátu zadaném v rozhraní {@link cz.vse.IAuthor}.
     *
     * @return Xname autora/autorky programu v požadovaném formátu
     */
    @Override
    public String getAuthorID()
    {
        return getScenarioManager().getAuthorID();
    }


    /***************************************************************************
     * Vrátí informaci o tom, je-li hra aktuálně spuštěná.
     * Spuštěnou hru není možno pustit znovu.
     * Chceme-li hru spustit znovu, musíme ji nejprve ukončit.
     *
     * @return Je-li hra spuštěná, vrátí {@code true},
     *         jinak vrátí {@code false}
     */
    @Override
    public boolean isAlive()
    {
        return isAlive;
    }


    /***************************************************************************
     * Nastaví informaci o tom, zda se hra právě hraje.
     * Metodu žádný interfejs nevyžaduje, takže nemusí být veřejná.
     * Může být package-private, a proto no ni vidí pouze kolegové z balíčku.
     *
     * @param alive Byla-li hra spuštěna, nastaví {@code true},
     *              byla-li ukončena, nastaví {@code false}
     */
    void setAlive(boolean alive)
    {
        isAlive = alive;
    }


    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané objekty.
     *
     * @return Batoh, do nějž hráč ukládá sebrané objekty
     */
    @Override
    public IBag getBag()
    {
        return Hands.getInstance();
    }


    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    @Override
    public Collection<? extends ICommand> getAllCommands()
    {
        return COMMAND_MANAGER.getAllCommands();
    }


    /***************************************************************************
     * Vrátí odkaz na přepravku s názvy povinných příkazů, tj. příkazů pro
     * <ul>
     *   <li>přesun hráče do jiného prostoru,</li>
     *   <li>zvednutí objektu (odebrání z prostoru a vložení do batohu),</li>
     *   <li>položení objektu (odebrání z batohu a vložení do prostoru),</li>
     *   <li>vyvolání nápovědy,</li>
     *   <li>okamžité ukončení hry.</li>
     * </ul>
     *
     * @return Přepravka názvy povinných příkazů
     */
    @Override
    public Commands getBasicCommands()
    {
        return BASIC_COMMANDS;
    }


    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public Collection<? extends IPlace> getAllPlaces()
    {
        return ROOM_MANAGER.getAllPlaces();
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální prostor,
     * tj. na prostor, v němž se hráč pravé nachází.
     *
     * @return Prostor, v němž se hráč pravé nachází
     */
    @Override
    public IPlace getCurrentPlace()
    {
        return ROOM_MANAGER.getCurrentPlace();
    }


    /***************************************************************************
     * Vrátí správce scénářů specifikujících požadované chování hry
     * v různých situacích.
     * Scénáře slouží k automatizovanému ověření funkčnosti hry
     * a/nebo demonstraci jejího možného průběhu.
     *
     * @return Správce scénářů dané hry
     */
    @Override
    public AScenarioManager getScenarioManager()
    {
        return ManagerWithLiterals.getInstance();
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param command Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    @Override
    public String executeCommand(String command)
    {
        return COMMAND_MANAGER.executeCommand(command);
    }


    /***************************************************************************
     * Ukončí celou hru a vrátí alokované prostředky.
     */
    @Override
    public void stop()
    {
        isAlive = false;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
