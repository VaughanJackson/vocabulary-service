package com.learner.vocabularyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    /** The repository used by this service. */
    private final CharacterRepository characterRepository;

    @Autowired
    public VocabularyServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }
}
