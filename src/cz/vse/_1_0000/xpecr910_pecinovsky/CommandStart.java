/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr910_pecinovsky;



/*******************************************************************************
 * Instance třídy {@code CommandStart} realizuje příkaz,
 * který spustí novou hru.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
class CommandStart extends ACommand
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
     * Vytvoří startovací příkaz.
     */
    CommandStart()
    {
        super("", "Příkaz startující hru.");
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Spustí danou hru.
     * Před tím ale zkontroluje, zda hra není náhodou již spuštěna,
     * protože odmítá spouštět běžící hru.
     * Součástí startu hry je i inicializace všech součástí hry,
     * tj. prostorů, batohu i pomocných příznaků jednotlivých příkazů.
     *
     * @param arguments Nultá položka obsahuje zadaný název daného příkazu,
     *                  tj. prázdný řetězec. Další položky by neměly být,
     *                  ale příkaz to nekontroluje.
     * @return Text zprávy vypsané po provedeni příkazu
     */
    @Override
    public String execute(String... arguments)
    {
        if (DemoGame.getInstance().isAlive()) {
            return "Zadal(a) jste prázdný příkaz.";
        }
        Room2Manager   .getInstance().initialize();
        Hands          .getInstance().initialize();
        Command2Manager.getInstance().initialize();

        String answer = "Vítáme vás ve služebním bytě. Jistě máte hlad." +
                      "\nNajděte v bytě ledničku - tam vás čeká svačina.";
        DemoGame.getInstance().setAlive(true);
        return answer;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
