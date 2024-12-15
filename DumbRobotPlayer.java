import java.util.*;
import java.util.Random;
public class DumbRobotPlayer extends Player
{
    public DumbRobotPlayer(int id, String name){
        super(id,name);
    }

    //Retourne le type d’action choisi nous considérons qu’un joueur robot stupide essayera toujours d’effectuer dans cet ordre là les actions suivantes 
    public Action chooseAction(Board board){

        //acheter une carte sur le plateau
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(canBuyCard(board.getCard(i, j))){
                    BuyCardAction bca = new BuyCardAction(board.getCard(i, j));
                    return bca;
                }
            }
        }


        //acheter deux jetons ressources de même type
        for (Resource resource : board.getAvailableResources()) {
            if (board.getNbResource(resource) >= 4) {
                PickSameTokensAction psta = new PickSameTokensAction(resource);
                return psta;
            }
        }


        //acheter des jetons ressources de type différents
        ArrayList<Resource> tokensToBuy = new ArrayList<Resource>();
        for (Resource resource : board.getAvailableResources()) {
            if (tokensToBuy.size() <= 3) {
                tokensToBuy.add(resource);
            }
            PickDiffTokensAction pdta = new PickDiffTokensAction(tokensToBuy);
            return pdta;
        }

        //passer son tour
        PassAction pa = new PassAction();
        return pa;        
    }


    //jetons a retirer
    public Resources chooseDiscardingTokens(){
        Resources tokensToDiscard = new Resources();
        while(super.getNbTokens()> 10){
            Random random = new Random();
            int rdm = random.nextInt(5);
            if(rdm==0 && super.getNbResource(Resource.DIAMOND) > 0){
                tokensToDiscard.updateNbResource(Resource.DIAMOND,1);
            }
            if(rdm==1 && super.getNbResource(Resource.SAPPHIRE) > 0){
                tokensToDiscard.updateNbResource(Resource.SAPPHIRE,1);
            }
            if(rdm==2 && super.getNbResource(Resource.EMERALD) > 0){
                tokensToDiscard.updateNbResource(Resource.EMERALD,1);
            }
            if(rdm==3 && super.getNbResource(Resource.RUBY) > 0){
                tokensToDiscard.updateNbResource(Resource.RUBY,1);
            }
            if(rdm==4 && super.getNbResource(Resource.ONYX) > 0){
                tokensToDiscard.updateNbResource(Resource.ONYX,1);
            }
        }
        return tokensToDiscard;
    }
}
