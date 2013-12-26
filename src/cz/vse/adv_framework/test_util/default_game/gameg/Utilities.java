package cz.vse.adv_framework.test_util.default_game.gameg;

import  cz.vse.adv_framework.game_txt.INamed;

import java.util.Collection;


/*******************************************************************************
 * Třídy {@code Utilities} slouží jako knihovna užitečných metod.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class Utilities
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Zjistí, zda v je zadané kolekci pojmenovaný objekt se zadany nazvem
     * a vrátí jej.
     *
     * @param  nazev   Název hledaného pojmenovaného objektu
     * @param  kolekce Kolekce, ve které pojmenovaný objekt hledám
     * @return Byl-li v kolekci objekt se zadaným názvem, vrátí jej,
     *         jinak vrátí prázdný odkaz {@code null}
     */
    public static INamed find(String nazev,
                               Collection<? extends INamed> kolekce)
    {
        nazev = nazev.toLowerCase();
        for (INamed ip : kolekce) {
            if (ip.getName().toLowerCase().equals(nazev)) {
                return ip;
            }
        }
        return null;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Soukromý konstruktor zabraňující vytvoření instancí
     */
    private Utilities(){/**/}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
