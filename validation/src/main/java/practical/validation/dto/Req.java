package practical.validation.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Req {

    @Size(min = 1, max = 10)
    @NotEmpty
    private String name;

    @Min(10)
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Req(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
