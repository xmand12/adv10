/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.adv_framework.test_util.default_game.gameg;

/* Statický import ke zkopírování do tříd, které jej budou potřebovat
 *
import static cz.pecinovsky.english.pojj.adv_demo.byt_lednička.hra.Texts.*;
 */



/*******************************************************************************
 * Knihovní třída {@code Texts} slouží jako schránka na textové konstanty,
 * které se používají na různých místech programu.
 * Centralizací definic těchto textových řetězců lze nejsnadněji dosáhnout toho,
 * že texty, které mají být shodné na různých místech programu,
 * budou doopravdy shodné.
 *
 * @author    Rudolf PECINOVSKÝ
 * @version   0.00.000
 */
public class Texts
{
//== CONSTANT CLASS ATTRIBUTES =================================================

    /** Jméno autora programu. */
    public static final String AUTOR = "PECINOVSKÝ Rudolf";

    /** Xname autora programu. */
    private static final String XNAME = "PECR999";

    /** Názvy používaných prostorů - místností. */
    public static final String
            PŘEDSÍŇ = "Předsíň",
            LOŽNICE = "Ložnice",
            OBÝVÁK  = "Obývák",
            KOUPELNA= "Koupelna",
            KUCHYŇ  = "Kuchyň";


    /** Názvy používaných objektů. */
    public static final String
            BOTNÍK  = "Botník",
            DEŠTNÍK = "Deštník",
            BRÝLE   = "Brýle",
            UMYVADLO= "Umyvadlo",
            TELEVIZE= "Televize",
            ČASOPIS = "Časopis",
            LEDNIČKA= "Lednička",
            PAPÍR   = "Papír",
            PIVO    = "Pivo",
            RUM     = "Rum",
            SALÁM   = "Salám",
            HOUSKA  = "Houska",
            VÍNO    = "Víno",
            POSTEL  = "Postel",
            ZRCADLO = "Zrcadlo",
            ŽUPAN   = "Župan";


    /** Názvy používaných příkazů. */
    public static final String
            pHELP    = "?",
            pJDI     = "Jdi",
            pNASAĎ   = "Nasaď",
            pOTEVŘI  = "Otevři",
            pPODLOŽ  = "Podlož",
            pPOLOŽ   = "Polož",
            pPŘEČTI  = "Přečti",
            pVEZMI   = "Vezmi",
            pZAVŘI   = "Zavři",
            pKONEC   = "Konec";


    /** Formát dodatku zprávy informujícího o aktuálním stavu hráče. */
    public static final String
            SOUSEDÉ  = "Sousedé:  ",
            PŘEDMĚTY = "Předměty: ",
            BATOH    = "Batoh:    ",
            FORMÁT_INFORMACE = "\n\nNacházíte se v místnosti: %s" +
                               "\n" + SOUSEDÉ  + "[%s]" +
                               "\n" + PŘEDMĚTY + "[%s]" +
                               "\n" + BATOH    + "[%s]";


    /** Texty zpráv vypisovaných v reakci na povinné příkazy.
     *  Počáteční z (zpráva) slouží k odlišení od stavů. */
    public  static final String
    zNENÍ_START     = "Hra neběží, lze ji spustit pouze prázdným příkazem.",
    zANP            = "\nZadaná akce nebyla provedena",
    zPORADÍM        = "\nChcete-li poradit, zadejte příkaz ?",
    zPRÁZDNÝ_PŘÍKAZ = "\nZadal(a) jste prázdný příkaz." + zPORADÍM,
    zNEZNÁMÝ_PŘÍKAZ = "\nTento příkaz neznám." + zPORADÍM,

    zPŘESUN         = "\nPřesunul(a) jste se do místnosti: ",
    zCIL_NEZADAN    = zANP + "\nNebyla zadána místnost, do níž se má přejít",
    zNENÍ_CIL       = zANP + "\nDo zadané místnosti se odsud nedá přejít",

    zZVEDNUTO       = "\nVzal(a) jste předmět: ",
    zPOLOŽENO       = "\nPoložil(a) jste předmět: ",
    zPŘEDMĚT_NEZADAN= zANP + "\nNebyl zadán předmět, s nímž mám manipulovat",
    zTĚŽKÝ_PŘEDMĚT  = zANP + "\nZadaný předmět nejde zvednout: ",
    zNENÍ_PŘEDMĚT   = zANP + "\nZadaný předmět v místnosti není: ",
    zNENÍ_V_BATOHU  = zANP + "\nPředmět není v batohu: ",
    zBATOH_PLNÝ     = zANP +
                      "\nZadaný předmět nemůžete vzít, máte už obě ruce plné",

