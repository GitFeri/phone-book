package phonebook.phonenumber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;

}
