package com.acme.enviopedidos.dto;

public class EnviarPedidoResponse {
    
    private String codigoEnvio;
    private String estado;

    public EnviarPedidoResponse() {}

    public EnviarPedidoResponse(String codigoEnvio, String estado) {
        this.codigoEnvio = codigoEnvio;
        this.estado = estado;
    }

    // Getters and Setters
    public String getCodigoEnvio() {
        return codigoEnvio;
    }

    public void setCodigoEnvio(String codigoEnvio) {
        this.codigoEnvio = codigoEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
