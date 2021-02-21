package edu.wctc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Country> countryList = new ArrayList<>();

    private static void controlBreak() {
        int count = 0;
        String currentContinent = countryList.get(0).getContinent();

        // print header for table
        System.out.printf("%-20s%-20s%n", "Continent", "Number of Countries");

        for (Country country : countryList) {

            if (!currentContinent.equals(country.getContinent())) {
                // this is the first country for a new continent

                // output count for previous continent
                System.out.printf("%-20s%-20d%n", currentContinent, count);

                // store the new continent
                currentContinent = country.getContinent();
                // reset the count
                count = 0;
            }

            // add this country to the count for current continent
            count++;
        }

        // one final printout for the last group
        System.out.printf("%-20s%-20d%n", currentContinent, count);
    }

    private static void writeCountryListObject() {
        // Write object list as binary file
        try (
                FileOutputStream fos = new FileOutputStream("countries.obj");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(countryList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readCountryListObject() {
        // Read the binary file back into list variable
        try (
                FileInputStream fis = new FileInputStream("countries.obj");
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            countryList = (List<Country>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void readCsvIntoList() {
        // Read CSV into object list

        try (Scanner fileInput = new Scanner(new File("countries.txt"))) {
            while (fileInput.hasNext()) {
                String line = fileInput.nextLine();
                String[] columns = line.split(";");

                Country country = new Country();
                countryList.add(country);

                // name, continent, capital, population, area
                country.setName(columns[0]);
                country.setContinent(columns[1]);
                country.setCapital(columns[2]);
                country.setPopulation(Integer.parseInt(columns[3]));
                country.setArea(Integer.parseInt(columns[4]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("PROGRAM START");

//        readCsvIntoList();

//        writeCountryListObject();

        readCountryListObject();

        controlBreak();

        System.out.println("Country list has " + countryList.size() + " countries");

        System.out.println("PROGRAM END");
    }
}
