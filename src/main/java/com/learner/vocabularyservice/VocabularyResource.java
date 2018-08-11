package com.learner.vocabularyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VocabularyResource {

    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping("vocabulary")
    public ResponseEntity<List<Character>> getAllCharacters() {
        final List<Character> characters = vocabularyService.getAllCharacters();
        return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
    }


}
