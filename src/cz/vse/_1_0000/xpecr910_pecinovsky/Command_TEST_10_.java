/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse._1_0000.xpecr910_pecinovsky;



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
public class Command_TEST_10_ extends ACommand
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
    public Command_TEST_10_()
    {
        super ("_TEST_10_", "Příkaz pro realizaci 10. domácího úkolu");
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
        Room2Manager roomManager = Room2Manager.getInstance();
        Room2 destination = roomManager.getPlace("_TEST_10_");
        roomManager.setCurrentPlace(destination);

        Hands hands = Hands.getInstance();
        ListINamed<Thing> things = hands.getObjects();
        things.clear();
        hands.add(new Thing("1"));
        hands.add(new Thing("2"));

        String answer = "Přesunul(a) jste se do testovacího prostoru";
        return answer;
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
