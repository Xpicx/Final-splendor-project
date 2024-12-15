
public class BuyCardAction implements Action
{
    public DevCard carte;
    
    public BuyCardAction(DevCard carte){
        this.carte=carte;
    }

    //Retire une carte du board pour la mettre dans dans le paquet du joueur et retire les resources avec lesquelles le joueur a acheté la carte 
    public void process(Player joueur,Board plateau){
        plateau.updateCard(carte);
        joueur.addPurchasedCard(carte);
        for (Resource resource : Resource.values()) {
            joueur.updateNbResource(resource, -carte.getCost().getNbResource(resource) + joueur.getResourceBonus().getNbResource(resource));
            plateau.updateNbResource(resource, carte.getCost().getNbResource(resource) - joueur.getResourceBonus().getNbResource(resource));
        }
        joueur.updatePoints(carte.getPoints());
    }

    //Renvoie la carte que le joueur a acheté
    public String toString(){
        return "Vous avez acheté "+carte+" !";
    }
}
