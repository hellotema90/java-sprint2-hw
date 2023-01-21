import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MonthlyReport {
    public ArrayList<Monthly> monthlys = new java.util.ArrayList<>();

    public void loadFile(int month, String path) {
        String content = readFileContentsOrNull(path);
        if (content == null) {
            System.out.println();
            return;
        }
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

    public String getTopItem(int month, boolean isExp) {
        LinkedHashMap<String, Integer> topItemByMonth = new LinkedHashMap<>();
        for (Monthly monthly : monthlys) {
            if (monthly.month == month) {
                if (monthly.isExpense == isExp) {
                    topItemByMonth.put(monthly.itemName, (monthly.quantity * monthly.sumOfOne));
                }
            }
        }
        String topItemName = null;
        for (String itemName : topItemByMonth.keySet()) {
            if (topItemName == null) {
                topItemName = itemName;
                continue;
            }
            if (topItemByMonth.get(topItemName) < topItemByMonth.get(itemName)) {
                topItemName = itemName;
            }
        }
        return topItemName;
    }

    public int getMaxExpense(int month, boolean isExp) {
        int max = 0;
        for (Monthly monthly : monthlys) {
            if (monthly.month == month) {
                if (monthly.isExpense == isExp) {
                    if ((monthly.quantity * monthly.sumOfOne) > max) {
                        max = monthly.quantity * monthly.sumOfOne;
                    }
                }
            }
        }
        return max;
    }

    public void printMonthReport() {
        if (monthlys.isEmpty()) {
            System.out.println("Данных нет");
        } else {
            for (int i = 1; i <= 3; i++) {

                System.out.println("Месяц номер: " + i);
                System.out.println("Самый прибыльный товар: " + getTopItem(i, false) + ", " + getMaxExpense(i, false));
                System.out.println("Самая большая трата: " + getTopItem(i, true) + ", " + getMaxExpense(i, true));
                System.out.println();


            }
        }
    }
}