/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.game_gui.IGameG;
import cz.vse.adv_framework.game_gui.IUIG;

import cz.vse.adv_framework.scenario.Scenario;
import cz.vse.adv_framework.scenario.ScenarioStep;

import cz.vse.adv_framework.utilities.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.filechooser.FileNameExtensionFilter;



//TODO Počáteční dolar v názvu oznamuje, že je třeba třídu ještě zkontrolovat - Prověřit
/*******************************************************************************
 * Instance třídy {@code $TestGUI} představují testovací objekty
 * připravené otestovat instance tříd implementujících rozhraní {@link IUIG}.
 * <p>
 * Počáteční dolar v názvu oznamuje, že je třeba třídu ještě zkontrolovat
 *
 * @author    Rudolf PECINOVSKY
 * @version   4.02.000,  23.3.2007
 */
class $TestGUI
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Objekt, který z úplného názvu třídy odvodí název,
     *  kterým se bude třídu prezentovat v seznamu v dialogovém okně. */
    private static final Converter converter = new Converter();

    private static final String SEPARATOR = "\n\n=========================\n\n";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        IO.placeWindowsAt(0, 500);
    }



//== KONSTANTNÍ ATRIBUTY INSTANCI ==============================================

    private final File rootFile;



//== PROMĚNNÉ ATRIBUTY INSTANCI ================================================
//== NESOUKROMÉ METODY TŘÍDY ===================================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Připraví instanci pro testování uživatelských rozhraní a her,
     * přičemž kořenový balíček, v němž jsou umístěny class-soubory tříd
     * určených k testování, je odvozen z umístění class-souboru třídy
     * zadané v parametru.
     *
     * @param rootClass Třída z jejíhož class-souboru se odvodí složka
     *            představující kořenový balíček
     */
    $TestGUI(Class<?> rootClass)
    {
        URL classURL = rootClass.getResource(
                                 rootClass.getSimpleName() + ".class");
        URI classURI;
        try {
            classURI = classURL.toURI();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(
                    "\nURL nepřevedeno na URI: " + classURL, ex);
        }
        File   classFile = new File(classURI);
        String path      = classFile.toString();
        String name      = rootClass.getName() + ".class";
        int    charNum   = name.length();
        int    last      = path.length() - charNum;
        String root      = path.substring(0, last);

        rootFile = new File(root);
    }


    /***************************************************************************
     * Připraví instanci pro testování uživatelských rozhraní a her,
     * přičemž kořenový balíček, v němž jsou umístěny class-soubory tříd
     * určených k testování, je odvozen z umístění class-souboru této třídy.
     */
    $TestGUI()
    {
        this($TestGUI.class);
    }



