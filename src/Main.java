import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		List<MonthlyReport> monthlyReports = new ArrayList<>();
		List<YearlyReport> yearlyReports = new ArrayList<>();
		runApp(monthlyReports, yearlyReports);
	}
	// Looped main menu of application. Scanning and performing commands from 1 to 6.
	private static void runApp(List<MonthlyReport> monthlyReports, List<YearlyReport> yearlyReports) {
		while (true) {
			System.out.println("\n____________MAIN MENU____________");
			System.out.println("Commands:");
			System.out.println("1. Read all monthly reports");
			System.out.println("2. Read all yearly reports");
			System.out.println("3. Verify and compare reports");
			System.out.println("4. Display information about all monthly reports");
			System.out.println("5. Display information about the annual report");
			System.out.println("6. Exit\n");
			System.out.print("Enter command: ");
			
			Scanner scanner = new Scanner(System.in);
			int command;
			while (!scanner.hasNextInt()) {
				System.out.print("This is not a number. Try again: ");
				scanner.nextLine();
			}
			command = scanner.nextInt();
			
			switch(command) {
				case 1: {
					String[] monthlyFiles = findReports("./Reports", "m.[0-9]{6}.csv");
					if (monthlyFiles.length == 0) {
						System.out.println("Monthly reports not found.");
						break;
					}
					readMonthlyReports("./Reports", monthlyFiles, monthlyReports);
					System.out.println("\nMonthly reports successfully readed.");
						break;
				}
				
				case 2: {
					String[] yearlyFiles = findReports("./Reports", "y.[0-9]{4}.csv");
					if (yearlyFiles.length == 0) {
						System.out.println("Yearly reports not found.");
						break;
					}
					readYearlyReports("./Reports", yearlyFiles, yearlyReports);
					System.out.println("\nYearly reports successfully readed.");
					break;
				}
				
				case 3: {
					if (monthlyReports.isEmpty() || yearlyReports.isEmpty()) {
						System.out.println("\nThere are not enough reports to compare.");
						break;
					}
					yearlyReports.forEach(yearlyReport -> {
						int currentYear = yearlyReport.getYear();
						int yearIncome = yearlyReport.getTotalIncome();
						int yearExtences = yearlyReport.getTotalExpences();
						List<MonthlyReport> currentYearMonthlyReports = monthlyReports.stream()
							      .filter(report -> report.getYear()== currentYear)
							      .collect(Collectors.toList());
						int allMonthIncome = 0;
						int allMonthExtences = 0;
						for (MonthlyReport monthlyReport : currentYearMonthlyReports) {
							allMonthIncome += monthlyReport.getTotalIncome();
							allMonthExtences += monthlyReport.getTotalExtences();
						}
						if (yearIncome == allMonthIncome && yearExtences == allMonthExtences) {
							System.out.println("\nMonthly reports are equal to the year report!");
							
						} else {
							System.out.println("\nMonthly reports are not equal to the year report!");
						}
					});
					break;
				}
					
				case 4: {
					if (monthlyReports.isEmpty()) {
						System.out.println("\nThere are no readed reports.");
						break;
					}
					System.out.println("\n_________MONTHLY REPORTS_________");
					monthlyReports.forEach(report -> {
						Item mostProfitableItem = report.getMostProfitableItem();
						Item mostExpensiveItem = report.getBiggestExpencesItem();
						System.out.println("\nYear " + report.getYear() + ", Month - " + report.getMonth());
						System.out.println("The most profitable item is " + mostProfitableItem.getName() + " with a total income " + mostProfitableItem.getTotalCost());
						System.out.println("The biggest expenses are " + mostExpensiveItem.getName() + " with a total expence " + mostExpensiveItem.getTotalCost());
					});
					break;
				}
				
				case 5: {
					if (yearlyReports.isEmpty()) {
						System.out.println("\nThere are no readed reports.");
						break;
					}
					System.out.println("\n_________YEARLY REPORTS_________");
					yearlyReports.forEach(report -> {
						HashMap<Integer, Integer> monthsProfit = report.getMonthProfit();
						int averageIncome = report.getAverageIncome();
						int averageExtences = report.getAverageExpences();
						System.out.println("\nYear - " + report.getYear());
						System.out.println("Every month profit:");
						for(Map.Entry<Integer, Integer> entry : monthsProfit.entrySet()) {
						    int month = entry.getKey();
						    int profit = entry.getValue();
						    System.out.println("Month " + month + " - " + profit);
						}
						System.out.println("Month average income is " + averageIncome);
						System.out.println("Month average extences are " + averageExtences);
					});
					break;
				}
					
				case 6: {
					System.out.println("\nYou've left this app.");
					scanner.close();
					System.exit(0);
				}
				
				default: System.out.println("Incorrect command! Try again.");
			}
		}
	}
	// Searches for reports in specific directory, returns array of filenames of reports.
	public static String[] findReports(String fileDirectory, String fileName) {
		File directory = new File(fileDirectory);
		String[] allReports = directory.list();
		String[] requiredReports = Arrays.stream(allReports)
				.filter(file -> file.matches(fileName)).toArray(String[]::new);
		return requiredReports;
	}
	// Reads all monthly report files, creates objects of them and adds them to array.
	public static void readMonthlyReports(String fileDirectory, String[] reportNames, List<MonthlyReport> monthlyReports) {
		String filepath;
		String report;
		int month;
		int year;
		List<Item> allItems;
		
		for (int i = 0; i < reportNames.length; i++) {
			filepath = fileDirectory + "/" + reportNames[i];
			report = readFile(filepath);
			month = Integer.valueOf(reportNames[i].substring(6, 8));
			year = Integer.valueOf(reportNames[i].substring(2, 6));
			allItems = new ArrayList<>();
			String[] reportLines = report.split("\n");
			for (int line = 1; line < reportLines.length; line++) {
				String[] values = reportLines[line].split(",");
				String name = values[0];
				boolean isExpence;
				if (values[1].equals("TRUE")) {
					isExpence = true;
				} else {
					isExpence = false;
				}
				int quantity = Integer.valueOf(values[2]);
				int cost = Integer.valueOf(values[3].trim());
				Item item = new Item(name, isExpence, quantity, cost);
				allItems.add(item);
			}
			MonthlyReport monthlyReport = new MonthlyReport(month, year, allItems);
			monthlyReports.add(monthlyReport);
		}
		
	}
	// Reads all yearly report files, creates objects of them and adds them to array.
	public static void readYearlyReports(String fileDirectory, String[] reportNames, List<YearlyReport> earlyReports) {
		String filepath;
		String report;
		int year;
		List<Month> allMonths;
		
		for (int i = 0; i < reportNames.length; i++) {
			filepath = fileDirectory + "/" + reportNames[i];
			report = readFile(filepath);
			year = Integer.valueOf(reportNames[i].substring(2, 6));
			allMonths = new ArrayList<>();
			String[] reportLines = report.split("\n");
			
			for (int line = 1; line < reportLines.length; line++) {
				String[] values = reportLines[line].split(",");
				int number = Integer.valueOf(values[0]);
				int amount = Integer.valueOf(values[1]);
				boolean isExpence;
				if (values[2].trim().equals("TRUE")) {
					isExpence = true;
				} else {
					isExpence = false;
				}
				Month month = new Month(number, amount, isExpence);
				allMonths.add(month);
			}
			
			YearlyReport earlylyReport = new YearlyReport(year, allMonths);
			earlyReports.add(earlylyReport);
		}
	}
	// Reads file.
	public static String readFile(String filepath) {
		try {
			FileInputStream inputFile = new FileInputStream(filepath);
			int count = 0;
			String result = "";
			while ((count = inputFile.read()) != -1) {
				result += (char)count;
			}
			inputFile.close();
			return result;
		} catch (IOException e) {
			System.out.println("Error reading file.");
			return null;
		}
	}

}
