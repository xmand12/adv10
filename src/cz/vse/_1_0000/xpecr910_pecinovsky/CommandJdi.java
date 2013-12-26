/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;



/*******************************************************************************
 * Instance třídy {@code CommandJdi} představuje příkaz
 * přesouvající ve hře hráče do zadaného sousedního prostoru.
 * Cílový prostor musí být v daném okamžiku znám
 * jako sousední prostor prostoru, v němž se hráč právě nachází.
 * <p>
 * Instance třídy je efektivně jedináček,
 * ale není třeba to v definici třídy explicitně zabezpečovat, protože vytvoření
 * a následnou správu všech příkazů má starosti k tomu definovaný správce,
 * který zabezpečí, aby od každé třídy příkazu vznikla pouze jediná instance.
 * </p>
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class CommandJdi extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří novou instanci příkazu.
     */
    public CommandJdi()
    {
        super("Jdi", "Přesune hráče do zadané sousední místnosti");
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Přesune hráče do zadaného sousedního prostoru.
     * Cílový prostor musí být v daném okamžiku znám jako sousední prostor
     * prostoru, v němž se hráč právě nachází.
     * Příkaz proto musí ohlídat, že byl vůbec nějaký prostor zadán,
     * a že zadaný prostor patří mezi současné sousedy aktuálního prostoru.
     *
     * @param arguments Nultá položka obsahuje zadaný název daného příkazu,
     *                  první položka obsahuje název cílového prostoru,
     *                  další položky jsou ignorovány
     * @return Text zprávy vypsané po provedeni příkazu
     */
    @Override
    public String execute(String... arguments)
    {
        Room2Manager      manager   = Room2Manager.getInstance();
        Room2             room      = manager.getCurrentPlace();
        ListINamed<Room2> neighbors = room.getNeighbors();
        Room2             destination = neighbors.getINamed(arguments[1]);

        if (destination == null) {
            return "Zadaná akce nebyla provedena\n" +
                   "Do zadané místnosti se odsud nedá přejít.";
        }
        manager.setCurrentPlace(destination);
        String answer = "Přesunul(a) jste se do místnosti: " +
                        destination.getName();
        return answer;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
