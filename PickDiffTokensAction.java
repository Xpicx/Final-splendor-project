public class PickDiffTokensAction implements Action {
    private Resource type1;
    private Resource type2;
    private Resource type3;

    /**
     * Constructeur d'objets de classe PickDiffTokensAction
     */
    public PickDiffTokensAction(Resource type1,Resource type2,Resource type3)
    {
        this.type1=type1;
        this.type2=type2;
        this.type3=type3;
    }

    public void process(Player joueur,Board plateau){
        plateau.updateNbResource(type1,-1);
        joueur.updateNbResource(type1,1);
        plateau.updateNbResource(type2,-1);
        joueur.updateNbResource(type2,1);
        plateau.updateNbResource(type3,-1);
        joueur.updateNbResource(type3,1);
        
    }
    
    public String toString(){
        return "Vous avez pris: 1"+type1+", 1"+type2+", 1"+type3+". ";
    }
}
