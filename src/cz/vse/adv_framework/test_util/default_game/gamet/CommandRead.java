package cz.vse.adv_framework.test_util.default_game.gamet;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code CommandRead} představuje příkaz,
 * kterým se hráč snaží přečíst nápis na předmětu, který drží v ruce.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CommandRead extends ACommand
{
//== CONSTANT CLASS ATTRIBUTES =================================================
//== VARIABLE CLASS ATTRIBUTES =================================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Vytvoří instanci mající na strosti reakci na příkaz <b>jdi</b>,
     * po jehož zadaní se hráč přesune do místnosti,
     * jejíž název bude uveden za příkazem.
     */
    public CommandRead()
    {
        super(pPŘEČTI, //"Přečti",
               "Přečte text na zadaném objektu,\n" +
               "přičemž musí tento objekt držet v ruce.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Vypíše hráči nápis na zadaném předmětu, který hráč držíá v ruce;
     * před tím však zkontroluje, zda je vůbec zadaná předmět, který se má
     * přečíst a zda tento předmět drží hráč v ruce.
     * Není-li předmětem papír, oznámí, že na předmětu není žádný nápis.
     * Před čtením papíru nejprve zkontroluje, zda má hráč nasazeny brýle.
     *
     * @param parametry Jediným smysluplným parametrem v tétho hře je papír
     * @return Text zpravy vypsane po porovedeni prikazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length == 0) {
            return "Není zadán předmět, který se má přečíst";
        }
        String predmet = parametry[0];
        if (! DefaultGame.getInstance().getBag().obsahuje(predmet)) {
            return "Zadaný předmět nelze přečíst, protože jej nedržíte: " +
                   predmet;
        }
        if (! predmet.equalsIgnoreCase("papír")) {
             return "Na předmětu " + predmet + " není žádný nápis.";
        }
        if (! State.BRYLE_NASAZENY) {
            return CHCE_PŘEČÍST_VZKAZ + NEMÁ_BRÝLE;
//            return "Rozhodl(a) jste se přečíst vzkaz na papíře.\n" +
//                   "Je ale psán příliš malým písmem, které je rozmazané";
        }
        return NAPSÁNO;
//        return "Na papíře je napsáno:\n" +
//               "Lednička stojí nakřivo, a proto jde špatně otevírat.\n" +
//               "Budete-li mít problémy, něčím ji podložte.";
    }



//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
//
//    /***************************************************************************
//     * Testovací metoda.
//     */
//    public static void test()
//    {
//        PrikazJdi x = new PrikazJdi();
//    }
//    /** @param args Parametry příkazového řádku - nepoužívané. */
//    public static void main(String[] args)  {  test();  }
}
