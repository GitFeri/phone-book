package phonebook.person;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    private ModelMapper modelMapper;

    public List<PersonDto> getPeople(Optional<String> partOfName) {
        List<Person> people = personRepository.findPeopleByNameContains(partOfName.orElse(""));
        java.lang.reflect.Type targetListType = new TypeToken<List<PersonDto>>() {
        }.getType();
        return modelMapper.map(people, targetListType);
    }

    @Transactional
    public PersonDto createPerson(CreatePersonCommand command) {
        Person person = new Person(command.getName());
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    public PersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto updatePerson(Long id, UpdatePersonCommand command) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + id));

        person.setName(command.getName());
        return modelMapper.map(person, PersonDto.class);
    }

    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

    public void deleteAllPeople() {
        personRepository.deleteAll();
    }

}
