package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {

        // Add the error message to flash attributes (survives the redirect)
        redirectAttributes.addFlashAttribute("errorMessage", "One or more files are too large! Maximum allowed size is 5MB per image.");

        // Redirect back to the GET route for your add property page
        return "redirect:/properties/add";
    }
}