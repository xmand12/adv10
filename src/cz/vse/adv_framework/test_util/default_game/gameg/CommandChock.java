package cz.vse.adv_framework.test_util.default_game.gameg;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Instance třídy {@code PrikazJdi} představuje příkaz
 * k podložení předmětu jiným předmětem.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class CommandChock extends ACommand
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
     * Vytvoří instanci mající na starosti reakci na příkaz <b>jdi</b>,
     * po jehož zadaní se hráč přesune do místnosti,
     * jejíž název bude uveden za příkazem.
     */
    public CommandChock()
    {
        super(pPODLOŽ, //"Podlož",
               "Podloží předmět zadaný v prvním parametru\n" +
               "předmětem zadaným v druhém parametru.");
    }


//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================

    /***************************************************************************
     * Příkaz vyjme druhý zadaný předmět z batohu a podloží s ním první
     * zadaný předmět; před tím však nejprve zkontroluje,
     * zda byly zadány oba předměty, zda se předmět, kterým se má druhý předmět
     * podložit, má hráč aktuálně v batohu, zda má hráč aktuálně jednu ruku
     * volnou, aby s ní mohl podkládaný předmět nadzvednout.
     * Při podkládání jiných předmětů než ledničky pouze oznámí provedení akce.
     * Je-li podkládaným předmětem lednička, tak je-li podkládána časopisem,
     * oznámí úspěšnost akce, jinak prozradí, že nadále nejde otevřít.
     *
     * @param parametry Název předmětu, který se má položit,
     *                  následovaný názvem předmětu, kterým se podklá
     * @return Text zprávy vypsané po provedení příkazu
     */
    @Override
    public String execute(String... parametry)
    {
        if (parametry.length != 2) {
            return "Příkaz potřebuje dva parametry:\n" +
                   "předmět, který se má podložit a\n" +
                   "předmět, kterým se bude podkládat";
        }
        String podkládaný = parametry[0];
        String podkladek  = parametry[1];
        Bag batoh = DefaultGame.getInstance().getBag();
        if (! batoh.contains(podkladek)) {
              return "Můžete podložit pouze předmětem, který držíte v ruce.";
        }                                                       //==========>
        Room mistnost = Room.getCurrentRoom();
        Thing predmet = (Thing)Utilities.find(podkládaný,
                          mistnost.getObjects());
        String odpoved = "";
        if (predmet != null) {
            odpoved = CHCE_PODLOŽIT + podkládaný + PŘEDMĚTEM + podkladek + ".";
//            odpoved = "Rozhodl(a) jste se podložit předmět " + podkládaný +
//                      " předmětem " + podkladek + ".";
        }
        if (batoh.getObjects().size() > 1) {
            odpoved += NELZE_NADZVEDNOUT;
//                  "\nBohužel máte obě ruce plné a nemáte ji čím nadzvednout.";
            return odpoved;                                     //==========>
        }
        if (! podkládaný.equalsIgnoreCase("lednička")) {
            return odpoved;
        }
        if (! podkladek.equalsIgnoreCase("časopis")) {
            odpoved += "Nezvolil(a) jste optimální předmět pro podložení.\n" +
                       "Lednička stále nejde otevřít.";
            return odpoved;                                     //==========>
        }
        batoh.remove(podkladek);
        State.LEDNICKA_PODLOZENA = true;
        odpoved += LEDNIČKA_PODLOŽENA;
//            "\nLednička je úspěšně podložena - nyní by již měla jít otevřít.";
        return odpoved;                                         //==========>
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
