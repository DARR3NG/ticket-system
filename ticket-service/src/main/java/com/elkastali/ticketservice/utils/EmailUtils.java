package com.elkastali.ticketservice.utils;

import com.elkastali.ticketservice.entities.User;

public class EmailUtils {

    public static String getVerifyMessage(User user, String host, String token) {
        return "Hello " + user.getNom() + ",\n" +
                "Welcome to Ticket Service. \n\n" +
                "Your email: " + user.getEmail() + "\n" +
                "Your  password: " + user.getPassword() +
                "\n\nPlease click on the below link to verify your account:\n" +
                getVerificationUrl(host, token) +
                "\n\nThanks,\nTicket Service Team";
    }

    public static String getUpdateMessage(User user) {
        return "Hi " + user.getNom() + ",\n" +
                "Your Ticket Service account has been updated successfully.\n\n" +
                "Your email: " + user.getEmail() + "\n" +
                "Your  password: " + user.getPassword() +" \n\n"+
                "If you have any questions or concerns, feel free to contact us.\n\n" +
                "Thanks,\nTicket Service Team";
    }

    private static  String getVerificationUrl(String host,String token){
        return host + "/api/users/verify?token=" + token;
    }
}
