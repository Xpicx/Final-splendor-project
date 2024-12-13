
/**
 * Décrivez votre classe PickSameTokensAction ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class PickSameTokensAction implements Action
{

    private Resource type;
    
    
    /**
     * Constructeur d'objets de classe PickSameTokensAction
     */
    public PickSameTokensAction(Resource type)
    {
        this.type=type;
        
    }

    public void process(Player joueur,Board plateau){
        plateau.updateNbResource(type,-2);
        joueur.updateNbResource(type,2);
    }
    
    public String toString(){
        return "Vous avez pris 2 jetons"+type;
    }
}
