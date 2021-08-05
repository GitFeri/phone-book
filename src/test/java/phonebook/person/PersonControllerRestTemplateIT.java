package phonebook.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from phone_numbers")
@Sql(statements = "delete from people")

public class PersonControllerRestTemplateIT {
    static final String URL = "/api/phonebook/people";

    @Autowired
    TestRestTemplate template;

    private Long id;

    @BeforeEach
    public void setUp() {
        PersonDto personDto = template.postForObject(URL,
                new CreatePersonCommand("John Doe", Set.of()),
                PersonDto.class);
        id = personDto.getId();

        template.postForObject(URL,
                new CreatePersonCommand("Jane Doe", Set.of()),
                PersonDto.class);
    }

    @Test
    public void testGetPeople() {
        List<PersonDto> personDtos = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {
                }).getBody();

        assertThat(personDtos)
                .hasSize(2)
                .extracting(PersonDto::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    public void testGetPersonById() {
        PersonDto personDto = template
                .exchange(URL + "/" + id,
                        HttpMethod.GET,
                        null,
                        PersonDto.class)
                .getBody();

        assertEquals("John Doe", personDto.getName());

    }

    @Test
    public void testDeletePersonById() {
        template.delete(URL + "/" + id);
        List<PersonDto> personDtos = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {
                }).getBody();

        assertThat(personDtos)
                .hasSize(1)
                .extracting(PersonDto::getName)
                .containsExactly("Jane Doe");

    }

    @Test
    public void testUpdatePerson() {
        template.put(URL + "/" + id,
                new UpdatePersonCommand("Jack Doe"));

        List<PersonDto> personDtos = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonDto>>() {
                }).getBody();

        assertThat(personDtos)
                .hasSize(2)
                .extracting(PersonDto::getName)
                .containsExactly("Jack Doe", "Jane Doe");
    }

}