import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();
            if (userInput == 1) {

                for (int i = 1; i <= 3; i++) {
                    monthlyReport.loadFile(i, "resources/" + "m.20210" + i + ".csv");
                }

            } else if (userInput == 2) {
                yearlyReport.loadFile( "resources/y.2021.csv");

            } else if (userInput == 3) {
                Checker checker = new Checker(yearlyReport, monthlyReport);
                boolean answer = checker.check();
                System.out.println("Сверка прошла успешно  " + answer );

            } else if (userInput == 4) {
                monthlyReport.printMonthReport();

            } else if (userInput == 5) {
                yearlyReport.printYearReport();

            } else if (userInput == 9999) {
                break;
            } else {
                System.out.println("Такой команды нет");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("9999 - Выход");

    }
}

