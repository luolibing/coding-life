package cn.tim.springboot.restdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: luolibing
 * Date: 2017/5/12 16:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestDocApplication.class)
@WebAppConfiguration
public class RestDocApplicationTest {
    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Before
    public void setUp() {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listPeople() throws Exception {
        createSamplePerson("George");
        createSamplePerson("Mary");

        this.document.snippets(
                responseFields(
                        fieldWithPath("[].id").description("The persons' ID"),
                        fieldWithPath("[].firstName").description("The persons' first name")
                )
        );

        this.mockMvc.perform(
                get("/people").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getPerson() throws Exception {
        Person samplePerson = createSamplePerson("Henry");

        this.document.snippets(
                responseFields(
                        fieldWithPath("id").description("The person's ID"),
                        fieldWithPath("firstName").description("The persons' first name")
                )
        );

        this.mockMvc.perform(
                get("/" + samplePerson.getId()).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private Person createSamplePerson(String lastName) {
        Person person = new Person(0L, lastName);
        personRepository.add(person);
        return person;
    }

    @Test
    public void createPerson() throws Exception {
        Map<String, String> newPerson = new HashMap<>();
        newPerson.put("firstName", "Anne");

        ConstrainedFields fields = new ConstrainedFields(Person.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name")
                )
        );

        this.mockMvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(newPerson)
                )
        ).andExpect(status().isCreated());
    }

    @Test
    public void updatePerson() throws Exception {
        Person originalPerson = createSamplePerson("Victoria");
        Map<String, String> updatedPerson = new HashMap<>();
        updatedPerson.put("firstName", "Edward");
        updatedPerson.put("lastName", "King");

        ConstrainedFields fields = new ConstrainedFields(Person.class);

        this.document.snippets(
                requestFields(
                        fields.withPath("firstName").description("The persons' first name")
                )
        );

        this.mockMvc.perform(
                put("/people/" + originalPerson.getId()).contentType(MediaType.APPLICATION_JSON).content(
                        this.objectMapper.writeValueAsString(updatedPerson)
                )
        ).andExpect(status().isNoContent());
    }

//    private Person createSamplePerson(String firstName, String lastName) {
//        return personRepository.save(new Person(firstName, lastName));
//    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
