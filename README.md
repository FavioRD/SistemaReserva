# 📌 Sistema de Gestión de Reservas

Este proyecto es un **Sistema de Gestión de Reservas** desarrollado en **Java**, que permite **registrar, gestionar y consultar reservas** realizadas por clientes.

---

## 🛠️ Tecnologías Utilizadas

- ☕ **Java** *(Programación Orientada a Objetos)*
- 📄 **Manejo de Archivos CSV** *(para almacenamiento de datos)*
- 📚 **Estructuras de Datos** *(Listas, Objetos)*
- 🎯 **Paradigma MVC** *(Modelo - Vista - Controlador)*

---

## 🚀 Instalación y Ejecución

### 🔹 Clonar el repositorio
```bash
git clone https://github.com/FavioRD/SistemaReserva.git
cd sistema-reservas
```

### 🔹 Abrir el proyecto en un IDE
💻 *Recomendado: IntelliJ, Eclipse o VS Code con Extensión Java.*

### 🔹 Compilar y ejecutar la aplicación
```bash
javac src/Main.java
java src/Main
```

---

## 📌 Funcionalidades Principales

✅ **Registrar Reservas**: Permite registrar una reserva con datos como **cliente, fecha, tipo de reserva, número de personas y duración**.

✅ **Consultar Reservas**: Lista todas las reservas registradas y su estado.

✅ **Cancelar Reserva**: Modifica el estado de una reserva a **"Cancelada"**.

✅ **Guardar y Cargar Datos**: Utiliza archivos **CSV** para persistencia de datos.

✅ **Soporte para Múltiples Formatos de CSV**: Puede leer reservas en **formato antiguo y nuevo**.

---

## 📖 Uso del Sistema

### 🔹 Agregar una reserva
1. Ingresar los datos del cliente.
2. Ingresar la fecha y hora de la reserva.
3. Ingresar la cantidad de personas y duración.
4. Guardar la reserva en el sistema.

### 🔹 Consultar reservas
📋 Se pueden listar todas las reservas **activas y finalizadas**.

### 🔹 Modificar una reserva
✏️ Se puede modificar el estado o los datos de la reserva.

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! 🎉 Si deseas colaborar, sigue estos pasos:

1. **Haz un fork del proyecto**.
2. **Crea una nueva rama:**
   ```bash
   git checkout -b mi-nueva-funcionalidad
   ```
3. **Realiza tus cambios y haz un commit:**
   ```bash
   git commit -m "Agregada nueva funcionalidad X"
   ```
4. **Sube tus cambios a GitHub:**
   ```bash
   git push origin mi-nueva-funcionalidad
   ```
5. **Abre un Pull Request en el repositorio original.**

---

💡 **Si tienes alguna sugerencia o mejora, no dudes en abrir un Issue en GitHub!** 🚀
