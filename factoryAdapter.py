from abc import ABC, abstractmethod

# ==========================================
# 1. LA INTERFAZ BASE (El estándar de nuestra app)
# ==========================================
class CloudStorage(ABC):
    """Interfaz que todos nuestros servicios de almacenamiento DEBEN respetar."""
    @abstractmethod
    def upload(self, file_name: str):
        pass

# ==========================================
# 2. PRODUCTO CONCRETO (El servicio moderno)
# ==========================================
class S3Storage(CloudStorage):
    """Implementación nativa y moderna para AWS S3."""
    def upload(self, file_name: str):
        print(f"☁️ [S3Storage] Subiendo '{file_name}' al bucket de AWS S3 de forma segura.")

# ==========================================
# 3. EL SISTEMA INCOMPATIBLE (El que necesita el Adapter)
# ==========================================
class LegacyFTPServer:
    """Un sistema viejo que no respeta nuestra interfaz CloudStorage."""
    def upload_to_legacy_ftp(self, path: str, timeout: int):
        print(f"💾 [LegacyFTPServer] Transfiriendo '{path}' por FTP antiguo con timeout de {timeout}s.")

# ==========================================
# 4. EL ADAPTER (Patrón Estructural)
# ==========================================
class FTPAdapter(CloudStorage):
    """
    Adapta el LegacyFTPServer para que luzca y actúe como un CloudStorage moderno.
    """
    def __init__(self):
        # El adaptador "envuelve" al sistema viejo
        self.legacy_ftp = LegacyFTPServer()

    def upload(self, file_name: str):
        # Traducimos el método moderno 'upload' al método viejo 'upload_to_legacy_ftp'
        print("🔌 [FTPAdapter] Adaptando la petición moderna al sistema viejo...")
        self.legacy_ftp.upload_to_legacy_ftp(path=file_name, timeout=30)

# ==========================================
# 5. EL FACTORY METHOD (Patrón Creacional)
# ==========================================
class StorageFactory:
    """
    Fábrica que decide qué servicio instanciar basándose en la configuración.
    """
    @staticmethod
    def get_storage_client(target_system: str) -> CloudStorage:
        if target_system.upper() == "AWS_S3":
            return S3Storage()
        elif target_system.upper() == "LEGACY_FTP":
            return FTPAdapter()
        else:
            raise ValueError(f"Sistema de almacenamiento desconocido: {target_system}")

# ==========================================
# PRUEBA DEL NIVEL 2
# ==========================================
if __name__ == "__main__":
    print("--- PRUEBA FACTORY + ADAPTER ---")
    
    # El cliente (nuestra app) pide un cliente S3
    cliente_s3 = StorageFactory.get_storage_client("AWS_S3")
    cliente_s3.upload("reporte_financiero.pdf")
    
    print("-" * 20)
    
    # El cliente pide conectarse al sistema viejo, pero lo usa IGUAL que el S3
    cliente_ftp = StorageFactory.get_storage_client("LEGACY_FTP")
    cliente_ftp.upload("backup_2015.zip")