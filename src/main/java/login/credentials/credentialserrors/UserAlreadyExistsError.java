package login.credentials.credentialserrors;

import connection.Database;

import java.util.HashMap;

public class UserAlreadyExistsError extends CredentialsError {
    @Override
    public boolean check(HashMap<String,String> userItems){
        if(!Database.registerCredentials(userItems.get("username"),
                userItems.get("password"),
                userItems.get("email"))) {
            super.errorMessage = "Username already exists";
            return false;
        }
        return true;
    }
}
