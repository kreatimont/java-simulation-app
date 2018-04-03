import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Main {


    public static void main(String[] args) {

        Phone phone = new Phone("Iphone 6", "133401215213", "China", 1360.0, 640.0);

        //create class from reflection
        try {

            Object objectPhone = Class.forName("Phone").getConstructor(String.class, String.class, String.class, double.class, double.class).
                    newInstance("Iphone 7", "3124235233", "China", 1920.0, 1240.0);

            Method methodDisplaySize = Phone.class.getDeclaredMethod("displaySize");
            Field modelField = Phone.class.getField("model");

            String model = String.class.cast(modelField.get(objectPhone));
            double displaySize = Double.class.cast(methodDisplaySize.invoke(objectPhone));

            System.out.println(String.format("Default phone: %1$s [displaySize: %2$f]", phone.model, phone.displaySize()));
            System.out.println(String.format("Reflection phone: %1$s [displaySize: %2$f]", model, displaySize));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}