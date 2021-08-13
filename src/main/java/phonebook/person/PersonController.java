package phonebook.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phonebook/people")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    public List<PersonDto> getPeople(@RequestParam Optional<String> partOfName) {
        return personService.getPeople(partOfName);
    }

    @GetMapping("/{id}")
    public PersonDto getPersonById(@PathVariable("id") Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping()
    public PersonDto createPerson(@Valid @RequestBody CreatePersonCommand command) {
        return personService.createPerson(command);
    }

    @PutMapping("/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @Valid @RequestBody UpdatePersonCommand command) {
        return personService.updatePerson(id, command);
    }

    @DeleteMapping("/{id}")
    public void deletePersonById(@PathVariable Long id) {
        personService.deletePersonById(id);
    }

    @DeleteMapping
    public void deleteAllPeople() {
        personService.deleteAllPeople();
    }
}
