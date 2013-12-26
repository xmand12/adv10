/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IUIG;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.test_util.default_game.gameg.DefaultGame;
//import cz.vse.adv_framework.test_util.default_game.gameg.DefaultGame;
import cz.vse.adv_framework.test_util.default_gui.DefaultGUI;



/*******************************************************************************
 * Třída {@code Triumvirate} je společným rodičem všech tříd, které pracují
 * s triumvirátem [správce scénářů - třída hry - hra].
 * Konstruktor jejich instancí prověří, zda je trojice konzistentní.
 * Vytvořená instance poskytuje metody na získání jednotlivých členů trojice.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public abstract class Triumvirate
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Správce scénářů, který spravuje scénáře umožňující testování hry. */
    final AScenarioManager manager;

    /** Třída, jejíž instance představují hry,
     *  které je možno hrát podle spravovaných scénářů. */
    final Class<? extends IGame> gameClass;

    /** Konkrétní hra (instance třídy {@link #gameClass}, která je/byla/bude
     *  právě testována prostřednictvím scénářů správce. */
    final IGame gameT;

    /** Implementuje-li hra rozhraní {@link IGameG}, naplní se  další atribut
     *  přetypovanou instancí, aby potomci nemuseli přetypovávat. */
    final IGameG gameG;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================

    /***************************************************************************
     * Vrátí instanci implicitní hry implementující rozhraní {@link IGame}.
     *
     * @return Instance implicitní hry
     */
    public static IGameG getDefaultGame()
    {
        return DefaultGame.getInstance();
    }


    /***************************************************************************
     * Vrátí novou instanci implicitního grafického rozhraní
     * implementujícího rozhraní {@link IUIG}.
     *
     * @return Nová instance implicitního grafického rozhraní
     */
    public static IUIG getDefaultGUI()
    {
        return DefaultGUI.getInstance();
    }



