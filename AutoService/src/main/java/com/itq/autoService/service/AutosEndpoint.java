
package com.itq.autoService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.itq.autoService.business.AutoService;
import com.itq.autoService.dto.Ack;
import com.itq.autoService.dto.Auto;
import com.itq.autoService.dto.Conductor;
import com.itq.autoService.dto.ConsultarConductoresRequest;
import com.itq.autoService.dto.EliminarConductorRequest;
import com.itq.autoService.dto.ConsultarVehiculosAsociadosRequest;
import com.itq.autoService.dto.AsociarDesasociarVehiculoRequest; 

@Endpoint
public class AutosEndpoint {
    private static final String NAMESPACE_URI = "http://com.taller";

    @Autowired
    private AutoService autoService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "auto")
    @ResponsePayload
    public Ack carRegistration(@RequestPayload Auto request) {
        Ack response = autoService.insertCar(request);
        return response;
    }

    /*@PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearVehiculoRequest")
    @ResponsePayload
    public Ack crearVehiculo(@RequestPayload Auto request) {
        Ack response = autoService.createVehicle(request);
        return response;
    }*/

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "conductor")
    @ResponsePayload
    public Ack createDriver(@RequestPayload Conductor request) {
        Ack response = autoService.createDriver(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "consultarVehiculosAsociadosRequest")
    @ResponsePayload
    public Ack consultarVehiculosAsociados(@RequestPayload ConsultarVehiculosAsociadosRequest request) {
        Ack response = autoService.consultarVehiculosAsociados(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "consultarConductoresRequest")
    @ResponsePayload
    public Ack consultarConductores(@RequestPayload ConsultarConductoresRequest request) {
        Ack response = autoService.consultarConductores(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarConductorRequest")
    @ResponsePayload
    public Ack eliminarConductor(@RequestPayload EliminarConductorRequest request) {
        Ack response = autoService.eliminarConductor(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "asociarDesasociarVehiculoRequest")
    @ResponsePayload
    public Ack asociarDesasociarVehiculo(@RequestPayload AsociarDesasociarVehiculoRequest request) {
        Ack response = autoService.asociarDesasociarVehiculo(request);
        return response;
    }
}
