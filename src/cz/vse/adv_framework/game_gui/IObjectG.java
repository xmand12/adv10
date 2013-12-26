/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse.adv_framework.game_gui;

import cz.vse.adv_framework.game_txt.IObject;

import javax.swing.Icon;



/*******************************************************************************
 * Instance rozhraní {@code IObjectG} přestavují objekty v místnostech.
 * Předměty mohou být jak věci, tak i osoby či jiné skutečnosti (vůně,
 * světlo, fuidum, ...).
 * <p>
 * Některé z předmětů mohou charakterizovat stav místnosti (např. je rozsvíceno),
 * jiné mohou být určeny k tomu, aby je hráč "zvednul" a získal tím nějakou
 * schopnost či možnost projít nějakým kritickým místem hry
 * (např. klíč k odemnkuntí dveří).
 * <p>
 * Rozhodnete-li se použít ve hře předměty, které budou obsahovat jiné předměty,
 * (truhla, stůl, ...), můžete je definovat paralelně jako zvláštní druh
 * místnosti, do které se "vstupuje" např. příkazem "prozkoumej truhlu"
 * a která se opouští např. příkazem "zavři truhlu".
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   5.0
 */
public interface IObjectG extends IObject
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí odkaz na obrázek daného předmětu.
     *
     * @return  Požadovaný odkaz
     */
//    @Override
    public Icon getPicture();



//== ZDĚDĚNÉ METODY ============================================================
//== VNOŘENÉ TŘÍDY =============================================================
}
