package com.vivekt.ex;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomMp3Selector {

    public static void main(String[] args) {
        // Change these paths as per your environment
        Path rootFolder = Paths.get("C:\\ent\\backup\\songs");     // root folder containing mp3s
        Path tempFolder = Paths.get("C:\\ent\\backup\\list");   // destination folder

        int filesToCopy = 10;

        try {
            // Ensure temp folder exists
            if (!Files.exists(tempFolder)) {
                Files.createDirectories(tempFolder);
            }

            // Collect all .mp3 files (recursive)
            List<Path> mp3Files;
            try (Stream<Path> walk = Files.walk(rootFolder)) {
                mp3Files = walk.filter(Files::isRegularFile)
                               .filter(p -> p.toString().toLowerCase().endsWith(".mp3"))
                               .collect(Collectors.toList());
            }

            if (mp3Files.isEmpty()) {
                System.out.println("No MP3 files found in: " + rootFolder);
                return;
            }

            // Shuffle and pick N random files
            Collections.shuffle(mp3Files);
            List<Path> selectedFiles = mp3Files.stream()
                                               .limit(filesToCopy)
                                               .collect(Collectors.toList());

            // Copy files
            for (Path file : selectedFiles) {
                Path targetPath = tempFolder.resolve(file.getFileName());
                Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied: " + file + " -> " + targetPath);
            }

            System.out.println("\n✅ Successfully copied " + selectedFiles.size() + " MP3 files to " + tempFolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
