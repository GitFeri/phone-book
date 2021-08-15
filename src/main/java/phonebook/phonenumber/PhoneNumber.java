package phonebook.phonenumber;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The phone number.(Start with '+', followed by numbers.)", example = "+36123456789")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Schema(description = "The phone number type", example = "MOBILE")
    private PhoneNumberType phoneNumberType;

    @Enumerated(value = EnumType.STRING)
    @Schema(description = "The phone number access.", example = "PUBLIC")
    private PhoneNumberAccess phoneNumberAccess;

    public PhoneNumber(Person person, String phoneNumber, PhoneNumberType phoneNumberType, PhoneNumberAccess phoneNumberAccess) {
        this.person = person;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
        this.phoneNumberAccess = phoneNumberAccess;
    }
}
