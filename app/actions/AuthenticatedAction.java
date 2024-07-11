package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class AuthenticatedAction extends Action<Authenticated> {

    @Override
    public CompletionStage<Result> call(Http.Request req) {
        String userId = req.session().getOptional("userId").orElse(null);
        if (userId == null) {
            return delegate.call(req).thenApply(result -> {
                if (result.status() == Http.Status.OK) {
                    return redirect(controllers.routes.HomeController.login());
                } else {
                    return result;
                }
            });
        }
        return delegate.call(req);
    }
}
