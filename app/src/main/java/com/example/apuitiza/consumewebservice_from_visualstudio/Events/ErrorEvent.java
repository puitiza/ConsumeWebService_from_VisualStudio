package com.example.apuitiza.consumewebservice_from_visualstudio.Events;

public class ErrorEvent {

    private final String txtError;

    public ErrorEvent(String txtError) {
        this.txtError = txtError;
    }

    public String getTxtError() {
        return txtError;
    }
}
