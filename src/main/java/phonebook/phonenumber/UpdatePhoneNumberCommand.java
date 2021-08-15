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
public class UpdatePhoneNumberCommand {

    @NotNull
    @NotBlank(message = "Phone number can not be blank!")
    @Schema(description = "The new phone number.", example = "+36123456789")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Must star with '+' sign, followed by any numbers.")
    private String phoneNumber;

    @Schema(description = "", example = "")
    private PhoneNumberType phoneNumberType;

    private PhoneNumberAccess phoneNumberAccess;

}
