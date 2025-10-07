import java.lang.reflect.Field;
import java.util.Objects;

public class ObjectComparator {

    public static void main(String[] args) throws Exception {
        // Example class
        Person p1 = new Person("Alice", 25, "Engineer");
        Person p2 = new Person("Alice", 30, "Architect");

        compareObjects(p1, p2);
    }

    public static void compareObjects(Object obj1, Object obj2) throws IllegalAccessException {
        if (obj1 == null || obj2 == null) {
            System.out.println("One of the objects is null.");
            return;
        }

        Class<?> clazz1 = obj1.getClass();
        Class<?> clazz2 = obj2.getClass();

        if (!clazz1.equals(clazz2)) {
            System.out.println("Objects are of different types: " + clazz1.getName() + " vs " + clazz2.getName());
            return;
        }

        System.out.println("Comparing objects of type: " + clazz1.getName());
        Field[] fields = clazz1.getDeclaredFields();

        boolean differencesFound = false;
        for (Field field : fields) {
            field.setAccessible(true); // allow access to private fields
            Object value1 = field.get(obj1);
            Object value2 = field.get(obj2);

            if (!Objects.equals(value1, value2)) {
                differencesFound = true;
                System.out.printf("Field '%s' differs: %s vs %s%n", field.getName(), value1, value2);
            }
        }

        if (!differencesFound) {
            System.out.println("All fields are equal.");
        }
    }
}

// Example class
class Person {
    private String name;
    private int age;
    private String occupation;

    public Person(String name, int age, String occupation) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
    }
}
