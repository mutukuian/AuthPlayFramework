package actions;

import play.mvc.With;

import java.lang.annotation.*;

@With(AuthenticatedAction.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {
}
