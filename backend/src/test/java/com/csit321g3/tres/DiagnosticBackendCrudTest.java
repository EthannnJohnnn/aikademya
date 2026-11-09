package com.csit321g3.tres;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:diagnostictest;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DiagnosticBackendCrudTest {
    @Autowired WebApplicationContext context;
    MockMvc mvc;

    @BeforeEach
    void setup() { mvc = MockMvcBuilders.webAppContextSetup(context).build(); }

    private int create(String url, String json, String idKey) throws Exception {
        String body = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(body, "$." + idKey)).intValue();
    }

    @Test
    void diagnosticChainAndPathwayCrud() throws Exception {
        int assessment = create("/api/assessments", "{\"title\":\"AI Literacy\"}", "assessmentId");
        int question = create("/api/questions", "{\"assessmentId\":" + assessment + ",\"competencyId\":1}", "questionId");
        int user = create("/api/users", "{\"email\":\"cayl-testing@example.com\"}", "userId");
        int attempt = create("/api/assessment-attempts", "{\"userId\":" + user + ",\"assessmentId\":" + assessment + "}", "attemptId");
        int response = create("/api/responses", "{\"attemptId\":" + attempt + ",\"questionId\":" + question + "}", "responseId");
        int result = create("/api/diagnostic-results", "{\"attemptId\":" + attempt + ",\"totalScore\":85.5}", "resultId");
        int pathway = create("/api/learning-pathways", "{\"userId\":" + user + ",\"resultId\":" + result + "}", "pathwayId");
        mvc.perform(get("/api/learning-pathways/" + pathway)).andExpect(status().isOk())
                .andExpect(jsonPath("$.result.resultId").value(result));
        mvc.perform(put("/api/diagnostic-results/" + result).contentType(MediaType.APPLICATION_JSON)
                .content("{\"attemptId\":" + attempt + ",\"totalScore\":90}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.totalScore").value(90));
        mvc.perform(delete("/api/diagnostic-results/" + result)).andExpect(status().isConflict());
        mvc.perform(delete("/api/learning-pathways/" + pathway)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/diagnostic-results/" + result)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/assessment-attempts/" + attempt)).andExpect(status().isConflict());
        mvc.perform(delete("/api/responses/" + response)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/assessment-attempts/" + attempt)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/questions/" + question)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/assessments/" + assessment)).andExpect(status().isNoContent());
    }

    @Test
    void invalidRequestsReturnUsefulStatuses() throws Exception {
        mvc.perform(post("/api/assessments").contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"\"}")).andExpect(status().isBadRequest());
        mvc.perform(post("/api/questions").contentType(MediaType.APPLICATION_JSON)
                .content("{\"assessmentId\":999999,\"competencyId\":1}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/api/assessment-attempts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":999999,\"assessmentId\":999999}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/api/diagnostic-results").contentType(MediaType.APPLICATION_JSON)
                .content("{\"attemptId\":999999,\"totalScore\":20}"))
                .andExpect(status().isNotFound());
    }
}
