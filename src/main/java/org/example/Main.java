package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        createCommandLine(args);
    }

    private static void createCommandLine(String[] args){
        Options options = new Options();
        options.addOption("i", "input", true, "Input file containing image URLs");
        options.addOption("o", "output", true, "Output directory for downloaded images");

        // Create the command line parser
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            // Retrieve the values of the specified options
            String inputFile = cmd.getOptionValue("i");
            List<String> strings = readURLsFromFile(inputFile);
            String outputDir = cmd.getOptionValue("o");

            for (var x:strings)
                downloadUsingNIO(x,outputDir);


            // Now you can use these values in the rest of your program
            // For example, pass them to your image downloader logic.
        } catch (ParseException e) {
            System.err.println("Error: " + e.getMessage());
            // Handle the error or display usage information
            printUsage(options);
        }
    }


    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ImageDownloaderCLI", options);
    }

    public static List<String> readURLsFromFile(String filePath) {
        List<String> urlList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] urlsInLine = line.split("\\s+");
                Arrays.stream(urlsInLine).forEach(x -> {
                    if (isValidURL(x))
                        Collections.addAll(urlList, x);
                    else {
                        System.out.println("Invalid URL : "+ x);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlList;
    }

    private static String generateFileName(String imageUrl) {
        // Extract the filename from the URL

        String regex = ".*\\.(jpg|jpeg|png|gif|bmp|tiff|webp|svg|ico)$";
        String substring = "";

        int lastIndexOfSlash = imageUrl.lastIndexOf('/');
        if (lastIndexOfSlash >= 0)
            substring = imageUrl.substring(lastIndexOfSlash + 1);

        if(substring.toLowerCase().matches(regex))
            return substring;
        return "image_" + System.currentTimeMillis() + ".jpg";
    }

    // More about NIO -> https://www.baeldung.com/java-download-file#using-nio
    private static void downloadUsingNIO(String urlStr, String outputDirectory) {
        try{
            URL url = new URL(urlStr);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());

            String fileName = generateFileName(urlStr);
            String outputPath = outputDirectory + File.separator + fileName;

            FileOutputStream fos = new FileOutputStream(outputPath);

            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            System.out.println("Downloaded : " + outputPath);
        }
        catch (Exception e){
            System.out.println("Error occurred while downloading "+ e);
        }

    }

    public static boolean isValidURL(String url) {
        // Regular expression for a valid URL
        String urlRegex = "^(https?|ftp)://[A-Za-z0-9.-]+(:[0-9]+)?(/[A-Za-z0-9%.-]+)*$";

        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }
}