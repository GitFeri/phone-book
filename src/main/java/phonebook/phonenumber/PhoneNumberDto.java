package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.person.Person;

@Data
@NoArgsConstructor
public class PhoneNumberDto {
    private Long id;

    private Person person;

    private String phoneNumber;

    private PhoneNumberType phoneNumberType;

    private PhoneNumberAccess phoneNumberAccess;
}
