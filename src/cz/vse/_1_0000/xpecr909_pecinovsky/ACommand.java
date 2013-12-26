/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_framework.game_txt.ICommand;





/*******************************************************************************
 * Instance třídy {@code ACommand} představují příkazy hry.
 *
 * Tento zdrojový kód je poněkud postižen schizofrenií vyvolanou nutností
 * předvést možnosti dvou přístupů k definici správců příkazů
 * (= instancí třídy {@code ACommand}):
 * <ul>
 *   <li>Pokud by byl správce definován jako instance samostatné třídy
 *     (v tomto případě je to třída {@link Command2Manager}),
 *     nemusela by třída být potomkem třídy {@link AACommand},
 *     ale stačilo by, aby implementovala interfejs {@link ICommand}.
 *     <br>&nbsp;</li>
 *   <li>Pokud by byl naopak jako správce definován objekt třídy,
 *     opět by třída nemusela být potomkem třídy {@link AACommand},
 *     ale stačilo by všechny statické metody z rodičovské třídy
 *     {@link AACommand} (tj. metody objektu realizujícího povinnosti
 *     správce příkazů) přestěhovat do třídy {@code ACommnad}
 *     jejíž objekt by se tak mohl ujmout role správce příkazů
 *     <br>&nbsp;</li>
 * </ul>
 * Z předchozího by se mohlo zdát, že definice třídy {@link AACommand}
 * je zcela zbytečná, protože v obou případech ji můžeme oželet.
 * Je definována proto, aby byly v definici rodičovské třídy příkazů
 * co nejviditelněji odděleny metody instancí (příkazů)
 * a a metody objektu třídy (tj. metody potenciálního správce).
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
abstract class ACommand extends AACommand
                        implements ICommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /** Název příkazu. */
    private final String name;

    /** Popis funkce příkazu a možností jeho použití. */
    private final String description;



//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří příkaz se zadaným názvem a popisem.
     *
     * @param name          Název vytvářeného příkazu
     * @param description   Popis vytvářeného příkazu
     */
    ACommand(String name, String description)
    {
        super(name);

        this.name        = name;
        this.description = description;
    }



//== ABSTRACT METHODS ==========================================================

    /***************************************************************************
     * Metoda realizující reakci hry na zadání daného příkazu.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy <i>konec</i> a <i>nápověda</i> nemají parametry,
     * příkazy <i>jdi</i> a <i>seber</i> mají jeden parametr
     * příkaz <i>použij</i> muže mít dva parametry atd.
     *
     * @param arguments Parametry příkazu;
     *                  jejich počet muže byt pro každý příkaz jiný,
     *                  ale počítají se až od prvního, protože nultý prvek pole
     *                  obsahuje název daného příkazu
     * @return Text zprávy vypsané po provedeni příkazu
     */
    @Override
    public abstract String execute(String... arguments)
    ;



//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí název příkazu, tj. text, který musí hráč zadat
     * pro vyvolaní daného příkazu.
     *
     * @return Název příkazu
     */
    @Override
    public String getName()
    {
        return name;
    }


    /***************************************************************************
     * Vrátí popis příkazu s vysvětlením jeho funkce,
     * významu jednotlivých parametrů
     * a možností (resp. účelu) použití daného příkazu.
     *
     * @return Popis příkazu
     */
    @Override
    public String getDescription()
    {
        return description;
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
