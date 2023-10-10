package com.itq.autoService.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itq.autoService.dto.Ack;
import com.itq.autoService.dto.AsociarDesasociarVehiculoRequest;
import com.itq.autoService.dto.Auto;
import com.itq.autoService.dto.Conductor;
import com.itq.autoService.dto.ConsultarConductoresRequest;
import com.itq.autoService.dto.ConsultarVehiculosAsociadosRequest;
import com.itq.autoService.dto.EliminarConductorRequest;

@Component
public class AutoService {
    private List<Auto> autos = new ArrayList<>();
    private List<Conductor> conductores = new ArrayList<>();

    /*public Ack insertCar(Auto auto) {
        Ack ack = new Ack();
        System.out.println("Color recibido: '" + auto.getColor() + "'");

        if (auto.getColor().equals("rojo")) {
            ack.setDescription("Bonito color");
        } else if (auto.getColor().equals("")) {
            ack.setDescription("¿Invisible?");
        } else {
            ack.setDescription("Bueno, por lo menos tiene color.");
        }

        ack.setCode(0);

        return ack;
    }*/

    public Ack createDriver(Conductor conductor) {
        Ack ack = new Ack();

        if (conductor == null) {
            ack.setCode(1);
            ack.setDescription("La información del conductor está incompleta.");
            return ack;
        }

        // Verifica si los campos nombre, tipoLicencia y conductorId están vacíos o nulos
        if (conductor.getNombre() == null || conductor.getNombre().isEmpty() || conductor.getTipoLicencia() == null || conductor.getTipoLicencia().isEmpty() || conductor.getConductorId() == null || conductor.getConductorId().isEmpty()) {
            ack.setCode(1);
            ack.setDescription("El nombre, el tipo de licencia y el conductorId del conductor son obligatorios.");
            return ack;
        }

        // Agregamos el conductor a la lista de conductores
        conductores.add(conductor);
        ack.setCode(0);
        ack.setDescription("Conductor creado exitosamente");

        // Verificar si el conductor se encuentra en la lista
        boolean conductorCreado = conductores.contains(conductor);

        if (conductorCreado) {
            System.out.println("El conductor se creó y se encuentra en la lista de conductores.");
        } else {
            System.out.println("El conductor se creó pero no se encuentra en la lista de conductores.");
        }

        return ack;
    }


    public Ack consultarVehiculosAsociados(ConsultarVehiculosAsociadosRequest request) {
        Ack response = new Ack();

        // Obtener el ID del conductor desde la solicitud
        String conductorId = request.getConductorId();

        // Verificar si se proporcionó un conductorId válido
        if (conductorId == null || conductorId.isEmpty()) {
            response.setCode(2); // Código de error para conductorId no válido
            response.setDescription("El conductorId no fue proporcionado o es inválido.");
        } else {
            // Aquí deberías realizar la lógica para consultar los vehículos asociados al conductor
            // Supongamos que tienes una lista de vehículos llamada 'vehiculosAsociados'
            List<Auto> vehiculosAsociados = obtenerVehiculosAsociados(conductorId);

            // Verificar si se encontraron vehículos asociados
            if (vehiculosAsociados.isEmpty()) {
                response.setCode(1); // Código de error si no se encontraron vehículos asociados
                response.setDescription("No se encontraron vehículos asociados para el conductor con ID: " + conductorId);
            } else {
                response.setCode(0); // Código de éxito
                response.setDescription("Consulta exitosa");
                // No establecemos la lista de vehículos asociados en la respuesta para evitar errores
            }
        }

        return response;
    }


    // Método ficticio para obtener vehículos asociados a un conductor (debes implementarlo)
    private List<Auto> obtenerVehiculosAsociados(String conductorId) {
        // Aquí deberías realizar la lógica para consultar los vehículos asociados al conductor
        // Devuelve una lista de vehículos o una lista vacía si no se encontraron vehículos
        // Esta es una implementación ficticia, debes adaptarla a tu sistema real
        List<Auto> vehiculosAsociados = new ArrayList<>();
        // Agrega vehículos ficticios a la lista
        vehiculosAsociados.add(crearAuto("VW", "Golf", "Rojo", "AB123", conductorId));
        vehiculosAsociados.add(crearAuto("Ford", "Focus", "Azul", "CD456", conductorId));
        return vehiculosAsociados;
    }

    // Método ficticio para crear un objeto Auto
    private Auto crearAuto(String marca, String modelo, String color, String idAuto, String idClient) {
        Auto auto = new Auto();
        auto.setMarca(marca);
        auto.setModelo(modelo);
        auto.setColor(color);
        auto.setIdAuto(idAuto);
        auto.setConductorId(idClient);
        return auto;
    }

