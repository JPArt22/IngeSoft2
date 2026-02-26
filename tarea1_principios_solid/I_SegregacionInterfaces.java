// Principio de Segregación de Interfaces (Interface Segregation Principle)
// Este principio dice que es mejor tener varias interfaces pequeñas y específicas
// que una interfaz grande que haga de todo. Las clases no deberían verse obligadas
// a implementar métodos que no usan.

// MAL EJEMPLO: Una interfaz gigante que obliga a implementar métodos innecesarios

interface DispositivoMultifuncionalMalo {
    void imprimir();
    void escanear();
    void enviarFax();
    void fotocopiar();
}

// Una impresora moderna tiene todas estas funciones
class ImpresoraModerna implements DispositivoMultifuncionalMalo {
    @Override
    public void imprimir() {
        System.out.println("Imprimiendo documento...");
    }
    
    @Override
    public void escanear() {
        System.out.println("Escaneando documento...");
    }
    
    @Override
    public void enviarFax() {
        System.out.println("Enviando fax...");
    }
    
    @Override
    public void fotocopiar() {
        System.out.println("Fotocopiando documento...");
    }
}

// Problema: Una impresora básica no tiene escáner ni fax,
// pero se ve obligada a implementar esos métodos
class ImpresoraBasica implements DispositivoMultifuncionalMalo {
    @Override
    public void imprimir() {
        System.out.println("Imprimiendo documento...");
    }
    
    @Override
    public void escanear() {
        throw new UnsupportedOperationException("Esta impresora no puede escanear");
    }
    
    @Override
    public void enviarFax() {
        throw new UnsupportedOperationException("Esta impresora no puede enviar fax");
    }
    
    @Override
    public void fotocopiar() {
        throw new UnsupportedOperationException("Esta impresora no puede fotocopiar");
    }
    // Esto es horrible: tenemos que implementar métodos que no tienen sentido
}

// BUEN EJEMPLO: Dividimos la interfaz en interfaces más pequeñas y específicas

// Cada funcionalidad tiene su propia interfaz
interface Impresora {
    void imprimir();
}

interface Escaner {
    void escanear();
}

interface Fax {
    void enviarFax();
}

interface Fotocopiadora {
    void fotocopiar();
}

// Una impresora básica solo implementa lo que puede hacer
class ImpresoraSencilla implements Impresora {
    @Override
    public void imprimir() {
        System.out.println("Imprimiendo documento...");
    }
    // Solo implementa imprimir, no tiene métodos que no use
}

// Una impresora multifuncional implementa todas las interfaces que necesita
class ImpresoraMultifuncional implements Impresora, Escaner, Fax, Fotocopiadora {
    @Override
    public void imprimir() {
        System.out.println("Imprimiendo documento...");
    }
    
    @Override
    public void escanear() {
        System.out.println("Escaneando documento...");
    }
    
    @Override
    public void enviarFax() {
        System.out.println("Enviando fax...");
    }
    
    @Override
    public void fotocopiar() {
        System.out.println("Fotocopiando documento...");
    }
}

// Un escáner standalone solo necesita implementar escanear
class EscanerIndependiente implements Escaner {
    @Override
    public void escanear() {
        System.out.println("Escaneando documento con escáner profesional...");
    }
}

// Ahora cada clase solo implementa lo que realmente necesita.
// No hay métodos vacíos ni excepciones raras.
