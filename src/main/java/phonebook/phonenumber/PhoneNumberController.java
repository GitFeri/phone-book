package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/phonenumber")
@AllArgsConstructor
public class PhoneNumberController {

    private PhoneNumberService phoneNumberService;

    @PostMapping
    public PhoneNumberDto createPhoneNumberAndAddToPerson(@RequestBody CreatePhoneNumberCommand command) {
        return phoneNumberService.createPhoneNumber(command);
    }

}
