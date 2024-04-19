package com.example.RestAPI.controller;

import com.example.RestAPI.service.fipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    fipeService service = new fipeService();

    @GetMapping("/marcas")
    public String consultarMarcas(){
        return service.consultarMarcas();
    }
    @GetMapping("/modelos/{marca}")
    public String consultarModelos(@PathVariable int marca){
        return service.consultarModelos(marca);
    }
    @GetMapping("/anos/{marca}/{modelo}")
    public String consultarAnos(@PathVariable int marca, @PathVariable int modelo){
        return service.consultarAnos(marca, modelo);
    }
    @GetMapping("/valor/{marca}/{modelo}/{ano}")
    public String consultarValor(@PathVariable int marca, @PathVariable int modelo, @PathVariable String ano){
        return service.consultarValor(marca, modelo, ano);
    }
}

