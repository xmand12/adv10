/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util;

import cz.vse.adv_framework.utilities.DBG_Logger;

import java.io.File;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JOptionPane;


import static cz.vse.adv_framework.utilities.CompareIgnoreCase.CIC;
import java.lang.reflect.InvocationTargetException;



/*******************************************************************************
 * Třída {@code ClassCollector} je knihovní třídou nabízející metody
 * pro posbírání tříd, které implementují zadané rozhraní nebo mají
 * zadaného předka, resp metod pro výběr jedné z těchto tříd.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 5.0
 */
public class ClassCollector
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Logger, prostřednictvím nějž zaznamenáváme veškeré informace. */
    protected final static DBG_Logger DBG = DBG_Logger.getInstance();

    /** Přípona class-souborů. */
    private static final String EXTENSION_CLASS = ".class";


//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Vrátí instanci zadané třídy vytvořenou bezparametrickým konstruktorem,
     * a to i v případě, že tento konstruktor je definován jako soukromý.
     *
     * @param   <T>   Předek třídy, jejíž instanci vytváříme
     * @param   cls   Class-objekt třídy, jejiž instance vytváří
     * @return  Instance zadané třídy
     */
    public static <T> T newInstanceOf(Class<? extends T> cls)
    {
        Constructor<? extends T> constructor;
        try {
            constructor = cls.getDeclaredConstructor();
        }
        catch(NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(
                "\nNepodarilo se vytvořit bezparametrický konstruktor třídy " +
                cls + "\n" + ex.getMessage(), ex);
        }
        constructor.setAccessible(true);
        T instance;
        try {
            instance = constructor.newInstance();
        } catch (InstantiationException   | IllegalAccessException |
                  IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(
                "\nNezdarene volaní konstruktoru třídy " +  cls +
                "\n" + ex.getMessage(), ex);
        }
        return instance;
    }


    /***************************************************************************
     * Vrátí kolekci s class-objekty všech tříd
     * v balíčku zadané třídy a jeho podbalíčcích,
     * které implementují zadané rozhraní, resp. jsou potomky zadané třídy.
     * Metoda pracuje se soubory, nebude proto pracovat v JAR-archivu.
     *
     * @param <T>       Třída, jejíž potomky či implementace hledáme
     * @param rootClass Třída v balíčku, v němž začínáme hledání
     * @param parent    Class-objekt rozhraní, jehož implementace hledáme,
     *                  resp. třídy, jejíhož hledáme potomka
     * @return Kolekce s class-objekty nalezených tříd
     */
    public static <T> Collection<Class<? extends T>>
                      findImplementations(Class<?> rootClass, Class<T> parent)
    {
        Collection<Class<? extends T>> classes = new ArrayList<>();
        String pkg      = rootClass.getPackage().getName();
        if (! "".equals(pkg)) {
            pkg += '.';
        }
        File   rootPath = getPathTo(rootClass);
        findInFolder(rootPath, parent, pkg, classes);
//        ArrayList<Class<T>> al = (ArrayList<Class<T>>)classes;
        return classes;
    }


    /***************************************************************************
     * Vrátí kolekci s class-objekty všech tříd v zadané KOŘENOVÉ
     * složce (balíčku) a jejích podsložkách (podbalíčcích),
     * které implementují zadané rozhraní, resp. jsou potomky zadané třídy.
     * Metoda pracuje se soubory, nebude proto pracovat v JAR-archivu.
     *
     * @param <T>     Třída, jejíž potomky či implementace hledáme
     * @param root    Kořenová složka, v níž začínáme hledání
     *                (MUSÍ SE JEDNAT O SLOŽKU KOŘENOVÉHO BALÍČKU)
     * @param parent  Class-objekt rozhraní, jehož implementace hledáme,
     *                resp. třídy, jejíhož hledáme potomka
     * @return Kolekce s class-objekty nalezených tříd
     */
    public static <T> Collection<Class<? extends T>>
                      findImplementations(File root, Class<T> parent)
    {
        Collection<Class<? extends T>> classes = new ArrayList<>();
        findInFolder(root, parent, "", classes);
//        ArrayList<Class<T>> al = (ArrayList<Class<T>>)classes;
        return classes;
    }


    /***************************************************************************
     * Pomoci testu zadaného v parametru otestuje v aktuálním projektu
     * všechny třídy, které jsou potomky zadaného předka,
     * tj. jsou potomky zadané třídy, resp. implementují zadané rozhraní.
     *
     * @param <T>    Třída, jejíž potomky či implementace hledáme,
     *               abychom je následně otestovali zadaným testem
     * @param root   Kořenová složka, v níž začínáme testování
     * @param parent Třída, která musí byt předkem testovaných tříd, nebo
     *               rozhraní, které musí testovanými třídami implementováno
     * @param test   Instance testovací třídy, která umí otestovat
     *               svěřenou instanci testované třídy
     */
    public static <T> void testClasses(File root, Class<T> parent,
                                       ITest<T> test)
    {
        Collection<Class<? extends T>> classes =
                   ClassCollector.<T> findImplementations(root, parent);
        for (Class<? extends T> cls : classes) {
            DBG.info("\n\n########## Test třídy: {0}", cls);
            try {
                T o = ClassCollector.<T>newInstanceOf(cls);
                test.test(o);
            } catch (Throwable t) {
                DBG.severe("\nXXXXXXXXXX Test se nezdařil\n   {0}",
                         t.getMessage());
            }
            DBG.info("~~~~~~~~~~ Konec testu třídy: {0}", cls);
        }
    }


    /***************************************************************************
     * Otevře dialogové okno s nabídkou všech tříd,
     * které jsou potomkem zadaného typu, resp. implementují zadané rozhraní,
     * umožní uživateli vybrat si jednu z nich
     * a vrátí instanci této třídy vytvořenou bezparametrickým konstruktorem.
     * <p>
     * Třídy budou v rozbalovacím seznamu uváděny
     * svými jednoduchými názvy bez názvů balíčků.
     *
     * @param <T>       Typ předka hledaných tříd
     * @param root      Kořenová složka, v níž začínáme hledání
     * @param parent    Class-objekt předka hledaných tříd
     * @param convertor Konvertor převádějící plný název třídy na zkrácený
     * @param prompt    Výzva specifikující, co uživatel zadává
     * @return Instance vybrané třídy
     */
    public static <T> T newInstanceOfChosedClass(File root,
                        Class<T> parent, Convertor convertor, String prompt)
    {
        Class<? extends T> cls = chooseClass(root, parent, convertor, prompt);
        T instance = newInstanceOf(cls);
        return instance;
    }


    /***************************************************************************
     * Otevře dialogové okno s nabídkou všech tříd,
     * které jsou potomkem zadaného typu, resp. implementují zadané rozhraní,
     * a umožní uživateli vybrat si jednu z nich.
     * <p>
     * Třídy budou v rozbalovacím seznamu uváděny
     * svými jednoduchými názvy bez názvů balíčků.
     *
     * @param <T>       Typ předka hledaných tříd
     * @param root      Kořenová složka, v níž začínáme hledání
     * @param parent    Class-objekt předka hledaných tříd
     * @param convertor Konvertor převádějící plný název třídy na zkrácený
     * @param prompt    Výzva specifikující, co uživatel zadává
     * @return Vybraná třída
     */
    public static <T> Class<? extends T> chooseClass(File root, Class<T> parent,
                                           Convertor convertor, String prompt)
    {
        Collection<Class<? extends T>> collection =
                                       findImplementations(root, parent);
        int size = collection.size();
        @SuppressWarnings("unchecked")
        Class<T>[] classArr = collection.toArray(
                              (Class<T>[])java.lang.reflect.Array.newInstance(
                                                           Class.class, size));
        String[] nameArr = new String[size];
        for (int i=0;   i < size;   i++) {
            nameArr[i] = convertor.full2shortName(classArr[i].getName());
        }
        String[] setříděné = nameArr.clone();
        Arrays.sort(setříděné, CIC);
        String name = (String)JOptionPane.showInputDialog(null,
                              prompt,
                              "Výběr třídy",
                              JOptionPane.QUESTION_MESSAGE,
                              null,
                              setříděné,
                              setříděné[0]);

        if (name == null) { System.exit(1); }      //##########>

        int index;
        for (index=0;   index < size;   index++) {
            if (name.equalsIgnoreCase(nameArr[index])) { break; }
        }
        Class<T> cls = classArr[index];
        return cls;
    }


    /***************************************************************************
     * Vrátí instanci třídy {@link File} odkazující na složku, v níž se nalézá
     * class-soubor zadané třídy.
     *
     * @param clazz Třídy, jejíž umístění hledáme
     * @return Cesta ke class-souboru zadané třídy
     */
    public static File getPathTo(Class<?> clazz)
    {
        String classFileName = clazz.getSimpleName() + ".class";
        URL url = clazz.getResource(classFileName);
        URI uri;
        try {
            uri = url.toURI();
        }
        catch (URISyntaxException ex) {
            throw new RuntimeException(
                "\nNepodařilo se převést URL balíčku typu " + clazz, ex);
        }
        File file = new File(uri);
        String folderPath = file.getAbsolutePath().replace(classFileName, "");
        file = new File(folderPath);
        return file;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /* Soukromý konstruktor bránící vytvoření instance. */
    private ClassCollector()  {/**/}


//== ABSTRACT METHODS ==========================================================
//== NESOUKROMÉ  METODY INSTANCI ===============================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     * Přidá do zadané kolekce tříd třídu (její class-objekt) se zadaným názvem
     * umístěnou v zadaném balíčku; před přidáním metoda zkontroluje,
     * že zadaný název je doopravdy názvem třídy, která
     * implementuje zadané rozhraní, resp. je potomkem zadaného předka.
     *
     * @param <T> Požadovaný typ předka, resp. implementovaného rozhraní
     * @param pkgName   Balíček, v němž se nachází přidávaná třída
     * @param clsName   Název přidávané třídy
     * @param parent    Předek přidávané třídy
     * @param classes   Kolekce, do které bude třída přidána
     */
    @SuppressWarnings("unchecked")
    private static <T> void addClass(String pkgName, String clsName,
                       Class<T> parent, Collection<Class<? extends T>> classes)
    {
        String fullName = pkgName + clsName;
        Class<T> cls;
        try {
            cls = (Class<T>)Class.forName(fullName);
        } catch (Throwable ex) {
            DBG.info("\n{0} - třídu {1}{2} se nepodařilo zavést.",
                     ex.getClass(), pkgName, clsName);
            return;
        }
        if (cls.isInterface()) {
            return;
        }
        try {
            cls.asSubclass(parent);
        } catch (ClassCastException cce) {
            return;
        }
        classes.add(cls);
    }


    /***************************************************************************
     * Najde v zadané složce a jejích podsložkách potomky zadané třídy,
     * resp. třídy implementující zadané rozhraní.
     *
     * @param <T> Požadovaný typ předka, resp. implementovaného rozhraní
     * @param root    Kořenová složka, v niž dané třídy hledáme
     * @param parent  Class objekt rozhraní, jehož implementace hledáme, resp.
     *                třídy, jejiž potomky hledáme
     * @param pkgName Název balíčku, v němž se hledá
     * @param classes   Kolekce tříd, do niž se nalezené třídy přidají
     */
    private static <T> void findInFolder(File root, Class<T> parent,
                       String pkgName, Collection<Class<? extends T>>classes)
    {
        /** Filtr souboru k další analýze - pouští jen class-soubory.*/
        class Compiled implements java.io.FileFilter {
            @Override
            public boolean accept(File soubor) {
                boolean folder, cls, interesting;
                interesting =
                    (folder = soubor.isDirectory())  ||
                    (cls    = soubor.getName().endsWith(EXTENSION_CLASS));
                return interesting;
            }
        }

        for (File f : root.listFiles(new Compiled())) {
            String file = f.getName();

            if (f.isDirectory()) { //Je to složka - zpracuje se rekurzivně
                findInFolder(f, parent, pkgName + file + ".", classes);
                continue;
            }

            int extension = file.lastIndexOf(EXTENSION_CLASS);
            if (extension != file.indexOf('.')) {
                continue; //Moc teček v názvu
            }
            String clsName = file.substring(0, extension);
            addClass(pkgName, clsName, parent, classes);
        }
    }


//== SOUKROMÉ A POMOCNÉ METODY INSTANCI ========================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================

    /***************************************************************************
     * Instance rozhraní představuje konvertor, který z úplného názvu třídy
     * odvodí název, kterým se bude prezentovat v seznamu v dialogovém okně.
     */
    public interface Convertor
    {
        /***********************************************************************
         * Za zadaného úplného názvu třídy odvodí zkrácenou verzi,
         * která danou třídu dostatečně identifikuje.
         * Konkrétní implementace specifikuje, jak moc bude název třídy zkrácen.
         * Někdy může třídu dostatečně identifikovat její jednoduché jméno,
         * jindy je vhodné ponechat v názvu i název rodičovského nebo
         * dokonce prarodičovského balíčku.
         *
         * @param fullName Plný název třídy
         * @return Zkrácený název
         */
        String full2shortName(String fullName);
    }



//== TESTY =====================================================================
//
//    /***************************************************************************
//     *
//     */
//    public static void test() {
//        Collection<Class> kolekce = findImplementations(S1_RUP.ICPU.class);
//        for (Class c : kolekce)
//            Systém.out.println(c);
//    }
//    /** @param args Parametry příkazového radku */
//    public static void main(String... args) { test(); }
}
