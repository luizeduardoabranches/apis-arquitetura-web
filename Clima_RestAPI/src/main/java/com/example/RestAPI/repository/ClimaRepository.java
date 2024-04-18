package com.example.RestAPI.repository;

import com.example.RestAPI.model.ClimaEntity;
import com.example.RestAPI.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClimaRepository extends MongoRepository<ClimaEntity, String> {

}
