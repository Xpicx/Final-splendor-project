import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class HumanPlayer extends Player
{
    public HumanPlayer(int id, String name){
        super(id,name);
    }

    public Action chooseAction(Board board){

        Action result = null;

        Game.display.out.println("Veuillez choisir une action: ");
        Game.display.out.println("Entrer 1 pour: Prendre deux jetons de la même ressource");
        Game.display.out.println("Entrer 2 pour: Prendre trois jetons de ressources différentes");
        Game.display.out.println("Entrer 3 pour: Acheter une carte développement");
        Game.display.out.println("Entrer 4 pour: Passer votre tour");
        Scanner scanner = new Scanner(Game.display.in);
        int choix = -1; //Lecture entrée clavier

        //Choix que peut faire le joueur
        while (choix < 1 || choix > 4) {
            try {
                Game.display.out.println("Veuillez choisir une action (1 à 4) :");
                choix = scanner.nextInt();
                scanner.nextLine();
                if (choix < 1 || choix > 4) {
                    Game.display.out.println("Erreur : le nombre doit être entre 1 et 4.");
                }
            } catch (InputMismatchException e) {
                Game.display.out.println("Erreur : veuillez entrer un entier valide.");
                scanner.nextLine(); 
            }
        }

        if (choix == 1) {
            String choixRessource = "";
            boolean choixFait = false;

            while (!choixFait) {
                Game.display.out.println("Veuillez entrer un type de ressource :");
                Game.display.out.println("D pour DIAMOND, S pour SAPPHIRE, E pour EMERALD, O pour ONYX, R pour RUBY");
                
                choixRessource = scanner.nextLine();

                if(choixRessource.equals("D")) {
                    result = new PickSameTokensAction(Resource.valueOf("DIAMOND"));
                    choixFait = true;
                }
                else if(choixRessource.equals("S")) {
                    result = new PickSameTokensAction(Resource.valueOf("SAPPHIRE"));
                    choixFait = true;
                }
                else if(choixRessource.equals("E")) {
                    result = new PickSameTokensAction(Resource.valueOf("EMERALD"));
                    choixFait = true;
                }
                else if(choixRessource.equals("O")) {
                    result = new PickSameTokensAction(Resource.valueOf("ONYX"));
                    choixFait = true;
                }
                else if(choixRessource.equals("R")) {
                    result = new PickSameTokensAction(Resource.valueOf("RUBY"));
                    choixFait = true;
                }else {
                    Game.display.out.println("Entrée invalide. Veuillez entrer une lettre valide.");
                }
            }
            Game.display.out.println(result.toString());
        }

        if (choix==2) {
            ArrayList<Resource> choixResources = new ArrayList<Resource>();
            while (choixResources.size() < 3) { 
                Game.display.out.println("Veuillez entrer une ressource en utilisant sa première lettre :");
                Game.display.out.println("D pour DIAMOND, S pour SAPPHIRE, E pour EMERALD, O pour ONYX, R pour RUBY");

                String choixRessource = scanner.nextLine().toUpperCase();

                Resource ressource = null;
                if (choixRessource.equals("D")) {
                    ressource = Resource.valueOf("DIAMOND");
                } else if (choixRessource.equals("S")) {
                    ressource = Resource.valueOf("SAPPHIRE");
                } else if (choixRessource.equals("E")) {
                    ressource = Resource.valueOf("EMERALD");
                } else if (choixRessource.equals("O")) {
                    ressource = Resource.valueOf("ONYX");
                } else if (choixRessource.equals("R")) {
                    ressource = Resource.valueOf("RUBY");
                }

                if (ressource != null) {
                    if (!choixResources.contains(ressource)) {
                        choixResources.add(ressource);
                        Game.display.out.println("Vous avez sélectionné : " + ressource);
                    } else {
                        Game.display.out.println("Cette ressource a déjà été sélectionnée. Veuillez en choisir une autre.");
                    }
                } else {
                    Game.display.out.println("Entrée invalide. Veuillez entrer une lettre correspondant à une ressource valide.");
                }
            }
            result = new PickDiffTokensAction(choixResources);
            Game.display.out.println(result.toString());
        }

        if (choix == 3) {
            boolean positionValide = false;
            int positionX = -1;
            int positionY = -1;

            while (!positionValide) {
                try {
                    Game.display.out.println("Veuillez choisir une carte à acheter sur le plateau :");
                    Game.display.out.println("Entrez les coordonnées de la carte (ligne et colonne).");
    
                    Game.display.out.println("Entrez la ligne de la carte (Entre 1 et 3 inclus).");
                    positionX = scanner.nextInt()-1;
                    scanner.nextLine();
                    Game.display.out.println("Entrez la colonne de la carte (Entre 1 et 4 inclus).");
                    positionY = scanner.nextInt()-1;
                    scanner.nextLine();
                    
                    if (positionX < 1 || positionX > 3 || positionY < 1 || positionY > 3) {
                        Game.display.out.println("Aucune carte n'existe à cette position. Réessayez.");
                    } else {
                        if (board.getCard(positionX, positionY) != null && canBuyCard(board.getCard(positionX, positionY))) {
                            result = new BuyCardAction(board.getCard(positionX, positionY));
                            positionValide = true;
                            Game.display.out.println(result.toString());
                        } else if (!canBuyCard(board.getCard(positionX, positionY))) {
                            Game.display.out.println("Vous n'avez pas assez de ressources pour acheter cette carte.");
                        }
                    }
                } catch (InputMismatchException e) {
                    Game.display.out.println("Erreur : veuillez entrer un nombre valide.");
                    scanner.nextLine();
                }
            }
        }

        if(choix==4) {
            result = new PassAction();
            Game.display.out.println(result.toString());
        }

        return result;
    }

    public Resources chooseDiscardingTokens(){
        Scanner scanner=new Scanner(System.in);
        Resources tokensToDiscard = null;
        while (super.getNbTokens()> 10) { 
            Game.display.out.println("Veuiller choisir un jeton à retirer: ");
            String tokenToDiscard=scanner.nextLine();
            while(tokenToDiscard.equals("DIAMOND") && tokenToDiscard.equals("SAPHIRE") && tokenToDiscard.equals("EMERALD") && tokenToDiscard.equals("RUBY") && tokenToDiscard.equals("ONYX") ){
                Game.display.out.println("Veuiller choisir 3 types de ressource");
                tokensToDiscard.updateNbResource(Resource.valueOf(tokenToDiscard), 1);
            }
        }
        return tokensToDiscard;
    }
}
