package com.example.RestAPI.service;

import com.example.RestAPI.model.ClimaEntity;
import com.example.RestAPI.repository.ClimaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class padService {

    @Autowired
    private ClimaRepository climaRepository;

    public String preverTempo() {
        String apiURL = "https://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR?token=9fe25332679ebce952fdd9f7f9a83c3e";
        String dadosMeteorologicos = "";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);

        dadosMeteorologicos = responseEntity.getBody();

        List<ClimaEntity> climaEntities = extrairDadosEDisponibilizar(dadosMeteorologicos);

        inserir(climaEntities);

        return dadosMeteorologicos;
    }

    private List<ClimaEntity> extrairDadosEDisponibilizar(String dadosMeteorologicos) {
        List<ClimaEntity> climaEntities = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(dadosMeteorologicos);
            if (root.isArray()) {
                for (JsonNode node : root) {

                    ClimaEntity climaEntity = new ClimaEntity();
                    UUID uuid = UUID.randomUUID();

                    climaEntity.setId(uuid.toString());
                    climaEntity.setCountry(node.get("country").asText());
                    climaEntity.setDate(node.get("date").asText());
                    climaEntity.setText(node.get("text").asText());
                    climaEntities.add(climaEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return climaEntities;
    }

    private void inserir(List<ClimaEntity> climaEntities) {
        climaRepository.saveAll(climaEntities);
    }

    public ClimaEntity atualizar(String id, ClimaEntity climaNovo){

        ClimaEntity existingClima = climaRepository.findById(id).orElse(null);

        existingClima.setDate(climaNovo.getDate());
        existingClima.setCountry(climaNovo.getCountry());
        existingClima.setText(climaNovo.getText());

        return climaRepository.save(existingClima);
    }

    public void deletar(String id){
        climaRepository.deleteById(id);
    }
}

