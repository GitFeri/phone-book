package phonebook.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phonebook/people")
@Tag(name = "Operation on people.", description = "Performing CRUD operations on the person class.")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    @Operation(summary = "Get all people, or people by part of the name.")
    @ApiResponse(responseCode = "200", description = "The operation was successful.")
    public List<PersonDto> getPeople(@RequestParam Optional<String> partOfName) {
        return personService.getPeople(partOfName);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by id.")
    @ApiResponse(responseCode = "200", description = "The operation was successful.")
    @ApiResponse(responseCode = "404", description = "The person not exist by id.")
    public PersonDto getPersonById(@PathVariable("id") Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping()
    @Operation(summary = "Create person with name.")
    @ApiResponse(responseCode = "200", description = "A person has been created")
    public PersonDto createPerson(@Valid @RequestBody CreatePersonCommand command) {
        return personService.createPerson(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the name of a person by id.")
    @ApiResponse(responseCode = "200", description = "A person has been modified.")
    @ApiResponse(responseCode = "404", description = "The person not exist by id.")
    public PersonDto updatePerson(@PathVariable Long id, @Valid @RequestBody UpdatePersonCommand command) {
        return personService.updatePerson(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person by id.")
    @ApiResponse(responseCode = "200", description = "A person has been deleted.")
    @ApiResponse(responseCode = "404", description = "The person not exist by id.")
    public void deletePersonById(@PathVariable Long id) {
        personService.deletePersonById(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete ALL people.")
    @ApiResponse(responseCode = "200", description = "All people has been deleted.")
    public void deleteAllPeople() {
        personService.deleteAllPeople();
    }
}
