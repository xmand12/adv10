/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework;

import cz.vse.adv_framework.game_gui.IGameG;

import cz.vse.adv_framework.test_util.ClassCollector;
import cz.vse.adv_framework.test_util.ITest;
import cz.vse.adv_framework.test_util._Test_115;

import cz.vse.adv_framework.utilities.DBG_Handler;
import cz.vse.adv_framework.utilities.MultiOutput;
import cz.vse.adv_framework.utilities.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;



/*******************************************************************************
 * Instance třídy {@code CollectiveTest} představují ...
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CollectiveTest implements ITest<Class <? extends IGameG>>
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================

    /** Soubor, do kterého budou zapisovány výsledky testu. */
    private static final File REPORT =
            Util.newFileInPRJ(CollectiveTest.class, "REPORT.TXT");

    /** Soubor, do kterého budou zapisovány výsledky testu. */
    private static final File VERBOSE_REPORT =
            Util.newFileInPRJ(CollectiveTest.class, "VERBOSE_REPORT.TXT");



//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================

    /** Výstupní proud-soubor, do nějž budou zapisovány výsledky testů. */
    private final PrintWriter report;

    /** Výstupní soubor, kam se budou paralelně zapisovat
     *  podrobné výsledky testů. */
    PrintStream printStream;

    /** Sdružený výstupní proud, který zapisuje paralelně na standardní výstup
     *  a dop zadaného souboru s podrobnou zprávou o průběhu testu. */
    private final MultiOutput multiOutput;




//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================

    /***************************************************************************
     * Metoda nechá vyhledat všechny třídy implementující rozhraní
     * {@code IGameG} a pak každou z těchto tříd otestuje,
     * nakolik odpovídá kontraktu definovanému tímto rozhraním.
     */
    public static void perform()
    {
        CollectiveTest collectiveTest = new CollectiveTest();
        Class<IGameG> clasIGameG = IGameG.class;
        Collection<Class<? extends IGameG>> collection = ClassCollector.
                         findImplementations(CollectiveTest.class, clasIGameG);
        List<Class<? extends IGameG>> list =
                     new ArrayList<Class<? extends IGameG>>(collection);
        Collections.sort(list, new Comparator<Class<?>>()
        {
            @Override
            public int compare(Class<?> c1, Class<?> c2)
            {
                return c1.getName().compareTo(c2.getName());
            }
        });

        tiskniSeznam("Třídy vybrané k hodnocení:", list);

        for (Class<? extends IGameG> clazz : collection) {
            collectiveTest.test(clazz);
        }
        collectiveTest.report.close();
        collectiveTest.printStream.close();
    }



//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     *
     */
    private CollectiveTest()
    {
        try {
            FileOutputStream fos = new FileOutputStream(VERBOSE_REPORT);
            printStream = new PrintStream(fos, true, "UTF-8");
            multiOutput = new MultiOutput(System.out, printStream);
        }
        catch (Exception ex) {
            throw new RuntimeException("\nNepodařilo se vytvořit " +
                  "výstupní soubor pro podrobné výpisy: " + VERBOSE_REPORT, ex);
        }
        DBG_Handler.setDefaultOutputStream(multiOutput);
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(REPORT);
        }
        catch (IOException ex) {
            throw new RuntimeException(
                    "\nNepodařilo se vytvořit soubor " + REPORT, ex);
        }
        String time = new Date().toString() + "\n\n";
        printStream.println(time);
        printWriter.println(time);

        report = printWriter;
    }



//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================

    /***************************************************************************
     * Otestuje zadanou instanci.
     *
     * @param gameClass Testovaná instance
     */
    @Override
    public void test(Class <? extends IGameG> gameClass)
    {
        _Test_115 test_115;
        try {
            test_115  = _Test_115.getInstance(gameClass);
        }
        catch (Throwable ex) {
            String message =
                "\n\nTřída:   " + gameClass +
                  "\nBalíček: " + gameClass.getPackage().getName() +
                  "\n##### Nepodařilo se vůbec vytvořit triumvirát #####\n" +
                   ex + "\n";
            report.write(message);
            return;
        }
        IGameG  gameG   = test_115.getGameG();
        String  pkgName = gameClass.getName();
        String  author  = gameG.getAuthorID() + " - " + gameG.getAuthorName();
        String  signature = String.format("%n%nAutor(ka): %s%nBalíček:   %s%n",
                                          author, pkgName);
        boolean result;
        try {
            report.write(signature);
            report.flush();
            try {
                result = test_115.verifyGame();
            }
            catch (Exception ex) {
                result = false;
                report.write(ex.getMessage());
                ex.printStackTrace(report);
            }
            if (result) {
                report.write("+++++ Test proběhl zdárně +++++\n");
            }
            else {
                report.write(
                    "##### Testovací program objevil nesrovnalosti #####\n");
            }
            report.flush();
        }
        catch (Exception ex) {
            throw new RuntimeException(
                "\nPři vyhodnocování hry se vyskytly chyby" + signature, ex);
        }

    }



//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================

    /***************************************************************************
     *
     * @param string
     * @param list
     */
    private static void tiskniSeznam(String string,
                                     List<?> list)
    {
        StringBuilder sb = new StringBuilder(string);
        for (Object o : list) {
            sb.append("\n   ").append(o);
        }
        sb.append("\n\n");
        System.out.println(sb.toString());
    }



//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== INTERNÍ DATOVÉ TYPY =======================================================
//== TESTY A METODA MAIN =======================================================

    /***************************************************************************
     * Testovací metoda.
     */
    public static void test()
    {
//        CollectiveTest inst = new CollectiveTest();
        perform();
        System.exit(0);
    }
    /** @param args Parametry příkazového řádku - nepoužívané. */
    public static void main( String[] args )  {  test();  }
}
