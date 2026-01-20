package org.example.ddd_study.domain.game.exception;


import org.example.ddd_study.domain.exception.DomainException;

public class GameDomainException extends DomainException {

    public GameDomainException(String message) {
        super(message);
    }

    public GameDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
