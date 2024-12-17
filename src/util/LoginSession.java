package util;

import model.User;

public class LoginSession {
	
	// singleton untuk menyimpan data user yang sedang logged in
    private static volatile LoginSession instance;
    private static User loggedInUser;

    private LoginSession() { }

    public static LoginSession getInstance() {
        if (instance == null) {
            synchronized (LoginSession.class) {
                if (instance == null) {
                    instance = new LoginSession();
                }
            }
        }
        return instance;
    }

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User loggedInUser) {
		LoginSession.loggedInUser = loggedInUser;
	}

}
