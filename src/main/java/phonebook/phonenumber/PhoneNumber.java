package phonebook.phonenumber;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import phonebook.person.Person;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Person person;

    private String phoneNumber;

    private PhoneNumberType phoneNumberType;

    private PhoneNumberAccess phoneNumberAccess;

    public PhoneNumber(Person person, String phoneNumber, PhoneNumberType phoneNumberType, PhoneNumberAccess phoneNumberAccess) {
        this.person = person;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
        this.phoneNumberAccess = phoneNumberAccess;
    }
}
