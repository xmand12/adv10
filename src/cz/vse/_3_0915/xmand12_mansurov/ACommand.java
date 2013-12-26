
package cz.vse._3_0915.xmand12_mansurov;

import cz.vse.adv_framework.game_txt.ICommand;

/*******************************************************************************
 * Instance tridy {@code ACommand} representuji prikazy, ktere hrac muze zadavat.
 *
 * @author  Daulet MANSUROV
 * @version 7.4
 */
public abstract class ACommand extends AACommand
                               implements ICommand
{
//== CONSTANT INSTANCE ATTRIBUTES ==============================================

    /*Konstanta obsahujici jmeno prikazu.*/
    private final String name;
    /*Konstanta obsahujici popis prikazu.*/
    private final String description;

//##############################################################################
//== CONSTUCTORS AND FACTORY METHODS ===========================================

    /***************************************************************************
     * Konstruktor, vytvarejici instanci prikazu podle zadanych kriterii.
     *
     * @param name jmeno prikazu
     * @param description popis prikazu
     */
    public ACommand(String name, String description) {
        super(name);
        this.name = name;
        this.description = description;
    }



//== ABSTRACT METHODS ==========================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String execute(String... arguments);


  //== INSTANCE GETTERS AND SETTERS ==============================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

}