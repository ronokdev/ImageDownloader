package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ImageDownloadServiceTest {

    private final String inputDir= "src/test/resources/input.txt";
    private final String outputDir = "src/test/resources/download/";

    private ImageDownloadService imageDownloadService;

    @BeforeEach
    void setUp() {
        imageDownloadService= new ImageDownloadService();

        File directory = new File(outputDir);

        if (directory.exists()) {
            File[] files = directory.listFiles();
            assert files != null;
            for (File file : files) {
                file.delete();
            }
        }
       assertEquals(Objects.requireNonNull(new File(outputDir).listFiles()).length,0);
    }

    @Test
    public void ShouldDownloadTwoImages() throws InterruptedException {
        String[] args = { "-i", inputDir , "-o", outputDir };
        imageDownloadService.createCommandLine(args);

        Thread.sleep(2000);

        assertEquals(Objects.requireNonNull(new File(outputDir).listFiles()).length,2);
    }

    @Test
    public void ShouldNotDownloadImages() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] args = { "-i", inputDir , "-r", outputDir };
        imageDownloadService.createCommandLine(args);

        // Get the captured stdout as a string
        String capturedOutput = outputStream.toString();

        // Assert the captured output
        String expectedOutput = "Error occurred while reading from CLI : Unrecognized option: -r\n"; // Replace with what you expect

        assertEquals(expectedOutput, capturedOutput);
        assertEquals(Objects.requireNonNull(new File(outputDir).listFiles()).length,0);
    }
}