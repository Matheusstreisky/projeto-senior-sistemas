package com.seniorsistemas.project.config.validation.exception;

public class ItemIsBeingUsedInPedidoException extends RuntimeException {

    public ItemIsBeingUsedInPedidoException() {
        super("The Item is being used in a Pedido.");
    }
}
