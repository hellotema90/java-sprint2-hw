import java.util.HashMap;

public class Checker {
    public YearlyReport yearlyReport;
    public MonthlyReport monthlyReport;


    public Checker(YearlyReport yearlyReport, MonthlyReport monthlyReport) {
        this.yearlyReport = yearlyReport;
        this.monthlyReport = monthlyReport;
    }

    public boolean check()  {
        boolean check = true;

        HashMap<Integer, HashMap<Boolean, Integer>> reportMonth = new HashMap<>();
        for (Monthly monthly : monthlyReport.monthlys) {

            if (!reportMonth.containsKey(monthly.month)) {
                reportMonth.put(monthly.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> amountMonths = reportMonth.get(monthly.month);
            amountMonths.put(monthly.isExpense, amountMonths.getOrDefault(monthly.isExpense, 0) + monthly.quantity * monthly.sumOfOne);
        }
        HashMap<Integer, HashMap<Boolean, Integer>> reportYear = new HashMap<>();
        for (Yearly yearly : yearlyReport.yearlys) {
            if (!reportYear.containsKey(yearly.month)) {
                reportYear.put(yearly.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> amountYears = reportYear.get(yearly.month);
            amountYears.put(yearly.isExpense, amountYears.getOrDefault(yearly.isExpense, 0) + yearly.amount);
        }
        for (Integer month : reportYear.keySet()) {
            HashMap<Boolean, Integer> amountYearByYear = reportYear.get(month);
            HashMap<Boolean, Integer> amountYearByMonth = reportMonth.get(month);

            if (amountYearByMonth == null) {
                System.out.println("Отчет отсутствует за месяц номер: " + month);
                check = false;
                continue;
            }

            for (Boolean isExpense : amountYearByYear.keySet()) {
                int yearByYear = amountYearByYear.get(isExpense);
                int yearByMonth = amountYearByMonth.getOrDefault(isExpense, 0);
                if(yearByYear != yearByMonth) {
                    System.out.println("Есть несоответствие");
                    check = false;
                }
            }
        }
        return check;
    }
}
