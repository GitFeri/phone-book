package phonebook.phonenumber;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import phonebook.person.Person;
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
        Person person = personRepository.findById(command.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot add phone number, because person not found with id : " + command.getPersonId()));

        PhoneNumber phoneNumber = new PhoneNumber(
                person,
                command.getPhoneNumber(),
                command.getPhoneNumberType(),
                command.getPhoneNumberAccess());

        phoneNumberRepository.save(phoneNumber);
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }

    public List<PhoneNumberDto> getPhoneNumbers(Optional<String> partOfPhoneNumber) {
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findPhoneNumberByPhoneNumberContains(partOfPhoneNumber
                .orElse(""));

        java.lang.reflect.Type targetListType = new TypeToken<List<PhoneNumberDto>>() {
        }.getType();
        return modelMapper.map(phoneNumbers, targetListType);
    }

    public PhoneNumberDto getPhoneNumberById(Long id) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id)
                .orElseThrow(() -> new PhoneNumberNotFoundException(id));

        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }

    public void deletePhoneNumberById(Long id) {
        phoneNumberRepository.deleteById(id);
    }

    @Transactional
    public PhoneNumberDto updatePhoneNumber(Long id, UpdatePhoneNumberCommand command) {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Phone number not found with id: " + id));

        phoneNumber.setPhoneNumber(command.getPhoneNumber());
        phoneNumber.setPhoneNumberAccess(command.getPhoneNumberAccess());
        phoneNumber.setPhoneNumberType(command.getPhoneNumberType());

        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }
}
