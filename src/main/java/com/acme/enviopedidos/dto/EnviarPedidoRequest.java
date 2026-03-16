package com.acme.enviopedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnviarPedidoRequest {
    
    @NotBlank(message = "El número de pedido es obligatorio")
    private String numPedido;
    
    @NotBlank(message = "La cantidad del pedido es obligatoria")
    private String cantidadPedido;
    
    @NotBlank(message = "El código EAN es obligatorio")
    private String codigoEAN;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;
    
    @NotBlank(message = "El número de documento es obligatorio")
    private String numDocumento;
    
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    // Getters and Setters
    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public String getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(String cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public String getCodigoEAN() {
        return codigoEAN;
    }

    public void setCodigoEAN(String codigoEAN) {
        this.codigoEAN = codigoEAN;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
