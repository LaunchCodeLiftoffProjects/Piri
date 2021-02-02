package org.launchcode.Piri.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterFormDTO extends LoginFormDTO {

    @Size(max = 30, message = "Must be no more than 30 characters.")
    private String firstName;

    @Size(max = 30, message = "Must be no more than 30 characters.")
    private String lastName;


    private String verifyPassword;

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}