package phonebook.person;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonCommand {

    @NotNull
    @NotBlank(message = "Name can not be blank!")
    private String name;
}