    public Ack consultarConductores(ConsultarConductoresRequest request) {
        Ack response = new Ack();

        // Obtener el conductorId desde la solicitud
        String conductorId = request.getConductorId();

        // Verificar si se proporcionó un conductorId válido
        if (conductorId == null || conductorId.isEmpty()) {
            response.setCode(1); // Código de error para conductorId no válido
            response.setDescription("El conductorId no fue proporcionado o es inválido.");
        } else {
            // Buscar el conductor en la lista de conductores por su conductorId
            Conductor conductorEncontrado = buscarConductorPorId(conductorId);

            if (conductorEncontrado != null) {
                // Se encontró el conductor, puedes incluir información sobre el conductor en la descripción
                response.setCode(0); // Código de éxito
                response.setDescription("Consulta exitosa. Conductor: " + conductorEncontrado.getNombre());
            } else {
                // No se encontró un conductor con el conductorId proporcionado
                response.setCode(2); // Código de error para conductor no encontrado
                response.setDescription("No se encontró un conductor con el conductorId proporcionado.");
            }
        }

        return response;
    }

    // Método ficticio para buscar un conductor por su ID
    private Conductor buscarConductorPorId(String conductorId) {
        for (Conductor conductor : conductores) {
            if (conductor.getConductorId().equals(conductorId)) {
                return conductor;
            }
        }
        return null; // Devuelve null si no se encontró ningún conductor con ese ID
    }


    
    public Ack eliminarConductor(EliminarConductorRequest request) {
        Ack ack = new Ack();

        if (request == null || request.getConductorId() == null || request.getConductorId().isEmpty()) {
            ack.setCode(1);
            ack.setDescription("La solicitud es incompleta o el conductorId no fue proporcionado.");
            return ack;
        }

        // Obtener el conductorId de la solicitud
        String conductorId = request.getConductorId();

        // Buscar el conductor en la lista de conductores por su conductorId
        Conductor conductorAEliminar = null;
        for (Conductor conductor : conductores) {
            if (conductor.getConductorId().equals(conductorId)) {
                conductorAEliminar = conductor;
                break; // Encontramos el conductor, salimos del bucle
            }
        }

        if (conductorAEliminar != null) {
            // Eliminar el conductor encontrado de la lista de conductores
            conductores.remove(conductorAEliminar);
            ack.setCode(0);
            ack.setDescription("Conductor eliminado exitosamente");
        } else {
            // No se encontró un conductor con el conductorId proporcionado
            ack.setCode(2); // Código de error para conductor no encontrado
            ack.setDescription("No se encontró un conductor con el conductorId proporcionado.");
        }

        return ack;
    }

    public Ack asociarDesasociarVehiculo(AsociarDesasociarVehiculoRequest request) {
        Ack response = new Ack();

        // Obtener el ID del conductor y el ID del vehículo desde la solicitud
        String conductorId = request.getConductorId();
        String vehiculoId = request.getVehiculoId();

        // Verificar si se proporcionaron IDs válidos
        if (conductorId == null || conductorId.isEmpty() || vehiculoId == null || vehiculoId.isEmpty()) {
            response.setCode(1); // Código de error para IDs no válidos
            response.setDescription("Los IDs del conductor y del vehículo son obligatorios.");
        } else {
            // Aquí deberías realizar la lógica para asociar o desasociar el vehículo del conductor
            // Puedes usar los IDs obtenidos para realizar la operación deseada

            // Verificar si la operación fue exitosa
            boolean operacionExitosa = true; // Cambia esto según el resultado de tu lógica

            if (operacionExitosa) {
                response.setCode(0); // Código de éxito
                response.setDescription("Operación de asociación/desasociación exitosa");
            } else {
                response.setCode(2); // Código de error
                response.setDescription("No se pudo realizar la operación de asociación/desasociación");
            }
        }

        return response;
    }
    public Ack insertCar(Auto auto) {
        Ack ack = new Ack();

        // Verifica si ya existe un vehículo con el mismo ID y cliente
        if (autoExiste(auto.getIdAuto(), auto.getConductorId())) {
            ack.setCode(1);
            ack.setDescription("Ya existe un vehículo con el mismo ID y cliente.");
        } else {
            // Crea un nuevo vehículo con los datos proporcionados
            Auto nuevoAuto = new Auto();
            nuevoAuto.setModelo(auto.getModelo());
            nuevoAuto.setColor(auto.getColor());
            nuevoAuto.setMarca(auto.getMarca());
            nuevoAuto.setIdAuto(auto.getIdAuto());
            nuevoAuto.setConductorId(auto.getConductorId());

            // Agrega el vehículo a la lista de vehículos
            autos.add(nuevoAuto);

            ack.setCode(0);
            ack.setDescription("Vehículo creado exitosamente");
        }

        return ack;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public List<Conductor> getConductores() {
        return conductores;
    }
    
    private boolean autoExiste(String idAuto, String idClient) {
        // Verifica si ya existe un vehículo con el mismo ID y cliente en la lista
        return autos.stream()
                .anyMatch(auto -> auto.getIdAuto().equals(idAuto) && auto.getConductorId().equals(idClient));
    }
}
