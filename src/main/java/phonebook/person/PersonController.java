package phonebook.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phonebook")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    public List<Person> getPeople() {
        return personService.getPeople();
    }

    @PostMapping()
    public PersonDto createPerson(@RequestBody CreatePersonCommand command) {
        return personService.createPerson(command);
    }

    @GetMapping("/{id}")
    public PersonDto getPersonById(@PathVariable("id") Long id) {
        return personService.getPersonById(id);
    }


    @PutMapping("/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody UpdatePersonCommand command) {
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
