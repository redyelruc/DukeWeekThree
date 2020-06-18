import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.File;

public class CSVTasks {
    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();

    }

    public String countryInfo(CSVParser parser, String country){
        String result = "NOT FOUND";
        for (CSVRecord record : parser){
            if (record.get("Country").equals(country)){
                result = country + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
            }
        }
        return result;
    }

    public String listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        String result = "NOT FOUND";
        StringBuilder countriesFound = new StringBuilder();
        for (CSVRecord record : parser){
            if (record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2)){
                countriesFound.append(record.get("Country")).append(" ");
            }
        }
        if (!countriesFound.toString().equals("")){
            result = countriesFound.toString();
        }
        return result;
    }

    public int numberOfExporters(CSVParser parser, String exportItem){
        int numOfExporters = 0;
        if (!exportItem.equals("")) {
            for (CSVRecord record : parser) {
                if (record.get("Exports").contains(exportItem.toLowerCase())) {
                    numOfExporters++;
                }
            }
        }
        return numOfExporters;
    }

    public String bigExporters(CSVParser parser, String amount){
        String result = "NONE";
        StringBuilder countries = new StringBuilder();
        if (!amount.equals("")) {
            for (CSVRecord record : parser) {
                if (record.get("Value (dollars)").length() > amount.length()) {
                    countries.append(record.get("Country")).append(" ");
                    countries.append(record.get("Value (dollars)")).append(" ");
                }
            }
        }
        if (!countries.toString().equals("")) {
            result = countries.toString();
        }
        return result;
    }

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null;
        for (CSVRecord currentRow : parser){
            if (coldestSoFar == null && Double.parseDouble(currentRow.get("TemperatureF")) != -9999){
                coldestSoFar = currentRow;
            } else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if (currentTemp < coldestTemp && currentTemp != -9999){
                    coldestSoFar = currentRow;
                }
            }
        }
        System.out.println(coldestSoFar.get("TemperatureF"));
        System.out.println(coldestSoFar.get("DateUTC"));
        return coldestSoFar;
    }

    public String fileWithColdestTemperature(){
        CSVRecord coldestSoFar = null;
        String fileName = "";
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if (coldestSoFar == null && Double.parseDouble(currentRow.get("TemperatureF")) != -9999){
                coldestSoFar = currentRow;
                fileName = f.getName();
            } else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if (currentTemp < coldestTemp && currentTemp != -9999){
                    coldestSoFar = currentRow;
                    fileName = f.getName();
                }
            }
        }
        FileResource fr = new FileResource("nc_weather/2014/" + fileName);
        System.out.println(fileName);
        CSVParser parser = fr.getCSVParser();
        coldestHourInFile(parser);
        return fileName;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar;
        lowestSoFar = null;
        int currentHumidity;
        int lowestHumidity;
        for (CSVRecord currentRow : parser){
            if (!(currentRow.get("Humidity").equals("N/A"))){
                if (lowestSoFar == null){
                    lowestSoFar = currentRow;
                } else{
                    currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
                    lowestHumidity = Integer.parseInt(lowestSoFar.get("Humidity"));
                    if (currentHumidity < lowestHumidity) {
                        lowestSoFar = currentRow;
                    }
                }
            }
        }
        System.out.print("Lowest Humidity was " + lowestSoFar.get("Humidity"));
        System.out.print(" at " + lowestSoFar.get("DateUTC"));
        return lowestSoFar;
    }

    public String lowestHumidityInManyFiles(){
        CSVRecord lowestSoFar = null;
        String fileName = "";
        DirectoryResource dr = new DirectoryResource();
        int currentHumidity;
        double lowestHumidity;
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            if (!(currentRow.get("Humidity").equals("N/A"))){
                if (lowestSoFar == null){
                    lowestSoFar = currentRow;
                    fileName = f.getName();
                } else {
                    currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
                    lowestHumidity = Integer.parseInt(lowestSoFar.get("Humidity"));
                    if (currentHumidity < lowestHumidity){
                        lowestSoFar = currentRow;
                        fileName = f.getName();
                    }
                }
            }

        }
        FileResource fr = new FileResource("nc_weather/2014/" + fileName);
        System.out.println(fileName);
        CSVParser parser = fr.getCSVParser();
        lowestHumidityInFile(parser);
        return fileName;
    }

    public double averageTemperatureInFile(CSVParser parser){
        double totalSoFar = 0.0;
        int numberOfRows = 0;
        for (CSVRecord currentRow : parser){
            if (Double.parseDouble(currentRow.get("TemperatureF")) > -9999){
                totalSoFar = totalSoFar + Double.parseDouble(currentRow.get("TemperatureF"));
                numberOfRows ++;
            }
        }
        double aveTemp = totalSoFar/numberOfRows;
        System.out.println("Average temperature in file is " + aveTemp);
        return aveTemp;
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double aveTemp;
        double totalSoFar = 0.0;
        int numberOfRows = 0;
        for (CSVRecord currentRow : parser){
            if (!currentRow.get("Humidity").equals("N/A") && Integer.parseInt(currentRow.get("Humidity")) > value){
                if (Double.parseDouble(currentRow.get("TemperatureF")) > -9999){
                    totalSoFar = totalSoFar + Double.parseDouble(currentRow.get("TemperatureF"));
                    numberOfRows ++;
                }
            }
        }
        if (numberOfRows == 0){
            aveTemp = 500;
            System.out.println("No temperatures with that humidity");
        } else {
            aveTemp = totalSoFar / numberOfRows;
            System.out.println("Average temperature in file is " + aveTemp);
        }
        return aveTemp;
    }
}
