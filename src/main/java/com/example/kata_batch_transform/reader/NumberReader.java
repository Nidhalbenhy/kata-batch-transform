package com.example.kata_batch_transform.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class NumberReader implements ItemReader<Integer> {

    private List<Integer> numbers;
    private int i = 0;

    public NumberReader(String filePath) {
        this.numbers = new ArrayList<>();

        try {
            // Utiliser FileSystemResource pour accéder à un fichier en dehors du classpath
            FileSystemResource resource = new FileSystemResource(filePath);

            // Vérification si le fichier existe
            if (!resource.exists()) {
                throw new IOException("Le fichier n'existe pas à l'emplacement spécifié : " + filePath);
            }
            //lecture du fichier ligne par ligne
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    // Diviser la ligne par des espaces
                    String[] elements = ligne.trim().split("\\s+");
                    for (String element : elements) {
                        try {
                            numbers.add(Integer.parseInt(element.trim()));
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur de format pour l'élément: " + element);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur d'accès au fichier: " + filePath);
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la lecture du fichier Input", e);
        }
    }


    //lire et récuperer les nombres de manière itérative
    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
      if(i<numbers.size()){
          return numbers.get(i++);
      }
      return null;
    }
}
