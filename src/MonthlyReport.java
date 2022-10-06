import java.util.List;
import java.util.stream.Collectors;


public class MonthlyReport {
	final private int month;
	final private int year;
	final private List<Item> items; // Lines of month report file
	
	public MonthlyReport(int month, int year, List<Item> items) {
		this.month = month;
		this.year = year;
		this.items = items;
	}
	
	public String getMonth() {
		String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		return allMonths[month - 1];
	}
	
	public int getYear() {
		return this.year;
	}
	
	public List<Item> getIncomeItems() {
		return items.stream()
			      .filter(item -> !item.getIsExpence())
			      .collect(Collectors.toList());
	}
	
	public List<Item> getExpencestems() {
		return items.stream()
			      .filter(item -> item.getIsExpence())
			      .collect(Collectors.toList());
	}
	public int getTotalIncome() {
		int totalIncome = 0;
		List<Item> incomeItems = getIncomeItems();
		for (Item item : incomeItems) {
			totalIncome += item.getTotalCost(); 
		}
		return totalIncome;
	}
	
	public int getTotalExtences() {
		int totalExtences = 0;
		List<Item> expenceItems = getExpencestems();
		for (Item item : expenceItems) {
			totalExtences += item.getTotalCost(); 
		}
		return totalExtences;
		
	}
	public Item getMostProfitableItem() {
		List<Item> income = getIncomeItems();
		int highestCost = 0;
		Item result = income.get(0);
		for(Item item : income){
			if ((item.getQuantity() * item.getCost()) > highestCost) {
				highestCost = item.getQuantity() * item.getCost();
				result = item;
			}
		}
		return result;
	}
	
	public Item getBiggestExpencesItem() {
		List<Item> expences = getExpencestems();
		int highestCost = 0;
		Item result = expences.get(0);
		for(Item item : expences){
			if ((item.getQuantity() * item.getCost()) > highestCost) {
				highestCost = item.getQuantity() * item.getCost();
				result = item;
			}
		}
		return result;
	}

}
