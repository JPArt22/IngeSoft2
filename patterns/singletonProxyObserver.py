import threading
from abc import ABC, abstractmethod

# ==========================================
# PARTE 1: OBSERVER (Comportamiento)
# ==========================================
# Inspirado en tu clase EventManager y los Listeners

class CloudEventManager:
    """Gestiona las suscripciones y notificaciones de eventos en la nube."""
    def __init__(self):
        # Diccionario para guardar listas de suscriptores según el evento
        self._listeners = {}

    def subscribe(self, event_type: str, listener):
        if event_type not in self._listeners:
            self._listeners[event_type] = []
        self._listeners[event_type].append(listener)

    def notify(self, event_type: str, data: str):
        if event_type in self._listeners:
            for listener in self._listeners[event_type]:
                listener.update(data)

class EventListener(ABC):
    """Interfaz base para todos los suscriptores."""
    @abstractmethod
    def update(self, data: str):
        pass

class CloudWatchLogger(EventListener):
    """Suscriptor que simula guardar logs en AWS CloudWatch."""
    def update(self, data: str):
        print(f"📝 [CloudWatch] LOG REGISTRADO: {data}")

class SNSAlert(EventListener):
    """Suscriptor que simula enviar alertas por email vía AWS SNS."""
    def __init__(self, email: str):
        self.email = email

    def update(self, data: str):
        print(f"📧 [SNS Alert] Enviando email a {self.email} -> ALERTA: {data}")

# ==========================================
# PARTE 2: SINGLETON (Creacional)
# ==========================================
# Inspirado en tu clase Database

class AWSResourceManager:
    """
    Motor centralizado para desplegar recursos. 
    Solo debe existir UNA instancia (Singleton).
    """
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super(AWSResourceManager, cls).__new__(cls)
                    # Inicializamos el gestor de eventos integrado en el Singleton
                    cls._instance.events = CloudEventManager()
        return cls._instance

    def deploy_ec2_instance(self, instance_name: str):
        """Lógica de negocio real para crear un servidor."""
        print(f"🚀 [AWSResourceManager] Desplegando instancia EC2 '{instance_name}'...")
        # Al terminar, notificamos a todos los interesados (Observer en acción)
        self.events.notify("deploy_success", f"Instancia {instance_name} creada con éxito.")

# ==========================================
# PARTE 3: PROXY (Estructural)
# ==========================================
# Inspirado en tu CachedYouTubeClass, pero aplicado a Seguridad

class IAMSecurityProxy:
    """
    Controla el acceso al Singleton. Revisa si el usuario tiene
    el rol necesario antes de permitir el despliegue.
    """
    def __init__(self, user_role: str):
        self.user_role = user_role
        # El Proxy conoce y envuelve al Singleton real
        self._resource_manager = AWSResourceManager()

    def deploy_ec2_instance(self, instance_name: str):
        print(f"🔐 [IAM Proxy] Verificando permisos para el rol: '{self.user_role}'...")
        
        if self.user_role == "admin":
            print("✅ [IAM Proxy] Permiso concedido. Redirigiendo petición...")
            # Delega el trabajo real al Singleton
            self._resource_manager.deploy_ec2_instance(instance_name)
        else:
            print("❌ [IAM Proxy] ACCESO DENEGADO. No tienes permisos para crear recursos.")
            # Podríamos disparar un evento de fallo de seguridad aquí si quisiéramos
            self._resource_manager.events.notify("security_alert", f"Intento fallido de {self.user_role}")

# ==========================================
# PRUEBA DEL NIVEL 3 (COMBO COMPLETO)
# ==========================================
if __name__ == "__main__":
    print("--- CONFIGURANDO EL SISTEMA ---")
    # 1. Obtenemos nuestra instancia única
    manager = AWSResourceManager()
    
    # 2. Creamos los suscriptores (Observers)
    logger = CloudWatchLogger()
    email_admin = SNSAlert("admin@miempresa.com")
    email_seguridad = SNSAlert("security@miempresa.com")
    
    # 3. Los suscribimos a los eventos que les interesan
    manager.events.subscribe("deploy_success", logger)
    manager.events.subscribe("deploy_success", email_admin)
    manager.events.subscribe("security_alert", logger)
    manager.events.subscribe("security_alert", email_seguridad)

    print("\n--- CASO 1: USUARIO SIN PERMISOS ---")
    # El desarrollador intenta crear una base de datos usando el Proxy
    dev_proxy = IAMSecurityProxy(user_role="developer")
    dev_proxy.deploy_ec2_instance("Servidor_Pruebas")

    print("\n--- CASO 2: USUARIO CON PERMISOS ---")
    # El administrador intenta crear el servidor usando el Proxy
    admin_proxy = IAMSecurityProxy(user_role="admin")
    admin_proxy.deploy_ec2_instance("Servidor_Produccion")