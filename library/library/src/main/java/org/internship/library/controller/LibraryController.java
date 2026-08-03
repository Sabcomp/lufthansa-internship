package org.internship.library.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.internship.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Library", description = "Library operations")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping
    @Operation(
            summary = "Add book",
            description = "Adds a new book with given title and isbn"
    )
    public ResponseEntity<String> addBook(
            @Parameter(description = "Book title") @RequestParam String title,
            @Parameter(description = "Book isbn") @RequestParam String isbn
    ){
        String result = libraryService.addBook(title, isbn);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