//== ABSTRACT METHODS ==========================================================
//== NESOUKROMÉ METODY INSTANCI ================================================

    /***************************************************************************
     * Otestuje zadané uživatelské rozhraní spuštěním zadané hry
     * ovládané příkazy ze zadaného vstupního znakového proudu.
     *
     * @param gui   Testované uživatelské rozhraní
     * @param game  Provozovaná hra
     * @param input Vstupní znakový proud s testovacími příkazy
     */
    public void test(IUIG gui, IGameG game, Reader input) {
        BufferedReader bInput = new BufferedReader(input);
        gui.startGame(game);
        String last   = "";
        String prolog =
            "Hra: " + converter.full2shortName(game.getClass().getName()) +
          "\nGUI: " + converter.full2shortName( gui.getClass().getName()) +
        "\n\nAutor(ka) hry: " + game.getAuthorName() +
          "\nAutor(ka) GUI: " + gui .getAuthorName() + SEPARATOR +
            "Minulý příkaz: ";
//        if (!end()) {
            for(;;) {
                String command;
                try { command = bInput.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(
                        "\nČtení příkazu se nezdařilo", ex);
                }
                if(command == null) {
                    break;
                }
                if(finish(prolog + last + "\n\nNyní zadávám:  " + command)) {
                    break;
                }
                gui.executeCommand(command);
                last = command;
            }
//        }
        game.stop();
        IO.message("KONEC TESTU");
    }


    /***************************************************************************
     * Otestuje zadané uživatelské rozhraní spuštěním zadané hry
     * ovládané příkazy ze zadaného textového řetězce.
     *
     * @param ui    Testované uživatelské rozhraní
     * @param game  Provozovaná hra
     * @param input Textový řetězec s testovacími příkazy
     */
    public void test(IUIG ui, IGameG game, String input) {
        Reader reader = new StringReader(input);
        test(ui, game, reader);
    }


    /***************************************************************************
     * Otestuje zadané uživatelské rozhraní spuštěním zadané hry
     * ovládané příkazy ze zadaného textového řetězce.
     *
     * @param ui    Testované uživatelské rozhraní
     * @param game  Provozovaná hra
     * @param i     Index testu, který budeme "hrát"
     */
    public void test(IUIG ui, IGameG game, int i ) {
        Scenario scenario = game.getScenarioManager().getGuiTestScenario();
        StringBuilder sb = new StringBuilder();
        for (ScenarioStep kt : scenario) {
            sb.append( kt.command ).append('\n');
        }
        test(ui, game, sb.toString() );
    }


    /***************************************************************************
     * Otestuje zadané uživatelské rozhraní spuštěním zadané hry
     * ovládané příkazy uloženými v souboru,
     * který uživatel zadá po spuštěni metody.
     *
     * @param ui    Testované uživatelské rozhraní
     * @param game  Provozovaná hra
     */
    public void test(IUIG ui, IGameG game) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.addChoosableFileFilter(new FileNameExtensionFilter(
            "Textový soubor s testovacími příkazy", "tst") );
        jfc.addChoosableFileFilter(
            new FileNameExtensionFilter("Textový soubor", "txt"));
        int choice = jfc.showOpenDialog(null);
        if(choice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = jfc.getSelectedFile();
        Reader reader;
        try { reader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(
                "\nSoubor %" + file + "% nebyl nalezen", ex);
        }
        test(ui, game, reader);
    }


    /***************************************************************************
     * Požádá uživatele o zadání:
     * <ul>
     *   <li>třídy s testovaným uživatelským rozhraním,</li>
     *   <li>třídy s testovanou hrou a </li>
     *   <li>indexu scénáře hry, jímž bude prověřena spolupráce
     *       instancí vybraných tříd.</li>
     * </ul>
     * Pak prověří funkci testovaného uživatelského rozhraní
     * spuštěním zadané hry a zadáváním příkazů ze zadaného scénáře.
     *
     * @return Vrátí testovanou instanci uživatelkého rozhraní
     */
    public IUIG testChoosenPair()
    {
        Class<? extends IUIG> guiClass =
                ClassCollector.chooseClass(rootFile, IUIG.class,
                converter, "Vyber třídu realizující uživatelské rozhraní");

        Class<? extends IGameG> hraClass =
                ClassCollector.chooseClass(rootFile, IGameG.class,
                converter, "Vyber třídu realizující hru");

        int indexScénáře = IO.input("Zadej index požadovaného scénáře", 10);

        IUIG  gui = newInstanceOf(guiClass);
        if (gui != null) {
            IGameG hra = newInstanceOf(hraClass);
            if (hra != null) {
                test(gui, hra, indexScénáře);
            }
        }

        return gui;
    }


    /***************************************************************************
     * Požádá uživatele o zadání:
     * <ul>
     *   <li>třídy s testovanou hrou a </li>
     *   <li>indexu scénáře hry, jímž bude prověřena spolupráce
     *       instancí vybraných tříd.</li>
     * </ul>
     * Pak prověří funkci uživatelského rozhraní zadaného v parametru
     * spuštěním zadané hry a zadáváním příkazů ze zadaného scénáře.
     * Při zadání záporného indexu scénáře aplikaci ukončí.
     *
     * @param gui Prověřované uživatelské rozhraní
     */
    public void testNextGame(IUIG gui)
    {
        Class<? extends IGameG> gameClass =
                ClassCollector.chooseClass(rootFile, IGameG.class,
                converter, "Vyber třídu realizující hru");

        int scenarioIndex = IO.input("Zadej index požadovaného scénáře", 2);
        if (scenarioIndex < 0) {
            System.exit(0);
        }

        IGameG game = newInstanceOf(gameClass);
        if (game != null) {
            test(gui, game, scenarioIndex);
        }
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Metoda zobrazí dialogové okno se zadanou zprávou a umožni testeru,
     * aby rozhodl o pokračování nebo ukončení testu.
     *
     * @param message Zpráva vypisované v otevřeném dialogovém okně
     * @return {@code true} má-li se končit s testováním,
     *         {@code false} má-li se zkusit další test
     */
    private static boolean finish(String message) {
        String  prompt = message + SEPARATOR + "Mám pokračovat?";
        boolean answer = IO.agree(prompt);
        return  ! answer;
    }



//== SOUKROMÉ A POMOCNÉ METODY INSTANCI ========================================

    /***************************************************************************
     * Vytvoří instanci zadané třídy a vrátí odkaz na ni.
     * Ošetří případná přerušení.
     *
     * @param <T>  Třída, jejíž instanci vytváříme
     * @param cls  Class-objekt třídy, jejíž instanci vytváříme
     * @return Vytvořená instance nebo {@code null}
     */
    private static <T> T newInstanceOf(Class<T> cls)
    {
        T instance;
        try {
            instance = cls.newInstance();
        } catch (Exception ex) {
            String message = "\nNepodařilo se vytvořit instanci třídy " + cls;
            JOptionPane.showMessageDialog(null, message);
            return null;
        }
        return instance;
    }



//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================

    /***************************************************************************
     * Instance třídy představuje konvertor, který z úplného názvu třídy odvodí
     * název, kterým se bude třída prezentovat v seznamu v dialogovém okně.
     */
    private static class Converter implements ClassCollector.Convertor
    {
        /***********************************************************************
         * Za zadaného úplného názvu třídy odvodí zkrácenou verzi,
         * která danou třídu dostatečně identifikuje.
         * Tato implementace odstraňuje z názvu vrchní vrstvy balíčků
         * až po balíček specifikující daného autora, tj. po balíček,
         * jehož název začíná autorovým sname. Umožňuje tak identifikovat
         * třídu i v případě, kdy ji různí autoři pojmenují shodně.
         *
         * @param fullName Plný název třídy
         * @return Název zkráceny k balíčku s xname
         */
        @Override
        public String full2shortName(String fullName) {
            int index = fullName.indexOf(".x") + 1;
            if (index <= 0) {
                index = fullName.indexOf("advent");
            }
            if (index < 0) {
                index = 0;
            }
            String shortName = fullName.substring(index);
            return shortName;
        }
    }



//== TESTY =====================================================================

    /***************************************************************************
     * Nechá uživatele vybrat třídu GUI a třídu hry přičemž třída zadaná
     * v parametru specifikuje umístění jejich class-souborů, přesněji
     * umožňuje odvodit umístění složky představující kořenový balíček.
     *
     * @param rootClass Třída z jejíhož class-souboru se odvodí složka
     *            představující kořenový balíček
     */
    public static void multiTest(Class<?> rootClass)
    {
        $TestGUI test = new $TestGUI(rootClass);
        IUIG gui = test.testChoosenPair();
        for (;;) {
            test.testNextGame(gui);
        }
    }


    /***************************************************************************
     * Nechá uživatele vybrat třídu GUI a třídu hry přičemž třída zadaná
     * v parametru specifikuje umístění jejich class-souborů, přesněji
     * umožňuje odvodit umístění složky představující kořenový balíček.
     *
     * @param rootClass Třída z jejíhož class-souboru se odvodí složka
     *              představující kořenový balíček
     */
    public static void test(Class<?> rootClass)
    {
        $TestGUI test = new $TestGUI(rootClass);
        test.testChoosenPair();
    }


    /***************************************************************************
     * Testovací metoda.
     */
    public static void test()
    {
        test($TestGUI.class);
    }
    /** @param args Parametry příkazového radku - nepoužívané. */
    public static void main( String[] args )  {  test();  }
}
