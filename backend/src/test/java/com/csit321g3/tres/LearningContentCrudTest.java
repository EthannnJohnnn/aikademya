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
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(body, "$.id")).longValue();
    }

    private long createModule() throws Exception {
        return create("/api/modules", "{\"title\":\"AI Basics\",\"competency\":\"Awareness\"}");
    }

    private long createLesson(long moduleId) throws Exception {
        return create("/api/lessons",
                "{\"moduleId\":%d,\"title\":\"What is AI?\"}".formatted(moduleId));
    }

    private long createActivity(long lessonId) throws Exception {
        return create("/api/activities",
                "{\"lessonId\":%d,\"title\":\"Spot the AI\"}".formatted(lessonId));
    }

    private long createScenario(long activityId) throws Exception {
        return create("/api/scenarios",
                "{\"activityId\":%d,\"question\":\"Is this AI?\",\"options\":\"A | B\"}".formatted(activityId));
    }

    @Test
    void fullChainSupportsCreateReadUpdateDelete() throws Exception {
        long m = createModule();
        long l = createLesson(m);
        long a = createActivity(l);
        long s = createScenario(a);

        mvc.perform(get("/api/modules/" + m)).andExpect(status().isOk());
        mvc.perform(get("/api/lessons/" + l))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.module.id").value(m));
        mvc.perform(get("/api/activities/" + a))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lesson.id").value(l));
        mvc.perform(get("/api/scenarios/" + s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activity.id").value(a));
        mvc.perform(get("/api/scenarios")).andExpect(status().isOk());

        mvc.perform(put("/api/modules/" + m).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Module\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Module"));
        mvc.perform(put("/api/lessons/" + l).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"moduleId\":%d,\"title\":\"Updated Lesson\"}".formatted(m)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Lesson"));
        mvc.perform(put("/api/activities/" + a).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lessonId\":%d,\"title\":\"Updated Activity\"}".formatted(l)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Activity"));
        mvc.perform(put("/api/scenarios/" + s).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"activityId\":%d,\"question\":\"Updated?\"}".formatted(a)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Updated?"));

        mvc.perform(delete("/api/scenarios/" + s)).andExpect(status().isNoContent());
        mvc.perform(get("/api/scenarios/" + s)).andExpect(status().isNotFound());
        mvc.perform(delete("/api/activities/" + a)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/lessons/" + l)).andExpect(status().isNoContent());
        mvc.perform(delete("/api/modules/" + m)).andExpect(status().isNoContent());
        mvc.perform(get("/api/modules/" + m)).andExpect(status().isNotFound());
    }

    @Test
    void deletingParentWithChildrenReturnsConflict() throws Exception {
        long m = createModule();
        long l = createLesson(m);
        long a = createActivity(l);
        createScenario(a);

        mvc.perform(delete("/api/modules/" + m)).andExpect(status().isConflict());
        mvc.perform(delete("/api/lessons/" + l)).andExpect(status().isConflict());
        mvc.perform(delete("/api/activities/" + a)).andExpect(status().isConflict());
    }

    @Test
    void unknownParentIdReturnsNotFound() throws Exception {
        mvc.perform(post("/api/lessons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"moduleId\":99999,\"title\":\"x\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/api/activities").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lessonId\":99999,\"title\":\"x\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/api/scenarios").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"activityId\":99999,\"question\":\"x\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void invalidInputReturnsBadRequest() throws Exception {
        mvc.perform(post("/api/modules").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest());
        mvc.perform(post("/api/lessons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"no parent\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unknownIdReturnsNotFound() throws Exception {
        mvc.perform(get("/api/modules/99999")).andExpect(status().isNotFound());
        mvc.perform(get("/api/lessons/99999")).andExpect(status().isNotFound());
        mvc.perform(get("/api/activities/99999")).andExpect(status().isNotFound());
        mvc.perform(get("/api/scenarios/99999")).andExpect(status().isNotFound());
    }
}
