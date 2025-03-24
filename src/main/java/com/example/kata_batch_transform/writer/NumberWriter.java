package com.example.kata_batch_transform.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NumberWriter implements ItemWriter<String> {

    private final String outputFile;

    public NumberWriter( String outputFile){
        this.outputFile = outputFile;
    }

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {

        // Ouvrir le fichier en mode "append" pour ajouter au lieu d'écraser
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            for (String c : chunk) {
                writer.write(c);
                writer.newLine();
                writer.flush();  // Force l'écriture dans le fichier
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture du fichier Output", e);
        }
    }
}
