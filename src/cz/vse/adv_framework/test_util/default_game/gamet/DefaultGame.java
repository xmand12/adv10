package cz.vse.adv_framework.test_util.default_game.gamet;

import cz.vse.adv_framework.game_txt.Commands;
import cz.vse.adv_framework.game_txt.ICommand;
import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.AScenarioManager;

import java.util.Collection;


import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code DefaultGame} představují hru, při níž se hráč ocitne
 * v malém bytě, který je třeba projít a najít v něm ledničku.
 * Mají na starosti logiku hry.
 * Jsou schopny akceptovat jednotlivé příkazy a poskytovat informace
 * o průběžném stavu hry a jejích součástech.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public class DefaultGame implements IGame
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Zda bude používán správce scénářů s konstantami nebo literály. */
    private static final boolean SPRÁVCE_S_KONSTANTAMI = false;

    /** Aktuálně provozovaná hra. Je-li tento odkaz prázdný, není aktuálně
     *  provozována žádná hra a může se spustit nová. */
    private static DefaultGame aktuálníHra = new DefaultGame();

    /** Přepravka s názvy čtyř povinných příkazů. */
    private static final Commands příkazy =
                     new Commands(pJDI, pPOLOŽ, pVEZMI, pHELP, pKONEC);



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//
//    /** Správce scénářů, s jejichž pomocí je možno hru testovat
//     * nebo demonstrovat její možný průběh. */
//    private final AScenarioManager správceScénářů =
//                        (SPRÁVCE_S_KONSTANTAMI  ?  new ManagerWithConstants()
//                                                :  new ManagerWithLiterals  ());
//
//
//
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== PŘÍSTUPOVÉ METODY ATRIBUTŮ TŘÍDY ==========================================

    /***************************************************************************
     * Vrátí odkaz na aktuálně provozovanou instanci hry.
     * Metoda slouží k tomu, aby mohli některé metody získat odkaz
     * na aktuální hru a od ní pak získat jinak nedostupné informace.
     *
     * @return Požadovaný odkaz
     */
    public static DefaultGame getInstance()
    {
        return aktuálníHra;
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří novou hru a nastaví jí správce scénářů.
     */
    private DefaultGame()
    {
        DefaultGame já = this;
//        správceScénářů.setGame(já);
    }



//== ABSTRACT METHODS ==========================================================
//== PŘÍSTUPOVÉ METODY INSTANCÍ ================================================

    /***************************************************************************
     * Vrátí jméno autora scénáře ve formátu "PŘÍJMENÍ Křestní",
     * tj. neprve příjmení psané velkými písmeny a za ním křestní jméno,
     * u nějž bude velké pouze první písmeno a ostatní písmena budou malá.
     *
     * @return Jméno autora/autorky programu ve tvaru PŘÍJMENÍ Křestní
     */
    @Override
    public String getAuthorName()
    {
        return getScenarioManager().getAuthorName();
    }


    /***************************************************************************
     * Vrátí xname autora/autorky programu zapsané velkými písmeny.<p>
     *
     * @return Xname autora/autorky programu
     */
    @Override
    public String getAuthorID()
    {
        return getScenarioManager().getAuthorID();
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální místnost,
     * tj. na místnost, v niž se pravé nachází hráč.
     *
     * @return Požadovaný odkaz
     */
    @Override
    public Room getCurrentPlace()
    {
        return Room.getAktuálníMístnost();
    }


    /***************************************************************************
     * Vrátí odkaz na batoh, do nějž bude hráč ukládat sebrané předměty.
     *
     * @return Požadovaný odkaz
     */
    @Override
    public Bag getBag()
    {
        return Bag.getBatoh();
    }


    /***************************************************************************
     * Vrátí kolekci odkazů na jednotlivé místnosti.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<Room> getAllPlaces()
    {
        return Room.getMístnosti();
    }


    /***************************************************************************
     * Vrátí kolekci všech příkazů použitelných ve hře.
     *
     * @return Požadovaná kolekce
     */
    @Override
    public Collection<ICommand> getAllCommands()
    {
        return ACommand.getPříkazy();
    }


    /***************************************************************************
     * Vrátí informaci o tom, je-li hra již ukončena.
     *
     * @return Požadovaná informace
     */
    @Override
    public boolean isAlive()
    {
        return ! ACommand.isSkončeno();
    }


    /***************************************************************************
     * Vrátí správce scénářů, které umožní prověřit funkčnost hry
     * a/nebo demonstrovat její průběh.
     *
     * @return Správce scénářů dané hry
     */
    @Override
    public AScenarioManager getScenarioManager()
    {
        return (SPRÁVCE_S_KONSTANTAMI  ?  ManagerWithConstants.getInstance()
                                       :  ManagerWithLiterals .getInstance());
    }



    /***************************************************************************
     * Vrátí odkaz na přepravku s názvy příkazů pro přesun do jiné místnosti,
     * zvednutí předmětu a jeho položení.
     *
     * @return Požadovaný odkaz
     */
    @Override
    public Commands getBasicCommands()
    {
        return příkazy;
    }



//== OSTATNÍ NESOUKROMÉ  METODY INSTANCÍ =======================================

    /***************************************************************************
     * Ukončí celou hru a vrátí alokované prostředky.
     */
    @Override
    public void stop()
    {
        ACommand.konec();
    }


    /***************************************************************************
     * Zpracuje zadaný příkaz a vrátí text zprávy pro uživatele.
     *
     * @param prikaz Zadávaný příkaz
     * @return Textová odpověď hry na zadaný příkaz
     */
    @Override
    public String executeCommand(String prikaz)
    {
        return ACommand.zpracujPříkaz(prikaz);
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
//        DefaultGame hra = getInstance();
//        _Test_101 test = _Test_101.getInstance(hra);
//        test.testGame();
//     }
//     /** @param args Parametry příkazového řádku - nepoužité. */
//     public static void main(String... args) { test(); }
}
