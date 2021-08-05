package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.person.Person;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhoneNumberCommand {

    private Long personId;

    private String phoneNumber;

    private PhoneNumberType phoneNumberType;

    private PhoneNumberAccess phoneNumberAccess;


}
