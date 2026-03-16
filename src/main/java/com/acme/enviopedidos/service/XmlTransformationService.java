package com.acme.enviopedidos.service;

import com.acme.enviopedidos.dto.EnviarPedidoRequest;
import org.springframework.stereotype.Service;

@Service
public class XmlTransformationService {

    public String transformToJsonXml(EnviarPedidoRequest request) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:env=\"http://WSDLs/EnvioPedidos/EnvioPedidosAcme\">");
        xmlBuilder.append("<soapenv:Header/>");
        xmlBuilder.append("<soapenv:Body>");
        xmlBuilder.append("<env:EnvioPedidoAcme>");
        xmlBuilder.append("<EnvioPedidoRequest>");
        
        xmlBuilder.append("<pedido>").append(escapeXml(request.getNumPedido())).append("</pedido>");
        xmlBuilder.append("<Cantidad>").append(escapeXml(request.getCantidadPedido())).append("</Cantidad>");
        xmlBuilder.append("<EAN>").append(escapeXml(request.getCodigoEAN())).append("</EAN>");
        xmlBuilder.append("<Producto>").append(escapeXml(request.getNombreProducto())).append("</Producto>");
        xmlBuilder.append("<Cedula>").append(escapeXml(request.getNumDocumento())).append("</Cedula>");
        xmlBuilder.append("<Direccion>").append(escapeXml(request.getDireccion())).append("</Direccion>");
        
        xmlBuilder.append("</EnvioPedidoRequest>");
        xmlBuilder.append("</env:EnvioPedidoAcme>");
        xmlBuilder.append("</soapenv:Body>");
        xmlBuilder.append("</soapenv:Envelope>");
        
        return xmlBuilder.toString();
    }

    public com.acme.enviopedidos.dto.EnviarPedidoResponse transformXmlToJson(String xmlResponse) {
        String codigo = extractXmlValue(xmlResponse, "Codigo");
        String mensaje = extractXmlValue(xmlResponse, "Mensaje");
        
        return new com.acme.enviopedidos.dto.EnviarPedidoResponse(codigo, mensaje);
    }

    private String extractXmlValue(String xml, String tagName) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";
        
        int startIndex = xml.indexOf(openTag);
        if (startIndex == -1) {
            return "";
        }
        
        startIndex += openTag.length();
        int endIndex = xml.indexOf(closeTag, startIndex);
        
        if (endIndex == -1) {
            return "";
        }
        
        return xml.substring(startIndex, endIndex);
    }

    private String escapeXml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}
