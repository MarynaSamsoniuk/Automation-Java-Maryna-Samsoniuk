package ua.edu.ukma.samsoniuk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Validate {
    boolean notNull() default false;
    int minLength() default -1;
    int maxLength() default -1;
}
