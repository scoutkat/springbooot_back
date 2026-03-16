package com.acme.enviopedidos.controller;

import com.acme.enviopedidos.dto.EnviarPedidoRequest;
import com.acme.enviopedidos.dto.EnviarPedidoResponse;
import com.acme.enviopedidos.dto.EnviarPedidoWrapperRequest;
import com.acme.enviopedidos.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEnviarPedido() throws Exception {
        // Arrange
        EnviarPedidoRequest pedidoRequest = new EnviarPedidoRequest();
        pedidoRequest.setNumPedido("75630275");
        pedidoRequest.setCantidadPedido("1");
        pedidoRequest.setCodigoEAN("00110000765191002104587");
        pedidoRequest.setNombreProducto("Armario INVAL");
        pedidoRequest.setNumDocumento("1113987400");
        pedidoRequest.setDireccion("CR 72B 45 12 APT 301");

        EnviarPedidoWrapperRequest wrapperRequest = new EnviarPedidoWrapperRequest();
        wrapperRequest.setEnviarPedido(pedidoRequest);

        EnviarPedidoResponse response = new EnviarPedidoResponse("80375472", "Entregado exitosamente al cliente");

        when(pedidoService.procesarPedido(any(EnviarPedidoRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/pedidos/enviar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrapperRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enviarPedidoRespuesta.codigoEnvio").value("80375472"))
                .andExpect(jsonPath("$.enviarPedidoRespuesta.estado").value("Entregado exitosamente al cliente"));
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/pedidos/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Servicio de envío de pedidos ACME está operativo"));
    }
}
