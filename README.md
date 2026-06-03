# CanchaYa - Backend

Backend REST de reservas de canchas deportivas. Proyecto final de Diseno de Software (UCC, Campus Pasto, 2026-1). El codigo sigue los cinco diagramas de la wiki: las clases, atributos, operaciones, capas y componentes existen tal como aparecen alli.

## Stack

Java 17 · Spring Boot 3.2 · Maven · MongoDB (Spring Data) · BCrypt para contrasenas.

## Como ejecutar

1. Tener MongoDB corriendo en `localhost:27017` (o cambiar `spring.data.mongodb.uri` en `src/main/resources/application.properties`).
2. Desde la raiz del proyecto:

```
mvn spring-boot:run
```

El backend queda en `http://localhost:8080`.

## Estructura (4 capas, dependencia descendente)

```
com.canchaya
├── controller/   AuthController, EstablecimientoController, CanchaController, ReservaController
├── service/      UsuarioService, EstablecimientoService, CanchaService, ReservaService
├── repository/   UsuarioRepository, EstablecimientoRepository, CanchaRepository, ReservaRepository
└── model/        Usuario (abstract), Cliente, AdminEstablecimiento, Establecimiento, Cancha, Reserva
                  + enums: RolUsuario, Deporte, EstadoCancha, EstadoReserva, MetodoPago
```

La capa controller responde `ResponseEntity` y delega en service; service tiene las reglas de negocio; repository accede a MongoDB; model son las entidades. Ninguna capa inferior conoce a una superior.

## Modelo de dominio

`Usuario` es abstracta y declara el metodo abstracto `getResumenPerfil()`. `Cliente` y `AdminEstablecimiento` heredan de ella y lo implementan distinto (polimorfismo de subtipo). Las relaciones entre entidades son referencias directas a objetos (`@DBRef`), como en el diagrama de clases:

- `Reserva` referencia su `Cliente` y su `Cancha`.
- `Establecimiento` referencia su `AdminEstablecimiento`.
- `Cancha` referencia su `Establecimiento`.
- `Cliente` mantiene su lista de `Reserva`.

Cada entidad tiene constructor sin argumentos, constructor completo, getters/setters y `toString()`. Los enums solo contienen estados, sin operaciones.

## Endpoints

| Metodo | Ruta | Parametros | Que hace |
|---|---|---|---|
| POST | `/auth/registro` | nombre, email, password | Registrar un Cliente |
| POST | `/auth/login` | email, password | Autenticar usuario |
| POST | `/establecimientos` | nombre, direccion, adminId | Crear establecimiento |
| GET | `/establecimientos/{id}` | - | Consultar establecimiento |
| POST | `/canchas` | nombre, deporte, establecimientoId | Agregar cancha |
| GET | `/canchas` | establecimientoId | Listar canchas de un establecimiento |
| POST | `/reservas` | clienteId, canchaId, fecha, horaInicio | Crear reserva (CONFIRMADA) |
| GET | `/reservas` | clienteId | Historial de reservas del cliente |
| PATCH | `/reservas/{reservaId}/cancelar` | - | Cancelar reserva |

## Notas de diseno

- El diagrama de capas no incluye una clase de seguridad ni DTOs, por eso no existen. Las contrasenas se guardan hasheadas con BCrypt directamente en `UsuarioService`.
- Las colecciones de MongoDB son `usuarios`, `establecimientos`, `canchas` y `reservas`. `Cliente` y `AdminEstablecimiento` comparten la coleccion `usuarios` por herencia.
- Las franjas (slots) no se persisten: la disponibilidad se calcula en tiempo de ejecucion.
