
/**
 * Décrivez votre classe DiscardTokensAction ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DiscardTokensAction implements Action
{   
    public Resources discardRessources;
    
    public DiscardTokensAction(Resources discardRessources){
        this.discardRessources=discardRessources;
    }
    
    public void process(Player joueur,Board plateau){
        for (Resource resource : Resource.values()) {
            joueur.updateNbResource(resource, -discardRessources.getNbResource(resource));
        }
    }
    
    public String toString(){
        return "Vous avez retirer";
    }
    
    
}
