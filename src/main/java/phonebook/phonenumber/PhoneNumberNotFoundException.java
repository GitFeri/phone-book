package phonebook.phonenumber;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PhoneNumberNotFoundException extends AbstractThrowableProblem {

    public PhoneNumberNotFoundException(Long id) {
        super(
                URI.create("/api/phonebook/phonenumber/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Phone number not found with id '%d'", id));
    }
}
