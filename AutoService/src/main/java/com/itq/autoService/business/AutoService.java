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

        String conductorId = request.getConductorId();

        // Verificar si se proporcionó un conductorId válido
        if (conductorId == null || conductorId.isEmpty()) {
            response.setCode(1); 
            response.setDescription("El conductorId no fue proporcionado o es inválido.");
        } else {
            List<String> nombresConductores = new ArrayList<>();
            List<String> idsVehiculos = new ArrayList<>();

            // Recorrer la lista de autos para buscar vehículos asociados al conductor
            for (Auto auto : autos) {
                if (auto.getConductorId().equals(conductorId)) {
                    nombresConductores.add(auto.getConductorId());
                    idsVehiculos.add(auto.getIdAuto());
                }
            }

            // Verificar si se encontraron vehículos asociados
            if (nombresConductores.isEmpty() || idsVehiculos.isEmpty()) {
                response.setCode(1); 
                response.setDescription("No se encontraron vehículos asociados para el conductor con ID: " + conductorId);
            } else {
                response.setCode(0); 
                response.setDescription("Consulta exitosa. Nombres de Conductores: " + nombresConductores.toString() + ". IDs de Vehículos: " + idsVehiculos.toString());
            }
        }

        return response;
    }


    private List<Auto> obtenerVehiculosAsociados(String conductorId) {
        List<Auto> vehiculosAsociados = new ArrayList<>();
        vehiculosAsociados.add(crearAuto("VW", "Golf", "Rojo", "AB123", conductorId));
        vehiculosAsociados.add(crearAuto("Ford", "Focus", "Azul", "CD456", conductorId));
        return vehiculosAsociados;
    }

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

        String conductorId = request.getConductorId();

        // Verificar si se proporcionó un conductorId válido
        if (conductorId == null || conductorId.isEmpty()) {
            response.setCode(1); 
            response.setDescription("El conductorId no fue proporcionado o es inválido.");
        } else {
            Conductor conductorEncontrado = buscarConductorPorId(conductorId);

            if (conductorEncontrado != null) {
                response.setCode(0); 
                response.setDescription("Consulta exitosa. Conductor: " + conductorEncontrado.getNombre());
            } else {
                response.setCode(2);
                response.setDescription("No se encontró un conductor con el conductorId proporcionado.");
            }
        }

        return response;
    }

    private Conductor buscarConductorPorId(String conductorId) {
        for (Conductor conductor : conductores) {
            if (conductor.getConductorId().equals(conductorId)) {
                return conductor;
            }
        }
        return null;
    }


    
    public Ack eliminarConductor(EliminarConductorRequest request) {
        Ack ack = new Ack();

        if (request == null || request.getConductorId() == null || request.getConductorId().isEmpty()) {
            ack.setCode(1);
            ack.setDescription("La solicitud es incompleta o el conductorId no fue proporcionado.");
            return ack;
        }

        String conductorId = request.getConductorId();

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
            ack.setCode(2);
            ack.setDescription("No se encontró un conductor con el conductorId proporcionado.");
        }

        return ack;
    }

    public Ack asociarDesasociarVehiculo(AsociarDesasociarVehiculoRequest request) {
        Ack response = new Ack();

        String conductorId = request.getConductorId();
        String vehiculoId = request.getVehiculoId();

        // Verificar si se proporcionaron IDs válidos
        if (conductorId == null || conductorId.isEmpty() || vehiculoId == null || vehiculoId.isEmpty()) {
            response.setCode(1); 
            response.setDescription("Los IDs del conductor y del vehículo son obligatorios.");
        } else {
            boolean operacionExitosa = desasociarVehiculoDeConductor(conductorId, vehiculoId);

            if (operacionExitosa) {
                response.setCode(0); 
                response.setDescription("Operación de desasociación exitosa. ID del vehículo desasociado: " + vehiculoId);
            } else {
                response.setCode(2); 
                response.setDescription("No se pudo realizar la operación de desasociación");
            }
        }

        return response;
    }

    private boolean desasociarVehiculoDeConductor(String conductorId, String vehiculoId) {
        Auto vehiculoADesasociar = null;
        for (Auto auto : autos) {
            if (auto.getIdAuto().equals(vehiculoId) && auto.getConductorId().equals(conductorId)) {
                vehiculoADesasociar = auto;
                break;
            }
        }

        if (vehiculoADesasociar != null) {
            // Eliminar el vehículo de la lista de autos
            autos.remove(vehiculoADesasociar);
            return true; 
        }

        return false; // No se encontró el vehículo para desasociar
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
