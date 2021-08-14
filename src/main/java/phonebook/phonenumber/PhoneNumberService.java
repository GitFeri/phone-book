package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import phonebook.person.Person;
import phonebook.person.PersonNotFoundException;
import phonebook.person.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;
    private PersonRepository personRepository;
    private ModelMapper modelMapper;


    @Transactional
    public PhoneNumberDto createPhoneNumber(CreatePhoneNumberCommand command) {
        Person person = personRepository
                .findById(command.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(command.getPersonId()));

        PhoneNumber phoneNumber = new PhoneNumber(
                person,
                command.getPhoneNumber(),
                command.getPhoneNumberType(),
                command.getPhoneNumberAccess());

        person.addPhoneNumber(phoneNumber);

        phoneNumberRepository.save(phoneNumber);
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }

    public List<PhoneNumberDto> getPhoneNumbers(Optional<String> partOfPhoneNumber) {
        List<PhoneNumber> phoneNumbers = phoneNumberRepository
                .findPhoneNumbersByPhoneNumberContains(partOfPhoneNumber.orElse(""));

        java.lang.reflect.Type targetListType = new TypeToken<List<PhoneNumberDto>>() {
        }.getType();

        return modelMapper.map(phoneNumbers, targetListType);
    }

    public PhoneNumberDto getPhoneNumberById(Long id) {
        PhoneNumber phoneNumber = findPhoneNumberById(id);
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }


    @Transactional
    public PhoneNumberDto updatePhoneNumber(Long id, UpdatePhoneNumberCommand command) {
        PhoneNumber phoneNumber = findPhoneNumberById(id);

        phoneNumber.setPhoneNumber(command.getPhoneNumber());
        phoneNumber.setPhoneNumberAccess(command.getPhoneNumberAccess());
        phoneNumber.setPhoneNumberType(command.getPhoneNumberType());

        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }

    public void deletePhoneNumberById(Long id) {
        phoneNumberRepository.deleteById(id);
    }

    private PhoneNumber findPhoneNumberById(Long id) {
        return phoneNumberRepository.findById(id).orElseThrow(() -> new PhoneNumberNotFoundException(id));
    }
}
