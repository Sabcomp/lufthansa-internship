package org.internship.library.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    private static final Logger logger = LogManager.getLogger(LibraryService.class);

    /**
     * Add a book to the library.
     * Demonstrates: TRACE, DEBUG, INFO
     */
    public String addBook(String title, String isbn) {
        logger.trace("Entering addBook() — title={}, isbn={}", title, isbn);
        logger.debug("Validating book data before insert — isbn={}", isbn);

        if (title == null || title.isBlank()) {
            logger.error("addBook() failed — title must not be blank");
            return "ERROR: title is required";
        }

        logger.info("Book added successfully — title='{}', isbn={}", title, isbn);
        return "Book '" + title + "' added.";
    }
}
