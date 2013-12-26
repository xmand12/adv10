/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse._1_0000.xpecr911_pecinovsky;



/*******************************************************************************
 * Instance třídy {@code CommandVezmi} realizuje příkaz,
 * který odebere zadaný objektu z aktuálního prostoru
 * a přesune jej do batohu.
 * Objekt přitom musí být zvednutelný a musí se do batohu vejít.
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
public class CommandVezmi extends ACommand
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
     * Vytvoří daný příkaz.
     */
    public CommandVezmi()
    {
        super ("Vezmi", "Odebere objekt z aktuálního prostoru " +
                        "a vloží jej do batohu");
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
     * Odebere zadaný objektu z aktuálního prostoru
     * a přesune jej do batohu.
     * Pro korektní přesun ale musí platit, že zadaný objekt je přesouvatelný
     * a že v batohu, který má konečnou kapacitu, je pro něj dostatek místa.
     *
     * @param arguments Nultá položka obsahuje zadaný název daného příkazu,
     *                  první položka obsahuje název přesouvaného objektu,
     *                  další položky jsou ignorovány
     * @return Text zprávy vypsané po provedeni příkazu
     */
    @Override
    public String execute(String... arguments)
    {
        if (arguments.length < 2) {
            return "\nNebylo zadáno, co se má vzít.";
        }
        Room2Manager      manager    = Room2Manager.getInstance();
        Room2             room       = manager.getCurrentPlace();
        ListINamed<Thing> roomThings = room.getObjects();
        Thing             thing      = roomThings.getINamed(arguments[1]);

        if (thing == null) {
            return "Zadaná akce nebyla provedena\n" +
                   "Zadaný předmět v místnosti není: " + arguments[1];
        }
        Hands   hands   = Hands.getInstance();
        boolean success = hands.add(thing);
        if (success) {
            roomThings.remove(thing);
            return "Vzal(a) jste předmět: " + thing.getName();
        }
        return "Zadaná akce nebyla provedena\n" +
               "Zadaný předmět nemůžete vzít, máte už obě ruce plné.";
     }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
