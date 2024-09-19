package com.seniorsistemas.project.config.validation.exception;

public class PedidoIsAlreadyClosed extends RuntimeException {

    public PedidoIsAlreadyClosed() {
        super("Pedido is already closed.");
    }
}
