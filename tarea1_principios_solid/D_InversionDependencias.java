// Principio de Inversión de Dependencias (Dependency Inversion Principle)
// Este principio dice que las clases de alto nivel no deben depender de
// clases de bajo nivel. Ambas deben depender de abstracciones (interfaces).
// Además, las abstracciones no deben depender de detalles, sino al revés.
// En palabras simples: programa contra interfaces, no contra implementaciones concretas.

// MAL EJEMPLO: La clase depende directamente de implementaciones concretas

class ServicioEmail {
    public void enviar(String mensaje) {
        System.out.println("Enviando email: " + mensaje);
    }
}

class ServicioSMS {
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS: " + mensaje);
    }
}

// Esta clase está acoplada directamente a ServicioEmail
class NotificadorMalo {
    private ServicioEmail servicioEmail;
    
    public NotificadorMalo() {
        this.servicioEmail = new ServicioEmail(); // acoplamiento fuerte
    }
    
    public void notificar(String mensaje) {
        servicioEmail.enviar(mensaje);
        // Problema: Si queremos cambiar a SMS o agregar más opciones,
        // tenemos que modificar esta clase. Está atada a una implementación específica.
    }
}

// BUEN EJEMPLO: Dependemos de una abstracción, no de una implementación

// Primero definimos una interfaz (abstracción)
interface ServicioNotificacion {
    void enviar(String mensaje);
}

// Las implementaciones concretas dependen de la abstracción
class NotificacionPorEmail implements ServicioNotificacion {
    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando email: " + mensaje);
        // lógica específica de email
    }
}

class NotificacionPorSMS implements ServicioNotificacion {
    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS: " + mensaje);
        // lógica específica de SMS
    }
}

class NotificacionPorPush implements ServicioNotificacion {
    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando notificación push: " + mensaje);
        // lógica específica de push
    }
}

// La clase de alto nivel depende de la abstracción, no de las implementaciones
class Notificador {
    private ServicioNotificacion servicio;
    
    // Inyectamos la dependencia a través del constructor
    public Notificador(ServicioNotificacion servicio) {
        this.servicio = servicio;
        // Ahora esta clase no sabe ni le importa qué implementación usa.
        // Puede ser email, SMS, push, o cualquier otra que se cree en el futuro.
    }
    
    public void notificar(String mensaje) {
        servicio.enviar(mensaje);
        // Esta clase no cambia aunque agreguemos 100 tipos de notificaciones
    }
}

// Ejemplo de uso:
// Notificador notificadorEmail = new Notificador(new NotificacionPorEmail());
// notificadorEmail.notificar("Hola por email");
//
// Notificador notificadorSMS = new Notificador(new NotificacionPorSMS());
// notificadorSMS.notificar("Hola por SMS");
//
// El mismo Notificador funciona con cualquier ServicioNotificacion.
// Esto hace el código flexible, fácil de testear y de mantener.
