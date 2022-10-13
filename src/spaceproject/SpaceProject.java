package spaceproject;
//Student name: Adrian Sanchez - EC1939656
//Edinburgh College
//Data Structures - Assessment on Stacks, Queues and ArrayLists
// Import necessary packages for the program to work.

import java.io.*;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.*;


public class SpaceProject {
    //Declare logfile and the file writer that will fill up the log with all the pod data information
    static File logFile;
    static FileWriter log;

    public static void main(String[] args) {

        //Initialize file reader and data containers for the processing corridor and the bays
        ECFile2022 ecf = new ECFile2022();
        Queue<Cargo> corridor = new LinkedList<>();
        ArrayList<Cargo> foodBay = new ArrayList<>();
        ArrayList<Cargo> technicalBay = new ArrayList<>();
        ArrayList<Cargo> personalBay = new ArrayList<>();
        ArrayList<Cargo> discardBay = new ArrayList<>();

        //Add a counter to keep track of the cargos and find the path for the folder where the data files are stored
        int counter = 1;
        String absolutePath = new File("").getAbsolutePath();
        File[] files = new File(absolutePath, "data").listFiles();

        //Initialize the file writer and the log file.
        try {
            logFile = new File(absolutePath + "/logs/" + Instant.now().getEpochSecond() + "-pod-data-log.txt");
        } catch (Exception e) {
            System.out.println("An error occurred creating the file.");
            e.printStackTrace();
        }
        try {
            log = new FileWriter(logFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Iterate through the files inside the data folder. Display the cargo number and the content of it
        for (File file : files) {
            writeLog("************************[Cargo " + counter + "]*************************");
            ArrayList<Stack<Cargo>> pods = new ArrayList<>();
            //Fill up the pods (Stacks) with cargo information
            try {
                pods = podFilling(ecf, file);

            } catch (java.lang.NumberFormatException e) {
                writeLog("The file " + file.getName() + " is unreadable. Please make sure the file is formatted according to policies.");
            }
            //While there are items inside the pods, keep emptying them into the processing corridor.
            if (!pods.isEmpty()) {
                //Declare arrays for the item's IDs to check if the item already exists on the bays later
                ArrayList<String> foodIdList = new ArrayList<>();
                ArrayList<String> technologyIdList = new ArrayList<>();
                ArrayList<String> personalIdList = new ArrayList<>();
                writeLog("\n" + currentTimestamp() + " : " + "Sending Pod content into processing corridor.");
                while (!pods.get(0).isEmpty()) {
                    corridor.add(pods.get(0).pop());
                }
                while (!pods.get(1).isEmpty()) {
                    corridor.add(pods.get(1).pop());
                }
                //Display task success in the log file
                writeLog(currentTimestamp() + " : " + "Processing corridor filled successfully.");
                writeLog("Corridor content:");
                writeLog(corridor.toString() + "\n");
                writeLog(currentTimestamp() + " : " + "Sending items into the bays");

                //While there are items in the corridor, keep emptying it into the different bays
                while (!corridor.isEmpty()) {
                    //Check from the ID whether the item belongs into food, personal or technical bay.
                    //If the item already exists on the bay, send to the discard list, otherwise add to the bay
                    String id = corridor.peek().getId();
                    if (id.charAt(0) == 'F') {
                        if (foodIdList.contains(id)) {
                            discardBay.add(corridor.poll());
                        } else {
                            foodBay.add(corridor.poll());
                            foodIdList.add(id);
                        }
                    } else if (id.charAt(0) == 'T') {
                        if (technologyIdList.contains(id)) {
                            discardBay.add(corridor.poll());
                        } else {
                            technicalBay.add(corridor.poll());
                            technologyIdList.add(id);
                        }
                    } else if (id.charAt(0) == 'P') {
                        if (personalIdList.contains(id)) {
                            discardBay.add(corridor.poll());
                        } else {
                            personalBay.add(corridor.poll());
                            personalIdList.add(id);
                        }
                    } else {
                        discardBay.add(corridor.poll());
                    }
                }
                //Display task success into the log file, and display the bay content in the file as well
                writeLog(currentTimestamp() + " : " + "Bays loaded successfully");
                if (foodBay.isEmpty()) {
                    writeLog("\n" + "Food Bay has no contents.");
                } else {
                    writeLog("\nFood Bay Content:");
                    writeLog(foodBay.toString());
                }
                if (technicalBay.isEmpty()) {
                    writeLog("\n" + "Technical Bay has no content");
                } else {
                    writeLog("\nTechnical Bay Content:");
                    writeLog(technicalBay.toString());
                }
                if (personalBay.isEmpty()) {
                    writeLog("\n" + "Personal Bay has no content.");
                } else {
                    writeLog("\nPersonal Bay Content:");
                    writeLog(personalBay.toString());
                }
                if (discardBay.isEmpty()) {
                    writeLog("\nDiscard/Duplicated Bay has no content");
                } else {
                    writeLog("\nDiscard/Duplicated Bay Content:");
                    writeLog(discardBay.toString());
                }

            }
            writeLog("");
            counter++;
        }
        //Close the file buffer and writing on it.
        try {
            log.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Function to write in the logfile, takes a string as a parameter
    public static void writeLog(String msg) {
        try {
            log.write(msg + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred writing the file");
            e.printStackTrace();
        }
    }

    //Function to fill the pods from the cargo Array. Takes the file reader initialized class and the data file as parameters
    public static ArrayList<Stack<Cargo>> podFilling(ECFile2022 ecf, File file) {
        //Declare Stacks to store the cargo items and weight variables to keep track of the cargo weight
        Stack<Cargo> pod1 = new Stack<>();
        Stack<Cargo> pod2 = new Stack<>();
        float pod1Weight = 0;
        float pod2Weight = 0;

        //Fill the cargo array with the information found inside the data file.
        Cargo[] cargo = ecf.loadData(file.getAbsolutePath());
        //Display success on the log file and display the information inside the cargo array
        writeLog(currentTimestamp() + " : " + "Cargos read successfully from file");
        writeLog(Arrays.toString(cargo) + "\n");
        writeLog(currentTimestamp() + " : " + "Filling pods with cargos contents");

        //Run through the items in the cargo list and fill the pods. If pod1 has more than 9 items, jump to fill pod2
        for (int i = 0; i < cargo.length; ) {
            for (Cargo supplies : cargo) {
                if (pod1.size() > 8) {
                    pod2Weight += supplies.getWeight();
                    pod2.push(supplies);
                    i++;
                } else {
                    pod1Weight += supplies.getWeight();
                    pod1.push(supplies);
                    i++;
                }
            }
        }

        //If any of the pods has more than 9 items or weighs more than 1600KG, display an error message on the log and interrupt the operation
        if (pod1.size() > 9 || pod2.size() > 9) {
            writeLog(currentTimestamp() + " : " + "There is more than 18 Items in this data list.Be aware that pods can just transport up to 9 items each, so please revise and reload the cargos again.");
            return new ArrayList<>();
        }
        if (pod1Weight > 1600 || pod2Weight > 1600) {
            writeLog(currentTimestamp() + " : " + "One or more cargo weight more than 1600kg, the operation can't continue. Please revise and reload the cargos again.");
            return new ArrayList<>();
        }
        //Display task success on log and display pods content, return the pods with its contents
        writeLog(currentTimestamp() + " : " + "Pods loaded successfully");
        writeLog("\n" + "Pod 1 Content:");
        writeLog(pod1 + "\n");
        writeLog("Pod 2 Content:");
        writeLog(pod2 + "\n");
        ArrayList<Stack<Cargo>> result = new ArrayList<>();
        result.add(pod1);
        result.add(pod2);
        return result;
    }

    //Function to display a timestamp into the log file.
    public static String currentTimestamp() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat f = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
        return f.format(c.getTime());
    }
}
