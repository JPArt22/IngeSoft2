# ☁️ Entrega: Patrones de Diseño aplicados a Entornos Cloud (AWS)

Para esta entrega, decidí orientar todos los ejercicios hacia el mundo del **Cloud Computing** (específicamente tomando inspiración en servicios de AWS). La razón de esto es que es un área que me gusta este área y sentí que aterrizar los patrones de diseño en problemas reales de infraestructura me ayudaría a entender mucho mejor su utilidad. 

En lugar de usar ejemplos abstractos, implementé casos de uso prácticos donde los patrones resuelven problemas de escalabilidad, compatibilidad y seguridad en la nube.

A continuación, presento la explicación de las tres partes desarrolladas:

---

## 1. Nivel 1: Patrón Creacional (Singleton)

![Diagrama del patrón Singleton](singleton.png)

**¿Qué hice acá?**
Implementé un **Gestor de Configuración de AWS** (`AWSConfigManager`). 

**¿Para qué sirve y qué problema resuelve?**
En la nube, leer credenciales o variables de entorno (como tu *Access Key*) toma tiempo y recursos. Si tengo 50 componentes en mi aplicación que necesitan conectarse a AWS, no tiene sentido que cada uno lea el archivo de configuración por su cuenta. 

El patrón **Singleton** nos asegura que la primera vez que se piden las credenciales, estas se cargan en memoria y se crea una instancia única. Si otro componente vuelve a pedir la configuración, el Singleton simplemente le devuelve la instancia que ya estaba creada, ahorrando memoria y tiempo de ejecución. Además, usé `threading.Lock()` para asegurar que si dos procesos piden la configuración exactamente al mismo milisegundo, no se creen dos instancias por accidente.

---

## 2. Nivel 2: Patrón Creacional + Estructural (Factory Method + Adapter)

![Diagrama de los patrones Factory Method y Adapter](factoryAdapter.png)

**¿Qué hice aquí?**
Creé un sistema de almacenamiento híbrido (`StorageFactory` y `FTPAdapter`) que permite a la aplicación subir archivos sin importar si el destino es un servicio en la nube moderno o un servidor antiguo.

**¿Para qué sirve y qué problema resuelve?**
Imaginemos que nuestra app guarda cosas en **AWS S3**, pero un cliente nuevo nos exige que sus archivos se guarden en un **Servidor FTP antiguo**. 
* **El Adapter (Estructural):** El servidor viejo tiene comandos distintos y complejos. Usé un Adapter para "envolver" ese código viejo y obligarlo a usar la misma interfaz de nuestra app (un simple método `.upload()`). Básicamente, actúa como un traductor.
* **El Factory Method (Creacional):** En lugar de llenar mi código principal de `if/else` para ver a dónde subo el archivo, la Fábrica se encarga de eso. Yo solo le digo "dame el servicio S3" o "dame el servicio Legacy", y me devuelve el objeto correcto.

**Resultado:** El código principal de la aplicación jamás se entera de la complejidad técnica que hay por detrás. Solo pide un servicio y usa `.upload()`.

---

## 3. Nivel 3: Creacional + Estructural + Comportamiento (Singleton + Proxy + Observer)

![Diagrama de los patrones Singleton, Proxy y Observer](singletonProxyObserver.png)

**¿Qué hice?**
Este es el ejercicio más completo. Construí un **Sistema Seguro de Despliegue de Recursos** que simula cómo se crea un servidor (Instancia EC2) de forma segura y cómo se reporta esa creación.

**¿Para qué sirve y cómo se combinan?**
Este flujo imita la vida real en una arquitectura Cloud:
1. **Singleton (El Motor - `AWSResourceManager`):** Es la clase central que se encarga de crear los servidores. Como vimos en el nivel 1, solo debe existir uno para no sobrecargar la cuenta de AWS ni perder el rastro de lo que se crea.
2. **Proxy (El Guardia - `IAMSecurityProxy`):** Yo no puedo dejar que cualquier usuario acceda directamente al Singleton para crear servidores (eso costaría mucho dinero). El Proxy se pone en medio; tiene la misma "cara" que el Singleton, pero su trabajo real es interceptar la petición, revisar si el usuario tiene rol de "admin" y, solo si es así, dejarlo pasar.
3. **Observer (El Megáfono - `CloudEventManager`):** Una vez que el servidor se crea exitosamente (o si hay un intento de hackeo), alguien tiene que avisar. El Observer detecta estos eventos y le notifica automáticamente a los "suscriptores", que en este caso son un sistema de Logs (simulando CloudWatch) y un sistema de envío de correos (simulando Amazon SNS).

**Resultado:** Logramos un código donde la lógica de creación (Singleton), la lógica de seguridad (Proxy) y la lógica de alertas (Observer) están totalmente separadas, pero trabajan juntas en un flujo limpio.

---

### Conclusión personal
Entender los patrones de diseño aislados es útil, pero el verdadero aprendizaje en este trabajo fue ver cómo **se pueden combinar**. Ningún software real usa un solo patrón; funcionan como bloques de Lego que, al unirse (como el combo del nivel 3), nos permiten armar arquitecturas robustas, seguras y fáciles de mantener.