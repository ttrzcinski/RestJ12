package com.ttrzcinski;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

//import org.joda.time.DateTime;

@Introspected
public class MessageSaved {

    @NonNull
    @NotBlank
    private Long instID;

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

    @NonNull
    @NotBlank
    private boolean instSent;

    @NonNull
    @NotBlank
    private Long instDateTime;

    public MessageSaved() {
        this.instSent = false;
    }

    public MessageSaved(final Message message, Long ID) {
        this.instSent = false;
        this.instEmail = message.getEmail();
        this.instTitle = message.getTitle();
        this.instContent = message.getContent();
        this.instMagicNumber = message.getMagicNumber();
        this.instID = ID;
        this.instDateTime = DateTime.now().getMillis();
    }

    @NonNull
    public Long getID() {
        return this.instID;
    }

    public void setID(@NonNull Long id) {
        this.instID = id;
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

    @NonNull
    public boolean isSent() {
        return this.instSent;
    }

    public void setSent(@NonNull boolean sent) {
        this.instSent = sent;
    }

    @NonNull
    public LocalDateTime getCreatedDate() {//Long getCreatedDate() {
        return this.instDateTime;
    }

    public void setCreatedDate(@NotNull LocalDateTime dateTime) {//(@NonNull long dateTime) {
        this.instDateTime = dateTime;
    }
}
