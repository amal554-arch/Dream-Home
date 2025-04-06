package dreamhome.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

public class EmailUtil {

    // 🔐 Use your Mailjet credentials
    private static final String API_KEY = "37d6825a35f01111395ef7318ff39402";
    private static final String API_SECRET = "2199af8b135190f1a2f88e854cecc496";

    public static void sendRegistrationEmail(String toEmail, String fullName, String password) {
        try {
            String htmlContent = "<html><body style='font-family:Arial,sans-serif;background:#f9f9f9;padding:20px;'>" +
                    "<div style='max-width:600px;background:#fff;padding:20px;border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,0.1);'>" +
                    "<h2 style='color:#2b7a78;'>Welcome to DreamHome, " + fullName + " 👋</h2>" +
                    "<p>Hi <strong>" + fullName + "</strong>,</p>" +
                    "<p>You have been successfully registered as a staff member in the <strong>DreamHome</strong> system.</p>" +
                    "<p><strong>Login Credentials:</strong></p>" +
                    "<ul>" +
                    "<li><strong>Email:</strong> " + toEmail + "</li>" +
                    "<li><strong>Temporary Password:</strong> admin123</li>" +
                    "</ul>" +
                    "<p>⚠️ Please log in and change your password immediately for security.</p>" +
                    "<br><p style='font-size:0.9em;color:#555;'>If you weren’t expecting this email, please ignore it or contact support.</p>" +
                    "<p style='margin-top:30px;'>Best regards,<br><strong>DreamHome Admin Team</strong></p>" +
                    "</div></body></html>";
    
            String json = "{\n" +
                    "  \"Messages\":[\n" +
                    "    {\n" +
                    "      \"From\": {\n" +
                    "        \"Email\": \"amalmohanan901@gmail.com\",\n" +
                    "        \"Name\": \"DreamHome\"\n" +
                    "      },\n" +
                    "      \"To\": [\n" +
                    "        {\n" +
                    "          \"Email\": \"" + toEmail + "\",\n" +
                    "          \"Name\": \"" + fullName + "\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"Subject\": \"Welcome to DreamHome – Your Staff Account\",\n" +
                    "      \"HTMLPart\": \"" + htmlContent.replace("\"", "\\\"").replace("\n", "") + "\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
    
            URI uri = URI.create("https://api.mailjet.com/v3.1/send");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Basic " +
                    Base64.getEncoder().encodeToString((API_KEY + ":" + API_SECRET).getBytes()));
            conn.setRequestProperty("Content-Type", "application/json");
    
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }
    
            int code = conn.getResponseCode();
            System.out.println("📬 Mailjet response: " + code);
            if (code >= 400) {
                throw new RuntimeException("❌ Failed to send email. HTTP code: " + code);
            } else {
                System.out.println("✅ Email sent to " + toEmail);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
