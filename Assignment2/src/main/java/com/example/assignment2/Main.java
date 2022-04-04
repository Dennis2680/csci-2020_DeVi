package com.example.assignment2;

// all my imports
import javafx.application.Application;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Scene;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Part 1
        try {
            //makes a new instance of a file/doc
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

            Document newDoc = domBuilder.newDocument();

            //root element for other element and their receptive values to join with
            Element rootElement = newDoc.createElement("converted_airline_safety");
            newDoc.appendChild(rootElement);

            //load the csv file
            BufferedReader csvReader = new BufferedReader(new FileReader("src\\main\\java\\com\\example\\assignment2\\airline_safety.csv"));

            //init variables
            int fieldCount;
            String[] titles = null;
            String[] values = null;


            //Takes the first line split by comma and sets them as labels
            String currentLine = csvReader.readLine();
            if (currentLine != null) {
                titles = currentLine.split(",", -1);
            }

            //Resizes the array to fit the extra column
            String[] newTitles = Arrays.copyOf(titles, (titles.length + 1));
            newTitles[titles.length] = "total_number_of_incidents_85_14";

            // read the lines one at a time and part the data with the column
            // this is done by reading each line then splitting and pairing the data to the designated colum
            // by appending the element to the root element
            int i = 0;
            while ((currentLine = csvReader.readLine()) != null) {
                values = currentLine.split(",", -1);
                Element rowElement = newDoc.createElement("record-" + i);
                try {
                    for (fieldCount = 0; fieldCount < values.length; fieldCount++) {
                        String currentValue = values[fieldCount];
                        Element currentElement = newDoc.createElement(newTitles[fieldCount]);
                        currentElement.appendChild(newDoc.createTextNode(currentValue));
                        rowElement.appendChild(currentElement);
                    }
                    int temp = (Integer.parseInt(values[2])) + (Integer.parseInt(values[5]));
                    String currentValue = String.valueOf(temp);
                    Element currentElement = newDoc.createElement(newTitles[values.length]);
                    currentElement.appendChild(newDoc.createTextNode(currentValue));
                    rowElement.appendChild(currentElement);
                } catch (Exception exp) {
                }
                rootElement.appendChild(rowElement);
                i++;
            }

            // used to format xml file
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer formatter = tranFactory.newTransformer();
            formatter.setOutputProperty(OutputKeys.INDENT, "yes");
            formatter.setOutputProperty(OutputKeys.METHOD, "xml");
            formatter.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // used to generate xml file with the format from above
            Source src = new DOMSource(newDoc);
            Result result = new StreamResult(new File("src\\main\\java\\com\\example\\assignment2\\converted_airline_safety.xml"));
            formatter.transform(src, result);

            csvReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        //Part 2
        try {
            //makes a new instance of a file/doc
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();

            //loads csv file
            BufferedReader csvReader = new BufferedReader(new FileReader("src\\main\\java\\com\\example\\assignment2\\airline_safety.csv"));

            //init variables
            String[] temp = null;
            String xmlData = "";
            ArrayList<Double> col1 = new ArrayList<>();
            ArrayList<Double> col2 = new ArrayList<>();
            ArrayList<Double> col3 = new ArrayList<>();
            ArrayList<Double> col4 = new ArrayList<>();
            ArrayList<Double> col5 = new ArrayList<>();
            ArrayList<Double> col6 = new ArrayList<>();
            ArrayList<Double> col7 = new ArrayList<>();
            ArrayList<String> min = new ArrayList<>();
            ArrayList<String> max = new ArrayList<>();
            ArrayList<String> avg = new ArrayList<>();
            String[] values;
            String[] labels = {"Min", "Max", "Average","Name"};
            String[] labelsForAvgIncidents = {"avg_85_99","avg_00_14"};
            double total, counter;

            // gets the titles of the columns
            String line = csvReader.readLine();
            if (line != null) {
                temp = line.split(",", -1);
            }

            // starts at 1 to not include airline name and proceeds to isolate
            // each value and assigns it to the designated colum
            // while at the same time finding the average of each column
            for (int i = 1; i < temp.length; i++) {
                counter = 0;
                total = 0;
                while ((line = csvReader.readLine()) != null) {
                    values = line.split(",", -1);
                    if (i == 1){
                        col1.add(Double.parseDouble(values[i]));
                    }
                    else if (i == 2){
                        col2.add(Double.parseDouble(values[i]));
                    }
                    else if (i == 3){
                        col3.add(Double.parseDouble(values[i]));
                    }
                    else if (i == 4){
                        col4.add(Double.parseDouble(values[i]));
                    }
                    else if (i == 5){
                        col5.add(Double.parseDouble(values[i]));
                    }
                    else if (i == 6){
                        col6.add(Double.parseDouble(values[i]));
                    } else {
                        col7.add(Double.parseDouble(values[i]));
                    }
                    total += Double.parseDouble(values[i]);

                    counter++;
                }
                avg.add(String.valueOf(total / counter));
                // used to reset the csv position because
                // seek, mark and reset did not want to work
                csvReader.close();
                csvReader = new BufferedReader(new FileReader("src\\main\\java\\com\\example\\assignment2\\airline_safety.csv"));
                line = csvReader.readLine();
            }

            // finds the min of the column with the method getMin
            // coded below
            min.add(getMin(col1));
            min.add(getMin(col2));
            min.add(getMin(col3));
            min.add(getMin(col4));
            min.add(getMin(col5));
            min.add(getMin(col6));
            min.add(getMin(col7));

            // finds the max of the column with the method getMin
            // coded below
            max.add(getMax(col1));
            max.add(getMax(col2));
            max.add(getMax(col3));
            max.add(getMax(col4));
            max.add(getMax(col5));
            max.add(getMax(col6));
            max.add(getMax(col7));

            // used to format the xml
            xmlData += "<?xml version=\"1.0\"?>\n" + "<Summary>\n";

            for (int j = 1; j < temp.length; j++) {
                xmlData += "   <Stat>\n";
                xmlData += "      <" + labels[labels.length -1] + "> " + temp[j] + " </" + labels[labels.length - 1] + ">\n";
                for (int i = 0; i < labels.length - 1; i++) {
                    if (i == 0) {
                        xmlData += "      <" + labels[i] + "> " + min.get(j - 1) + " </" + labels[i] + ">\n";
                    } else if (i == 1) {
                        xmlData += "      <" + labels[i] + "> " + max.get(j - 1) + " </" + labels[i] + ">\n";
                    } else {
                        xmlData += "      <" + labels[i] + "> " + avg.get(j - 1) + " </" + labels[i] + ">\n";
                    }
                }
                xmlData += "   </Stat>\n";
            }

            for(int j = 0; j < labelsForAvgIncidents.length; j++){
                xmlData += "   <Stat>\n";
                xmlData += "      <" + labels[labels.length - 1] + "> " + labelsForAvgIncidents[j] + " </" + labels[labels.length - 1] + ">\n";
                for(int i = 0; i < labels.length - 1; i++){
                    if (i == 0 || i == 1) {
                        xmlData += "      <" + labels[i] + "> " + " </" + labels[i] + ">\n";
                    } else {
                        if (j == 0) {
                            xmlData += "      <" + labels[i] + "> " + avg.get(j + 2) + " </" + labels[i] + ">\n";
                        } else {
                            xmlData += "      <" + labels[i] + "> " + avg.get(j + 4) + " </" + labels[i] + ">\n";
                        }
                    }
                }
                xmlData += "   </Stat>\n";
            }

            xmlData += "</Summary>";
            csvReader.close();

            try {
                // converts the string into the xml file
                Document doc = domBuilder.parse(new InputSource(new StringReader(xmlData)));

                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();

                // generates xml
                Source src = new DOMSource(doc);
                Result result = new StreamResult(new File("src\\main\\java\\com\\example\\assignment2\\airline_summary_statistics.xml"));
                aTransformer.transform(src, result);


            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Part 3
        // init variables
        ArrayList<String> airlineName = new ArrayList<>();
        ArrayList<Double> fatal_accidents_85_99 = new ArrayList<>();
        ArrayList<Double> fatal_accidents_00_14 = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        String [] values;
        String [] temp = null;

        // loads csv file
        BufferedReader csvReader = new BufferedReader(new FileReader("src\\main\\java\\com\\example\\assignment2\\airline_safety.csv"));

        // get titles
        String line = csvReader.readLine();
        if (line != null) {
            temp = line.split(",", -1);
        }

        // take the titles that I need
        // and places them to labels string array
        labels.add(temp[0]);
        labels.add(temp[2]);
        labels.add(temp[5]);

        // used to isolate the column and the data of said columns
        for (int i = 0; i < 3; i++) {
            while ((line = csvReader.readLine()) != null) {
                values = line.split(",", -1);
                if (i == 0) {
                    airlineName.add(values[0]);
                } else if (i == 1) {
                    fatal_accidents_85_99.add(Double.parseDouble(values[2]));
                } else {
                    fatal_accidents_00_14.add(Double.parseDouble(values[5]));
                }
            }
            csvReader.close();
            // used to reset the csv position because
            // seek, mark and reset did not want to work
            csvReader = new BufferedReader(new FileReader("src\\main\\java\\com\\example\\assignment2\\airline_safety.csv"));
            line = csvReader.readLine();
        }

        // This is to generate the graph
        stage.setTitle("Assignment 2 Part 3");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number of accidents from 1985 to 2014");
        xAxis.setLabel("Airline");
        yAxis.setLabel("Value");

        // used to insert graph data for the first bar
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(labels.get(1));
        for(int i = 0; i < airlineName.size(); i++) {
            series1.getData().add(new XYChart.Data(airlineName.get(i), fatal_accidents_85_99.get(i)));
        }

        // used to insert graph data for the second bar
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(labels.get(2));
        for(int i = 0; i < airlineName.size(); i++) {
            series2.getData().add(new XYChart.Data(airlineName.get(i), fatal_accidents_00_14.get(i)));
        }

        // set the size of the window and displays the graph
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();

    }

    //fines the max value of a given array list with the type of double
    //as it parameter
    public String getMax(ArrayList<Double> arr){
        double temp = arr.get(0);

        for(int i = 1; i < arr.size(); i++){
            if(temp < arr.get(i)){
                temp = arr.get(i);
            }
        }
        return String.valueOf(temp);
    }

    //fines the min value of a given array list with the type of double
    //as it parameter
    public String getMin(ArrayList<Double> arr){
        double temp = arr.get(0);

        for(int i = 1; i < arr.size(); i++){
            if(temp > arr.get(i)){
                temp = arr.get(i);
            }
        }
        return String.valueOf(temp);
    }

    public static void main(String[] args) {
        launch();
    }
}