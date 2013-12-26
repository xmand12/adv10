/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.test_util; //ITest<T>


/*******************************************************************************
 * Rozhraní <b>{@code ITest}</b> definuje požadavky na testovací třídu,
 * jejiž instanci bude předaná každá instance třídy
 * implementující předem zadané rozhraní.
 *
 * @param <T> Rozhraní implementované testovanými instancemi
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface ITest<T>
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Otestuje zadanou instanci.
     *
     * @param instance Testovaná instance
     */
//    @Override
    public void test(T instance);



//== ZDĚDĚNÉ METODY ============================================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
}
