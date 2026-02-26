// Principio de Sustitución de Liskov (Liskov Substitution Principle)
// Este principio dice que las clases hijas deben poder usarse en lugar
// de sus clases padres sin romper la funcionalidad del programa.
// O sea, si alguien espera un objeto del tipo padre, debería poder
// recibir cualquier objeto hijo sin problemas.

// MAL EJEMPLO: El pingüino hereda de Ave pero no puede volar

class Ave {
    public void volar() {
        System.out.println("El ave está volando por el cielo");
    }
}

// Problema: Los pingüinos son aves pero no vuelan
class PinguinoMalo extends Ave {
    @Override
    public void volar() {
        // ¿Qué hacemos aquí? Los pingüinos no vuelan
        throw new UnsupportedOperationException("Los pingüinos no pueden volar");
        // Esto rompe el principio porque si alguien espera un Ave,
        // el Pingüino no se comporta como se espera
    }
}

// Si alguien hace esto, el programa falla:
// Ave miAve = new PinguinoMalo();
// miAve.volar(); // Boom! Excepción

// BUEN EJEMPLO: Rediseñamos la jerarquía para que tenga sentido

// Clase base más general
class Animal {
    private String nombre;
    
    public Animal(String nombre) {
        this.nombre = nombre;
    }
    
    public void comer() {
        System.out.println(nombre + " está comiendo");
    }
    
    public void moverse() {
        System.out.println(nombre + " se está moviendo");
    }
}

// Solo las aves que vuelan heredan de esta clase
class AveVoladora extends Animal {
    public AveVoladora(String nombre) {
        super(nombre);
    }
    
    public void volar() {
        System.out.println(getNombre() + " está volando por el cielo");
    }
    
    private String getNombre() {
        return "Ave";
    }
}

// Las aves que no vuelan heredan directamente de Animal
class Pinguino extends Animal {
    public Pinguino() {
        super("Pingüino");
    }
    
    public void nadar() {
        System.out.println("El pingüino está nadando en el agua");
    }
    
    @Override
    public void moverse() {
        System.out.println("El pingüino se desliza sobre el hielo");
    }
}

// Otras aves voladoras
class Aguila extends AveVoladora {
    public Aguila() {
        super("Águila");
    }
    
    public void cazar() {
        System.out.println("El águila está cazando");
    }
}

// Ahora cada clase se puede sustituir correctamente por su padre:
// - Cualquier AveVoladora puede volar
// - Cualquier Animal puede comer y moverse
// - No hay comportamientos inesperados ni excepciones raras
