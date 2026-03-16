package com.acme.enviopedidos.dto;

public class EnviarPedidoWrapperResponse {
    
    private EnviarPedidoResponse enviarPedidoRespuesta;

    public EnviarPedidoWrapperResponse() {}

    public EnviarPedidoWrapperResponse(EnviarPedidoResponse enviarPedidoRespuesta) {
        this.enviarPedidoRespuesta = enviarPedidoRespuesta;
    }

    public EnviarPedidoResponse getEnviarPedidoRespuesta() {
        return enviarPedidoRespuesta;
    }

    public void setEnviarPedidoRespuesta(EnviarPedidoResponse enviarPedidoRespuesta) {
        this.enviarPedidoRespuesta = enviarPedidoRespuesta;
    }
}
