package com.example.kata_batch_transform.controller;

import com.example.kata_batch_transform.processor.NumberProcess;
import com.example.kata_batch_transform.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransformController {

    @Autowired
    TransformService transformService;

    // Cette Méthode prenant en entrée le nombre et retournant la chaîne de caractères.
    @GetMapping("/transform/{item}")
    public ResponseEntity<String> transformtNumber(@PathVariable Integer item) throws Exception {
        String result = transformService.transformNumber(item);
        return ResponseEntity.ok(result);
    }
}
