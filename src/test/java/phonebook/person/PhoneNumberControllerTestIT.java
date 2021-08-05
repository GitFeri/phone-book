package phonebook.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import phonebook.phonenumber.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from phone_numbers")
@Sql(statements = "delete from people")

public class PhoneNumberControllerTestIT {
    static final String PEOPLE_URL = "/api/phonebook/people";
    static final String PHONE_NUMBER_URL = "/api/phonebook/phonenumber";

    @Autowired
    TestRestTemplate template;

    private Long personId;
    private Long phoneNumberId;

    @BeforeEach
    public void setUp() {
        PersonDto personDto = template.postForObject(PEOPLE_URL,
                new CreatePersonCommand("John Doe", Set.of()),
                PersonDto.class);
        personId = personDto.getId();

        template.postForObject(PEOPLE_URL,
                new CreatePersonCommand("Jane Doe", Set.of()),
                PersonDto.class);

        template.postForObject(PHONE_NUMBER_URL,
                new CreatePhoneNumberCommand(
                        personId,
                        "123456789",
                        PhoneNumberType.MOBILE,
                        PhoneNumberAccess.SECRET),
                PhoneNumberDto.class);
        PhoneNumberDto phoneNumberDto = template.postForObject(PHONE_NUMBER_URL,
                new CreatePhoneNumberCommand(
                        personId,
                        "555555555",
                        PhoneNumberType.MOBILE,
                        PhoneNumberAccess.PUBLIC),
                PhoneNumberDto.class);
        phoneNumberId = phoneNumberDto.getId();


    }
    @Test
    public void testGetPhoneNumberById() {
        PhoneNumberDto phoneNumberDto = template
                .exchange(PHONE_NUMBER_URL + "/" + phoneNumberId,
                        HttpMethod.GET,
                        null,
                        PhoneNumberDto.class)
                .getBody();

        assertEquals("John Doe", phoneNumberDto.getPerson().getName());
        assertEquals(PhoneNumberAccess.PUBLIC,phoneNumberDto.getPhoneNumberAccess());

    }

    @Test
    public void testUpdatePhoneNumber() {
        template.put(PHONE_NUMBER_URL + "/" + phoneNumberId,
                new UpdatePhoneNumberCommand("333333333",
                        PhoneNumberType.WORK,
                        PhoneNumberAccess.PUBLIC)

        );

        PhoneNumberDto phoneNumberDto = template
                .exchange(PHONE_NUMBER_URL + "/" + phoneNumberId,
                        HttpMethod.GET,
                        null,
                        PhoneNumberDto.class)
                .getBody();

        assertEquals("John Doe", phoneNumberDto.getPerson().getName());
        assertEquals(PhoneNumberType.WORK,phoneNumberDto.getPhoneNumberType());
        assertEquals("333333333",phoneNumberDto.getPhoneNumber());
    }
}
