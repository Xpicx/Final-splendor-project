import java.util.*;
import java.util.Random;
public class DumbRobotPlayer extends Player
{
    public DumbRobotPlayer(int id, String name){
        super(id,name);
    }
    
    public Action chooseAction(Board board){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(canBuyCard(board.getCard(i, j))){
                    BuyCardAction bca = new BuyCardAction(board.getCard(i, j));
                    return bca;
                }
            }
        }
        
        for (Resource resource : board.getAvailableResources()) {
            if (board.getNbResource(resource) >= 4) {
                PickSameTokensAction psta = new PickSameTokensAction(resource);
                return psta;
            }
        }
        
        ArrayList<Resource> tokensToBuy = new ArrayList<Resource>();
        for (Resource resource : board.getAvailableResources()) {
            if (board.getNbResource(resource) >= 4 && tokensToBuy.size() <= 3) {
                tokensToBuy.add(resource);
            }
            PickDiffTokensAction pdta = new PickDiffTokensAction(tokensToBuy);
            return pdta;
        }
        PassAction pa = new PassAction();
        return pa;        
    }
    
    public Resources chooseDiscardingTokens(){
        Resources tokensToDiscard = null;
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
