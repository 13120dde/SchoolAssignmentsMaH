package flertrådatProgrammeringL3V1;

/**
 * Created by Patrik Lind on 2016-11-29.
 *
 * This class represents a food-item that has variables name, weight and volume.
 */
public class FoodItem {

    private String itemName;
    private double volume, weight;

    /**Instantiate this object.
     *
     * @param volume : double
     * @param weight : double
     * @param itemName : String
     */
    public FoodItem(double volume, double weight, String itemName){
        this.itemName=itemName;
        this.volume = volume;
        this.weight = weight;
    }

    /**Retuns the name of the item.
     *
     * @return itemName : String
     */
    public String getItemName() {
        return itemName;
    }

    /**Returns the volume of this item.
     *
     * @return volume : double
     */
    public double getVolume() {
        return volume;
    }

    /**Returns the weight of this item.
     *
     * @return weight : double
     */
    public double getWeight() {
        return weight;
    }

    public String toString(){
        return itemName+"nVolume: "+volume+"\nWeight: "+weight;
    }


}
