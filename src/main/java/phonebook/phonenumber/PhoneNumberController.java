package phonebook.phonenumber;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phonebook/phonenumber")
@Tag(name = "Operation on phone number.", description = "Performing CRUD operations on the phoneNumber class.")
@AllArgsConstructor
public class PhoneNumberController {

    private PhoneNumberService phoneNumberService;

    @GetMapping
    private List<PhoneNumberDto> getPhoneNumbers(@RequestParam Optional<String> partOfPhoneNumber) {
        return phoneNumberService.getPhoneNumbers(partOfPhoneNumber);
    }

    @GetMapping("/{id}")
    public PhoneNumberDto getPhoneNumberById(@PathVariable Long id) {
        return phoneNumberService.getPhoneNumberById(id);
    }

    @PostMapping
    public PhoneNumberDto createPhoneNumberAndAddToPerson(@Valid @RequestBody CreatePhoneNumberCommand command) {
        return phoneNumberService.createPhoneNumber(command);
    }

    @PutMapping("/{id}")
    public PhoneNumberDto updatePhoneNumber(@PathVariable Long id,@Valid @RequestBody UpdatePhoneNumberCommand command) {
        return phoneNumberService.updatePhoneNumber(id, command);
    }

    @DeleteMapping("/{id}")
    public void deletePhoneNumberById(@PathVariable Long id) {
        phoneNumberService.deletePhoneNumberById(id);
    }

}
