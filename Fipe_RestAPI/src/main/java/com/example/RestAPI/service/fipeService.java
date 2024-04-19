package com.example.RestAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.example.RestAPI.Model.MarcasEntity;
import com.example.RestAPI.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class fipeService {

    @Autowired
    private MarcaRepository marcaRepository;

    private String consultarURL(String apiUrl){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        dados = responseEntity.getBody();

        return dados;
    }

    public String consultarMarcas() {
        String response = consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas");

        List<MarcasEntity> marcasEntities = extrairDadosEDisponibilizar(response);

        inserir(marcasEntities);

        return response;
    }
    public String consultarModelos(int id) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+id+"/modelos");
    }
    public String consultarAnos(int marca, int modelo) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos");
    }
    public String consultarValor(int marca, int modelo, String ano) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos/"+ano);
    }


    private List<MarcasEntity> extrairDadosEDisponibilizar(String dadosMeteorologicos) {
        List<MarcasEntity> marcasEntities = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(dadosMeteorologicos);
            if (root.isArray()) {
                for (JsonNode node : root) {

                    MarcasEntity marcasEntity = new MarcasEntity();

                    UUID uuid = UUID.randomUUID();

                    marcasEntity.setId(uuid.toString());
                    marcasEntity.setCodigo(node.get("codigo").asInt());
                    marcasEntity.setNome(node.get("nome").asText());

                    marcasEntities.add(marcasEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(marcasEntities.get(0));

        return marcasEntities;
    }

    private void inserir(List<MarcasEntity> climaEntities) {
        marcaRepository.saveAll(climaEntities);
    }

    public MarcasEntity atualizar(String id, MarcasEntity marcaNovo){

        MarcasEntity existingMarca = marcaRepository.findById(id).orElse(null);

        existingMarca.setCodigo(marcaNovo.getCodigo());
        existingMarca.setNome(marcaNovo.getNome());

        return marcaRepository.save(existingMarca);
    }

    public void deletar(String id){
        marcaRepository.deleteById(id);
    }
}
