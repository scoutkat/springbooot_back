package com.acme.enviopedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class EnviarPedidoWrapperRequest {
    
    @Valid
    @NotNull(message = "Los datos del pedido son obligatorios")
    private EnviarPedidoRequest enviarPedido;

    public EnviarPedidoRequest getEnviarPedido() {
        return enviarPedido;
    }

    public void setEnviarPedido(EnviarPedidoRequest enviarPedido) {
        this.enviarPedido = enviarPedido;
    }
}
