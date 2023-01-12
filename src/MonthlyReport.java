import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MonthlyReport {
    public ArrayList<Monthly> monthlys = new java.util.ArrayList<>();

    public void loadFile(int month, String path) {
        String content = readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i]; //item_name,is_expense,quantity,sum_of_one
            String[] parts = line.split(",");
            String itemName = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int sumOfOne = Integer.parseInt(parts[3]);
            Monthly monthly = new Monthly(itemName, isExpense, quantity, sumOfOne, month);
            monthlys.add(monthly);
        }
    }

    public String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return null;
        }
    }

    public int sumExpense() {
        int sum = 0;
        for (Monthly monthly : monthlys) {
                if(monthly.isExpense){
                    sum += monthly.quantity * monthly.sumOfOne ;
                }
            }
        return sum;
    }

    public int sumIncome() {
        int sum = 0;
        for (Monthly monthly : monthlys) {
            if(!monthly.isExpense){
                sum += monthly.quantity * monthly.sumOfOne ;
            }
        }
        return sum;
    }

    public int maxIncome() {
        int max = 0;
        for (Monthly monthly : monthlys) {
            if (!monthly.isExpense) {
                if ((monthly.quantity * monthly.sumOfOne) > max) {
                    max = monthly.quantity * monthly.sumOfOne;
                }
            }
        }
        return max;
    }

    public String maxIncomeItem() {
        int max = 0;
        String item = null;
        for (Monthly monthly : monthlys) {
            if (!monthly.isExpense) {
                if ((monthly.quantity * monthly.sumOfOne) > max) {
                    max = monthly.quantity * monthly.sumOfOne;
                    item = monthly.itemName;
                }
            }
        }
        return item;
    }

    public int maxExpense() {
        int max = 0;
        for (Monthly monthly : monthlys) {
            if (monthly.isExpense) {
                if ((monthly.quantity * monthly.sumOfOne) > max) {
                    max = monthly.quantity * monthly.sumOfOne;
                }
            }
        }
        return max;
    }

    public String maxExpenseItem() {
        int max = 0;
        String item = null;
        for (Monthly monthly : monthlys) {
            if (monthly.isExpense) {
                if ((monthly.quantity * monthly.sumOfOne) > max) {
                    max = monthly.quantity * monthly.sumOfOne;
                    item = monthly.itemName;
                }
            }
        }
        return item;
    }

    public void printMonthReport() {
        for (int i = 1; i <= 3; i++) {

                System.out.println("Месяц номер: " + i);
                System.out.println("Самый прибыльный товар: " + maxIncomeItem() + ", " + maxIncome());
                System.out.println("Самая большая трата: " + maxExpenseItem() + ", " + maxExpense());
                System.out.println();
            }
        }





}