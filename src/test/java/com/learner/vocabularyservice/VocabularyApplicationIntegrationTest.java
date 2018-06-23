package com.learner.vocabularyservice;

import com.learner.vocabularyservice.config.ConfigProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests the REST services provided by this application.
 * TODO refactor to reduce code duplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VocabularyApplicationIntegrationTest {

    /**
     * Logger used by this.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VocabularyApplicationIntegrationTest.class);


    private static final String BASE_URL = "http://localhost/characters/";
    private static final String CHARACTER_SOUGHT = "表";
    // NOTE actual CHARACTER_ID will vary from collecton load to collection load. This is just an example value.
    private static final String CHARACTER_ID = "5ad34cf0bb426a874d0932bc";

    // TODO Use Jackson/Spring Boot to serialise a Character to JSON?
    private static final String CHARACTER_JSON = "{" +
            "\"character\" :\"表\"," +
            "\"frequencyRank\":177," +
            "\"frequency\":226768," +
            "\"cumulativePercentage\":53.28," +
            "\"pinyin\":\"biao3\"," +
            "\"englishTranslation\":\"surface/exterior/to watch/to show/express/an example/a list or table/a meter/a watch/chart/external\"" +
            "}";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    /**
     * Configuration properties used by this.
     */
    @Autowired
    private ConfigProperties properties;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    /**
     * This tell us that the ability to get paged characters is enabled and working as expected. It tests the behaviour
     * resulting from the declaration of the {@link CharacterRepository#findByCharacter(String)} method.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void getPagedCharactersWorksAsExpected() throws Exception {
        mockMvc.perform(get("/characters")
            .param("page", "0")
            .param("size", "3")
            .param("sort", "frequencyRank"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.characters[0].frequencyRank").value(1))
            .andExpect(jsonPath("$._embedded.characters[1].frequencyRank").value(2))
            .andExpect(jsonPath("$._embedded.characters[2].frequencyRank").value(3));
}

    /**
     * This tells us the character "表" can be found using our explicitly declared REST repository method
     * {@link CharacterRepository#findByCharacter(String)}.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void findByCharacterWorksAsExpected() throws Exception {
        mockMvc.perform(get("/characters/search/findByCharacter")
            .param("汉字", CHARACTER_SOUGHT)).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.characters[0].character").value(CHARACTER_SOUGHT));
    }

    /**
     * We want to be sure that the POST = Create CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     *
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void postCharacterMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/characters/")
            .content(CHARACTER_JSON)).andDo(print())
            .andExpect(status().isMethodNotAllowed());
    }

    /**
     * We want to be sure that the GET = Read CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void getCharacterMethodNotAllowed() throws Exception {
        mockMvc.perform(get("/characters/" + CHARACTER_ID)).andDo(print())
            .andExpect(status().isMethodNotAllowed());
    }

    /**
     * We want to be sure that the PUT = Update CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void putCharacterMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/characters/" + CHARACTER_ID).content(CHARACTER_JSON)).andDo(print())
            .andExpect(status().isMethodNotAllowed());
    }

    /**
     * We want to be sure that the DELETE CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void deleteCharacterMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/characters/" + CHARACTER_ID)).andDo(print())
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testAuthorisedOriginsAllowed() throws Exception {

        final String[] allowedOrigins = splitAllowedOrigins(properties.getCorsAllowedOrigins());
        LOGGER.debug("allowedOrigins = {}", allowedOrigins);

        for(final String origin: allowedOrigins) {
            LOGGER.debug("Testing allowed origin {} is allowed...", origin);
            mockMvc.perform(get("/characters")
                .header("Origin", origin)
                .param("page", "0")
                .param("size", "3")
                .param("sort", "frequencyRank"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.characters[0].frequencyRank").value(1))
                .andExpect(jsonPath("$._embedded.characters[1].frequencyRank").value(2))
                .andExpect(jsonPath("$._embedded.characters[2].frequencyRank").value(3));
        }
    }

    @Test
    public void testUnauthorisedOriginsForbidden() throws Exception {
        mockMvc.perform(get("/characters")
            .header("Origin", "http://unknown")
            .param("page", "0")
            .param("size", "3")
            .param("sort", "frequencyRank"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * TODO I find this disconcerting: if no origin is declared, then the request is acceptable?
     * @throws Exception should something unexpected happen
     */
    @Test
    public void testUndeclaredOriginAllowed() throws Exception {
        mockMvc.perform(get("/characters")
            .param("page", "0")
            .param("size", "3")
            .param("sort", "frequencyRank"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.characters[0].frequencyRank").value(1))
            .andExpect(jsonPath("$._embedded.characters[1].frequencyRank").value(2))
            .andExpect(jsonPath("$._embedded.characters[2].frequencyRank").value(3));
    }

    /**
     * Splits the allowed origins into an array of separate, trimmed origin strings.
     * @param allowedOrigins the allowed origins list as a single string
     * @return an array of trimmed allowed origins
     */
    private String[] splitAllowedOrigins(final String allowedOrigins) {
        return Stream.of(allowedOrigins.split(","))
            .map(String::trim)
            .collect(toList())
            .toArray(new String[]{});
    }


}
