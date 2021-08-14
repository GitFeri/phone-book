package phonebook.phonenumber;

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
import phonebook.person.CreatePersonCommand;
import phonebook.person.PersonDto;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from phone_numbers")
@Sql(statements = "delete from people")

public class PhoneNumberControllerRestIT {
    private static final String PEOPLE_URL = "/api/phonebook/people";
    private static final String PHONE_NUMBER_URL = "/api/phonebook/phonenumber";

    private static final String PHONE_NUMBER_1 = "+3611111111";
    private static final String PHONE_NUMBER_2 = "+2222222222";
    private static final String PHONE_NUMBER_3 = "+3633333333";
    private static final String PHONE_NUMBER_4 = "+3644444444";

    @Autowired
    private TestRestTemplate template;

    private Long personIdForTest;
    private Long phoneNumberIdForTest;

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

        template.postForObject(PHONE_NUMBER_URL,
                new CreatePhoneNumberCommand(
                        personIdForTest,
                        PHONE_NUMBER_1,
                        PhoneNumberType.MOBILE,
                        PhoneNumberAccess.SECRET),
                PhoneNumberDto.class);

        PhoneNumberDto phoneNumberDto =
                template.postForObject(PHONE_NUMBER_URL,
                        new CreatePhoneNumberCommand(
                                personIdForTest,
                                PHONE_NUMBER_2,
                                PhoneNumberType.MOBILE,
                                PhoneNumberAccess.PUBLIC),
                        PhoneNumberDto.class);
        phoneNumberIdForTest = phoneNumberDto.getId();
    }

    @Test
    public void testCreatePhoneNumber() {
        PhoneNumberDto phoneNumberDto = template
                .postForObject(PHONE_NUMBER_URL,
                        new CreatePhoneNumberCommand(
                                personIdForTest,
                                PHONE_NUMBER_4,
                                PhoneNumberType.HOME,
                                PhoneNumberAccess.SECRET),
                        PhoneNumberDto.class);

        assertEquals(PHONE_NUMBER_4, phoneNumberDto.getPhoneNumber());
        assertEquals("John Doe", phoneNumberDto.getPerson().getName());
    }

    @Test
    public void testGetPhoneNumbers() {
        List<PhoneNumberDto> phoneNumberDtos =
                template.exchange(PHONE_NUMBER_URL,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PhoneNumberDto>>() {
                                })
                        .getBody();

        assertThat(phoneNumberDtos)
                .hasSize(2)
                .extracting(PhoneNumberDto::getPhoneNumber)
                .containsExactly(PHONE_NUMBER_1, PHONE_NUMBER_2);
    }

    @Test
    public void testGetPhoneNumbersByPartOfNumber() {
        template.postForObject(PHONE_NUMBER_URL,
                new CreatePhoneNumberCommand(
                        personIdForTest,
                        PHONE_NUMBER_4,
                        PhoneNumberType.HOME,
                        PhoneNumberAccess.PUBLIC),
                PhoneNumberDto.class);


        List<PhoneNumberDto> phoneNumberDtos =
                template.exchange(PHONE_NUMBER_URL + "?partOfPhoneNumber=222222",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PhoneNumberDto>>() {
                                })
                        .getBody();

        assertThat(phoneNumberDtos)
                .hasSize(1)
                .extracting(PhoneNumberDto::getPhoneNumber)
                .containsExactly(PHONE_NUMBER_2);
    }

    @Test
    public void testGetPhoneNumbersByPartOfNumber2() {
        template.postForObject(PHONE_NUMBER_URL,
                new CreatePhoneNumberCommand(
                        personIdForTest,
                        PHONE_NUMBER_4,
                        PhoneNumberType.HOME,
                        PhoneNumberAccess.PUBLIC),
                PhoneNumberDto.class);


        List<PhoneNumberDto> phoneNumberDtos =
                template.exchange(PHONE_NUMBER_URL + "?partOfPhoneNumber=36",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<PhoneNumberDto>>() {
                                })
                        .getBody();

        assertThat(phoneNumberDtos)
                .hasSize(2)
                .extracting(PhoneNumberDto::getPhoneNumber)
                .containsExactly(PHONE_NUMBER_1, PHONE_NUMBER_4);
    }

    @Test
    public void testGetPhoneNumberById() {
        PhoneNumberDto phoneNumberDto =
                template.exchange(PHONE_NUMBER_URL + "/" + phoneNumberIdForTest,
                                HttpMethod.GET,
                                null,
                                PhoneNumberDto.class)
                        .getBody();

        assertEquals("John Doe", phoneNumberDto.getPerson().getName());
        assertEquals(PhoneNumberAccess.PUBLIC, phoneNumberDto.getPhoneNumberAccess());
    }

    @Test
    public void testGetPhoneNumberByWrongId() {
        long wrongId = 999999999L;
        Problem result =
                template.exchange(PHONE_NUMBER_URL + "/" + wrongId,
                                HttpMethod.GET,
                                null,
                                Problem.class)
                        .getBody();

        assertEquals(URI.create(PHONE_NUMBER_URL + "/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    public void testUpdatePhoneNumber() {
        template.put(PHONE_NUMBER_URL + "/" + phoneNumberIdForTest,
                new UpdatePhoneNumberCommand(PHONE_NUMBER_3,
                        PhoneNumberType.WORK,
                        PhoneNumberAccess.PUBLIC));

        PhoneNumberDto phoneNumberDto =
                template.exchange(PHONE_NUMBER_URL + "/" + phoneNumberIdForTest,
                                HttpMethod.GET,
                                null,
                                PhoneNumberDto.class)
                        .getBody();

        assertEquals("John Doe", phoneNumberDto.getPerson().getName());
        assertEquals(PhoneNumberType.WORK, phoneNumberDto.getPhoneNumberType());
        assertEquals(PHONE_NUMBER_3, phoneNumberDto.getPhoneNumber());
    }

    @Test
    public void testDeletePhoneNumberById() {
        template.delete(PHONE_NUMBER_URL + "/" + phoneNumberIdForTest, phoneNumberIdForTest);

        Problem result =
                template.exchange(PHONE_NUMBER_URL + "/" + phoneNumberIdForTest,
                                HttpMethod.GET,
                                null,
                                Problem.class)
                        .getBody();

        assertEquals(URI.create(PHONE_NUMBER_URL + "/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}
