# Android Ecommerce

> E-commerce app for Android platform

## Requisitos

- Android Studio 2024.1 - LadyBug.
- JDK- 11.
- Gradle 8.9.
- Dispositivo con Android - API 34.

## Instalación

1. Clona este repositorio:

   ```bash
   git clone https://github.com/javigp2002/ECommerceAndroidApp
    ```
2. Abre el proyecto en Android Studio.
3. Accede al build gradle y sincroniza el proyecto.
3. Crea un archivo `secrets.properties` en la raíz del proyecto y añade las siguientes variables:

   ```
   MAPS_API_KEY=<VALOR_API_KEY_GOOGLE>
   ``` 
4. Ejecuta el proyecto en tu dispositivo o emulador.

## Tecnologías

- Kotlin
- Retrofit2
- Coroutines
- SharedPreferences
- Google Api Maps And Play Services
- GSON

## Estructura de carpetas

```
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── com.example.ecommerce
│   │   │   │   │   ├── adapters
│   │   │   │   │   ├── api
│   │   │   │   │   ├── dependency
│   │   │   │   │   ├── domain.repository
│   │   │   │   │   ├── presentation
│   │   │   │   │   ├── ui.theme
│   │   │   │   │   ├── utils
│   │   │   ├── res
│   │   │   │   ├── drawable
│   │   │   │   ├── layout
│   │   │   │   ├── values
│   │   │   │   ├── xml

```

El código está organizado a través de la separación de capas y _clean architecture_ de manera que
se pueda mantener y escalar el proyecto de manera sencilla.

Asimismo, la capa de presentación está determinada por las distinas _activities_ y _fragments_ que
se encargan de mostrar la información al usuario, la mayoría están asociadas con su ViewModel tal y
como Google plantea que se realize.

## Lista de Endpoints

Estas se emplean a través del cliente de retrofit y en la capa de 'Datasource'. Dividimos en dos:

- **ProductApi**:
  - `GET /api/products/all`: Obtiene todos los productos.
    - `POST /api/products/add`: Añadir un producto. **ADMIN**
    - `POST /api/products/delete/{id}`: Elimina un producto. **ADMIN**
    - `PUT /api/products/edit/{id}`: Actualiza un producto. **ADMIN**
    - `POST /api/cart/buy`: Realizar la compra.
    - `POST /api/cart/add`: Añadir un producto al carrito.
    - `GET /api/cart/deleteAll`: Limpiar carrito.
    - `POST /api/cart/delete`: Eliminar un producto del carrito.
- **UserApi**:
    - `POST /api/user/login`: Iniciar sesión.

# Autores

- Rafael Delgado García-Valdecasas
- Javier González Peregrín