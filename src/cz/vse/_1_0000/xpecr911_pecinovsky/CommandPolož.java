/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse._1_0000.xpecr911_pecinovsky;



/*******************************************************************************
 * Instance třídy {@code EmptyCommand} realizuje příkaz, který
 * <p>
 * Instance třídy je efektivně jedináček,
 * ale není třeba to v definici třídy explicitně zabezpečovat, protože vytvoření
 * a následnou správu všech příkazů má starosti k tomu definovaný správce,
 * který zabezpečí, aby od každé třídy příkazu vznikla pouze jediná instance.
 * </p>
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 12.01
 */
public class CommandPolož extends ACommand
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
     *
     */
    public CommandPolož()
    {
        super ("Polož",
               "Odebere objekt z batohu a uloží jej do aktuálního prostoru");
    }



//== ABSTRACT METHODS ==========================================================
/*
    //Vlastní deklarace pro překladač je dále, aby se snadněji zanášely
    //případné úpravy konstraktu prostým porovnáním souborů
    @Override
    public abstract String execute(String... arguments)
    ;
*/


//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Metoda realizující reakci hry na zadání daného příkazu.
     * Počet parametrů je závislý na konkrétním příkazu,
     * např. příkazy <i>konec</i> a <i>nápověda</i> nemají parametry,
     * příkazy <i>jdi</i> a <i>seber</i> mají jeden parametr
     * příkaz <i>použij</i> muže mít dva parametry atd.
     *
     * @param arguments Parametry příkazu;
     *                  jejich počet muže byt pro každý příkaz jiný
     * @return Text zprávy vypsané po provedeni příkazu
     */
    @Override
    public String execute(String... arguments)
    {
        if (arguments.length < 2) {
            return "\nNebylo zadáno, co se má položit.";
        }
        String            thingName = arguments[1];
        Hands             hands     = Hands.getInstance();
        Thing             thing     = hands.remove(thingName);

        if (thing == null) {
            return "Zadaná akce nebyla provedena\n" +
                   "Předmět není v batohu: " + thingName;
        }

        Room2Manager roomManager = Room2Manager.getInstance();
        Room2        currentRoom = roomManager.getCurrentPlace();
        currentRoom.getObjects().add(thing);

        return "Položil(a) jste předmět: " + thing.getName();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
