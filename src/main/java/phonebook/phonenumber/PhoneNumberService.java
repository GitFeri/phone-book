package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import phonebook.person.CreatePersonCommand;
import phonebook.person.Person;
import phonebook.person.PersonDto;
import phonebook.person.PersonRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;
    private PersonRepository personRepository;
    private ModelMapper modelMapper;


    @Transactional
    public PhoneNumberDto createPhoneNumber(CreatePhoneNumberCommand command) {
        Person person = personRepository.findById(command.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id : " + command.getPersonId()));

        PhoneNumber phoneNumber = new PhoneNumber(
                person,
                command.getPhoneNumber(),
                command.getPhoneNumberType(),
                command.getPhoneNumberAccess());

        person.getPhoneNumbers().add(phoneNumber);
        phoneNumberRepository.save(phoneNumber);
        return modelMapper.map(phoneNumber,PhoneNumberDto.class);
    }
}
