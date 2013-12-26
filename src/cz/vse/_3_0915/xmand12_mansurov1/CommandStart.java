package cz.vse._3_0915.xmand12_mansurov1;

/*******************************************************************************
 * Instance tridy {@code CommandStart} representuje prikaz startujici hru
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
class CommandStart extends ACommand
{

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     *Konstruktor vytvarijici instenci dane tridy
     */
    public CommandStart()
    {
        super("", "Příkaz startující hru.");
    }



//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(String... arguments) {
        String answer;
        if (Game.getInstance().isAlive()) {
            answer = "Zadal(a) jste prázdný příkaz.";
        } else{
            Game.getInstance().start();
            Rooms.initialize();
            answer  = "Vy jste známý španělský korsar.\n "
                    + "Váš tatínek před smrti dal vám mapu, ve které je\n "
                    + "označené místo, kde se nachází poklad.\n"
                    + "Kromě toho dal vám klíč od něho.";
        }
        return answer;
    }

}
