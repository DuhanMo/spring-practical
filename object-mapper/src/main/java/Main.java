import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Car;
import dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User();
        user.setAge(10);
        user.setName("홍길동");

        Car car1 = new Car();
        car1.setName("AUDI");
        car1.setNumber("1111");

        Car car2 = new Car();
        car2.setName("BMW");
        car2.setNumber("2222");

        List<Car> cars = Arrays.asList(car1, car2);
        user.setCars(cars);

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json); // json 으로 출력

        User parsingUser = objectMapper.readValue(json, User.class);
        System.out.println(parsingUser); // json -> 객체

        // node parsing start

        JsonNode jsonNode = objectMapper.readTree(json);

        String objName = jsonNode.get("name").asText();
        int objAge = jsonNode.get("age").asInt();

        System.out.println("objName = " + objName);
        System.out.println("objAge = " + objAge);

        ArrayNode nodeCars = (ArrayNode) jsonNode.get("cars"); // json 내부에서도 배열이기 때문에 ArrayNode로 형변환한다.
        List<Car> objCars = objectMapper.convertValue(nodeCars, new TypeReference<>() { // convertValue를 통해 List<Car>로 변환할 것이란것을 명시한다.
        });

        System.out.println("objCars = " + objCars);

        // JsonNode는 바꾸지 못한다.
        // 하지만 ObjectNode 로 자바소스로 변경후에 데이터를 변경가능하다 -> AOP와 조합해서 사용할 수 있다.
        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "모두한");
        System.out.println(objectNode.toPrettyString());
    }
}
