/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.scenario;

import cz.vse.adv_framework.game_txt.INamed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



/*******************************************************************************
 * Instance třídy {@code Scenario} představují scénáře,
 * podle kterých je možno hrát hru, pro kterou jsou určeny.
 * Aby bylo možno jednotlivé scénáře od sebe odlišit,
 * je každý pojmenován a má přiřazen typ, podle které lze blíže určit,
 * k čemu je možno daný scénář použít.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class Scenario implements INamed, Iterable<ScenarioStep>
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název scénáře. */
    private final String name;

    /** Typ scénáře. */
    private final TypeOfScenario type;

    /** Třída hry. */
    private final AScenarioManager scenarioManager;

    /** Jednotlivé kroky daného scénáře. */
    private final List<ScenarioStep> steps = new ArrayList<>();



//== VARIABLE INSTANCE ATTRIBUTES ==============================================

    /** Podpis scénáře - vyrábí se až na požádání. */
    private String podpis;



//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří nový scénář zadaného názvu a typu určený pro zadanou hru.
     * Konstruktor není veřejný, protože bude volán pouze z metod třídy
     * {@link AScenarioManager}, která je ve stejném balíčku.
     *
     * @param name  Název scénáře. Název musí být neprázdný řetězec
     *              a nepočítají se do něj případné úvodní a závěrečné mezery.
     * @param type  Typ scénáře
     * @param scenarioManager Správce, jenž bude vytvořený scénář spravovat
     *              (a který si jej pro sebe vytváří)
     * @param steps Jednotlivé kroky realizující daný scénář;
     *              příkaz v prvním (přesněji nutlém) kroku
     *              musí být definována jako prázdný řetězec
     */
    @SuppressWarnings("AssignmentToMethodParameter")
    public Scenario(String name, TypeOfScenario type,
                    AScenarioManager scenarioManager, ScenarioStep... steps)
    {
        name = name.trim();
        boolean wrongStartTested = false;

        boolean _1 =
            //Scénář má neprázdné jméno a zadný typ
            (name  == null)  ||  ("".equals(name))    ||  (type == null)  ||
            //Má zadaný nenulový počet korků
            (steps == null)  ||  (steps.length == 0);

        boolean _2 =
            ((wrongStartTested =
              (steps[0].typeOfStep == TypeOfStep.tsNOT_START))
              &&
              //  má zadaný neprázdný příkaz
              ((steps[0].command == null)  ||
                steps[0].command.isEmpty()));

        boolean _2w = wrongStartTested;

        boolean _3 =
            (! "".equals(steps[wrongStartTested ? 1 :0].command));


        if (//Scénář má neprázdné jméno a zadný typ
            (name  == null)  ||  ("".equals(name))    ||  (type == null)    ||

            //Má zadaný nenulový počet korků
            (steps == null)  ||  (steps.length == 0)                        ||

//            //První příkaz je testem reakce na špatné spuštění,
//            (((wrongStartTested =
//               (steps[0].typeOfStep == TypeOfStep.tsNOT_START))
//              &&
//              //  a má přitom zadaný prázdný příkaz
//              ((steps[0].command == null)  ||
//                steps[0].command.isEmpty())))                               ||
//
//            //První příkaz netestující reakci na špatné spuštění
//            //není zadán jako prázdný řetězec
//            (! "".equals(steps[wrongStartTested ? 1 :0].command))           ||

            false)
        {
            throw new IllegalArgumentException(
                "\nHodnoty parametrů konstruktoru neodpovídají kontraktu:" +
                "\n   Název: «" + name +
               "»\n   Kroků: "  + (steps==null ? "NULL" : steps.length) +
                "\n   Startovací příkaz: «" +
                      ((steps==null || steps.length==0) ? "NULL"
                                                        : steps[0].command) + "»"
               );
        }
        this.name = name;
        this.type = type;
        this.scenarioManager = scenarioManager;
        this.steps.addAll(Arrays.asList(steps));
    }


    /***************************************************************************
     * Vytvoří nový demonstrační scénář zadaného názvu, který bude obsahovat
     * pouze zadání příkazů a nebude umožňovat kontrolu správných reakcí hry.
     * Konstruktor není veřejný, protože jej bude volat pouze správce scénářů,
     * který si scénář vytváří pro sebe.
     * <p>
     * Parametry musí odpovídat kontraktu uvedenému u čtyřparametrické verze.
     *
     * @param name             Název scénáře v rámci scénářů dané hry
     * @param scenarioManager  Třída hry, pro níž je scénář vytvářen
     * @param command          Posloupnost akcí realizujících daný scénář
     */
    Scenario(String name, AScenarioManager scenarioManager,
             String... command)
    {
        this(name, TypeOfScenario.scDEMO, scenarioManager,
             prepareSteps(command));
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název scénáře.
     *
     * @return Název scénáře
     */
    @Override
    public String getName()
    {
        return name;
    }


    /***************************************************************************
     * Vrátí typ scénáře.
     *
     * @return  Typ scénáře
     */
    public TypeOfScenario getType()
    {
        return type;
    }


    /***************************************************************************
     * Vrátí svého správce scénářů.
     *
     * @return  Správce scénářů spravující a zpříástupňující daný scénář
     */
    public AScenarioManager getManager()
    {
        return scenarioManager;
    }


    /***************************************************************************
     * Vrátí řetězec s jednotlivými příkazy scénáře.
     * Každý příkaz bude na samostatném řádku uvozený zadaným prefixem.
     *
     * @param  prefix  Začátek řádku předcházející každému příkazu
     * @return Řetězec s jednotlivými příkazy scénáře
     */
    public String getCommands(String prefix)
    {
        StringBuilder sb = new StringBuilder();
        for (ScenarioStep step : this) {
            sb.append(prefix).append(step.command).append('\n');
        }
        return sb.toString();
    }


    /***************************************************************************
     * Vrátí seznam příkazů daného scénáře tak, jak by byly zadávány.
     *
     * @return Seznam příkazů daného scénáře
     */
    public List<String> getListOfCommands()
    {
        List<String> list = new ArrayList<>();
        for (ScenarioStep step : this) {
            list.add(step.command);
        }
        return list;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vrátí iterátor umožňující procházet kroky daného scénáře.
     *
     * @return  Iterátor kroků scénáře
     */
    @Override
    public Iterator<ScenarioStep> iterator()
    {
        return steps.iterator();
    }


    /***************************************************************************
     * Vrátí textový podpis daného scénáře,
     * v němž je uveden jeho index, název a správce.
     *
     * @return Textový podpis daného scénáře
     */
    @Override
    public String toString()
    {
        if (podpis == null) {
            AScenarioManager asm = getManager();
            podpis = "Scénář(Index=" + asm.getIndexOf(this) +
                     ", Název="      + getName() +
                     ", Správce="    + getManager() + ")";
        }
        return podpis;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Ze zadaných popisů akcí vytvoří pole odpovídajících demonstračních
     * kroků scénáře, tj. kroků, které nebudou umožňovat testování,
     * ale budou sloužit pouze k simulaci průběhu hry.
     *
     * @param  command Pole popisů zadávaných akcí
     * @return Pole demonstračních kroků scénáře
     */
    private static ScenarioStep[] prepareSteps(String[] command)
    {
        ScenarioStep.clearIndex();
        ScenarioStep[] steps = new ScenarioStep[command.length+1];
        steps[0] = new ScenarioStep("");
        for (int i=0;   i < command.length;   i++) {
            ScenarioStep step = new ScenarioStep(command[i]);
            steps[i+1] = step;
        }
        return steps;
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
//        Scenario inst = new Scenario();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
