// This class represents every line of yearly report. 

public class Month {
	final private int number;
	final private int amount;
	final private boolean isExpence;
	
	public Month(int number, int amount, boolean isExpence) {
		this.number = number;
		this.amount = amount;
		this.isExpence = isExpence;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public boolean getIsExpence() {
		return this.isExpence;
	}
}
