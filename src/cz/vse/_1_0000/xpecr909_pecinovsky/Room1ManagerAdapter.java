/* The file is saved in UTF-8 codepage.
 * Check: «Stereotype», Section mark-§, Copyright-©, Alpha-α, Beta-β, Smile-☺
 */
package cz.vse._1_0000.xpecr909_pecinovsky;

import cz.vse.adv_du.I08T_SpaceManagerT;

import java.util.Collection;



/*******************************************************************************
 * Instance třídy {@literal _08D_Room2ManagerAdapter} představují adaptér pro
 * správce prostorů ve vzorové textové konverzační hře.
 * Třída je definována jako adaptér, jejíž instance zabalují objekt třídy
 * {@link Room1}, který slouží jako správce svých instancí.
 * Slouží-li jako správce instancí objekt jejich mateřské třídy,
 * nelze jej uložit do proměnné. Adaptér umožňuje tuto nedokonalost obejít.
 * <p>
  * Jednička v názvu třídy má symbolizovat, že definice jednotlivých prostorů
 * i definice jejich správce jsou součástí jediného zdrojového kódu.
 *
 * @author  Rudolf PECINOVSKÝ
 * @version 0.00.0000 — 20yy-mm-dd
 */
class Room1ManagerAdapter implements IRoomManager,
                                     I08T_SpaceManagerT
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jediná instance dané třídy &ndash; jedináček. */
    private static final Room1ManagerAdapter SINGLETON =
                     new Room1ManagerAdapter();



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Jednoduchá, statická tovární metoda vracející
     * jedinou instanci dané třídy &ndash; jedináčka.
     *
     * @return Jediná instance třídy
     */
    static Room1ManagerAdapter getInstance()
    {
        return SINGLETON;
    }


    /***************************************************************************
     *
     */
    private Room1ManagerAdapter()
    {
    }



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================

    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public
    Collection<Room1> getAllPlaces()
    {
        return Room1.getAllPlaces();
    }


    /***************************************************************************
     * Vrátí odkaz na aktuální prostor,
     * tj. na prostor, v němž se hráč pravé nachází.
     *
     * @return Prostor, v němž se hráč pravé nachází
     */
    @Override
    public
    Room1 getCurrentPlace()
    {
        return Room1.getCurrentPlace();
    }


    /***************************************************************************
     * Vrátí odkaz na prostor se zadaným názvem.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public
    Room1 getPlace(String name)
    {
        return Room1.getPlace(name);
    }



//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Inicializuje všechny prostory ve hře do jejich počátečního stavu.
     */
    @Override
    public
    void initialize()
    {
        Room1.initialize();
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
