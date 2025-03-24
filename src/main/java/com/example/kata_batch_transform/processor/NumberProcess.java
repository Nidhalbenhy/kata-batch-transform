package com.example.kata_batch_transform.processor;

import com.example.kata_batch_transform.service.TransformService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class NumberProcess implements ItemProcessor<Integer,String> {

    @Autowired
    TransformService transformService;

    //Appeler le logique Métier implémenter dans le service  TransformService.
    @Override
    public String process(Integer item) throws Exception {
        return transformService.transformNumber(item);
    }
}
