package com.ttrzcinski;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

@Introspected
public class Book {

    @NonNull
    @NotBlank
    private String instName;

    public Book() {
    }

    @NonNull
    public String getName() {
        return this.instName;
    }

    public void setName(@NonNull String name) {
        this.instName = name;
    }
}
