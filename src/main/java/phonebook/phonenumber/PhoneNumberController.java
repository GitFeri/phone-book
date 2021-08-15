package phonebook.phonenumber;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all phone number or by part of the phone number.")
    @ApiResponse(responseCode = "200", description = "The operation was successful.")
    private List<PhoneNumberDto> getPhoneNumbers(@RequestParam Optional<String> partOfPhoneNumber) {
        return phoneNumberService.getPhoneNumbers(partOfPhoneNumber);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get phone number by id.")
    @ApiResponse(responseCode = "200", description = "The operation was successful.")
    @ApiResponse(responseCode = "404", description = "The phone number not exist by id.")
    public PhoneNumberDto getPhoneNumberById(@PathVariable Long id) {
        return phoneNumberService.getPhoneNumberById(id);
    }

    @PostMapping
    @Operation(summary = "Create phone number for an existing person.")
    @ApiResponse(responseCode = "200", description = "A phone number has been created")
    @ApiResponse(responseCode = "400", description = "Invalid format of phone number.")
    @ApiResponse(responseCode = "404", description = "The person not exist by id.")
    public PhoneNumberDto createPhoneNumberAndAddToPerson(@Valid @RequestBody CreatePhoneNumberCommand command) {
        return phoneNumberService.createPhoneNumber(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a phone number and it's properties.")
    @ApiResponse(responseCode = "200", description = "A phone number has been updated.")
    @ApiResponse(responseCode = "400", description = "Invalid format of phone number.")
    @ApiResponse(responseCode = "404", description = "The phone number not exist by id.")
    public PhoneNumberDto updatePhoneNumber(@PathVariable Long id,@Valid @RequestBody UpdatePhoneNumberCommand command) {
        return phoneNumberService.updatePhoneNumber(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a phone number.")
    @ApiResponse(responseCode = "200", description = "A phone number has been deleted")
    @ApiResponse(responseCode = "404", description = "The phone number not exist by id.")
    public void deletePhoneNumberById(@PathVariable Long id) {
        phoneNumberService.deletePhoneNumberById(id);
    }

}
