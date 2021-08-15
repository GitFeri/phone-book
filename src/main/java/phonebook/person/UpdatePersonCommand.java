package phonebook.person;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "The new name of the person", example = "John Doe")
    private String name;
}

