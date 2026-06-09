package ua.edu.ukma.samsoniuk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RuntimeValidate {
    String fieldName();
    boolean notNull() default false;
    int minLength() default -1;
    int maxLength() default -1;
}