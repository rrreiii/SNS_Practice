package com.example.hirasawarei.sns_beta.uploadPosts;

public class ErrorHelper {
    public boolean check_errors(String[] errors) {
        for (int i = 0; i < errors.length; i++) {
            if (!errors[i].contentEquals("")) {
                return false;
            }
        }
        return true;
    }

    public String check_input_name(String u_name) {
        String error = "";
        if (u_name.equals("")) {
            error = "Not input user name";
        }
        return error;
    }

    public String check_input_pass(String u_pass) {
        String error = "";
        if (u_pass.equals("")) {
            error = "Not input password";
        }
        return error;
    }

    public String check_input_email(String u_email) {
        String error = "";
        if (u_email.equals("")) {
            error = "Not input your email address";
        }
        return error;
    }

    public String check_input_phone(String u_phone) {
        String error = "";
        if (u_phone.equals("")) {
            error = "Not input your phone number";
        }
        return error;
    }

    public String check_input_image() {
        String error = "";
        return error;
    }
}

