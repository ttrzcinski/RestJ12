package com.ttrzcinski;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

/**
 * Represents a message query with only E-mail address.
 */
@Introspected
public class MessageQuery {

    @NonNull
    @NotBlank
    private String instEmail;

    public MessageQuery() {

    }

    @NonNull
    public String getEmail() {
        return this.instEmail;
    }

    public void setEmail(@NonNull String email) {
        this.instEmail = email;
    }
}
