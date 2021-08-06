package phonebook.phonenumber;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.person.Person;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhoneNumberCommand {

    private Long personId;

    @Schema(description = "Phone number", example = "+3635456789")
    @NotNull
    @NotBlank(message = "Phone number can not be blank!")
    private String phoneNumber;

    @Schema(description = "Type of the phone number", example = "MOBILE")
    private PhoneNumberType phoneNumberType;

    @Schema(description = "Access of the phone number", example = "SECRET")
    private PhoneNumberAccess phoneNumberAccess;


}
