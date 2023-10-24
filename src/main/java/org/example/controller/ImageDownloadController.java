package org.example.controller;

import org.example.service.ImageDownloadService;

public class ImageDownloadController {

    public void startImageDownload(String[] args){
        ImageDownloadService imageDownloadService = new ImageDownloadService();
        imageDownloadService.createCommandLine(args);
    }
}
