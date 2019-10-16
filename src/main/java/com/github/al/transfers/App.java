package com.github.al.transfers;

import com.github.al.transfers.web.AccountController;
import com.github.al.transfers.web.ErrorResponse;
import com.github.al.transfers.web.TransferController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final int DEFAULT_PORT = 7000;

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                int port = Integer.parseInt(args[0]);
                start(port);
            } catch (NumberFormatException ex) {
                start(DEFAULT_PORT);
            }
        } else {
            start(DEFAULT_PORT);
        }
    }

    public static Javalin start(int port) {

        Injector injector = Guice.createInjector(new AppModule());

        Javalin app = Javalin.create().start(port);

        configureRoutes(app, injector);
        configureExceptions(app);

        return app;
    }

    private static void configureExceptions(Javalin app) {
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ErrorResponse errorResponse = new ErrorResponse(String.format("failed to find resource %s", ctx.path()));

            ctx.json(errorResponse).status(HttpStatus.NOT_FOUND_404);
        });
        app.exception(BadRequestResponse.class, (e, ctx) -> {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            ctx.json(errorResponse).status(HttpStatus.BAD_REQUEST_400);
        });
        app.exception(ServiceException.class, (e, ctx) -> {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            ctx.json(errorResponse).status(HttpStatus.BAD_REQUEST_400);
        });
    }

    private static void configureRoutes(Javalin app, Injector injector) {
        app.routes(() -> {
            get("/", ctx -> ctx.result("Transfer Backend"));
            path("api", () -> {
                AccountController accountController = injector.getInstance(AccountController.class);
                TransferController transferController = injector.getInstance(TransferController.class);
                get("accounts", accountController::findAll);
                get("accounts/:id", accountController::findById);
                get("accounts/:id/transfers", transferController::findByAccountId);
                post("accounts", accountController::create);
                get("transfers", transferController::findAll);
                get("transfers/:id", transferController::findById);
                post("transfers", transferController::transfer);
            });
        });
    }
}