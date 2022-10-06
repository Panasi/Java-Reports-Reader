import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

public class YearlyReport {
	final private int year;
	final private List<Month> allMonths; // Lines of yearly report file
	
	public YearlyReport(int year, List<Month> allMonths) {
		this.year = year;
		this.allMonths = allMonths;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public List<Month> getIncomeMonths() {
		return allMonths.stream()
			      .filter(month -> !month.getIsExpence())
			      .collect(Collectors.toList());
	}
	
	public List<Month> getExpencesMonths() {
		return allMonths.stream()
			      .filter(month -> month.getIsExpence())
			      .collect(Collectors.toList());
	}
	public int getTotalIncome() {
		List<Month> income = getIncomeMonths();
		int totalIncome = 0;
		for(Month month : income){
			totalIncome += month.getAmount();
		}
		return totalIncome;
	}
	
	public int getTotalExpences() {
		List<Month> expences = getExpencesMonths();
		int totalExpences = 0;
		for(Month month : expences){
			totalExpences += month.getAmount();
		}
		return totalExpences;
	}
	
	public int getAverageIncome() {
		return getTotalIncome() / getIncomeMonths().size();
	}
	
	public int getAverageExpences() {
		return getTotalExpences() / getExpencesMonths().size();
	}
	
	public HashMap<Integer, Integer> getMonthProfit() {
		HashMap<Integer, Integer> monthsProfit = new HashMap<>();
		List<Month> income = getIncomeMonths();
		List<Month> expences = getExpencesMonths();
		for (int i = 0; i < income.size(); i++) {
			int number = income.get(i).getNumber();
			int profit = income.get(i).getAmount() - expences.get(i).getAmount();
			monthsProfit.put(number, profit);
		}
		return monthsProfit;
	}
	
}
