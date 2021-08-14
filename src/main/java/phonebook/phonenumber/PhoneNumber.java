package phonebook.phonenumber;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import phonebook.person.Person;

import javax.persistence.*;

@Getter
@Setter
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

    @Enumerated(value = EnumType.STRING)
    private PhoneNumberType phoneNumberType;

    @Enumerated(value = EnumType.STRING)
    private PhoneNumberAccess phoneNumberAccess;

    public PhoneNumber(Person person, String phoneNumber, PhoneNumberType phoneNumberType, PhoneNumberAccess phoneNumberAccess) {
        this.person = person;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
        this.phoneNumberAccess = phoneNumberAccess;
    }
}
