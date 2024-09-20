package com.seniorsistemas.project.config.validation.exception;

public class PedidoIsAlreadyClosedException extends RuntimeException {

    public PedidoIsAlreadyClosedException() {
        super("Pedido is already closed.");
    }
}
