package phonebook;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(statements = "delete from phone_numbers")
@Sql(statements = "delete from persons")
public class PersonTest {



}
