package cz.vse.adv_framework.test_util.default_game.gameg;



/*******************************************************************************
 * Třída {@code State} slouží jako schránka na informace o stavech,
 * které nelze jednoduše uložit v nějaké instanci, tj. na stavy,
 * které jeden příkaz nastaví a druhý zjišťuje a třída, pro jejíž instanci
 * se stav nastavuje, s takovouto možností obecně nepočítá.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class State
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================

    /** Příznak toho, že uživatel má nasazeny brýle, a může proto číst. */
    static boolean BRYLE_NASAZENY = false;

//    static boolean LEDNICKA_OTEVRENA = false;
    //Nepotřebuji, protože když je otevřena, jsem v ledničce,
    //a když nejsem v ledničce, tak je zavřena.

    static boolean LEDNICKA_PODLOZENA = false;

    /** Příznak toho, že uživatel rozmlouvá s inteligentní ledničkou. */
    static boolean DIALOG = false;



//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Uvede případné nastavované příznaky do počátečního stavu.
     */
    public static void initialize()
    {
        BRYLE_NASAZENY     = false;
        LEDNICKA_PODLOZENA = false;
        DIALOG             = false;
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor bránící vytvoření instancí. */
    private State()  {/**/}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
