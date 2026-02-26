// Principio Abierto/Cerrado (Open/Closed Principle)
// Este principio dice que las clases deben estar abiertas para extensión
// pero cerradas para modificación. O sea, podemos agregar nueva funcionalidad
// sin tener que cambiar el código que ya existe y funciona.

// MAL EJEMPLO: Cada vez que queremos agregar un nuevo tipo de pago,
// tenemos que modificar la clase existente
class ProcesadorPagosMalo {
    public void procesarPago(String tipoPago, double monto) {
        if (tipoPago.equals("TarjetaCredito")) {
            System.out.println("Procesando pago con tarjeta de crédito: $" + monto);
            // lógica específica de tarjeta
        } else if (tipoPago.equals("PayPal")) {
            System.out.println("Procesando pago con PayPal: $" + monto);
            // lógica específica de PayPal
        } else if (tipoPago.equals("Bitcoin")) {
            System.out.println("Procesando pago con Bitcoin: $" + monto);
            // lógica específica de Bitcoin
        }
        // Cada vez que hay un nuevo método de pago, hay que modificar esta clase
        // agregando otro if. Esto es propenso a errores y difícil de mantener.
    }
}

// BUEN EJEMPLO: Usamos polimorfismo para extender sin modificar

// Definimos una interfaz común para todos los métodos de pago
interface MetodoPago {
    void procesarPago(double monto);
}

// Cada método de pago es una clase separada que implementa la interfaz
class TarjetaCredito implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con tarjeta de crédito: $" + monto);
        // lógica específica de tarjeta
    }
}

class PayPal implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con PayPal: $" + monto);
        // lógica específica de PayPal
    }
}

class Bitcoin implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con Bitcoin: $" + monto);
        // lógica específica de Bitcoin
    }
}

// Si mañana queremos agregar TransferenciaBancaria, solo creamos una nueva clase
// sin tocar ninguna de las anteriores
class TransferenciaBancaria implements MetodoPago {
    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando transferencia bancaria: $" + monto);
        // lógica específica de transferencia
    }
}

// El procesador de pagos ahora no necesita saber qué tipo de pago es
class ProcesadorPagos {
    public void procesar(MetodoPago metodoPago, double monto) {
        metodoPago.procesarPago(monto);
        // Esta clase está cerrada para modificación (no necesitamos cambiarla)
        // pero abierta para extensión (podemos agregar nuevos métodos de pago)
    }
}
