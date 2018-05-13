package com.learner.vocabularyservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests the REST services provided by this application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VocabularyApplicationIntegrationTest {

    private static final String BASE_URL = "http://localhost/characters/";
    private static final String CHARACTER_SOUGHT = "表";
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

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    /**
     * This tell us that the ability to get paged characters is enabled and working as expected. It tests the behaviour
     * resulting from the declaration of the {@link CharacterRepository#findByCharacter(String)} method.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void getPagedCharactersWorksAsExpected() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(get("/characters")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "frequencyRank"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$._embedded.characters[0].frequencyRank").value(1))
                        .andExpect(jsonPath("$._embedded.characters[1].frequencyRank").value(2))
                        .andExpect(jsonPath("$._embedded.characters[2].frequencyRank").value(3))
                        .andReturn();
    }

    /**
     * This tells us the character "表" can be found using our explicitly declared REST repository method
     * {@link CharacterRepository#findByCharacter(String)}.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void findByCharacterWorksAsExpected() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(get("/characters/search/findByCharacter")
                            .param("汉字", CHARACTER_SOUGHT)).andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$._embedded.characters[0].character").value(CHARACTER_SOUGHT))
                            .andExpect(jsonPath("$._embedded.characters[0]._links.self.href").value(BASE_URL + CHARACTER_ID))
                            .andReturn();
    }

    /**
     * We want to be sure that the POST = Create CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     *
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void postCharacterMethodNotAllowed() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(post("/characters/").content(CHARACTER_JSON)).andDo(print())
                        .andExpect(status().isMethodNotAllowed())
                        .andReturn();
    }

    /**
     * We want to be sure that the GET = Read CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void getCharacterMethodNotAllowed() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(get("/characters/" + CHARACTER_ID)).andDo(print())
                        .andExpect(status().isMethodNotAllowed())
                        .andReturn();
    }

    /**
     * We want to be sure that the PUT = Update CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void putCharacterMethodNotAllowed() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(put("/characters/" + CHARACTER_ID).content(CHARACTER_JSON)).andDo(print())
                        .andExpect(status().isMethodNotAllowed())
                        .andReturn();
    }

    /**
     * We want to be sure that the DELETE CRUD operation, by default provided by a REST Repository, has been
     * successfully disabled here.
     * @throws Exception should something unexpected happen.
     */
    @Test
    public void deleteCharacterMethodNotAllowed() throws Exception {
        final MvcResult result =
                this.mockMvc.perform(delete("/characters/" + CHARACTER_ID)).andDo(print())
                        .andExpect(status().isMethodNotAllowed())
                        .andReturn();
    }




}
