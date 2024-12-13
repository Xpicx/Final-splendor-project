import java.util.ArrayList;
import java.util.HashMap;

public class Resources extends HashMap<Resource, Integer> {

    private final HashMap<Resource, Integer> resources;

    /**
     * Constructeur qui initialise toutes les ressources à 0.
     */
    public Resources() {
        resources = new HashMap<>();
        for (Resource resource : Resource.values()) {
            resources.put(resource, 0);
        }
    }

    /**
     * Constructeur qui initialise toutes les ressources à partir de valeurs données.
     */
    public Resources(int coutDiamond, int coutSapphire, int coutEmerald, int coutRuby, int coutOnyx) {
        resources = new HashMap<>();
        resources.put(Resource.DIAMOND, coutDiamond);
        resources.put(Resource.SAPPHIRE, coutSapphire);
        resources.put(Resource.EMERALD, coutEmerald);
        resources.put(Resource.RUBY, coutRuby);
        resources.put(Resource.ONYX, coutOnyx);
    }

    /**
     * Renvoie la quantité d'un type de ressource.
     */
    public int getNbResource(Resource resource) {
        return resources.get(resource);
    }

    /**
     * Change la valeur d'une ressource passée en paramètre par un entier positif ou nul.
     */
    public void setNbResource(Resource resource, int quantity) {
        if (quantity >= 0) {
            resources.put(resource, quantity);
        } 
    }

    /**
     * Met à jour une ressource passée en paramètre avec une quantité donnée.
     * La valeur reste non négative.
     */
    public void updateNbResource(Resource resource, int quantity) {
        int currentQuantity = getNbResource(resource);
        int newQuantity = currentQuantity + quantity;

        if (newQuantity >= 0) {
            resources.put(resource, newQuantity);
        }
    }

    /**
     * Renvoie une liste des ressources pour lesquelles la quantité est supérieure à zéro.
     */
    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> availableResources = new ArrayList<>();
        for (Resource resource : Resource.values()) {
            if (getNbResource(resource) > 0) {
                availableResources.add(resource);
            }
        }
        return availableResources;
    }
}