package controllers;

import play.mvc.*;




public class HomeController extends Controller {


    public Result index() {
        return ok(views.html.login.render());
    }

    public Result register(){
        return ok(views.html.register.render());
    }

    public Result handleRegister(){
        //handle registration logic here
        return redirect(routes.HomeController.index());
    }

    public Result login(){
        return ok(views.html.login.render());
    }

    public Result handleLogin(){
        //handle login logic here
        return redirect(routes.HomeController.index());
    }

    public Result forgotPassword() {
        return ok(views.html.forgotPassword.render(""));
    }

    public Result handleForgotPassword() {
        //handle forgot password logic here
        return redirect(routes.HomeController.index());
    }

}
