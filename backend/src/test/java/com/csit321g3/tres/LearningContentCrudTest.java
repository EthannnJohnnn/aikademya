
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
    "spring.datasource.url=jdbc:h2:mem:learningtest",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class LearningContentCrudTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private long create(String url, String json) throws Exception {
        String body = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return ((Number) JsonPath.read(body, "$.id")).longValue();
    }

    private long createModule() throws Exception {
        return create("/api/modules",
            "{\"title\":\"AI Basics\"}");
    }

    private long createLesson(long moduleId) throws Exception {
        return create("/api/lessons",
            "{\"moduleId\":%d,\"title\":\"What is AI?\"}"
                .formatted(moduleId));
    }

    private long createScenario(long lessonId) throws Exception {
        return create("/api/scenarios",
            "{\"lessonId\":%d,\"situation\":\"AI classroom example\"}"
                .formatted(lessonId));
    }

    @Test
    void fullChainSupportsCreateReadUpdateDelete() throws Exception {
        long moduleId = createModule();
        long lessonId = createLesson(moduleId);
        long scenarioId = createScenario(lessonId);

        // Read
        mvc.perform(get("/api/modules/" + moduleId))
            .andExpect(status().isOk());

        mvc.perform(get("/api/lessons/" + lessonId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.module.id").value(moduleId));

        mvc.perform(get("/api/scenarios/" + scenarioId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.lesson.id").value(lessonId));

        // Update
        mvc.perform(put("/api/modules/" + moduleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Module\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Module"));

        mvc.perform(put("/api/lessons/" + lessonId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"moduleId\":%d,\"title\":\"Updated Lesson\"}"
                        .formatted(moduleId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Lesson"));

        mvc.perform(put("/api/scenarios/" + scenarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"lessonId\":%d,\"situation\":\"Updated Situation\"}"
                        .formatted(lessonId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.situation")
                .value("Updated Situation"));

        // Delete children before parents
        mvc.perform(delete("/api/scenarios/" + scenarioId))
            .andExpect(status().isNoContent());

        mvc.perform(delete("/api/lessons/" + lessonId))
            .andExpect(status().isNoContent());

        mvc.perform(delete("/api/modules/" + moduleId))
            .andExpect(status().isNoContent());

        mvc.perform(get("/api/modules/" + moduleId))
            .andExpect(status().isNotFound());
    }

    @Test
    void deletingParentWithChildrenReturnsConflict() throws Exception {
        long moduleId = createModule();
        long lessonId = createLesson(moduleId);
        createScenario(lessonId);

        mvc.perform(delete("/api/modules/" + moduleId))
            .andExpect(status().isConflict());

        mvc.perform(delete("/api/lessons/" + lessonId))
            .andExpect(status().isConflict());
    }

    @Test
    void unknownParentIdReturnsNotFound() throws Exception {
        mvc.perform(post("/api/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"moduleId\":99999,\"title\":\"Example\"}"))
            .andExpect(status().isNotFound());

        mvc.perform(post("/api/scenarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"lessonId\":99999,\"situation\":\"Example\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void invalidInputReturnsBadRequest() throws Exception {
        mvc.perform(post("/api/modules")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"\"}"))
            .andExpect(status().isBadRequest());

        mvc.perform(post("/api/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"No parent\"}"))
            .andExpect(status().isBadRequest());

        mvc.perform(post("/api/scenarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"situation\":\"No parent\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void unknownIdReturnsNotFound() throws Exception {
        mvc.perform(get("/api/modules/99999"))
            .andExpect(status().isNotFound());

        mvc.perform(get("/api/lessons/99999"))
            .andExpect(status().isNotFound());

        mvc.perform(get("/api/scenarios/99999"))
            .andExpect(status().isNotFound());
    }
}
