package com.acme.enviopedidos.service;

import com.acme.enviopedidos.dto.EnviarPedidoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlTransformationServiceTest {

    private XmlTransformationService xmlTransformationService;

    @BeforeEach
    void setUp() {
        xmlTransformationService = new XmlTransformationService();
    }

    @Test
    void testTransformToJsonXml() {
        // Arrange
        EnviarPedidoRequest request = new EnviarPedidoRequest();
        request.setNumPedido("75630275");
        request.setCantidadPedido("1");
        request.setCodigoEAN("00110000765191002104587");
        request.setNombreProducto("Armario INVAL");
        request.setNumDocumento("1113987400");
        request.setDireccion("CR 72B 45 12 APT 301");

        // Act
        String xml = xmlTransformationService.transformToJsonXml(request);

        // Assert
        assertNotNull(xml);
        assertTrue(xml.contains("<pedido>75630275</pedido>"));
        assertTrue(xml.contains("<Cantidad>1</Cantidad>"));
        assertTrue(xml.contains("<EAN>00110000765191002104587</EAN>"));
        assertTrue(xml.contains("<Producto>Armario INVAL</Producto>"));
        assertTrue(xml.contains("<Cedula>1113987400</Cedula>"));
        assertTrue(xml.contains("<Direccion>CR 72B 45 12 APT 301</Direccion>"));
        assertTrue(xml.contains("soapenv:Envelope"));
    }

    @Test
    void testTransformXmlToJson() {
        // Arrange
        String xmlResponse = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soapenv:Body>" +
                "<env:EnvioPedidoAcmeResponse>" +
                "<EnvioPedidoResponse>" +
                "<Codigo>80375472</Codigo>" +
                "<Mensaje>Entregado exitosamente al cliente</Mensaje>" +
                "</EnvioPedidoResponse>" +
                "</env:EnvioPedidoAcmeResponse>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        // Act
        var response = xmlTransformationService.transformXmlToJson(xmlResponse);

        // Assert
        assertNotNull(response);
        assertEquals("80375472", response.getCodigoEnvio());
        assertEquals("Entregado exitosamente al cliente", response.getEstado());
    }

    @Test
    void testTransformXmlToJsonWithEmptyResponse() {
        // Arrange
        String xmlResponse = "<soapenv:Envelope><soapenv:Body></soapenv:Body></soapenv:Envelope>";

        // Act
        var response = xmlTransformationService.transformXmlToJson(xmlResponse);

        // Assert
        assertNotNull(response);
        assertEquals("", response.getCodigoEnvio());
        assertEquals("", response.getEstado());
    }
}
