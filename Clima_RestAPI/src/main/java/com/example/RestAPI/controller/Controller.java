package com.example.RestAPI.controller;

import com.example.RestAPI.service.padService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.RestAPI.model.ClimaEntity;

import java.util.List;

@RestController
@RequestMapping("clima")
public class Controller {

    //Classe de serviços
    @Autowired
    padService service = new padService();

    @GetMapping
    public String preverTempo(){
        return service.preverTempo();
    }

    @PutMapping("/atualizar/{id}")
    public ClimaEntity atualizar(@PathVariable String id, @RequestBody ClimaEntity clima){
        return service.atualizar(id, clima);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletar(@PathVariable String id){
        service.deletar(id);
    }

}

