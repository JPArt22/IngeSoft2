// Principio de Responsabilidad Única (Single Responsibility Principle)
// Este principio dice que cada clase debe tener una sola razón para cambiar,
// o sea, una sola responsabilidad. Si una clase hace muchas cosas diferentes,
// se vuelve difícil de mantener y entender.

// MAL EJEMPLO: Esta clase hace demasiadas cosas
class UsuarioMalo {
    private String nombre;
    private String email;
    
    public UsuarioMalo(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    // La clase gestiona datos del usuario
    public void guardarEnBaseDeDatos() {
        System.out.println("Guardando usuario en la base de datos...");
        // código para guardar
    }
    
    // Y también envía emails (¿qué tiene que ver esto con un usuario?)
    public void enviarEmailBienvenida() {
        System.out.println("Enviando email de bienvenida a: " + email);
        // código para enviar email
    }
    
    // Y también genera reportes (esto tampoco debería estar aquí)
    public void generarReporte() {
        System.out.println("Generando reporte del usuario...");
        // código para generar reporte
    }
}

// BUEN EJEMPLO: Cada clase tiene una única responsabilidad

// Esta clase solo se encarga de representar los datos del usuario
class Usuario {
    private String nombre;
    private String email;
    
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
}

// Esta clase solo se encarga de guardar usuarios en la base de datos
class UsuarioRepositorio {
    public void guardar(Usuario usuario) {
        System.out.println("Guardando usuario en la base de datos...");
        // código para guardar
    }
}

// Esta clase solo se encarga de enviar emails
class ServicioEmail {
    public void enviarBienvenida(Usuario usuario) {
        System.out.println("Enviando email de bienvenida a: " + usuario.getEmail());
        // código para enviar email
    }
}

// Esta clase solo se encarga de generar reportes
class GeneradorReportes {
    public void generarReporteUsuario(Usuario usuario) {
        System.out.println("Generando reporte del usuario: " + usuario.getNombre());
        // código para generar reporte
    }
}

// Ahora cada clase tiene una razón para cambiar:
// - Usuario cambia si cambian los datos que representan al usuario
// - UsuarioRepositorio cambia si cambia cómo guardamos en la BD
// - ServicioEmail cambia si cambia la forma de enviar emails
// - GeneradorReportes cambia si cambia el formato de los reportes
