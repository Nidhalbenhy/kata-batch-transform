package com.example.kata_batch_transform.service.impl;

import com.example.kata_batch_transform.service.TransformService;
import org.springframework.stereotype.Service;

@Service
public class TransformServiceImpl implements TransformService {
    // Le logique métier métier pour transformer un nombre (compris entre 0 et 100)
    // en une chaîne de caractères selon l'énoncé
    @Override
    public String transformNumber(Integer item) {

        System.out.println("traitement " + item);
        StringBuilder result = new StringBuilder();
        // Règle pour la division par 3
        if(item %3 ==0){
            result.append("FOO");
        }
        // Règle pour la division par 5
        if(item %5 ==0){
            result.append("BAR");
        }
        // Règle si contient 3 ou 5 ou 7
        for(char c : item.toString().toCharArray()){
            switch(c){
                case '3' ->result.append("FOO");
                case '5' ->result.append("BAR");
                case '7' ->result.append("QUIX");
            }
        }
        String transform = result.length()==0 ? String.valueOf(item) : result.toString();
        return item + "  ==>" + transform;
    }
}
