package controllers;

import models.Login;
import models.User;
import play.data.FormFactory;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.mvc.*;
import play.libs.concurrent.HttpExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import actions.Authenticated;

@Singleton
public class HomeController extends Controller {
    private final Form<User> userForm;
    private final Form<Login> loginForm;
    private final HttpExecutionContext ec;
    private final FormFactory formFactory;
    private final MessagesApi messagesApi;

    @Inject
    public HomeController(HttpExecutionContext ec, FormFactory formFactory, MessagesApi messagesApi) {
        this.loginForm = formFactory.form(Login.class);
        this.ec = ec;
        this.formFactory = formFactory;
        this.userForm = formFactory.form(User.class);
        this.messagesApi = messagesApi;
    }

    public Result index(Http.Request request) {
        return ok(views.html.login.render(loginForm, request, messagesApi.preferred(request)));
    }

    public Result register(Http.Request request) {
        return ok(views.html.register.render(userForm, request, messagesApi.preferred(request)));
    }

    public Result handleRegister(Http.Request request) {
        Form<User> boundForm = userForm.bindFromRequest(request);
        String confirmPassword = formFactory.form().bindFromRequest(request).get("confirm_password");

        if (boundForm.hasErrors()) {
            return badRequest(views.html.register.render(boundForm, request, messagesApi.preferred(request)));
        }

        User user = boundForm.get();

        if (!user.isValidEmailFormat(user.getEmail())) {
            boundForm = boundForm.withError(new ValidationError("email", "Invalid email format"));
            return badRequest(views.html.register.render(boundForm, request, messagesApi.preferred(request)));
        }

        if (!user.getPassword().equals(confirmPassword)) {
            boundForm = boundForm.withError(new ValidationError("password", "Passwords do not match"));
            return badRequest(views.html.register.render(boundForm, request, messagesApi.preferred(request)));
        }

        User existingUser = User.find.query().where().eq("email", user.getEmail()).findOne();
        if (existingUser != null) {
            boundForm = boundForm.withError(new ValidationError("email", "Email is already taken"));
            return badRequest(views.html.register.render(boundForm, request, messagesApi.preferred(request)));
        }

        user.setPassword(user.getPassword());
        user.save();

        return redirect(routes.HomeController.register()).flashing("success", "User successfully registered");
    }

    public Result login(Http.Request request) {
        return ok(views.html.login.render(loginForm, request, messagesApi.preferred(request)));
    }

    public Result handleLogin(Http.Request request) {
        Form<Login> boundForm = loginForm.bindFromRequest(request);
        if (boundForm.hasErrors()) {
            return badRequest(views.html.login.render(boundForm, request, messagesApi.preferred(request)));
        }
        Login login = boundForm.get();
        User user = User.find.query().where().eq("email", login.getEmail()).findOne();

        if (user == null || !user.checkPassword(login.getPassword())) {
            boundForm = boundForm.withError(new ValidationError("email", "Invalid email or password"));
            return badRequest(views.html.login.render(boundForm, request, messagesApi.preferred(request)));
        }
        return redirect(routes.HomeController.dashboard()).flashing("success", "Logged in successfully")
                .addingToSession(request, "userId", user.getId().toString());
    }

    @Authenticated
    public Result dashboard(Http.Request request) {
        return ok(views.html.dashboard.render());
    }

    public Result forgotPassword() {
        return ok(views.html.forgotPassword.render(""));
    }

    public Result handleForgotPassword() {
        return redirect(routes.HomeController.index());
    }

    public Result logout() {
        return redirect(routes.HomeController.index()).withNewSession().flashing("success", "You have been logged out");
    }
}