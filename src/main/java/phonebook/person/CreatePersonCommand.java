package phonebook.person;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.phonenumber.PhoneNumber;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonCommand {

    @NotNull
    @NotBlank(message = "Name can not be blank!")
    @Schema(description = "Name of the person", example = "John Doe")

    private String name;
    private Set<PhoneNumber> phoneNumbers;
}
