package rw.axelle.ne.java_ne.utils;

import okhttp3.*;

import java.io.IOException;

public class MailtrapEmailSender {

    public static void sendEmail(String recipientEmail, String subject, String message) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@example.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"inezaaxelle44@gmail.com\"}],\"subject\":\"" + subject + "\",\"text\":\"" + message + "\",\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.api.mailtrap.io/api/send/2983893")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 477bc7f0d60d72aeca9d669568420bda")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("Mailtrap API Response: " + response.toString());
            System.out.println("Response Body: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
