package com.learner.vocabularyservice;

import java.util.List;

public interface VocabularyService {

    /**
     * Gets the entire vocabulary in one go.
     * @return the entire vocabulary in a single list
     */
    List<Character> getAllCharacters();
}
