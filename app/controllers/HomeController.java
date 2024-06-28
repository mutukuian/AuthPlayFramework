package controllers;


import models.Login;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.mvc.*;
import models.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;


import play.libs.concurrent.HttpExecutionContext;
import play.data.FormFactory;
import play.data.Form;


@Singleton
public class HomeController extends Controller {
    Form<User> userForm;
    private final Form<Login> loginForm;
    private final HttpExecutionContext ec;
    private final FormFactory formFactory;
    private MessagesApi messagesApi;

    @Inject
    public HomeController( HttpExecutionContext ec, FormFactory formFactory, MessagesApi messagesApi) {
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
        if(boundForm.hasErrors()) {
            return badRequest(views.html.register.render(boundForm, request, messagesApi.preferred(request)));
        }
        User user = boundForm.get();
        if (!user.getPassword().equals(confirmPassword)) {
            List<String> errs = new ArrayList<>();
            errs.add("Passwords do not match");
            Form<User> boundForm1 = boundForm.withError(new ValidationError("password", errs, new ArrayList<>()));
            return badRequest(views.html.register.render(boundForm1, request, messagesApi.preferred(request)));
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

        if (user == null || !user.getPassword().equals(login.getPassword())) {
            boundForm = boundForm.withError(new ValidationError("email", "Invalid email or password"));
            return badRequest(views.html.login.render(boundForm, request, messagesApi.preferred(request)));
        }
        // Set the user session, or any other logic you want after successful login
        return redirect(routes.HomeController.index()).flashing("success", "Logged in successfully");
    }




    public Result forgotPassword() {
        return ok(views.html.forgotPassword.render(""));
    }

    public Result handleForgotPassword() {
        // handle forgot password logic here
        return redirect(routes.HomeController.index());
    }
}
