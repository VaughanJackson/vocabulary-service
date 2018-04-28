package com.learner.vocabularyservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character, String>{

    List<Character> findByCharacter(@Param("汉字") String character);

}
