/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse.adv_framework.scenario;

import cz.vse.IAuthor;

import cz.vse.adv_framework.empty_classes.EmptyGame;

import cz.vse.adv_framework.game_txt.IGame;

import cz.vse.adv_framework.test_util._Test_101;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



/*******************************************************************************
 * Instance třídy {@code AScenarioManager} spravují scénáře své hry
 * a jsou schopny na požádání dodat scénář, resp. kolekci scénářů
 * se zadanými vlastnostmi.
 * Každá hra musí mít k dispozici minimálně
 * <b>základní úspěšný scénář</b>,
 * který demonstruje některou z možných cest úspěšného dokončení hry,
 * a <b>chybový scénář</b>,
 * který demonstruje reakci hry na chybně zadané příkazy.
 * <p>
 * Jednotlivé scénáře se musí lišit svým názvem,
 * přičemž názvy základního úspěšného a základního chybového scénáře
 * jsou předem pevně dány.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public abstract class AScenarioManager implements IAuthor, Iterable<Scenario>
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Název základního úspěšného scénáře. */
    public static final String HAPPY_SCENARIO = "_HAPPY_";

    /** Název základního chybového scénáře. */
    public static final String MISTAKE_SCENARIO = "_MISTAKE_";

    /** Název scénáře pro testování GUI. */
    public static final String GUI_TEST_SCENARIO = "_DEMO_";

    /** Index základního úspěšného scénáře. */
    public static final int INDEX_HAPPY = 0;

    /** Index základního chybového scénáře. */
    public static final int INDEX_MISTAKE = 1;

    /** Název scénáře pro testování GUI. */
    public static final int INDEX_GUI_TEST = 2;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Třída, jejíž instance představují hry,
     *  které je možno hrát podle spravovaných scénářů. */
    private final Class<? extends IGame> gameClass;

    /** Jméno autora programu. */
    private final String author;

    /** Xname autora programu. */
    private final String xname;

    /** Seznam spravovaných scénářů. */
    private final List<Scenario> scenarioList = new ArrayList<>();

    /** Seznam názvů spravovaných scénářů. */
    private final List<String> nameList = new ArrayList<>();

    /** Příznak ukončenosti přidávání dalších scénářů do správy. */
    private boolean sealed;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Konkrétní hra (instance třídy {@link #gameClass}, která je/byla/bude
     *  právě testována prostřednictvím scénářů tohoto správce. */
    private IGame game;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Vytvoří nového správce scénářů na základě správce zadaného v parametru.
     * Vytvořený správce převezme od rozšiřovaného správce jeho data,
     * tj. jméno a xname autora, třídu hry a všechny jeho scénáře.
     * Je pak připraven doplnit převzatou sadu scénářů o další,
     * které si vytvoří sám na základě zadaných parametrů.
     *
     * @param  extendedSM Rozšiřovaný správce scénářů
     * @return Požadovaný správce scénářů
     */
    public static AScenarioManager extend(AScenarioManager extendedSM)
    {
        ExtendingSM manager = new ExtendingSM(extendedSM);
        return manager;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nového správce scénářů spravujícího všechny scénáře pro
     * hry, které jsou instancemi třídy zadané svým class-objektem.
     * Jméno autora/autorky programu a jeho/její xname musí být ve tvaru
     * specifikovaném v kontraktu metod rozhraní {@link IAuthor}.
     * <p>
     * Správce není po svém vytvoření ještě plně funkční - takovým se stane
     * až poté, co budou do jeho správy předány všechny požadované scénáře
     * a seznam spravovaných scénářů se zalepí.
     *
     * @param author Jméno autora/autorky programu v požadovaném tvaru
     * @param xname  Xname autora/autorky programu v požadovaném tvaru
     * @param gameClass Class-objekt třídy, pro jejíž instance jsou určeny
     *                  spravované scénáře. Je-li zadáno {@code null},
     *                  předpokládá se, že třída hry ještě není připravena
     *                  a je proto možnou pouze testovat scénáře
     *                  nebo simulovat průběh budoucí hry.
     */
    protected AScenarioManager(String author, String xname,
                               Class<? extends IGame> gameClass)
    {
        this.author    = author;
        this.xname     = xname;
        this.gameClass = (gameClass == null)
                         ?  EmptyGame.class
                         :  gameClass;
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * {@inheritDoc}
     */
    @Override
    public final String getAuthorName()
    {
        return author;
    }


    /***************************************************************************
     * {@inheritDoc}
     */
    @Override
    public final String getAuthorID()
    {
        return xname;
    }


    /***************************************************************************
     * Vrátí třídu, přesněji řečeno class-objekt třídy,
     * jejíž instance umějí realizovat spravované scénáře.
     *
     * @return  Třída hry schopné realizovat spravované scénáře
     */
    public final Class<? extends IGame> getGameClass()
    {
        return gameClass;
    }


    /***************************************************************************
     * Vrátí instanci hry, kterou můžeme hrát podle spravovaných scénářů.
     * Metoda však nezaručuje, že hra je právě rozehraná
     * a že proto nebude třeba ji před dalším spuštěním nejprve ukončit.
     *
     * @return Instance hry hratelné podle spravovaných scénářů
     */
    public final IGame getGame()
    {
        if (game == null) {
            if (gameClass == null) {
                throw new IllegalStateException(
                    "\nTřída hry je ve správci scénářů nastavena na null," +
                    "\na není proto možné získat instanci hry");
            }
            game = _Test_101.getInstanceOfGame(gameClass);
        }
        return game;
    }

//
//    /***************************************************************************
//     * Nastaví konkrétní hru, kterou bude možno hrát podle scénářů
//     * spravovaných daným správcem.
//     *
//     * @param game Nastavovaná hra
//     */
//    public final void setGame(IGame game)
//    {
//        if (gameClass.equals(game.getClass())) {
//            this.game = game;
//            return;
//        }
//        throw new IllegalArgumentException(
//                "\nTřída zadané hry neodpovídá třídě her daného správce" +
//                "\n      Zadáno:     " + game.getClass() +
//                "\n      Požadováno: " + gameClass);
//
//    }
//

    /***************************************************************************
     * Vrátí index zadaného scénáře
     *
     * @param scenario Scénář, jehož index hledáme
     * @return Index zadaného scénáře
     */
    public int getIndexOf(Scenario scenario)
    {
        return scenarioList.indexOf(scenario);
    }


    /***************************************************************************
     * Vrátí seznam názvů všech spravovaných scénářů.
     *
     * @return Seznam s názvy scénářů
     */
    public final List<String> getAllScenarioNames()
    {
        verifySealed();
        return Collections.unmodifiableList(nameList);
    }


    /***************************************************************************
     * Vrátí počet spravovaných scénářů.
     *
     * @return Seznam s názvy scénářů
     */
    public final int getSize()
    {
        verifySealed();
        return scenarioList.size();
    }


    /***************************************************************************
     * Vrátí scénář se zadaným indexem.
     *
     * @param index Index požadovaného scénáře
     * @return Scénář se zadaným indexem
     */
    public final Scenario getScenario(int index)
    {
        verifySealed();
        return scenarioList.get(index);
    }


    /***************************************************************************
     * Vrátí scénář se zadaným názvem.
     *
     * @param name Název požadovaného scénáře
     * @return Scenario se zadaným názvem; není-li, vrátí {@code null}
     */
    public final Scenario getScenario(String name)
    {
        verifySealed();
        for (int i=0;   i < nameList.size();   i++) {
            if (nameList.get(i).equalsIgnoreCase(name)) {
                return scenarioList.get(i);
            }
        }
        throw new IllegalArgumentException(
                "\nScénář se zadaným názvem nemám ve správě");
    }


    /***************************************************************************
     * Vrátí základní úspěšný scénář &ndash; happy scenario.
     *
     * @return Základní úspěšný scénář
     */
    public final Scenario getHappyScenario()
    {
        verifySealed();
        return scenarioList.get(INDEX_HAPPY);
    }


    /***************************************************************************
     * Vrátí základní chybový scénář.
     *
     * @return Základní chybový scénář
     */
    public final Scenario getMistakeScenario()
    {
        verifySealed();
        return scenarioList.get(INDEX_MISTAKE);
    }


    /***************************************************************************
     * Vrátí scénář určený pro test GUI.
     *
     * @return Scénář určený pro test GUI
     */
    public final Scenario getGuiTestScenario()
    {
        verifySealed();
        return scenarioList.get(INDEX_GUI_TEST);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//
//    /***************************************************************************
//     * Základní test ověřující, jestli správce scénářů vyhovuje
//     * zadným okrajovým podmínkám.
//     */
//    public void autoTest()
//    {
//        _Test_101 test = _Test_101.getInstance(this);
//        test.testScenarioManager();
//    }
//

    /***************************************************************************
     * Vrátí iterátor, který bude poskytovat jednotlivé spravované scénáře
     * v pořadí, v jakém byly zadány.
     *
     * @return Iterátor přes spravované scénáře
     */
    @Override
    public final Iterator<Scenario> iterator()
    {
        verifySealed();
        return scenarioList.iterator();
    }


    /***************************************************************************
     * Vytvoří nový scénář se zadaným názvem a posloupností kroků
     * a přidá jej mezi spravované scénáře.
     * Přitom kontroluje dodržení následujících omezení:
     * <ul>
     *   <li>Seznam scénářů ještě není zalepen a je proto ještě možno
     *       přidat další scénář.</li>
     *   <li>Prvním přidaným scénářem musí bý scénář typu
     *       {@link TypeOfScenario#scHAPPY}.
     *       Případný zadaný název je ignorován a scénáři se přiřadí název
     *       uložený v atributu {@link #HAPPY_SCENARIO}.</li>
     *   <li>Druhým přidaným scénářem musí bý scénář typu
     *       {@link TypeOfScenario#scMISTAKES}.
     *       Případný zadaný název je ignorován a scénáři se přiřadí název
     *       uložený v atributu {@link #MISTAKE_SCENARIO}.</li>
     *   <li>U každého dalšího přidávaného scénáře se jeho název
     *       musí lišit od názvů všech scénářů přidaných před ním.</li>
     *   <li>Každý přidávaný scénář musí začínat krokem s příkazem
     *       zadaným jako prázdný řetězec, což je startovací příkaz hry.
     *       Hra se po jeho zpracování dostane do svého výchozího stavu.
     * </ul>
     *
     * @param name  Název přidávaného scénáře (u prvního a druhého zadávaného
     *              scénáře se zadané názvy ignorují)
     * @param type  Typ přidávaného scénáře (na typ prvního a druhého
     *              přidávaného scénáře jsou kladeny výše popsané požadavky).
     * @param steps Kroky vytvářeného a následně přidávaného scénáře.
     * @throws IllegalStateException
     *         Přidávání scénářů již bylo uzavřeno
     * @throws IllegalArgumentException
     *         Není-li splněn některý z ostatních požadavků
     */
    protected void addScenario(String name, TypeOfScenario type,
                               ScenarioStep... steps)
    {
        verifyNotSeald();
        String názevScénáře;
        if (scenarioList.isEmpty()) {
            if (type != TypeOfScenario.scHAPPY) {
                throw new IllegalArgumentException(
                "\nPrvním zadaným scénářem musí být základní úspěšný scénář");
            }
            názevScénáře = HAPPY_SCENARIO;
        }
        else if (scenarioList.size() == 1) {
            if (type != TypeOfScenario.scMISTAKES) {
                throw new IllegalArgumentException(
                "\nDruhým zadaným scénářem musí být základní chybový scénář");
            }
            názevScénáře = MISTAKE_SCENARIO;
        }
        else {
            názevScénáře = name;
        }
        Scenario scenario = new Scenario(názevScénáře, type, this, steps);
        addScenario(scenario);
    }


    /***************************************************************************
     * Vytvoří nový scénář se zadaným názvem a
     * posloupností demonstračních kroků definovaných zadanými příkazy,
     * přiřadí mu typ {@link TypeOfScenario#scDEMO}
     * a přidá tento scénář mezi spravované scénáře.
     * Přitom kontroluje dodržení omezení popsaných v komentáři metody
     * {@link #addScenario(String, TypeOfScenario, ScenarioStep...)},
     * z čehož plyne, že tuto metodu je možno použít nejdříve
     * pro třetí přidávaný scénář.
     *
     * @param name     Název přidávaného scénáře
     * @param command  Akce v jednotlivých krocích scénáře
     * @throws IllegalStateException
     *         Přidávání scénářů již bylo uzavřeno
     * @throws IllegalArgumentException
     *         Není-li splněn některý z ostatních požadavků
     */
    protected void addScenario(String name, String... command)
    {
        verifyNotSeald();
        if (scenarioList.size() < 2) {
            throw new IllegalArgumentException(
                "\nScénář typu TypScénáře.DEMONSTRAČNÍ " +
                "smí být zařazen nejdříve jako třetí");
        }
        Scenario    scenario = new Scenario(name, this, command);
        addScenario(scenario);
    }


    /***************************************************************************
     * Zalepí seznam spravovaných scénářů a zavře zavře tak možnost
     * předávání dalších scénářů do správy daného správce.
     * Od této chvíle je správce plně funkční.
     */
    protected void seal()
    {
        sealed = true;
    }

//
//    /***************************************************************************
//     * Základní test ověřující, jestli správce scénářů vyhovuje
//     * zadným okrajovým podmínkám.
//     */
//    public void testScenarioManager()
//    {
//        _Test_101 test = _Test_101.getInstance(this);
//        test.testScenarioManager();
//    }
//
//
//    /***************************************************************************
//     * Testování funkce hry prováděné postupně
//     * prostřednictvím všech scénářů daného správce
//     */
//    public void testGame()
//    {
//        _Test_101 test = _Test_101.getInstance(game);
//        test.testGame();
//    }
//
//
//    /***************************************************************************
//     * Test ověřující funkci textové verze hry.
//     */
//    public void testScenarioForGUI()
//    {
//        _Test_101 test = _Test_101.getInstance(game);
//        test.playGameByScenario(INDEX_GUI_TEST);
//    }
//
//
//    /***************************************************************************
//     * Test demonstrující hraní hry na implicitním GUI.
//     */
//    public void testGameG()
//    {
//        _Test_115 test = _Test_115.getInstance((IGameG)game);
//        test.playGameByScenario(INDEX_GUI_TEST);
//    }
//

    /***************************************************************************
     * Vrátí textový podpis daného správce scénářů, v němž uvede
     * xname a jméno jeho autora, název třídy hry, k níž se vztahují
     * a seznam názvů všech spravovaných scénářů.
     * Podpis je pro přehlednost rozdělen na několik řádků.
     *
     * @return Textový podpis daného správce scénářů
     */
    @Override
    public String toString()
    {
        return "Správce scénářů autora: " + xname + " - " + author +
               "\npro hru: " + gameClass +
               "\nScénáře: " + nameList;
    }


    /***************************************************************************
     * Ověří, že přidávání scénářů do správy ještě nebylo ukončeno,
     * tj. že seznam scénářů ještě není zalepen.
     *
     * @throws IllegalStateException Přidávání scénářů již bylo uzavřeno
     */
    protected final void verifyNotSeald() throws IllegalStateException
    {
        if (sealed) {
            throw new IllegalStateException(
                    "\nSeznam scénářů je již zalepen, není možno přidat další");
        }
    }


    /***************************************************************************
     * Ověří, že přidávání scénářů do správy již bylo ukončeno,
     * tj. že seznam scénářů je již zalepen.
     *
     * @throws IllegalStateException Přidávání scénářů ještě nebylo ukončeno
     */
    protected final void verifySealed()
    {
        if (! sealed) {
            throw new IllegalStateException(
                "\nSprávce scénářů ještě není možno použít -" +
                "\nještě nebylo uzavřeno přidávání scénářů do jeho správy" +
                "\n(jinými slovy: správce ještě není \"zalepen\")");
        }
    }


//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================

    /***************************************************************************
     * Přidá zadaný scénář mezi spravované. Přitom kontroluje, že přidávání
     * scénářů ještě nebylo uzavřeno (seznam scénářů ještě není zalepen)
     * a že scénář se zadaným názvem ještě nebyl přidán.
     *
     * @param scenario Přidávaný scénář
     * @exception IllegalArgumentException Není-li splněn některý z požadavků
     */
    private void addScenario(Scenario scenario)
    {
        if (sealed) {
            throw new IllegalStateException(
                "\nPředávání nových scénářů do správy již bylo uzavřeno");
        }
        String name = scenario.getName();
        if ((name == null)  ||  "".equals(name)) {
            throw new IllegalArgumentException(
                    "\nNázev scénáře musí být neprázdný řetězec");
        }
        for (String ns : nameList) {
            if (name.equalsIgnoreCase(ns)) {
                throw new IllegalArgumentException(
                    "\nScénář se zadaným názvem již existuje: " + name);
            }
        }
        nameList    .add(name);
        scenarioList.add(scenario);
    }



//== EMBEDDED TYPES AND INNER CLASSES ==========================================

///#############################################################################
///#############################################################################
///#############################################################################

    /***************************************************************************
     * Instance třídy {@code ExtendingSM} jsou správci scénářů odvození
     * od existujících scénářů, jejichž seznam spravovaných scénářů rozšiřují.
     * Používají je testovací metody, které doplňují existující scénáře
     * vlastními (většinou syntetickými) scénáři.
     *
     * @author    Rudolf PECINOVSKÝ
     * @version   0.00.000
     */
    private static class ExtendingSM extends AScenarioManager
    {
        final AScenarioManager aux = this;
        final AScenarioManager source;

        /***********************************************************************
         * Vytvoří nového správce scénářů,
         * který bude rozšířením správce zadaného v parametru,
         * přesněji bude mít rozšířenou množinu spravovanýchz scénářů.
         *
         * @param source Výchozí rozšiřovaný správce scénářů
         */
        ExtendingSM(AScenarioManager source)
        {
            super(source.getAuthorName(), source.getAuthorID(),
                  source.getGameClass());
            this.source = source;
            for (Scenario scenario : source) {
                //Výchozí scénář má již svůj seznam zkontrolovaný,
                //takže jej nemusím znovu kontrolovat a navíc si ušetřím
                //odvozování původních parametrů konstruktoru scénáře
                aux.nameList    .add(scenario.getName());
                aux.scenarioList.add(scenario);
            }
        }


        /***********************************************************************
         * {@inheritDoc} */
        @Override
        public void addScenario(String name, TypeOfScenario type,
                                ScenarioStep... steps)
        {
            super.addScenario(name, type, steps);
        }


        /***********************************************************************
         * {@inheritDoc} */
        @Override
        public void seal()
        {
            super.seal();
        }


        /***********************************************************************
         * Vrátí odkaz na správce scénářů,
         * který se stal zdrojem daného rozšiřujícího správce.
         *
         * @return Zdrojový správce scénářů.
         */
        public AScenarioManager getSource()
        {
            return source;
        }
    }



//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        AScenarioManager inst = new AScenarioManager();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
