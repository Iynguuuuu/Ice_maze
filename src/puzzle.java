// w1990839 | 20222388 | Iynkaran Pavanantham

import java.io.File;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;


public class puzzle {

    private final static Scanner inputReader = new Scanner(System.in);
    private static parser parsedInputFile;
    private static boolean shouldSkipLoad;
    private static File inputFile;
    private static String inputFileName;

    public static void main(String[] args)
            throws Exception {
                System.out.println("puzzle");

                while (true) {
                    shouldSkipLoad = false;

                    System.out.println("Enter i to get new input");
                    System.out.println("Enter x to Exit the program");

                    System.out.print("\nOption: ");
                    String option = inputReader.nextLine();

                    switch (option) {
                        case "0":
                            System.exit(0);
                            break;
                        case "i":
                            loadNewInput();
                            break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                }
    }

    private static void calculateDistance(){
        Instant startTime = null;
        Instant endTime = null;
        Duration timeElapsed = null;
        while (true) {
            System.out.println("Ice blocks Height " + parsedInputFile.getLines().size());
            System.out.println("\n");
            System.out.println("Enter 1 to print the path");
            System.out.println("Enter x return to menu again.");

            System.out.print("\nOption: ");

            String option = inputReader.nextLine();

            int[][] n = parsedInputFile.getPuzzle();
            int[] s = parsedInputFile.getStartPoint();
            int[] t = parsedInputFile.getEndPoint();

            icesliderpuzzle solver = new icesliderpuzzle();

            switch (option) {
                case "1":
                    if (startTime == null) {
                        startTime = Instant.now();
                    }
                    System.out.println("\nfinding the shortest path\n");

                    System.out.println(solver.shortestDistance(n, s, t));

                    if (endTime == null) {
                        endTime = Instant.now();
                        timeElapsed = Duration.between(startTime, endTime);
                    }
                    System.out.print("\nTime elapsed: ");

                    if (timeElapsed.toMillis() > 1000){
                        System.out.print(timeElapsed.toSeconds() + " seconds\n");
                        return;
                    }

                    System.out.print(timeElapsed.toMillis() + " milliseconds\n");
                    break;
                case "x":
                    System.out.println("\n");
                    inputFileName = null;
                    parsedInputFile = null;
                    shouldSkipLoad = true;
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }

    }

    private static void loadNewInput()
    {
        while(true)
        {
            if (shouldSkipLoad)
                return;

            System.out.println("");
            System.out.println("Enter g to enter graph name to select.");
            System.out.println("Enter m to go back to main menu");

            System.out.print("\nchoice: ");
            String option = inputReader.nextLine();
            boolean graphLoadError = false;

            switch (option)
            {
                case "1":
                    System.out.println("Choose the text pattern file");
                    System.out.println("Please check the taskbar for a new icon");
                    try {
                        parser fileParser = new parser();
                        fileParser.readFile();
                        fileParser.loadLines();
                        fileParser.loadValues();
                        if (!fileParser.isFileRead()) {
                            throw new Exception("File not loaded");
                        }
                        inputFileName = fileParser.getFileName();
                        inputFile = fileParser.getFile();
                        parsedInputFile = fileParser;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("\nError reading pattern file, please try again\n");
                        graphLoadError = true;
                    }
                    if (!graphLoadError) {
                        shouldSkipLoad = true;
                        calculateDistance();
                    } else {
                        continue;
                    }
                    break;
                case "g":
                    System.out.println("place the pattern text file in 'patterns' folder and enter the text file name in the console");
                    try {
                        String userInputFileName;
                        do {
                            System.out.println("File should be with '.txt' file extension");
                            System.out.print("Enter file name:  ");
                            userInputFileName = inputReader.nextLine();
                        } while ((!userInputFileName.endsWith(".txt")));

                        parser fileParser = new parser();
                        fileParser.readFile("src/patterns/" + userInputFileName);
                        fileParser.loadLines();
                        fileParser.loadValues();
                        if (!fileParser.isFileRead()) {
                            throw new Exception("File not loaded");
                        }
                        inputFileName = fileParser.getFileName();
                        inputFile = fileParser.getFile();
                        parsedInputFile = fileParser;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("\nError reading pattern file, please try again\n");
                        graphLoadError = true;
                    }

                    if (!graphLoadError) {
                        shouldSkipLoad = true;
                        calculateDistance();
                    } else {
                        continue;
                    }
                    break;
                case "m":
                    return;
                default:
                    System.out.println("invalid choice");
                    break;
            }
        }
    }
}

