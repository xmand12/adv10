/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.scenario.AScenarioManager;
import cz.vse.adv_framework.scenario.Scenario;



/*******************************************************************************
 * Instance třídy {@code _Test_101} jsou schopny otestovat jednotlivé části
 * projektu textové komunikační hry &nbsp; adventury.
 * Třída slouží jako fasáda k modulu obsahujícímu nejrůznější
 * testovací nástroje pro testování hry a jejích jednotlivých součástí,
 * u nichž je schopna ověřit, nakolik odpovídají požadavkům rámce
 * definovaného v balíčku <b>{@code cz.vse.adv_framework.game_txt}</b>.
 * <p>
 * Třída slouží jako fasáda k k třídám v tomto balíčku poskytujícím nejrůznější
 * testovací nástroje pro testování hry a jejích jednotlivých součástí, pomocí
 * nichž je možno ověřit, nakolik tyto součásti odpovídají požadavkům rámce.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class _Test_101 extends Triumvirate
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Jedná-li se o obhajobu, bude zde číslo > 0. */
    public static int version = 0;

    /** Celkový bodový zisk. */
    protected double score = 0;

    /** Stručná zpráva o průběhu testu. */
    protected StringBuilder shortMessage = new StringBuilder();

    /** Kompletní zpráva o průběhu testu. */
    protected StringBuilder verboseMessage = new StringBuilder();



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci třídy, jejíž úplný název získá v parametru.
     * Tato třída musí implementovat rozhraní {@link IGame},
     * a definovat tovární metodu {@code getInstance()} nebo {@code getHra()}.
     *
     * @param arg0 Název hledané třídy.
     * @return Instance třídy zadané v parametru
     * @throws RuntimeException
     *         Není-li parametr názvem třídy implementující {@link IGame}
     *         nebo nelze-li získat instanci této třídy zavoláním
     *         tovární metody {@code getInstance()} nebo {@code getHra()}
     */
    @SuppressWarnings("unchecked")
    public static IGame getInstanceOfGame(String arg0) throws RuntimeException
    {
        Class<?>               cls;
        Class<? extends IGame> clsIGame;
        try {
            cls = Class.forName(arg0);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("\nParametr není jménem třídy: " + arg0);
        }
        if (!IGame.class.isAssignableFrom(cls)) {
            throw new RuntimeException(
                      "\nZadaná třída neimplementuje rozhraní IGame: " + arg0);
        }
        clsIGame = (Class<IGame>)cls;
        return getInstanceOfGame(clsIGame);
    }


    /***************************************************************************
     * Zjistí, jestli třída zadaná svým class-objektem definuje statickou
     * tovární metodu {@code getInstance} nebo {@code getGame} nebo
     * {@code getHra} požádá nalezenou metodu o instanci dané třídy.
     *
     * @param cls Class-objekt třídy, jejíž instanci chceme získat
     * @return Instance zadané třídy
     * @throws RuntimeException  Nepodaří-li se získat požadovanou instanci
     * @throws SecurityException Nemáme-li právo přehrabovat se v class-objektu
     */
    public static IGame getInstanceOfGame(Class<? extends IGame> cls)
            throws RuntimeException//, SecurityException
    {
        //Přehrává získání instance na testovací třídu, protože součástí operace
        //je i kontrola dodržení některých požadavků kontraktu
        return GameTClassTest.getInstanceOfGame(cls);
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci testovací třídy napojenou na zadaného správce scénářů.
     * Správce scénářů je klíčovým bodem celé hry, protože jeho prostřednictvím
     * se je možno dostat ke všem ostatním dostupným informacím o hře.
     *
     * @param manager Správce scénářů prověřované aplikace
     * @return Instance schopná testovat zadaného správce scénářů
     *         včetně případné hry, jejíž scénáře spravuje
     */
    public static _Test_101 getInstance(AScenarioManager manager)
    {
        return new _Test_101(fromManager(manager));
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat
     * instance zadané třídy hry.
     *
     * @param gameClass Třída testované hry
     * @return Instance schopná testovat zadanou třídu hry a její hru
     */
    public static _Test_101 getInstance(Class<? extends IGame> gameClass)
    {
        return new _Test_101(fromGameClass(gameClass));
    }


    /***************************************************************************
     * Vrátí instanci testovací třídy schopnou testovat zadanou hru.
     *
     * @param game Testovaná hra
     * @return Instance schopná testovat zadanou hru
     */
    public static _Test_101 getInstance(IGame game)
    {
        return new _Test_101(fromGame(game));
    }


    /***************************************************************************
     * Vytvoří novou instanci testovací třídy napojenou na zadaného
     * správce scénářů, který poskytne podklady pro testování
     * zadané hry a její instance-
     *
     * @param crate  Přepravka se správcem scénářů prověřované aplikace,
     *               class-objektem třídy hry a prověřovanou hrou
     * @throws IllegalArgumentException Nevyhovují-li parametry podmínkám
     */
    private _Test_101(Crate crate)
    {
        super(crate);
        testScenarioManager();
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí bodové hodnocení doposud provedených testů
     *
     * @return Bodové hodnocení doposud provedených testů
     */
    public double getScore()
    {
        return score;
    }


    /***************************************************************************
     * Vrátí zkrácenou verzi zprávy o provedených testech.
     *
     * @return Zkrácená verze zprávy o provedených testech
     */
    public String getShortMessage()
    {
        return shortMessage.toString();
    }


    /***************************************************************************
     * Vrátí podrobnou zprávu o provedených testech.
     *
     * @return Podrobná zpráva o provedených testech
     */
    public String getVerboseMessage()
    {
        return verboseMessage.toString();
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * "Zahraje" podle scénáře se zadaným indexem, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param i Index scénáře, podle nějž se hra bude hrát
     */
    public void playGameByScenario(int i)
    {
        playGameByScenario(manager.getScenario(i));
    }


    /***************************************************************************
     * "Zahraje" podle scénáře se zadaným názvem, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param name Název scénáře, podle nějž se hra bude hrát
     */
    public void playGameByScenario(String name)
    {
        playGameByScenario(manager.getScenario(name));
    }


    /***************************************************************************
     * "Zahraje" podle zadaného scénáře, aniž by se snažila prověřit,
     * zda se hra chová opravdu tak, jak případný testovací scénář naplánoval.
     * Metodu lze proto použít i pro demonstrační scénáře.
     *
     * @param scenario Scénář, podle nějž se hra bude hrát
     */
    public void playGameByScenario(Scenario scenario)
    {
        GameTRunTest test = new GameTRunTest(gameT);
        test.executeScenario(scenario);
    }


    /***************************************************************************
     * Simuluje hraní hry podle scénáře se zadaným indexem.
     *
     * @param i   Index scénáře, podle nějž se hra bude simulovat
     * @param showSavedInfo {@code true} požádá, aby po každém kroku scénáře,
     *            který lze použít jako testovací, byly vypsány informace
     *            o požadovaném stavu hry po provedené daného kroku
     */
    public void simulateScenario(int i, boolean showSavedInfo)
    {
        simulateScenario(manager.getScenario(i), showSavedInfo);
    }


    /***************************************************************************
     * Simuluje hraní hry podle scénáře se zadaným názvem.
     *
     * @param name  Název scénáře, podle nějž se hra bude simulovat
     * @param showSavedInfo {@code true} požádá, aby po každém kroku scénáře,
     *              který lze použít jako testovací, byly vypsány informace
     *              o požadovaném stavu hry po provedené daného kroku
     */
    public void simulateScenario(String name, boolean showSavedInfo)
    {
        simulateScenario(manager.getScenario(name), showSavedInfo);
    }


    /***************************************************************************
     * Simuluje hraní hry podle zadaného scénáře.
     *
     * @param scenario  Scénář, podle nějž se hra bude simulovat
     * @param showSavedInfo {@code true} požádá, aby po každém kroku scénáře,
     *                  který lze použít jako testovací, byly vypsány informace
     *                  o požadovaném stavu hry po provedené daného kroku
     */
    public void simulateScenario(Scenario scenario, boolean showSavedInfo)
    {
        ScenarioSimulator.simulateScenario(scenario, showSavedInfo);
    }


    /***************************************************************************
     * Prověří, že hra pracuje podle obou povinných scénářů,
     * tj. podle základního úspěšného a základního chybového scénáře.
     */
    public void testGame()
    {
//        testScenarioManager();
        testGameByScenario(0);
        testGameByScenario(1);
//        testGameB();
   }


    /***************************************************************************
     * Prověří hru tak, že ji postupně "zahraje" podle všech testovacích scénářů
     * spravovaných jejím správcem scénářů.
     */
    public void testGameByAllScenarios()
    {
        verifyTestability();
        GameTRunTest test = new GameTRunTest(gameT);
        test.executeAllScenarios();
    }


    /***************************************************************************
     * Prověří hru tak, že ji "zahraje" podle scénáře se zadaným indexem.
     * Zadaný scénář musí být testovací.
     *
     * @param i Index testovacího scénáře
     */
    public void testGameByScenario(int i)
    {
        testGameByScenario(manager.getScenario(i));
    }


    /***************************************************************************
     * Prověří hru tak, že ji "zahraje" podle scénáře se zadaným názvem.
     * Zadaný scénář musí být testovací.
     *
     * @param name Název testovacího scénáře
     */
    public void testGameByScenario(String name)
    {
        testGameByScenario(manager.getScenario(name));
    }


    /***************************************************************************
     * Prověří hru tak, že ji "zahraje" podle zadaného scénáře.
     * Zadaný scénář musí být testovací.
     *
     * @param scenario Scénář, podle nějž se hra bude testovat
     */
    public void testGameByScenario(Scenario scenario)
    {
        System.out.println("##### Test verze " + _Test_101.version);
        verifyTestability();
        GameTRunTest test = new GameTRunTest(gameT);
        test.executeScenario(scenario);
        score += test.getScore();
        shortMessage  .append(test.getShortMessage());
        verboseMessage.append(test.getVerboseMessage());
    }


    /***************************************************************************
     * Prověří, zda správce scénářů vyhovuje zadaným požadavkům.
     */
    public void testScenarioManager()
    {
        ScenarioManagerTest smt = ScenarioManagerTest.getInstance();
        smt.test(manager);
        score += smt.getScore();
        shortMessage  .append(smt.getShortMessage());
        verboseMessage.append(smt.getVerboseMessage());
    }


    /***************************************************************************
     * Prověří, zda správce scénářů vyhovuje zadaným požadavkům.
     *
     * @return Vyhovuje-li, vrátí {@code true}, jinak vrátí {@code false}
     */
    public boolean answerTestScenarioManager()
    {
        ScenarioManagerTest smt = ScenarioManagerTest.getInstance();
        return smt.answerTest(manager);
    }


//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Prověří, že aktuální triumvirát je možno testovat,
     * tj. že existuje testovaná hra a jsou splněny další podmínky.
     *
     * @throws RuntimeException Nelze-li daný triumvirát testovat
     */
    private void verifyTestability() throws RuntimeException
    {
        if (gameT == null) {
            throw new RuntimeException(
                    "\nHra je zadána jako prázdný odkaz => nelze ji testovat");
        }
        verifyTriumvirate();
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        _Test_101 inst = new _Test_101();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
