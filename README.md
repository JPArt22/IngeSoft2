# Curso: Ingeniería de Software II
# Laboratorio: API REST, JWT y Servidor SSH

**Estudiante:** Jhon Edison Prieto Artunduaga  

## Descripción
[cite_start]Este proyecto implementa un servidor local en Node.js que gestiona tareas (To-Do List) utilizando operaciones CRUD[cite: 9]. [cite_start]El servidor está protegido mediante JSON Web Tokens (JWT) [cite: 14, 16, 18, 20] y se configuró para permitir el acceso remoto y manipulación de datos desde un dispositivo móvil vía protocolo SSH.

---

## 1. Pruebas Locales (Consola PC)

[cite_start]En esta fase se inició el servidor en `localhost:3000` [cite: 9] y se comprobó el flujo completo de la API mediante PowerShell.

### Inicialización, Registro y Autenticación
- [cite_start]**Imagen 01:** Inicialización del servidor (`node server.js`) y registro de un usuario nuevo mediante un `POST` a `/auth/register` con el correo `jhprieto@test.com`[cite: 9, 30]. [cite_start]El sistema responde con la confirmación de usuario creado y su ID[cite: 33].
- [cite_start]**Imagen 02:** Inicio de sesión (`POST` a `/auth/login`)[cite: 35]. 
- [cite_start]**Imagen 03:** Impresión del Token JWT generado y almacenado en memoria para las validaciones posteriores[cite: 36, 37].

### Operaciones CRUD de Tareas
- **Imagen 04:** Creación de una tarea mediante `POST` a `/tasks`, enviando el token en los headers. [cite_start]Se crea la tarea con el título "Tarea final" y estado "pending"[cite: 46, 48].
- [cite_start]**Imagen 05:** Actualización de la tarea (`PUT` a `/tasks/:id`) para cambiar exitosamente su estado a "completed"[cite: 53, 62].
- [cite_start]**Imagen 06:** Eliminación de la tarea utilizando el método `DELETE` hacia el ID específico de la tarea creada[cite: 77].

---

## 2. Configuración del Servidor SSH

Para habilitar la administración remota, se preparó el entorno de Windows Server.

- [cite_start]**Imagen 07:** Instalación del componente `OpenSSH.Server` mediante PowerShell en modo Administrador[cite: 84, 94, 99].
- [cite_start]**Imagen 08:** Arranque del servicio con `Start-Service sshd` y validación de la IP local mediante `ipconfig` para la conexión de red[cite: 100, 101].

---

## 3. Pruebas Remotas vía SSH (Dispositivo Móvil)

[cite_start]Se utilizó la aplicación móvil **Termius** [cite: 127] para conectar el teléfono a la consola del PC mediante la red local.

- [cite_start]**Imagen 09:** Pantalla de autenticación interactiva (Keyboard interactive authentication) ingresando las credenciales del host en Termius[cite: 130, 132].
- **Imagen 10:** Ejecución de operaciones desde el celular. [cite_start]Se realiza un nuevo registro y login remoto obteniendo un nuevo token JWT válido[cite: 160, 162, 166, 168]. [cite_start]Posteriormente, se crea una tarea remota mediante un `POST` con el título "Tarea desde el Celular" y descripción "Coronando el laboratorio por SSH"[cite: 169, 171].

---

**Conclusión:** Se comprobó la total funcionalidad de la API REST local, la robustez de la autenticación por JWT y se superó el reto de conectividad administrando el sistema de forma remota a través de un túnel SSH desde un terminal móvil.
