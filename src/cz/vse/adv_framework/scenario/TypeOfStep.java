/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.scenario;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;



/*******************************************************************************
 * Instance třídy {@code TypeOfStep} představují
 * možné typy kroků zadávaných ve scénáři.
 * Znalost typu kroku umožňuje zkontrolovat správnost zadání dat
 * v jednotlivých krocích scénáře.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public enum TypeOfStep
{
//== HODNOTY VÝČTOVÉHO TYPU ====================================================

//Typy řádných kroků, které se musí všechny objevit v základním úspěšném scénáři
    /** Startovací krok s prázdným názvem.              */  tsSTART        (0),
    /** Hráč se přesune z jednoho prostoru do druhého.  */  tsMOVE         (0),
    /** Úspěšné "zvednutí" objektu v prostoru.          */  tsPICK_UP      (0),
    /** Úspěšné položení objektu z batohu.              */  tsPUT_DOWN     (0),

//Na následující typy kroků musí hra umět zareagovat =>
//   musí být otestovány v základním chybovém scénáři

    /** Příkaz (kdykoliv) nestandardně ukončující hru.  */  tsEND          (1),

    /** Nápověda.                                       */  tsHELP         (1),

//Problémy se správným zadáním akce
    /** Špatný startovací příkaz, není prázdný řetězec.*/   tsNOT_START    (1),
    /** Nezadání příkazu - odeslání prázdného řetězce.  */  tsEMPTY        (1),
    /** Hra nezná zadanou akci.                         */  tsUNKNOWN      (1),

//Zadání některého ze základních příkazů bez povinného parametru
    /** Nebyl zadán cílový prostor přesunu.             */  tsMOVE_WA      (1),
    /** Nebyl zadán objekt, který se má zvednout.       */  tsPICK_UP_WA   (1),
    /** Nebyl zadán objekt, který se má položit.        */  tsPUT_DOWN_WA  (1),

//Problémy se změnou místnosti
    /** Cílový prostor není sousedem aktuálního prostoru*/  tsBAD_NEIGHBOR (1),

//Problémy se zvednutim či položením předmětu
    /** Zadaný předmět v aktuálním prostoru není.       */  tsBAD_OBJECT   (1),
    /** Zadaný předmět nelze zvednout.                  */  tsUNMOVABLE    (1),
    /** Další předmět se již nevejde do batohu.         */  tsBAG_FULL     (1),

    /** Zadaný předmět nelze položit.                   */  tsNOT_IN_BAG   (1),

//Nepovinné typy kroků
    /** Zadaný krok nepopisuje klasickou akci,
     *  ale je součástí rozhovoru hráče s nějakou postavou či zařízením hry
     *  nebo nějaké obdobné činnosti.                   */  tsDIALOG       (2),

    /** Příkaz nepatří mezi základní povinné příkazy,
     *  mění pouze nějaký vnitřní stav hry.             */  tsNON_STANDARD (2),

//Typy kroku nepoužitelných pro testování reakce hry na zadanou akci
    /** Krok s tímto typem akce nebude možno použít pro test správné funkce hry,
     *  protože neobsahuje data o stavu po provedení akce.
     *  Krok je určen pouze pro předvedení funkce hry.
     *  Demonstrační kroky se používají např. při testování funkce
     *  uživatelského rozhraní.                         */  tsDEMO         (3),

    /** Typ kroku neurčeného pro zařazení do nějakého scénáře, ale pouze pro
     *  doplnění chybového hlášení a pomocné akce.      */  tsNOT_SET      (4);



//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Typy kroků, které musí být povinně obsaženy v základním scénáři. */
    public static final Set<TypeOfStep> REGULAR_COMMANDS;

    /** Nápověda + typy chybně zadaných příkazů, na něž musí hra umět reagovat
     *  a schopnost této reakce je proto třeba vyzkoušet =>
     *  všechny tyto typy kroků musí být v základním chybovém scénáři. */
    public static final Set<TypeOfStep> MISTAKE_COMMAND;



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================

    static {
        Set<TypeOfStep> regular = EnumSet.noneOf(TypeOfStep.class);
        Set<TypeOfStep> mistake = EnumSet.noneOf(TypeOfStep.class);
        for (TypeOfStep stepType : TypeOfStep.values()) {
            if (Subtype.stCORRECT.equals(stepType.SUBTYPE)) {
                regular.add(stepType);
            }
            else if (Subtype.stMISTAKE.equals(stepType.SUBTYPE)) {
                mistake.add(stepType);
            }
        }
        //Definuji obě množiny jako neměnné
        REGULAR_COMMANDS = Collections.unmodifiableSet(regular);
        MISTAKE_COMMAND  = Collections.unmodifiableSet(mistake);
    }



//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Podtyp testu. */
    public final Subtype SUBTYPE;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Definuje nový typ kroku a na základě hodnoty parametru
     * mu přiřadí příslušný podtyp.
     *
     * @param SUBTYPE  Ordinální číslo podtypu (aby byl zápis kratší)
     */
    private TypeOfStep(int podtyp)
    {
        this.SUBTYPE = Subtype.values()[podtyp];
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================


    /***************************************************************************
     * Vrátí podtyp příslušného kroku scénáře.
     *
     * @return Podtyp daného kroku scénáře
     */
    public Subtype getSubtype()
    {
        return SUBTYPE;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================

    /***************************************************************************
     * Podtypy testovacích kroků jsou roztříděny do několika skupin;
     * výčtový typ {@code Subtype} tyto skupiny definuje.
     */
    public enum Subtype
    {
        /** Správně zadaný povinně zařazený příkaz.       0 */  stCORRECT,
        /** Povinně testovaný chybně zadaný příkaz.       1 */  stMISTAKE,
        /** Nepovinný příkaz.                             2 */  stFACULTATIVE,
        /** Demonstrační krok bez doprovodných informací,
         *  který proto nelze použít k testu funkce hry.  3 */  stDEMO,
        /** Krok vytvořený pro pomocné práce.             4 */  stUNDEFINED;
    }



//== TESTOVACÍ METODY A TŘÍDY ==================================================
}
