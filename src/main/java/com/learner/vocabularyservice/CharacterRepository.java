package com.learner.vocabularyservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CharacterRepository extends MongoRepository<Character, String>{


    /**
     * Redeclares this {@link PagingAndSortingRepository} method to enable it as all
     * REST repository methods are now disabled by default. Enables support for requests such as
     * "http://localhost:8080/characters?page=0&size=3&sort=frequencyRank,asc"
     * @param var1
     * @return page of characters
     */
    @RestResource
    Page<Character> findAll(Pageable var1);

    /**
     * Declares this method to trigger Spring Data REST repository support for search requests
     * like "http://localhost:8080/characters/search/findByCharacter?汉字=中"
     * @param character the search parameter, matching on the unicode character itself
     * @return the resulting character, if any
     */
    @RestResource
    List<Character> findByCharacter(@Param("汉字") String character);

}
