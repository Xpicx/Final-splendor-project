
public class BuyCardAction implements Action
{
    public DevCard carte;
    
    public BuyCardAction(DevCard carte){
        this.carte=carte;
    }
    
    public void process(Player joueur,Board plateau){
        plateau.updateCard(carte);
        joueur.addPurchasedCard(carte);
        for (Resource resource : Resource.values()) {
            joueur.updateNbResource(resource, -carte.getCost().getNbResource(resource));
            plateau.updateNbResource(resource, carte.getCost().getNbResource(resource));
        }
        joueur.updatePoints(carte.getPoints());
    }
    
    public String toString(){
        return "Vous avez acheté "+carte+" !";
    }
}
