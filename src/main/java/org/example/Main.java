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
import org.example.controller.ImageDownloadController;

public class Main {
    public static void main(String[] args) {
        ImageDownloadController imageDownloadController = new ImageDownloadController();
        imageDownloadController.startImageDownload(args);
    }
}