package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phonebook/phonenumber")
@AllArgsConstructor
public class PhoneNumberController {

    private PhoneNumberService phoneNumberService;

    @GetMapping("/{id}")
    public PhoneNumberDto getPhoneNumberById(@PathVariable Long id) {
        return phoneNumberService.getPhoneNumberById(id);
    }

    @PostMapping
    public PhoneNumberDto createPhoneNumberAndAddToPerson(@RequestBody CreatePhoneNumberCommand command) {
        return phoneNumberService.createPhoneNumber(command);
    }

    @PutMapping("/{id}")
    public PhoneNumberDto updatePhoneNumber(@PathVariable Long id, @RequestBody UpdatePhoneNumberCommand command) {
        return phoneNumberService.updatePhoneNumber(id, command);
    }

    @DeleteMapping("/{id}")
    public void deletePhoneNumberById(@PathVariable Long id) {
        phoneNumberService.deletePhoneNumberById(id);
    }

}