    zNÁPOVĚDA       = "\nPříkazy, které je možno v průběhu hry zadat:" +
                      "\n============================================",

    zUVÍTÁNÍ        =
        "\nVítáme vás ve služebním bytě. Jistě máte hlad." +
        "\nNajděte v bytě ledničku - tam vás čeká svačina.",

    zCELÉ_UVÍTÁNÍ   = zUVÍTÁNÍ +
                      String.format(FORMÁT_INFORMACE,
                                    PŘEDSÍŇ, cm(LOŽNICE, OBÝVÁK, KOUPELNA),
                      cm(BOTNÍK,  DEŠTNÍK), cm()),

    zKONEC         = "Konec hry. \nDěkujeme, že jste zkusil(a) naši hru.";


    /** Texty zpráv vypisované v reakci na nepovinné příkazy. */
    public static final String
    LEDNICE_NEJDE_OTEVŘÍT =
        "\nLednička nejde otevřít. Na ledničce leží nějaký popsaný papír.",

    CHCE_PŘEČÍST_VZKAZ =
        "\nRozhodl(a) jste se přečíst vzkaz na papíře.",

    NEMÁ_BRÝLE =
        "\nText je psán příliš malým písmem, které je rozmazané",

    NASADIL_BRÝLE =
        "\nNasadil(a) jste si brýle.",

    NAPSÁNO =
        "\nNa papíru je napsáno:" +
        "\nLednička stojí nakřivo, a proto jde špatně otevírat." +
        "\nBudete-li mít problémy, něčím ji podložte.",

    CHCE_PODLOŽIT =
        "\nRozhodl(a) jste se podložit předmět ",

    PŘEDMĚTEM =
        " předmětem ",

    NELZE_NADZVEDNOUT =
        "\nBohužel máte obě ruce plné a nemáte ledničku čím nadzvednout." ,

    LEDNIČKA_PODLOŽENA =
        "\nLednička je úspěšně podložena - nyní by již měla jít otevřít.",

    OTEVŘEL_LEDNIČKU =
        "\nÚspěšně jste otevřel(a) ledničku.",

    BERE_ALKOHOL =
        "\nPokoušíte si vzít z inteligentní ledničky ",

    KOLIK_LET =
        "\nToto je inteligentní lednička, která neumožňuje " +
        "\npodávání alkoholických nápojů mladistvým." +
        "\nKolik je vám let?",

    NAROZEN =
        "V kterém roce jste se narodil(a)?\n",

    ODEBRAL =
        "Věřím vám a předávám vám požadovaný nápoj.\n" +
        "Odebral(a) jste z ledničky: ",

    NEZAPOMEŇ =
        "\nDobrou chuť. Nezapomeňte zavřít ledničku.",

    ZAVŘEL_LEDNIČKU =
        "Úspěšně jste zavřel(a) ledničku.";



//== VARIABLE CLASS ATTRIBUTES =================================================
//== STATIC INITIALIZER (CLASS CONSTRUCTOR) ====================================
//== CONSTANT INSTANCE ATTRIBUTES ==============================================
//== VARIABLE INSTANCE ATTRIBUTES ==============================================
//== CLASS GETTERS AND SETTERS =================================================
//== OTHER NON-PRIVATE CLASS METHODS ===========================================

    /***************************************************************************
     * Vrátí řetězec obsahující zadané názvy oddělené čárkami.
     *
     * @param názvy Názvy, které je třeba sloučit
     * @return Výsledný řetězec ze sloučených zadaných názvů
     */
    public static String cm(String... názvy)
    {
        if (názvy.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(názvy[0]);
        for (int i=1;   i < názvy.length;   i++) {
            sb.append(", ").append(názvy[i]);
        }
        return sb.toString();
    }



//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /** Soukromý konstruktor zabraňující vytvoření instance.*/
    private Texts() {}



//== ABSTRACT METHODS ==========================================================
//== INSTANCE GETTERS AND SETTERS ==============================================
//== OTHER NON-PRIVATE INSTANCE METHODS ========================================
//== PRIVATE AND AUXILIARY CLASS METHODS =======================================
//== PRIVATE AND AUXILIARY INSTANCE METHODS ====================================
//== EMBEDDED TYPES AND INNER CLASSES ==========================================
//== TESTING CLASSES AND METHODS ===============================================
}
