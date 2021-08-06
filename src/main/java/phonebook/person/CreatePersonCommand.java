package phonebook.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.phonenumber.PhoneNumber;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonCommand {
    @Schema(description = "Name of the person", example = "John Doe")
    private String name;
    private Set<PhoneNumber> phoneNumbers;
}
