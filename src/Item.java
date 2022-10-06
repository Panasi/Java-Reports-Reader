//This class represents every line of monthly report

public class Item {
	final private String name;
	final private boolean isExpence;
	final private int quantity;
	final private int cost;
	
	public Item(String name, boolean isExpence, int quantity, int cost) {
		this.name = name;
		this.isExpence = isExpence;
		this.quantity = quantity;
		this.cost = cost;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getIsExpence() {
		return this.isExpence;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public int getTotalCost() {
		return this.quantity * this.getCost();
	}
	
}
