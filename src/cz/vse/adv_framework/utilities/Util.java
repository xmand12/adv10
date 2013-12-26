/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import cz.vse.adv_framework.game_txt.INamed;

import java.io.File;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;



/*******************************************************************************
 * Třída {@code Util} je knihovní třídou s užitečnými metodami.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class Util
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Uvozovky či jiný znak, kterým označujeme začátek vypisovaného řetězce,
     *  aby byly vidět i případné úvodní mezery. */
    public static final String A_ = "«";

    /** Uvozovky či jiný znak, kterým označujeme konec vypisovaného řetězce,
     *  aby byly vidět i případné závěrečné mezery. */
    public static final String _Z = "»";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Převede zadaný vektor na řetězec znaků přičemž
     * každou z hodnot uzavře do francouzských uvozovek
     * jednotlivé takto převedené hodnoty oddělí čárkami
     * a celý vektor uzavře do kulatých závorek
     *
     * @param arr   Pole, jehož hodnoty chceme vypsat
     * @return      Požadovaný řetězec
     */
    public static String arr2String(Object[] arr)
    {
        if (arr == null) {
            return "### Byl zadán prázdný odkaz null ###";
        }
        StringBuilder sb = new StringBuilder("(");
        boolean první = true;
        for (Object o : arr) {
            if (první) {
                první = false;
            } else {
                sb.append(", ");
            }
            sb.append(A_).append(o).append(_Z);
        }
        sb.append(')');
        return sb.toString();
    }


    /***************************************************************************
     * Převede zadaný vektor na řetězec znaků přičemž
     * každou z hodnot uzavře do francouzských uvozovek,
     * jednotlivé takto převedené hodnoty oddělí čárkami
     * a celý vektor uzavře do kulatých závorek
     *
     * @param arr   Pole, jehož hodnoty chceme vypsat
     * @return      Požadovaný řetězec
     */
    public static String arr2String(int[] arr)
    {
        StringBuilder sb = new StringBuilder("(");
        for (int o : arr) {
            sb.append(A_).append(o).append(_Z).append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.setCharAt(sb.length()-1, ')');
        return sb.toString();
    }


    /***************************************************************************
     * Převede kolekci objektů typu {@link INamed}
     * na jednorozměrné pole řetězců.
     *
     * @param collection  Převáděná kolekce pojmenovaných
     * @return Požadované pole stringů
     */
    public static String[] colINamed2arrString(
                           Collection<? extends INamed> collection)
    {
        if (collection == null) {
            return new String[0];
        }
        String[] ret = new String[collection.size()];
        int i=0;
        for (INamed named : collection) {
            if (named == null) {
                 throw new RuntimeException(
                    "\nV obdržené kolekci je místo stringu null: " + collection);
            }
            ret[i++] = named.getName();
        }
        return ret;
    }


    /***************************************************************************
     * Převede kolekci pojmenovaných objektů
     * na seřazenou množinu jejich názvů.
     * Zdvojené objekty z původní kolekce se v nové kolekci objeví jen jednou.
     *
     * @param iNamed Převáděná kolekce
     * @return Seřazená množina jejich názvů
     */
    public static SortedSet<String> colINamed2ssetString
                                    (Collection<? extends INamed> iNamed)
    {
        SortedSet<String> tsString = new TreeSet<String>();
        for (INamed in : iNamed) {
            tsString.add(in.getName());
        }
        return tsString;
    }


    /***************************************************************************
     * Vrátí informaci o tom, zda zadané pole obsahuje zadaný řetězec.
     *
     * @param member Hledaný řetězec
     * @param array  Prohledávané pole
     * @return  Výsledek hledání: {@code true}=našel, {@code false}=nenašel
     */
    public static boolean  containsIgnoreCase(String member, String[] array)
    {
        for (String string : array) {
            if (member.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }


    /***************************************************************************
     * Vrátí informaci o tom, zda se obsah většího z vektorů liší od menšího
     * opravdu pouze o zadaný řetězec.
     *
     * @param member  Řetězec, v němž se mají oba vektory odlišovat
     * @param bigger  Většíá z obou řetězců
     * @param smaller Menší z obou řetězců
     * @return  Je-li předpoklad pravdivý, vrátí {@code true},
     *          jinak vrátí {@code false}
     */
    public static boolean  differOnlyIn(String member,
                                        String[] bigger, String[] smaller)
    {
        if ((bigger.length - smaller.length)  !=  1)  {
            return false;
        }
        member = member.toLowerCase();
        List<String> bigList   = toLowerCaseList(bigger);
        List<String> smallList = toLowerCaseList(smaller);

        for (String string : smallList) {
            if (! bigList.remove(string)) {
                return false;
            }
        }
        if (bigList.remove(member)  &&  bigList.isEmpty()) {
            return true;
        }
        return false;
    }


    /***************************************************************************
     * Převede zadané pole řetězců na seznam těchže řetězců
     * převedených na malá písmena.
     *
     * @param array Převáděné pole
     * @return Výsledný seznam
     */
    public static List<String> toLowerCaseList(String[] array)
    {
        List<String> list = new ArrayList<String>(array.length);
        for (String string : array) {
            list.add(string.toLowerCase());
        }
        return list;
    }


    /***************************************************************************
     * Vrátí instanci představující cestu k souboru se zadaným názvem
     * umístěným v kořenové složce projektu NetBeans obsahujícího zadanou třídu.
     * Není-li kořenový balíček ve složce {@code <něco>/build/classes},
     * vrátí prázdný odkaz {@code null}.
     *
     * @param clazz Třídu do jejíhož projektu se má soubor umístit
     * @param fileName Název umisťovaného souboru
     * @return Požadovaný soubor
     */
    public static File newFileInPRJ(Class<?> clazz, String fileName)
    {
        URL url = clazz.getResource("bluej.pkg");
        if (url == null) { return null; }           //==========>
        URI uri;
        try {
            uri = url.toURI();
        }
        catch(URISyntaxException ex) {
            throw new RuntimeException(
                    "\nNepodařilo se převést URL na URI - URL=" + url, ex);
        }
        File file = new File(uri);
        String path = file.getAbsolutePath();
        int    index = path.indexOf("build\\classes\\");
        if (index < 0) { return null; }             //==========>
        path = path.substring(0, index);
        file = new File(path, fileName);
        return file;
    }


    /***************************************************************************
     * Vrátí informaci o tom, jsou-li zadané dva pole řetězců shodné
     * neuvažujeme-li velikost písmen, tj. jsou-li obě pole shodně velká a
     * jsou-li příslušně shodné jejich vzájemně si odpovídající položky.
     *
     * @param arr1 První pole
     * @param arr2 Druhý pole
     * @return Požadovaná informace
     */
    public static boolean strArrEqualsIgnoreCase(String[] arr1, String[] arr2)
    {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i=0;   i < arr1.length;   i++) {
            if (! arr1[i].equalsIgnoreCase(arr2[i])) {
                return false;
            }
        }
        return true;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** */
    private Util() {}


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        String[] ss = {"raz", "dva", "tři"};
//        System.out.println("###: " + arr2String(ss));
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
