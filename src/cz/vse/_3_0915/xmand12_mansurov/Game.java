package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.Commands;
import cz.vse.adv_framework.game_txt.IBag;
import cz.vse.adv_framework.game_txt.ICommand;
import cz.vse.adv_framework.game_txt.IGame;
import cz.vse.adv_framework.game_txt.IPlace;
import cz.vse.adv_framework.scenario.AScenarioManager;
import java.util.Collection;

/*******************************************************************************
 * Instance třídy {@literal Game} představuje vzorovou verzi textové hry.
 * Instance této třídy mají na starosti logiku hry.
 * Jsou schopny akceptovat jednotlivé příkazy a poskytovat informace
 * o průběžném stavu hry a jejích součástí.
 * <p />
 * Třída hry musí být definována jako jedináček (singleton)
 * a kromě metod deklarovaných v tomto rozhraní musí definovat
 * statickou tovární metodu {@literal getInstance()}.
 * Splnění této podmínky nemůže prověřit překladač,
 * ale prověří ji až následné testy hry.
 * <p />
 * Instance třídy {@literal EmptyGame} představují prototypy instancí hry,
 * které ještě nejsou schopny plnohodnotného spuštění a slouží pouze
 * ke kompletaci těch vlastností správce scénářů, které bude v budoucnu
 * plnit ve spolupráci s plnohodnotnou hrou.
 *
 * @author  Daulet Mansurov
 * @version 7.4
 */
public class Game implements IGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Odkaz na jedinou instanci (jedináčka) této hry. */
    private static final Game GAME = new Game();
/**
 * Staticka promenna načítající již uloženou hru .
 * @param gameData
 */
    static void loadGame(GameData gameData) {
        Bag actBag = Bag.getInstance();
        Bag oldBag = gameData.getBag();
        actBag.removeAllObject();
        for (Things thing : oldBag.getObjects()) {
            actBag.putInto(thing);
        }
        QuestManager.loadQmMapFrom(gameData.getQm());
        Rooms.loadRoomsFrom(gameData.getALL_ROOMS());
        Rooms.getCurrentPlace().setCurrentPlace(gameData.getCurrRoom());
    }

//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Proměnná udržující informaci o tom, je-li hra právě spuštěná. */
    private boolean isAlive = false;

    private String info;

    public String getInfo() {
        info = "\n";
        info += "Nachazíte se: " + getCurrentPlace().getName() + "\n";
        info += "Sousedé: " + getCurrentPlace().getNeighbors().toString() + "\n";
        info += "Objekty v prostoru: " + getCurrentPlace().getObjects().toString() + "\n";
        info += "Objekty v batohu: " + Bag.getInstance().getObjects().toString() + "\n";
        return info;
    }

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Tovární metoda vracející odkaz na jedninou existující instanci dané hry.
     *
     * @return Instance dané hry
     */
    public static Game getInstance()
    {
        return GAME;
    }

    /***************************************************************************
     * Soukromý konstruktor definující jedinou instanci.
     * Protože je soukromý, musí být definován, i když má prázdné tělo.
     */
    private Game()
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
     * @return Je-li hra spuštěná, vrátí {@literal true},
     *         jinak vrátí {@literal false}
     */
    @Override
    public boolean isAlive()
    {
        return isAlive;
    }

    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané objekty.
     *
     * @return Batoh, do nějž hráč ukládá sebrané objekty
     */
    @Override
    public IBag getBag()
    {
        return Bag.getInstance();
    }

    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Kolekce všech příkazů použitelných ve hře
     */
    @Override
    public Collection<? extends ICommand> getAllCommands()
    {
        return AACommand.getAllCommands();
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
        return new Commands("jdi", "vezmi", "polož", "?", "konec");
    }

    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public Collection<? extends IPlace> getAllPlaces()
    {
        return Rooms.getAllPlaces();
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
        return Rooms.getCurrentPlace();
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
        return Scenario.getInstance();
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
        String answer = AACommand.executeCommand(command);
        return answer;
    }

    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param command Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    public String executeCommand(String command, boolean verbose) {
        String answer = AACommand.executeCommand(command);
        if (verbose) {
            if (answer.startsWith("Děkuj")) {
                return answer;
            }
            answer += "\n" + getInfo();
        }
        return answer;
    }

    /***************************************************************************
     * Odstartuje celou hru a vrátí alokované prostředky.
     */
    public void start()
    {
        isAlive = true;
    }

    /***************************************************************************
     * Ukončí celou hru a vrátí alokované prostředky.
     */
    @Override
    public void stop()
    {
        if (isAlive) {
            isAlive = false;
        }
    }
}