//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci testovací třídy napojenou na zadaného správce scénářů.
     * Správce scénářů je klíčovým bodem celé hry, protože jeho prostřednictvím
     * se je možno dostat ke všem ostatním dostupným informacím o hře.
     *
     * @param manager Správce scénářů prověřované aplikace
     * @return Instance schopná testovat zadého správce scénářů
     *         včetně případné hry, jejíž scénáře spravuje
     */
    static Crate fromManager(AScenarioManager manager)
    {
        if (manager == null) {
            throw new RuntimeException("\nSprávce scénářů " +
                      "je zadán jako prázdný odkaz (null)");
        }
        AScenarioManager       sm = manager;
        Class<? extends IGame> gc = sm.getGameClass();
        IGame                  gm = sm.getGame();
        return new Crate(sm, gc, gm);
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat
     * instance zadané třídy hry.
     *
     * @param gameClass Třída testované hry
     * @return Instance schopná testovat zadanou třídu hry a její hru
     */
    static Crate fromGameClass(Class<? extends IGame> gameClass)
    {
        if (gameClass == null) {
            throw new RuntimeException("\nClass-objekt třídy hry " +
                      "je zadán jako prázdný odkaz (null)");
        }
        Class<? extends IGame> gc = gameClass;
        IGame                  gm = GameTClassTest.getInstanceOfGame(gc);
        AScenarioManager       sm = gm.getScenarioManager();
        return new Crate(sm, gc, gm);
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat zadanou hru.
     *
     * @param game Testovaná hra
     * @return Instance schopná testovat zadanou hru
     */
    static Crate fromGame(IGame game)
    {
        if (game == null) {
            throw new RuntimeException("\nTestovaná hra " +
                      "je zadána jako prázdný odkaz (null)");
        }
        IGame                  gm = game;
        Class<? extends IGame> gc = game.getClass();
        AScenarioManager       sm = game.getScenarioManager();
        return new Crate(sm, gc, gm);
    }


    /***************************************************************************
     * Ověří, že zadané tři parametry si navzájem odpovídají, tj. že:
     * <ul>
     *   <li>je zadán správce scénářů,</li>
     *   <li>hra a třída hry musejí být zadány obě nebo žádná,</li>
     *   <li>zadaná hra je instancí zadané třídy hry,</li>
     *   <li>zadaná třída hry vrací zadanou hru, která je jedináčkem,</li>
     *   <li>zadaná hra vrací zadaného správce scénářů,</li>
     *   <li>správce scénářů hlásí napojení na zadanou třídu hry,</li>
     *   <li>správce scénářů hlásí napojení na zadanou instanci hry,</li>
     *   <li>jméno a xname autora hry musí být totožné
     *       se jménem a xname autor správce scénářů,</li>
     * </ul>
     *
     * @param manager   Správce scénářů prověřované aplikace
     * @param gameClass Třída testované hry
     * @param game      Testovaná instance
     * @throws IllegalArgumentException Nevyhovují-li parametry podmínkám
     */
    static void verifyTriumvirate(AScenarioManager manager,
                                  Class<? extends IGame> gameClass, IGame game)
           throws IllegalArgumentException
    {
        if (manager == null) {
            throw new IllegalArgumentException(
                  "\nNebyl zadán správce scénářů");
        }
        if ((gameClass == null)) {
            if (game != null) {
                throw new IllegalArgumentException(
                      "\nByla zadána instance hry, ale ne třída hry");
            }
            return;     //==========>
        }

        //gameClass != null
        if (game == null) {
            throw new IllegalArgumentException(
                  "\nByla zadána třída hry, ale ne instance hry");
        }
        if (game.getClass() != gameClass) {
            throw new IllegalArgumentException(
                  "\nZadaná třída hry se neshoduje s třídou zadané hry" +
                  "\n   Zadaná třída hry: " + gameClass +
                  "\n   Třída zadané hry: " + game.getClass());
        }
        if (game != GameTClassTest.getInstanceOfGame(gameClass))  {
            throw new IllegalArgumentException(
                  "\nZadaná hra není hrou, kterou vrací tovární metoda zadané" +
                  "třídy jako svého jedináčka\n   Třída: " + gameClass);
        }
        Class<? extends IGame> managerGameClass = manager.getGameClass();
        if  (gameClass != managerGameClass)  {
            throw new IllegalArgumentException(
                  "\nZadaná třída hry se neshoduje s třídou příslušnou " +
                     "k zadanému správce scénářů:" +
                  "\n   Třída, k níž se hlásí scénář: " + managerGameClass +
                  "\n   Zadaná třída hry:             " + gameClass);
        }
        IGame mGame = manager.getGame();
        if (game != mGame) {
            throw new IllegalArgumentException(
                  "\nInstance hry vrácená správcem scénářů není totožná " +
                  "se zadanou instancí hry: " + gameClass);
        }
        if (! (manager.getAuthorName().equals(game.getAuthorName())  &&
               manager.getAuthorID()  .equals(game.getAuthorID()  )  ))
        {
            throw new IllegalArgumentException(
               "\nAutor hry není totožný s autorem správce scénářů:" +
               "\n   Autoh správce scénářů: " + manager.getAuthorID() + " - " +
                                                manager.getAuthorName() +
               "\n   Autoh hry:             " + game.getAuthorID() + " - " +
                                                game.getAuthorName());
        }
    }


    /***************************************************************************
     * Zahraje podle zadaného scénáře, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param game     Hra, která se bude hrát podle zadaného scénáře
     * @param scenario Scénář, podle nějž se hra bude hrát
     * @param gui      Instance GUI, která bude zobrazovat průběh hry
     *                 a vůči níž bude testovací program vystupovat v roli hráče
     */
    public static void playGameGByScenario(
                       IGame game, Scenario scenario, IUIG gui)
    {
        GameGRunTest.playGameByScenario(game, scenario, gui);
//        TestGUI test = new TestGUI(game.getClass());
//        test.test(gui, game, AScenarioManager.INDEX_GUI_TEST);
    }


    /***************************************************************************
     * "Zahraje" podle zadaného scénáře, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param game   Hra, která se bude hrát podle zadaného scénáře
     * @param index  Index scénáře, podle nějž se hra bude hrát
     * @param gui    Instance GUI, která bude zobrazovat průběh hry
     *               a vůči níž bude testovací program vystupovat v roli hráče
     */
    public static void playGameGByScenario(IGame game, int index, IUIG gui)
    {
        Scenario scenario = game.getScenarioManager().getScenario(index);
        playGameGByScenario(game, scenario, gui);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Na základě obsahu přepravky s trojicí klíčových objektů
     * vytvoří novou instanci základu testovacích objektů
     *
     * @param crate Přepravka s potřebnými objekty
     * @throws IllegalArgumentException Nevyhovují-li parametry podmínkám
     */
    Triumvirate(Crate crate)
    {
        this(crate.manager, crate.gameClass, crate.game);
    }


    /***************************************************************************
     * Na základě obsahu přepravky s trojicí klíčových objektů
     * vytvoří novou instanci základu testovacích objektů
     *
     * @param manager   Správce scénářů prověřované aplikace
     * @param gameClass Třída testované hry
     * @param game      Testovaná instance
     * @throws IllegalArgumentException Nevyhovují-li parametry podmínkám
     */
    Triumvirate(AScenarioManager manager,
                Class<? extends IGame> gameClass, IGame game)
    {
        this.manager   = manager;
        this.gameClass = gameClass;
        this.gameT     = game;
        this.gameG     = (game instanceof IGameG)  ?  (IGameG)game  :  null;
    }




//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí hru prověřovanou daným testem.
     *
     * @return Hra prověřovaná daným testem
     */
    public IGame getGame()
    {
        return gameT;
    }


    /***************************************************************************
     * Vrátí hru prověřovanou daným testem.
     *
     * @return Hra prověřovaná daným testem
     */
    public IGameG getGameG()
    {
        return gameG;
    }


    /***************************************************************************
     * Vrátí hru prověřovanou daným testem.
     *
     * @return Hra prověřovaná daným testem
     */
    Crate getCrate()
    {
        return new Crate(manager, gameClass, gameT);
    }


    /***************************************************************************
     * Vrátí třídu hry prověřované tímto testem.
     *
     * @return Třída hry prověřované tímto testem
     */
    public Class<? extends IGame> getGameClass()
    {
        return gameClass;
    }


    /***************************************************************************
     * Vrátí správce scénářů testované hry.
     *
     * @return Správce scénářů testované hry
     */
    public AScenarioManager getManager()
    {
        return manager;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Ověří, že hra implementuje rozhraní {@link IGameG}.
     * Není-li tomu tak, vyhodí výjimku.
     *
     * @throws IllegalArgumentException
     *         Neimplementuje-li hra rozhraní {@link IGameG}
     */
    final void verifyImplementsIGameG()
    {
        if (gameG == null) {
            throw new IllegalArgumentException(
                "\nHra neimplementuje rozhraní IGameG: " + gameClass);
        }
    }


    /***************************************************************************
     * Ověří, že správce scénářů, třída hry a hra si navzájem odpovídají,
     * tj. že:
     * <ul>
     *   <li>je zadán správce scénářů,</li>
     *   <li>hra a třída hry musejí být zadány obě nebo žádná,</li>
     *   <li>zadaná hra je instancí zadané třídy hry,</li>
     *   <li>zadaná třída hry vrací zadanou hru, která je jedináčkem,</li>
     *   <li>zadaná hra vrací zadaného správce scénářů,</li>
     *   <li>správce scénářů hlásí napojení na zadanou třídu hry,</li>
     *   <li>správce scénářů hlásí napojení na zadanou instanci hry,</li>
     *   <li>jméno a xname autora hry musí být totožné
     *       se jménem a xname autor správce scénářů,</li>
     * </ul>
     */
    void verifyTriumvirate()
    {
        verifyTriumvirate(manager, gameClass, gameT);
    }


//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    static class Crate
    {
        final AScenarioManager       manager;
        final Class<? extends IGame> gameClass;
        final IGame                  game;


        Crate(AScenarioManager manager,
              Class<? extends IGame> gameClass, IGame game)
        {
            this.manager   = manager;
            this.gameClass = gameClass;
            this.game      = game;
        }
    }



//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    static void test()
//    {
//        Triumvirate inst = new Triumvirate();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
