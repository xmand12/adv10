package cz.vse._3_0915.xmand12_mansurov;

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
            answer  = "Vy jste známý španělský korzár. Jednou k Vám přišel duch vašeho otce, \n"
                    + "a řekl, jdete do jeskyně, která se nachází v blízkosti města. \n"
                    + "Když jste přišel do jeskyně, váš otec ti dal mapu, v které se ukazuje, \n"
                    + "kde je jeho poklad a dal Vám klíč od truhlice.";
        }
        return answer;
    }

}
