package dreamhome.utils;

import dreamhome.model.Staff;

public class Session {
    private static Staff loggedInStaff = null;

    public static void login(Staff staff) {
        loggedInStaff = staff;
    }

    public static Staff getLoggedInStaff() {
        return loggedInStaff;
    }

    public static void logout() {
        loggedInStaff = null;
    }

    public static boolean isLoggedIn() {
        return loggedInStaff != null;
    }

    public static boolean isManager() {
        return isLoggedIn() && "Manager".equalsIgnoreCase(loggedInStaff.getRole());
    }

    public static boolean isSupervisor() {
        return isLoggedIn() && "Supervisor".equalsIgnoreCase(loggedInStaff.getRole());
    }

    public static boolean isAssistant() {
        return isLoggedIn() && "Assistant".equalsIgnoreCase(loggedInStaff.getRole());
    }
}
