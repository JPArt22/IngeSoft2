import threading

class AWSConfigManager:
    """
    Patrón Singleton: Asegura que solo exista una instancia del gestor 
    de configuración de AWS en toda la aplicación.
    """
    _instance = None
    _lock = threading.Lock()  # Cerrojo para evitar problemas si hay múltiples hilos (Thread-safe)

    def __new__(cls):
        # 1. Primer chequeo rápido para ver si ya existe la instancia
        if cls._instance is None:
            # 2. Bloqueamos el hilo para que nadie más entre mientras creamos la instancia
            with cls._lock:
                # 3. Segundo chequeo (necesario por si dos hilos pasaron el primer chequeo a la vez)
                if cls._instance is None:
                    print("⚙️ [AWSConfigManager] Creando la instancia única y cargando credenciales...")
                    # Creamos la instancia real
                    cls._instance = super(AWSConfigManager, cls).__new__(cls)
                    # Inicializamos nuestros datos simulados de configuración
                    cls._instance._load_credentials()
        
        # 4. Retornamos la instancia (nueva o la que ya existía)
        return cls._instance

    def _load_credentials(self):
        """Simula la carga de variables de entorno o AWS Secrets."""
        self.aws_region = "us-east-1"
        self.access_key = "AKIA-SIMULADA-12345"
        self.is_connected = True

    def get_config_summary(self):
        """Devuelve un resumen de la configuración actual."""
        return f"Región: {self.aws_region} | Estado: {'Conectado' if self.is_connected else 'Desconectado'}"

# ==========================================
# PRUEBA DEL NIVEL 1
# ==========================================
if __name__ == "__main__":
    print("--- PRUEBA SINGLETON ---")
    config1 = AWSConfigManager()  # Aquí se crea y carga todo
    config2 = AWSConfigManager()  # Aquí simplemente devuelve la instancia ya creada

    print(config1.get_config_summary())
    print(f"¿config1 y config2 son exactamente el mismo objeto en memoria?: {config1 is config2}\n")