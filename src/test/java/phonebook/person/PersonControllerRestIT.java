package phonebook.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from phone_numbers")
@Sql(statements = "delete from people")

public class PersonControllerRestIT {
    private static final String PEOPLE_URL = "/api/phonebook/people";

    @Autowired
    private TestRestTemplate template;

    private Long personIdForTest;

    @BeforeEach
    public void setUp() {
        PersonDto personDto =
                template.postForObject(PEOPLE_URL,
                        new CreatePersonCommand("John Doe"),
                        PersonDto.class);

        personIdForTest = personDto.getId();

        template.postForObject(PEOPLE_URL,
                new CreatePersonCommand("Jane Doe"),
                PersonDto.class);
    }

    @Test
    public void testGetPeople() {
        List<PersonDto> personDtos =
                template.exchange(PEOPLE_URL,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PersonDto>>() {
                                })
                        .getBody();

        assertThat(personDtos)
                .hasSize(2)
                .extracting(PersonDto::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    public void testGetPeopleByPartOfName() {
        template.postForObject(PEOPLE_URL,
                new CreatePersonCommand("Jane Roe"),
                PersonDto.class);

        List<PersonDto> personDtos =
                template.exchange(PEOPLE_URL + "?partOfName=Jane",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PersonDto>>() {
                                })
                        .getBody();

        assertThat(personDtos)
                .hasSize(2)
                .extracting(PersonDto::getName)
                .containsExactly("Jane Doe", "Jane Roe");
    }

    @Test
    public void testGetPersonById() {
        PersonDto personDto =
                template.exchange(PEOPLE_URL + "/" + personIdForTest,
                                HttpMethod.GET,
                                null,
                                PersonDto.class)
                        .getBody();

        assertEquals("John Doe", personDto.getName());
    }

    @Test
    public void testGetPersonByWrongId() {
        long wrongId = 99999999L;
        Problem result =
                template.exchange(PEOPLE_URL + "/" + wrongId,
                                HttpMethod.GET,
                                null,
                                Problem.class)
                        .getBody();

        assertEquals(URI.create(PEOPLE_URL + "/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testUpdatePerson() {
        template.put(PEOPLE_URL + "/" + personIdForTest,
                new UpdatePersonCommand("Jack Doe"));

        List<PersonDto> personDtos =
                template.exchange(PEOPLE_URL,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PersonDto>>() {
                                })
                        .getBody();

        assertThat(personDtos)
                .hasSize(2)
                .extracting(PersonDto::getName)
                .containsExactly("Jack Doe", "Jane Doe");
    }

    @Test
    public void testDeletePersonById() {
        template.delete(PEOPLE_URL + "/" + personIdForTest);
        List<PersonDto> personDtos =
                template.exchange(PEOPLE_URL,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PersonDto>>() {
                                })
                        .getBody();

        assertThat(personDtos)
                .hasSize(1)
                .extracting(PersonDto::getName)
                .containsExactly("Jane Doe");
    }

    @Test
    public void testDeletePeople() {
        template.delete(PEOPLE_URL);

        List<PersonDto> personDtos =
                template.exchange(PEOPLE_URL,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PersonDto>>() {
                                })
                        .getBody();

        assertThat(personDtos).isEmpty();
    }
}
