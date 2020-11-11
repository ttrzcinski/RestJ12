package com.ttrzcinski;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

/**
 * Represents a message as newly added E-mail message with values Email:, Title:, </br>
 * Content: and Magic Number.
 */
@Introspected
public class Message {

    @NonNull
    @NotBlank
    private String instEmail;

    @NonNull
    @NotBlank
    private String instTitle;

    @NonNull
    @NotBlank
    private String instContent;

    @NonNull
    @NotBlank
    private Long instMagicNumber;

    public Message() {

    }

    @NonNull
    public String getEmail() {
        return this.instEmail;
    }

    public void setEmail(@NonNull String email) {
        this.instEmail = email;
    }

    @NonNull
    public String getTitle() {
        return this.instTitle;
    }

    public void setTitle(@NonNull String title) {
        this.instTitle = title;
    }

    @NonNull
    public String getContent() {
        return this.instContent;
    }

    public void setContent(@NonNull String content) {
        this.instContent = content;
    }
    
    @NonNull
    public Long getMagicNumber() {
        return this.instMagicNumber;
    }

    public void setMagicNumber(@NonNull Long magicNumber) {
        this.instMagicNumber = magicNumber;
    }
}
