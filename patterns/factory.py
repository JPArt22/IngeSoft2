from abc import ABC, abstractmethod

# 1. El Producto (Interfaz)
class Notification(ABC):
    @abstractmethod
    def send(self, message: str):
        pass

# 2. Productos Concretos
class EmailNotification(Notification):
    def send(self, message: str):
        print(f"Enviando Email: {message}")

class SMSNotification(Notification):
    def send(self, message: str):
        print(f"Enviando SMS: {message}")

# 3. El Creador (La Fábrica)
class NotificationFactory(ABC):
    @abstractmethod
    def create_notification(self) -> Notification:
        pass

    def do_something(self, message: str):
        # La fábrica usa el producto pero no sabe cuál es exactamente
        notification = self.create_notification()
        notification.send(message)

# 4. Fábricas Concretas
class EmailFactory(NotificationFactory):
    def create_notification(self) -> Notification:
        return EmailNotification()

class SMSFactory(NotificationFactory):
    def create_notification(self) -> Notification:
        return SMSNotification()

# --- Ejecución ---
if __name__ == "__main__":
    # Si el cliente quiere enviar Email
    factory = EmailFactory()
    factory.do_something("¡Hola! Este es un Factory Method puro.")