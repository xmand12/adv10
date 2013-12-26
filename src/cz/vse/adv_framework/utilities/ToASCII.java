/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.utilities;

import java.util.HashMap;
import java.util.Map;



/*******************************************************************************
 * Třída slouží k převodů znaků a řetězců do znakové sady ASCII.
 * Její metody konvertují zadané texty či znaky na řetězce
 * obsahující pouze znaky s kódem menším než 128.
 * Znaky s kódem větším než 127 převedou na jejich ASCII ekvivalenty
 * (včetně možnosti rozepsat character na více znaků, např. ß -> ss, « -> <<)
 * nebo na tvar {@code \}{@code uXXXX}.
 *
 * @author Rudolf PECINOVSKÝ
 * @version 6.03.2017 — 2010-11-08
 */
public final class ToASCII
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Mapa s převody znaků do ASCII. */
    private static final Map<Character,String> PŘEVOD =
                                           new HashMap<Character, String>(64);
    static {
        String[][] dvojice = {
            {"Á", "A"},  {"á", "a"},    {"Ä", "AE"}, {"ä", "ae"},
            {"Č", "C"},  {"č", "c"},    {"Ď", "D"},  {"ď", "d"},
            {"É", "E"},  {"é", "e"},    {"Ě", "E"},  {"ě", "e"},
            {"Í", "I"},  {"í", "i"},
            {"Ĺ", "L"},  {"ĺ", "l"},    {"Ľ", "L"},  {"ľ", "l"},
            {"Ň", "N"},  {"ň", "n"},    {"Ó", "O"},  {"ó", "o"},
            {"Ô", "O"},  {"ô", "o"},    {"Ö", "OE"}, {"ö", "oe"},
            {"Ŕ", "R"},  {"ŕ", "r"},    {"Ř", "R"},  {"ř", "r"},
            {"Š", "S"},  {"š", "s"},    {"Ť", "T"},  {"ť", "t"},
            {"Ú", "U"},  {"ú", "u"},    {"Ů", "U"},  {"ü", "ue"},
            {"Ü", "UE"}, {"ü", "ue"},
            {"Ý", "Y"},  {"ý", "y"},
            {"Ž", "Z"},  {"ž", "z"},
            {"ß", "ss"}, {"©", "(c)"},  {"®", "(R)"},
            {"‹", "<"},  {"›", ">"},    {"«", "<<"}, {"»", ">>"},
            {"„", "\""}, {"“", "\""},   {"”", "\""},
            {"‚", "\'"}, {"‘", "\'"},   {"’", "\'"},
            {"×", "x"},  {"÷", ":"},
            {"–", "-"},  {"—", "-"},    //ndash, mdash
            {"¦", "|"},
        };
        for (String[] ss : dvojice) {
            PŘEVOD.put(new Character(ss[0].charAt(0)),  ss[1]);
        }
    }



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zkonvertuje zadaný řetězec na jeho ekvivalent
     * obsahující pouze znaky s kódem menším než 128.
     * Znaky s kódem větším než 127 převede na jejich ASCII ekvivalenty
     * (včetně možnosti rozepsat character na více znaků, např. ß -> ss, « -> <<)
     * nebo na tvar {@code \}{@code uXXXX}.
     *
     * @param text Text určený k převodu
     * @return  Převedený text
     */
    public static String text(CharSequence text)
    {
        final int DÉLKA = text.length();
        final StringBuilder sb = new StringBuilder(DÉLKA);
        for (int i = 0;   i < DÉLKA;   i++) {
            char c = text.charAt(i);
            if (c < 128) {
                sb.append(c);
            }else if (PŘEVOD.containsKey(c)) {
                sb.append(PŘEVOD.get(c));
            }else {
                sb.append(rozepiš(c));
            }
        }
        return sb.toString();
    }


    /***************************************************************************
     * Zkonvertuje zadaný character na jeho ekvivalent s kódem menším než 128.
     * Znaky s kódem větším než 127 převede na jejich ASCII ekvivalenty
     * včetně možnosti rozepsat znak na více znaků,
     * (např. (ß) -> (ss), («) -> (<<)) nebo na tvar {@code \}{@code uXXXX}.
     * Proto také nemůže vracet pouhý znak, ale musí vracet {@link String}.
     *
     * @param c konvertovaný character
     * @return ekvivalent zadaného znaku bez diakritiky
     */
    public static String character(char c)
    {
            if (c < 128) {
                return (Character.toString(c));
            }else if (PŘEVOD.containsKey(c)) {
                return PŘEVOD.get(c);
            }else {
                return rozepiš(c);
            }
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromy konstruktor bránící vytvoření instance. */
    private ToASCII() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================

    /***************************************************************************
     *
     */
    private static String rozepiš(char c) {
        return String.format("\\u%04x", (int)c);
    }



//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        CharSequence txt = new StringBuilder(
//                           "Příliš žluťoučký kůň úpěl ďábelské ódy.\n" +
//                           "PŘÍLIŠ ŽLUŤOUČKÝ KŮŇ ÚPĚL ĎÁBELSKÉ ÓDY.\n" +
//                           "[müßli](§-ß-¤) «Göthe»");
//        System.out.println(txt);
//        String bhc = ToASCII.text(txt);
//        System.out.println(bhc);
//    }
//    /** @param args GUI příkazového řádku - nepoužívané. */
//    public static void main(String args[]) {
//        test();
//    }
}
