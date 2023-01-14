import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class YearlyReport {
    public ArrayList<Yearly> yearlys = new ArrayList<>();

    public void loadFile (String path) {
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i]; // "month,amount,is_expense"
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            Yearly yearly = new Yearly(month, amount, isExpense);
            yearlys.add(yearly);
        }
    }


    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
            return null;
        }
    }

    public int sumExpenseInMonth(int month){
        int sum = 0;
        for (Yearly yearly : yearlys) {
            if(yearly.month == (month + 1)){
                if(yearly.isExpense){
                    sum += yearly.amount;
                }
            }
        }
        return sum;
    }

    public int sumIncomeInMonth(int month) {
        int sum = 0;
        for (Yearly yearly : yearlys) {
            if(yearly.month == (month + 1)){
                if(!yearly.isExpense){
                    sum += yearly.amount;
                }
            }
        }
        return sum;
    }

    public double averageExpense(){
        double sum = 0;
        int numMonth = 1;
        for (Yearly yearly : yearlys) {
            if(yearly.isExpense) {
                sum += yearly.amount;
                numMonth++;
                }
            }
        return sum/numMonth;
    }
    public double averageIncome(){
        double sum = 0;
        int numMonth = 1;
        for (Yearly yearly : yearlys) {
            if(!yearly.isExpense) {
                sum += yearly.amount;
                numMonth++;
            }
        }
        return sum/numMonth;
    }
    public void printYearReport() {
        System.out.println("Рассматриваемый год: " + 2021);
            for (int i = 1; i <= 3; i++) {
                System.out.println("Прибыль по месяцу " + i + " : " + (sumIncomeInMonth(i) - sumExpenseInMonth(i)));
            }
                System.out.println("Средний расход за все месяцы в году: " + averageExpense());
                System.out.println("Средний доход за все месяцы в году: " + averageIncome());

    }

    }
