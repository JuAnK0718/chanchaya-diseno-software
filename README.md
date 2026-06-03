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

## Pruebas de endpoints

Los 9 endpoints se probaron en cadena contra la instancia de MongoDB Atlas (registro → login → establecimiento → cancha → reserva → cancelación). Todos responden correctamente: `200 OK` (o `204 No Content` en la cancelación). La reserva nace `CONFIRMADA` con `horaFin` calculada automáticamente (`10:00` + 60 min = `11:00`) y la contraseña se guarda con hash BCrypt.

1, registrar usuario endpoint
<img width="702" height="742" alt="image" src="https://github.com/user-attachments/assets/fbe277e8-e4f4-4701-aad5-4f6450800ec2" />

2. logear usuario endpoint
<img width="707" height="748" alt="image" src="https://github.com/user-attachments/assets/d6e670c0-b644-43c0-a9b7-de69f8f7f641" />

no autorizado
<img width="702" height="755" alt="image" src="https://github.com/user-attachments/assets/b84a52ef-7e43-4ce4-a297-036d8f7ea33b" />


3. crear establecimiento
<img width="708" height="762" alt="image" src="https://github.com/user-attachments/assets/2f4289b5-b65e-4510-a2a8-4e7857138dd3" />

4.Consultar un Establecimiento
<img width="993" height="750" alt="image" src="https://github.com/user-attachments/assets/6add6e64-05ea-4ace-9201-d88f5631e3a7" />

5. agregar una cancha a establecimiento
<img width="709" height="753" alt="image" src="https://github.com/user-attachments/assets/57618703-85e5-4834-b878-192557fbdd2d" />

6. Listar Canchas de un Establecimiento
<img width="709" height="763" alt="image" src="https://github.com/user-attachments/assets/bd76762a-4683-4504-ad37-02edadd99e97" />

7.Crear una Reserva (instantánea)
<img width="711" height="755" alt="image" src="https://github.com/user-attachments/assets/f7238554-e199-439a-8ed0-2f3b97897006" />

8. Historial de Reservas del Cliente
<img width="715" height="753" alt="image" src="https://github.com/user-attachments/assets/5c286342-88ce-46d1-9b26-17712bdc5df1" />

9. Cancelar una Reserva
<img width="720" height="757" alt="image" src="https://github.com/user-attachments/assets/6ed5706a-fa02-4ead-9fa9-5f1998cc816a" />


Guía paso a paso para reproducir las pruebas en Postman (qué método, URL y parámetros poner en cada una): **[docs/postman.md](docs/postman.md)**.

> Nota: los endpoints reciben parámetros (`@RequestParam`), no JSON. En Postman usa **Body → x-www-form-urlencoded** en los `POST`, y la pestaña **Params** en los `GET`/`PATCH`.

## Notas de diseno

- El diagrama de capas no incluye una clase de seguridad ni DTOs, por eso no existen. Las contrasenas se guardan hasheadas con BCrypt directamente en `UsuarioService`.
- Las colecciones de MongoDB son `usuarios`, `establecimientos`, `canchas` y `reservas`. `Cliente` y `AdminEstablecimiento` comparten la coleccion `usuarios` por herencia.
- Las franjas (slots) no se persisten: la disponibilidad se calcula en tiempo de ejecucion.
