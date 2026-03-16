package com.acme.enviopedidos.controller;

import com.acme.enviopedidos.dto.EnviarPedidoRequest;
import com.acme.enviopedidos.dto.EnviarPedidoResponse;
import com.acme.enviopedidos.dto.EnviarPedidoWrapperRequest;
import com.acme.enviopedidos.dto.EnviarPedidoWrapperResponse;
import com.acme.enviopedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<EnviarPedidoWrapperResponse> enviarPedido(
            @Valid @RequestBody EnviarPedidoWrapperRequest request) {
        
        EnviarPedidoResponse response = pedidoService.procesarPedido(request.getEnviarPedido());
        
        EnviarPedidoWrapperResponse wrapperResponse = new EnviarPedidoWrapperResponse(response);
        
        return ResponseEntity.ok(wrapperResponse);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Servicio de envío de pedidos ACME está operativo");
    }
}
