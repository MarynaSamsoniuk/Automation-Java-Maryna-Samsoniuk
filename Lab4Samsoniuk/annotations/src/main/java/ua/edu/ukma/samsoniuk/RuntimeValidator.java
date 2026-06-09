package ua.edu.ukma.samsoniuk;

import java.lang.reflect.Field;

public class RuntimeValidator {

    public static void validate(Object target, Object validatorInstance) throws IllegalArgumentException {

        for (Field field : validatorInstance.getClass().getDeclaredFields()) {
            RuntimeValidate annotation = field.getAnnotation(RuntimeValidate.class);
            if (annotation == null) continue;

            String fieldName = annotation.fieldName();

            Object value;
            try {
                Field targetField = target.getClass().getDeclaredField(fieldName);
                targetField.setAccessible(true);
                value = targetField.get(target);
            } catch (NoSuchFieldException e) {
                continue;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Немає доступу до поля " + fieldName, e);
            }

            if (annotation.notNull() && value == null) {
                throw new IllegalArgumentException("Поле '" + fieldName + "' не може бути null");
            }

            if (value instanceof String str) {
                if (annotation.minLength() >= 0 && str.length() < annotation.minLength()) {
                    throw new IllegalArgumentException(
                            "Поле '" + fieldName + "' має довжину " + str.length() + ", мінімальна допустима довжина – " + annotation.minLength());
                }
                if (annotation.maxLength() >= 0 && str.length() > annotation.maxLength()) {
                    throw new IllegalArgumentException(
                            "Поле '" + fieldName + "' має довжину " + str.length() + ", максимальна допустима довжина – " + annotation.maxLength());
                }
            }
        }
    }
}