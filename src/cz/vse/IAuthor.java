/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy.
 */
package cz.vse;



/*******************************************************************************
 * Instance rozhraní {@code IAuthor} umějí na požádání vrátit
 * jméno a identifikační kód (xname) autora/autorky své třídy.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public interface IAuthor
{
//== CONSTANTS =================================================================
//== DECLARED METHODS ==========================================================

    /***************************************************************************
     * Vrátí jméno autora programu ve formátu <b>PŘÍJMENÍ Křestní</b>,
     * tj. nejprve příjmení psané velkými písmeny a za ním křestní jméno,
     * u nějž bude velké pouze první písmeno a ostatní písmena budou malá.
     *
     * @return Jméno autora/autorky programu ve tvaru PŘÍJMENÍ Křestní
     */
//    @Override
    public String getAuthorName()
    ;


    /***************************************************************************
     * Vrátí xname autora/autorky programu zapsané velkými písmeny.<p>
     * Xname má tvar <b>{@code XPPPK99}</b> nebo <b>{@code XPPPK999}</b>
     * nebo <b>{@code QPPPK99}</b>,
     * kde<br>
     * <ul>
     *   <li><b>{@code PPP}</b> zastupuje první tří písmena příjmení
     *      autora / autorky zapsaná velkými písmeny, </li>
     *   <li><b>{@code K}</b> zastupuje první písmeno křestního jména a </li>
     *   <li><b>{@code 99}</b>, resp. <b>{@code 999}</b> zastupují číslice
     *      přiřazené systémem, pomocí nichž se rozliší studenti(ky)
     *      s předchozími znaky shodnými.
     * </ul>
     * Jinými slovy musí vyhovovat regulárnímu výrazu <br><br>
     * <b>{@code [QX]([A-Z])([A-Z_]){3}\d{2,3}_$1[a-z]*.*}</b>,
     *
     * @return Xname autora/autorky programu
     */
//    @Override
    public String getAuthorID()
    ;



//== INHERITED METHODS =========================================================
//== EMBEDDED DATA TYPES =======================================================

    /***************************************************************************
     * Instance třídy {@code Adapter} poskytují implicitní implementaci
     * rozhraní {@link cz.vse.IAuthor}.
     * Odsazení metod je takové, aby je bylo možno bez problému
     * zkopírovat do příslušné třídy.
     */
    public static class Adapter implements IAuthor
    {
    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    //== STATIC CONSTRUCTOR (CLASS INITIALIZER, STATIC INICITALIZING BLOCK) ====
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    //== CLASS GETTERS AND SETTERS =============================================
    //== OTHER NON-PRIVATE CLASS METHODS =======================================

    //##########################################################################
    //== CONSTUCTORS AND FACTORY METHODS =======================================

    //== ABSTRACT METHODS ======================================================
    //== INSTANCE GETTERS AND SETTERS ==========================================

        /** {@inheritDoc} */
        @Override
        public String getAuthorName()
        {
            return "PECINOVSKÝ Rudolf";
        }


        /** {@inheritDoc} */
        @Override
        public String getAuthorID()
        {
            return "XPECR999";
        }



    //== OTHER NON-PRIVATE INSTANCE METHODS ====================================
    //== PRIVATE AND AUXILIARY CLASS METHODS ===================================
    //== PRIVATE AND AUXILIARY INSTANCE METHODS ================================
    //== EMBEDDED TYPES AND INNER CLASSES ======================================
    //== TESTING CLASSES AND METHODS ===========================================
    }



    ///#############################################################################
    ///#############################################################################
    ///#############################################################################

}
