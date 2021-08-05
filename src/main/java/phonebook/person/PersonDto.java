package phonebook.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.phonenumber.PhoneNumber;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;

    private String name;

    private List<PhoneNumber> phoneNumbers;
}
