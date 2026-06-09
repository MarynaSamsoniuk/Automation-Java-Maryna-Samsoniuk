package ua.edu.ukma.samsoniuk;

import com.squareup.javapoet.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("ua.edu.ukma.samsoniuk.GenerateValidator")
@SupportedSourceVersion(SourceVersion.RELEASE_24)
public class ValidatorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateValidator.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                        "@GenerateValidator має використовуватись лише для класів", element);
                continue;
            }

            TypeElement classElement = (TypeElement) element;
            String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
            String className = classElement.getSimpleName().toString();

            GenerateValidator annotation = element.getAnnotation(GenerateValidator.class);
            String validatorName = annotation.validatorName();
            if (validatorName == null || validatorName.isEmpty()) {
                validatorName = className + "Validator";
            }

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(validatorName)
                    .addModifiers(Modifier.PUBLIC);

            for (Element enclosed : classElement.getEnclosedElements()) {
                if (enclosed.getKind() != ElementKind.FIELD) continue;
                Validate validate = enclosed.getAnnotation(Validate.class);
                if (validate == null) continue;

                String fieldName = enclosed.getSimpleName().toString();
                classBuilder.addField(createRuleField(fieldName, validate));
            }

            classBuilder.addMethod(MethodSpec.methodBuilder("validate")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(classElement), "obj")
                    .returns(void.class)
                    .addException(IllegalArgumentException.class)
                    .addStatement("$T.validate(obj, this)", ClassName.get(packageName, "RuntimeValidator"))
                    .build());

            try {
                JavaFile.builder(packageName, classBuilder.build())
                        .build()
                        .writeTo(processingEnv.getFiler());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        "Згенеровано " + validatorName);
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "Помилка при генерації валідатора " + e.getMessage());
            }
        }
        return true;
    }

    private FieldSpec createRuleField(String fieldName, Validate validate) {
        AnnotationSpec runtimeAnnotation = AnnotationSpec.builder(RuntimeValidate.class)
                .addMember("fieldName", "$S", fieldName)
                .addMember("notNull", "$L", validate.notNull())
                .addMember("minLength", "$L", validate.minLength())
                .addMember("maxLength", "$L", validate.maxLength())
                .build();

        return FieldSpec.builder(String.class, fieldName + "Rule")
                .addModifiers(Modifier.PRIVATE)
                .addAnnotation(runtimeAnnotation)
                .initializer("$S", fieldName)
                .build();
    }
}