package com.acme.enviopedidos.service;

import com.acme.enviopedidos.dto.EnviarPedidoRequest;
import com.acme.enviopedidos.dto.EnviarPedidoResponse;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final XmlTransformationService xmlTransformationService;
    private final ExternalApiService externalApiService;

    public PedidoService(XmlTransformationService xmlTransformationService, ExternalApiService externalApiService) {
        this.xmlTransformationService = xmlTransformationService;
        this.externalApiService = externalApiService;
    }

    public EnviarPedidoResponse procesarPedido(EnviarPedidoRequest request) {
        // Transform JSON to XML
        String xmlRequest = xmlTransformationService.transformToJsonXml(request);
        
        // Send XML request to external API
        String xmlResponse = externalApiService.sendXmlRequest(xmlRequest);
        
        // Transform XML response to JSON
        return xmlTransformationService.transformXmlToJson(xmlResponse);
    }
}
