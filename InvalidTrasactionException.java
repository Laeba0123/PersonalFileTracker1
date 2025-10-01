package com.laiba.BeginnerProjects.PersonalFileTracker;
/**
 * CUSTOM EXCEPTION: For handling transaction-related errors
 * EXCEPTION HANDLING CONCEPT: Creating custom exceptions for specific error types
 */
public class InvalidTrasactionException extends Exception{
    public InvalidTrasactionException(String message) {
        super(message);
    }
    /**
     * Constructor with message and cause
     * Why include cause? Helps with debugging by showing the original exception
     */
    public InvalidTrasactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
