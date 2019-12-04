package dumaya.dev.BibWeb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class APIException extends RuntimeException{

    public APIException(String operation, String message, String stack) {
        super("Erreur de communication avec l'API. Appel service : " + operation + " Message :" + message + " StackTrace : " + stack);
    }
}
