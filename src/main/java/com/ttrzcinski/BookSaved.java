package com.ttrzcinski;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class BookSaved {

    @NonNull
    @NotBlank
    private String instName;

    @NonNull
    @NotBlank
    private String instISBN;

    public BookSaved() {

    }

    @NonNull
    public String getName() {
        return this.instName;
    }

    public void setName(@NonNull String name) {
        this.instName = name;
    }

    @NonNull
    public String getIsbn() {
        return this.instISBN;
    }

    public void setIsbn(@NonNull String isbn) {
        this.instISBN = isbn;
    }
}
