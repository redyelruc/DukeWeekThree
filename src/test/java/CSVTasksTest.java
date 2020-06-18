import edu.duke.*;
import org.apache.commons.csv.*;
import org.junit.*;

public class CSVTasksTest {

    CSVTasks csvtask;
    FileResource fr = new FileResource(".idea/exports_small.csv");
    CSVParser parser = fr.getCSVParser();

    @Before
    public void setup() {
        csvtask = new CSVTasks();

    }

    @Test
    public void testCountryInfoEmptyStringReturnsNOTFOUND(){
        String country = "";
        String expected = "NOT FOUND";
        Assert.assertEquals(expected, csvtask.countryInfo(parser, country));
    }

    @Test
    public void testCountryInfoIrelandReturnsNOTFOUND(){
        String country = "Nauru";
        String expected = "NOT FOUND";
        Assert.assertEquals(expected, csvtask.countryInfo(parser, country));
    }

    @Test
    public void testCountryInfoMalawiReturnsExpected(){
        String country = "Malawi";
        String expected = "Malawi: tea, sugar, cotton, coffee: $1,332,000,000";
        Assert.assertEquals(expected, csvtask.countryInfo(parser, country));
    }

    @Test
    public void testListExportersTwoProductsNoCountriesWithBothReturnsNOTFOUND(){
        String exportItem1 = "tobacco";
        String exportItem2 = "gold";
        String expected = "NOT FOUND";
        Assert.assertEquals(expected, csvtask.listExportersTwoProducts(parser, exportItem1, exportItem2));
    }

    @Test
    public void testListExportersTwoProductsOneCountryWithBothReturnsMadagascar(){
        String exportItem1 = "coffee";
        String exportItem2 = "vanilla";
        String expected = "Madagascar ";
        Assert.assertEquals(expected, csvtask.listExportersTwoProducts(parser, exportItem1, exportItem2));
    }

    @Test
    public void testListExportersTwoProductsTwoCountriesWithBothReturnsNamibiaSouthAfrica(){
        String exportItem1 = "gold";
        String exportItem2 = "diamonds";
        String expected = "Namibia South Africa ";
        Assert.assertEquals(expected, csvtask.listExportersTwoProducts(parser, exportItem1, exportItem2));
    }

    @Test
    public void testNumberOfExportersEmptyStringReturns0(){
        String exportItem = "";
        int expected = 0;
        Assert.assertEquals(expected, csvtask.numberOfExporters(parser, exportItem));
    }

    @Test
    public void testNumberOfExportersCocaineReturns0(){
        String exportItem = "cocaine";
        int expected = 0;
        Assert.assertEquals(expected, csvtask.numberOfExporters(parser, exportItem));
    }

    @Test
    public void testNumberOfExportersGoldReturns3(){
        String exportItem = "gold";
        int expected = 3;
        Assert.assertEquals(expected, csvtask.numberOfExporters(parser, exportItem));
    }

    @Test
    public void testNumberOfExportersGOLDReturns3(){
        String exportItem = "GOLD";
        int expected = 3;
        Assert.assertEquals(expected, csvtask.numberOfExporters(parser, exportItem));
    }

    @Test
    public void testBigExportersReturnsUSA(){
        String amount = "$999,999,999,999";
        String expected = "Germany $1,547,000,000,000 United States $1,610,000,000,000 ";
        Assert.assertEquals(expected, csvtask.bigExporters(parser, amount));
    }

    @Test
    public void testColdestHourInFile20120101Returns35point1(){
        String expected = "35.1";
        fr = new FileResource("nc_weather/2012/weather-2012-01-01.csv");
        parser = fr.getCSVParser();
        Assert.assertEquals(expected, csvtask.coldestHourInFile(parser).get("TemperatureF"));
    }

    @Test
    public void testFileWithColdestTemperatureJan1to32014Returns20140103(){
        String expected = "weather-2014-01-03.csv";
        Assert.assertEquals(expected, csvtask.fileWithColdestTemperature());
    }

    @Test
    public void testLowestHumidityInFile201401201Returns24(){
        String expected = "24";
        fr = new FileResource("nc_weather/2014/weather-2014-01-20.csv");
        parser = fr.getCSVParser();
        Assert.assertEquals(expected, csvtask.lowestHumidityInFile(parser).get("Humidity"));
    }

    @Test
    public void testLowestHumidityInManyFilesJan19to202014Returns20140120(){
        String expected = "weather-2014-01-20.csv";
        Assert.assertEquals(expected, csvtask.lowestHumidityInManyFiles());
    }

    @Test
    public void testAverageTemperatureInFile20Jan2014Returns44point933333(){
        double expected = 44.933333333333;
        fr = new FileResource("nc_weather/2014/weather-2014-01-20.csv");
        parser = fr.getCSVParser();
        Assert.assertEquals(expected, csvtask.averageTemperatureInFile(parser), 0.000001);
    }

    @Test
    public void testAverageTemperatureWithHighHumidityInFile20140120value80Returns500(){
        double expected = 500;
        int value = 80;
        fr = new FileResource("nc_weather/2013/weather-2014-03-30.csv");
        parser = fr.getCSVParser();
        Assert.assertEquals(expected, csvtask.averageTemperatureWithHighHumidityInFile(parser, value), 0.0000001);
    }

    @Test
    public void testAverageTemperatureWithHighHumidityInFile20140320value80Returns500(){
        double expected = 41.78666666666;
        int value = 80;
        fr = new FileResource("nc_weather/2014/weather-2014-03-20.csv");
        parser = fr.getCSVParser();
        Assert.assertEquals(expected, csvtask.averageTemperatureWithHighHumidityInFile(parser, value), 0.0000001);
    }
}
