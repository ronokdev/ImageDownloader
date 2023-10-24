package org.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.example.service.NioImageDownloader.ImageDownload;
import org.example.service.NioImageDownloader.NioDownloader;

public class ImageDownloadService {

    ImageDownload imageDownload = new NioDownloader();

    public void createCommandLine(String[] args){
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
                download(x,outputDir);

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

    private String generateFileName(String imageUrl) {
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
    private void download(String urlStr, String outputDirectory) {
        String fileName = generateFileName(urlStr);
        String outputPath = outputDirectory + File.separator + fileName;
        System.out.println("Downloaded : " + outputPath);
        imageDownload.downloadImage(urlStr,outputPath);
    }

    public static boolean isValidURL(String url) {
        // Regular expression for a valid URL
        String urlRegex = "^(https?|ftp)://[A-Za-z0-9.-]+(:[0-9]+)?(/[A-Za-z0-9%.-]+)*$";

        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }
}
