package phonebook.person;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PersonNotFoundException extends AbstractThrowableProblem {

    public PersonNotFoundException(Long id) {
        super(
                URI.create("phonebook/people/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Person not found with '%d'", id));
    }
}
