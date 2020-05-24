package com.learner.vocabularyservice;

import com.learner.vocabularyservice.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VocabularyApplicationIntegrationTest {

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
        expectGetCharactersRequestSuccess(mockMvc.perform(getCharactersRequest())
                .andDo(print()));
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
            expectGetCharactersRequestSuccess(mockMvc.perform(getCharactersRequest().header("Origin", origin))
                .andDo(print()));
            mockMvc.perform(getVocabularyRequest().header("Origin", origin))
                /* VS #1 Do NOT output this result running test from Maven, as it leads to a VM crash .andDo(print()) */
                .andExpect(status().isOk());
        }
    }

    @Test
    public void testUnauthorisedOriginsForbidden() throws Exception {
        mockMvc.perform(getCharactersRequest().header("Origin", "http://unknown"))
            .andDo(print())
            .andExpect(status().isForbidden());
        mockMvc.perform(getVocabularyRequest().header("Origin", "http://unknown"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * TODO I find this disconcerting: if no origin is declared, then the request is acceptable?
     * @throws Exception should something unexpected happen
     */
    @Test
    public void testUndeclaredOriginAllowed() throws Exception {
        expectGetCharactersRequestSuccess(mockMvc.perform(getCharactersRequest())
            .andDo(print()));
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

    /**
     * Utility method factoring out repeated expectations for a successfully performed get characters request.
     * @param response the outcome of having performed a get characters request
     * @return a {@link ResultActions} to which further expectations can be applied
     * @throws Exception should something unexpected happen
     */
    private ResultActions expectGetCharactersRequestSuccess(final ResultActions response) throws Exception {
        return response.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.characters[0].frequencyRank").value(1))
                .andExpect(jsonPath("$._embedded.characters[1].frequencyRank").value(2))
                .andExpect(jsonPath("$._embedded.characters[2].frequencyRank").value(3));

    }

    /**
     * Utility method factoring out the performance of a fairweather get characters request.
     * @return the {@link MockHttpServletRequestBuilder} upon which further request characteristics can be built
     */
    private MockHttpServletRequestBuilder getCharactersRequest() {
        return get("/characters")
            .param("page", "0")
            .param("size", "3")
            .param("sort", "frequencyRank");
    }

    /**
     * Utility method factoring out the performance of a fairweather get vocabulary request.
     * @return the {@link MockHttpServletRequestBuilder} upon which further request characteristics can be built
     */
    private MockHttpServletRequestBuilder getVocabularyRequest() {
        return get("/vocabulary");
    }



}
