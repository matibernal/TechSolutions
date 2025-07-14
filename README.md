# TechSolutions

**Aplicación móvil de gestión de pedidos, turnos presenciales y soporte técnico a distancia, desarrollada para el final de la materia Programación de Aplicaciones Móviles.**

---

## 📱 ¿Qué es TechSolutions?

TechSolutions es una app para **soporte y gestión de pedidos/reparaciones** y **turnos de atención** para empresas, instituciones o personaes. Permite que los usuarios soliciten arreglos o citas de soporte, y que el personal interno/admin gestione esas solicitudes.

---

## 🚀 Funcionalidades principales

### Usuarios (Clientes)
- **Registro y login seguro** (con verificación de email vía Firebase Auth)
- **Solicitar pedidos** de arreglos (ej: "No funciona la impresora de la sala 2")
- **Ver estado** de sus pedidos (pendiente, aceptado, rechazado)
- **Reservar, modificar o cancelar citas** presenciales de soporte.
- **Enviar consultas a atención al cliente(sugerencias, reclamos o dudas)

### Personal Interno (Admin)
- **Login seguro**
- **Ver y gestionar pedidos**: aceptar, rechazar, cambiar estado
- **Ver y gestionar citas**: ver detalles, cancelar cita
- **Visualizar nombre/email del usuario solicitante en cada pedido,cita o consulta**
- **Ver listado de consultas de atención al cliente: ver detalles completos de cada consulta/ticket enviado x usuarios.
---

## 👥 Cuentas de prueba

| Rol    | Email                  | Contraseña |
|--------|------------------------|------------|
| Admin  | matrekb@gmail.com      | matibernal123 |
| User   | matibernal2000@gmail.com | matutebernal123 |

**Igualmente usted puede crear un usuario con su email y contraseña, le llegara un email de verificación de cuenta, una vez verificado podra ingresar con su email y contraseña.

> ⚠️ El email de admin debe estar dado de alta como usuario “admin” en la colección usuarios en Firestore para acceder a la parte administrativa.

---

## 🛠️ Instalación y ejecución

1. **Clonar el repositorio**
   
bash
   git clone https://github.com/matibernal/TechSolutions.git
   
2. **Abrir el proyecto en Android Studio**

   -Asegurarse de tener instalado Android Studio Flamingo o superior
   -Sincronizar Grandle cuando lo solicite

3. **Conectar Firebase**

  -El proyecto ya incluye integración con Firebase Auth y Firestore
  - El proyecto ya incluye el archivo `google-services.json` necesario para conectar con Firebase, por lo que no es necesario configurarlo nuevamente. (Lo deje hasta que el profesor corrija el final y entregue la nota)

4. **Correr la app**

   -Ejecutar en emulador o dispositivo real Android API 29+ (SE RECOMIENDA UTILIZAR EL EMULADOR PIXEL 5 API 33)

## 🗂️ Estructura princiapl

1. **Autenticación: Firebase Auth (con verificación de correo)**
2. **Persistencia: Firestore (usuarios, pedidos, citas, consultas)**
3. **UI: Activities independiente, listados dinámicos con RecyclerView**

## 💬 Atención al cliente (consultas)

1. **Los usuarios pueden enviar un mensaje de contacto desde la app (nombre, apellido, email y mensaje)
2. **Las consultas quedan guardadas en la colección "consultas" de Firestore
3. **El personal interno(admin) puede ver todas las consultas desde la app, con detalles del usuario, mensaje y fecha.

## 👨‍🏫 Acceso del profesor a la base de datos

-El profesor Sergio Medina fue agregado como "Lector" en el proyecto de Firebase, para poder visualizar todas las colecciones, datos y reglas de seguridad.
-Se le concedio acceso al email: "sergiod.medina@davinci.edu.ar"

**Link directo al proyecto Firebase: https://console.firebase.google.com/project/techsolutions-bc434/overview

## ⚙️ Notas y aclaraciones

1. **Las reglas de Firestore estan configuradas para solo permitir accesso a usuarios autenticados.
2. **El backend se encuentra completamente en Firebase
3. **No se usan servicios pagos ni envío de mails externos: la comunicación admin-usuario queda dentro de la app y la base de datos

## 📈 Mejorar futuras (ideas para seguir la app)

-Envio de notificaciones automáticas a usuarios y admin
-Edición/borrado de consultas por parte de admin
-Integrar respuestas del admin a los mensajes de atención al cliente
-Exportar historial de pedidos/citas

**Se intento implementar una integracion con SendGrid para envió de emails automáticos a los admins cuando un usuario enviaba una consulta
**Sin embargo, la funcionalidad requería una cuenta paga, por lo que lo descarté para esta versión.

## 🏷️ Autor
**Matías Bernal**
-Final Programación de Aplicaciones Moviles - Julio 2025

