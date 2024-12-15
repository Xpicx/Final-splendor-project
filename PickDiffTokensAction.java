import java.util.ArrayList;
public class PickDiffTokensAction implements Action {
    private ArrayList<Resource> resourcesToTake;
    
    /**
     * Constructeur d'objets de classe PickDiffTokensAction 
     */
    public PickDiffTokensAction(ArrayList<Resource> resourcesToTake) {
        this.resourcesToTake = resourcesToTake;
    }

    public void process(Player joueur,Board plateau){
        for (Resource resource : resourcesToTake) {
            plateau.updateNbResource(resource, -1);
            joueur.updateNbResource(resource, 1);
        }
    }
    
    public String toString(){
        String result = "Vous avez pris:";
        for (Resource resource : resourcesToTake) {
            result += " 1 " + resource.toSymbol();
        }
        return result;
    }
}
