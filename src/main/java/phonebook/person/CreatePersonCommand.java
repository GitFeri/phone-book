package phonebook.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.phonenumber.PhoneNumber;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonCommand {
    private String name;
    private Set<PhoneNumber> phoneNumbers;
}
