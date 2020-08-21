import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExchangeOffice {
    Map<Currency, Double> currencies = new HashMap<>();

    public ExchangeOffice() {
        try {
            File database = new File("ExchangeOffice.txt");
            Scanner myReader = new Scanner(database);
            String line = myReader.nextLine();
            String[] array = line.split(" ");
            for (int i = 0; i < array.length; i += 2)
                currencies.put(Currency.valueOf(array[i]), Double.parseDouble(array[i + 1]));
            myReader.close();
            System.out.println("Data was succesfully read from file");
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("File doesn't exist.");
        }
    }

    //first load of money and write to file
    public ExchangeOffice(Double value) {
        try {
            File database = new File("ExchangeOffice.txt");

            if (database.createNewFile()) {
                System.out.println("File created: \"" + database.getName() + "\"");

                for (Currency i : Currency.values())
                    currencies.put(i, value);

                FileWriter myWriter = new FileWriter("ExchangeOffice.txt");
                currencies.forEach((k, v) -> {
                    try {
                        myWriter.write((k.getName()) + " " + v + " ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                myWriter.close();
                System.out.println("DataBase with initial currencies was successfully created.");

            } else {
                System.out.println("File \"" + database.getName() + "\" already exists.");

                for (Currency i : Currency.values())
                    currencies.put(i, value);
                FileWriter myWriter = new FileWriter("ExchangeOffice.txt");
                currencies.forEach((k, v) -> {
                    try {
                        myWriter.write((k.getName()) + " " + v + " ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                myWriter.close();
                System.out.println("File was successfully overwritten.");

            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    void showFunds() {
        if (currencies.isEmpty()) System.out.println("You don't have money yet");
        else currencies.forEach((k, v) -> System.out.print((k.getName() + ":" + format(v) + "   ")));

    }


    static String format(double d) {
        BigDecimal format = new BigDecimal(d);
        return format.setScale(2, RoundingMode.HALF_EVEN).toString();
    }


    void exchange(double amount, Currency from, Currency to) {
        double realAmount = amount * 0.8;

        try {
            File database = new File("ExchangeOffice.txt");
            Scanner myReader = new Scanner(database);
            String line = myReader.nextLine();
            String[] array = line.split(" ");
            for (int i = 0; i < array.length; i += 2)
                currencies.put(Currency.valueOf(array[i]), Double.parseDouble(array[i + 1]));
            myReader.close();

            if (amount > currencies.get(from)) {
                System.out.println("No so much money " + from + "!");
                System.out.println();
            } else {
                currencies.put(from, currencies.get(from) + amount);
                currencies.put(to, currencies.get(to) - realAmount * CurrencyConverter.convert(from, to));
            }

            FileWriter myWriter = new FileWriter("ExchangeOffice.txt");
            currencies.forEach((k, v) -> {
                try {
                    myWriter.write((k.getName()) + " " + v + " ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            myWriter.close();
            System.out.println("Successfully wrote to the file.");


        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("File doesn't exist.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }


    }
}
