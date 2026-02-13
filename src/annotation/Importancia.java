package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
// retention define el tiempo que vive la anotaciòn
@Retention(RetentionPolicy.RUNTIME)
// target permite definir donde colocar la anotaciòn
@Target(ElementType.TYPE)
public @interface Importancia {

    NivelImportancia value() default NivelImportancia.MEDIA;
}
