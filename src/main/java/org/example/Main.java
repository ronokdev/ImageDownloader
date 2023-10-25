package org.example;

import org.example.controller.ImageDownloadController;

public class Main {
    public static void main(String[] args) {
        ImageDownloadController imageDownloadController = new ImageDownloadController();
        imageDownloadController.startImageDownload(args);
    }
}