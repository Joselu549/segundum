package arso.segundum.puertos.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arso.segundum.puertos.ManejadorEventos;

@Component
public class ConsumidorEventos {

    @Autowired
    private ManejadorEventos manejadorEventos;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
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
