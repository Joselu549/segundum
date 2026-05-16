package arso.segundum.adaptadores;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arso.segundum.puertos.IManejadorEventos;

@Component
public class ConsumidorEventosRabbitMQ {

    @Autowired
    private IManejadorEventos manejadorEventos;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void handleEvent(Map<String, String> mensaje) {
        System.out.println("Mensaje recibido: " + mensaje);

        String tipo = mensaje.get("tipo");

        if ("usuario-creado".equals(tipo)) {
            this.manejadorEventos.usuarioCreado(mensaje.get("id"),
                    mensaje.get("email"), mensaje.get("nombre"), mensaje.get("apellidos"));
        } else if ("usuario-modificado".equals(tipo)) {
            this.manejadorEventos.usuarioModificado(mensaje.get("id"),
                    mensaje.get("nombre"), mensaje.get("apellidos"));
        }
    }
}
