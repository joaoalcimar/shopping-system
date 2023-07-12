package br.com.productapi.exceptions.settings;

import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}
