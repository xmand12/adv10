package cz.vse.adv_framework.test_util.default_game.gamet;

import  cz.vse.adv_framework.game_txt.ICommand;



/*******************************************************************************
 * Instance tridy {@code CommandHelp} predstavuje prikaz vyvolavajici napovedu.
 * Ta vypíše všechny existující příkazy spolu s jejich popisy
 * bez ohledu na to, je-li daný příkaz možno v daném okamžiku použít.
 *
 * @author    Rudolf PECINOVSKY
 * @version   5.0
 */
public class CommandHelp extends ACommand
{
//== KONSTANTNI ATRIBUTY TRIDY =================================================
//== PROMENNE ATRIBUTY TRIDY ===================================================
//== KONSTANTNI ATRIBUTY INSTANCI ==============================================
//== PROMENNE ATRIBUTY INSTANCI ================================================
//== NESOUKROME METODY TRIDY ===================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

    /***************************************************************************
     * Vytvori prikaz, jenz po zadani otazniku vypise napovedu
     * popisujici vsechny prikazy ve hre.
     */
    CommandHelp()
    {
        super(Texts.pHELP,
              "Přehled příkazů, které je možno v průběhu hry zadat");
    }


//== ABSTRAKTNI METODY =========================================================
//== NESOUKROME METODY INSTANCI ================================================

    /***************************************************************************
     * Vypise nazvy a popisy vsech prikazu.
     *
     * @param  param Nepouzite parametry prikazu
     * @return Nazvy a popisy vsech prikazu
     */
    @Override
    public String execute(String... param)
    {
        StringBuilder sb = new StringBuilder(
            Texts.zNÁPOVĚDA);
//            "Příkazy, které je možno v průběhu hry zadat:\n");
        for (ICommand ap : ACommand.getPříkazy())
        {
            String nazev = ap.getName();
            if (nazev.equals("")) {
                continue;
            }
            sb.append("\n").append(ap.getName())
              .append("\n").append(ap.getDescription()).append("\n");
        }
        return sb.toString();

    }



//== SOUKROME A POMOCNE METODY TRIDY ===========================================
//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTING CLASSES AND METHODS ===============================================
}
