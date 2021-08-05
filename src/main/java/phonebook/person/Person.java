package phonebook.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import phonebook.phonenumber.PhoneNumber;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<PhoneNumber> phoneNumbers;
}
