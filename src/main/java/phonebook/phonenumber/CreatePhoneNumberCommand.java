package phonebook.phonenumber;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhoneNumberCommand {

    @Schema(description = "The person id for the phone number.", example = "12345")
    @NotNull
    private Long personId;

    @Schema(description = "Phone number", example = "+3635456789")
    @NotNull
    @NotBlank(message = "Phone number can not be blank!")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Must star with '+' sign, followed by any numbers.")
    private String phoneNumber;

    @Schema(description = "Type of the phone number", example = "MOBILE")
    @NotNull
    private PhoneNumberType phoneNumberType;

    @Schema(description = "Access of the phone number", example = "SECRET")
    @NotNull
    private PhoneNumberAccess phoneNumberAccess;


}
