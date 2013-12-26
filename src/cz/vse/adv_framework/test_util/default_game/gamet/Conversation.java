package cz.vse.adv_framework.test_util.default_game.gamet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static cz.vse.adv_framework.test_util.default_game.gameg.Texts.*;



/*******************************************************************************
 * Trida {@code Conversation} definuje kód vedoucí s uživatelem rozhovor.
 * Při vedení rozhovoru se celý systém nachází ve zvláštním stavu,
 * v němž je modifikována cesta textů zadávaných uživatelem.
 *
 * @author    Rudolf PECINOVSKY
 * @version   4.00,  17.3.2007
 */
public class Conversation
{
//== KONSTANTNI ATRIBUTY TRIDY =================================================

    private static final String NEMOHU =
            "Bohužel vám nemohu %s vydat.\n" +
            "Vemte si něco jiného nebo zavřete ledničku.";

    private static final String ZADEJ_CELE_CISLO =
            "Musíte zadat svůj %s jako celé číslo.\n" +
            "Zkuste to ještě jednou.";



//== PROMENNE ATRIBUTY TRIDY ===================================================

    private static int krok = 0;

    private static int vek = 0;

    private static Something  objekt;
    private static String   názevObjektu;



//== KONSTANTNI ATRIBUTY INSTANCI ==============================================
//== PROMENNE ATRIBUTY INSTANCI ================================================
//== NESOUKROME METODY TRIDY ===================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

    /** Soukromý konstruktor bránící vytvoření instancí. */
    private Conversation()  {/**/}



//== ABSTRAKTNI METODY =========================================================
//== NESOUKROME METODY INSTANCI ================================================

    /***************************************************************************
     * Odstartuje rozhovor o nemožnosti jednoduchého odebrání zadaného předmětu.
     *
     * @param alkohol Předmět, který che hráč odebrat z ledničky
     * @return První část rozhovoru pronesená zadaným předmětem
     */
    static String start(Something alkohol)
    {
        State.ROZHOVOR    = true;
        Conversation.objekt = alkohol;
        názevObjektu    = alkohol.getName();
        krok = 1;
        return BERE_ALKOHOL+ názevObjektu + "." + KOLIK_LET;
//        return String.format(
//                "Pokoušíte si vzít z inteligentní ledničky %s.\n" +
//                "Tato lednička neumožňuje podávání alkoholických nápojů " +
//                "mladistvým.\nKolik je vám let?",
//                nazevPartnera);
    }


    /***************************************************************************
     * Reaguje na odpovědi uživatele na svoje minulé otázky.
     *
     * @param  text Odpověď uživatele
     * @return Další otázka, případně závěr rozhovoru
     */
    static String reaguj(String text)
    {
        switch (krok)
        {
            case 1: return krok_1(text);
            case 2: return krok_2(text);
            default:
                throw new IllegalStateException (
                        "\nRozhovor se ocitl v neočekávaném stavu.");
        }
    }



//== SOUKROME A POMOCNE METODY TRIDY ===========================================

    /***************************************************************************
     * Reakce na první odpověď, v níž uživatel zadává svůj věk.
     *
     * @param text Věk uživatele
     * @return Odpověď programu
     */
    private static String krok_1(String text) {
        text = text.trim();
        try {
            vek = Integer.parseInt(text);
        }catch(NumberFormatException nfe) {
            return String.format(ZADEJ_CELE_CISLO, "věk");
        }
        if (vek < 18) {
            State.ROZHOVOR = false;
            return String.format(NEMOHU, názevObjektu);
        }
        krok = 2;
        return NAROZEN;
//        return "V kterém roce jste se narodil(a)?";
    }


    /***************************************************************************
     * Reakce na druhou odpověď, v níž uživatel zadává rok narození.
     *
     * @param text Věk uživatele
     * @return Odpověď programu
     */
    @SuppressWarnings("boxing")
    private static String krok_2(String text) {
        text = text.trim();
        int rok;
        try {
            rok = Integer.parseInt(text);
        }catch(NumberFormatException nfe) {
            return String.format(ZADEJ_CELE_CISLO, "rok narození");
        }
        int letos = Calendar.getInstance().get(Calendar.YEAR);
        int vek2  = letos - rok;

        State.ROZHOVOR = false;

        if (Math.abs(vek - vek2) > 1) {
            return String.format(
                    "Vámi udaných %s let neodpovídá roku narození %s\n" +
                    NEMOHU, vek, rok, názevObjektu);
        }
        Room.getAktuálníMístnost().zvedni(názevObjektu);
        DefaultGame.getInstance().getBag().přidej(objekt);
        return ODEBRAL + názevObjektu + NEZAPOMEŇ;
//        return String.format(
//                "Věřím vám a předávám vám požadovaný nápoj." +
//              "\nOdebral(a) jste z ledničky: %s" +
//              "\nDobrou chuť. Nezapomeňte zavřít ledničku.",
//                názevObjektu);
    }



//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
