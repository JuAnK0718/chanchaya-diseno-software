# CanchaYa — Backend REST

Backend de reservas de canchas deportivas. Proyecto final de Diseño de Software — UCC Campus Pasto, 2026-1. El código implementa los cinco diagramas de la wiki: clases, atributos, operaciones, capas y componentes tal como aparecen allí.

---

## Stack

| | |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.2 |
| Build | Maven |
| Base de datos | MongoDB · Spring Data |
| Seguridad | HTTP Basic · BCrypt |

---

## Ejecución

Requiere MongoDB en `localhost:27017` (o variable de entorno `MONGODB_URI` con URI de Atlas).

```
mvn spring-boot:run
```

El backend queda disponible en `http://localhost:8080`.

---

## Arquitectura en capas

```
com.canchaya
├── controller/   AuthController · EstablecimientoController · CanchaController · ReservaController
├── service/      UsuarioService · EstablecimientoService · CanchaService · ReservaService
├── repository/   UsuarioRepository · EstablecimientoRepository · CanchaRepository · ReservaRepository
└── model/        Usuario (abstract) · Cliente · AdminEstablecimiento
                  Establecimiento · Cancha · Reserva
                  enums: RolUsuario · Deporte · EstadoCancha · EstadoReserva · MetodoPago
```

Dependencia estrictamente descendente: controller → service → repository → model. Ninguna capa inferior conoce a una superior.

---

## Modelo de dominio

`Usuario` es abstracta y declara `getResumenPerfil()`. `Cliente` y `AdminEstablecimiento` la implementan de forma distinta (polimorfismo). Las relaciones entre entidades son referencias directas (`@DBRef`):

- `Reserva` → `Cliente` + `Cancha`
- `Establecimiento` → `AdminEstablecimiento`
- `Cancha` → `Establecimiento`
- `Cliente` → `List<Reserva>`

---

## Endpoints

> Los endpoints reciben `@RequestParam`, no JSON. En Postman: **Body → x-www-form-urlencoded** en los POST, pestaña **Params** en GET/PATCH.

| Método | Ruta | Parámetros |
|---|---|---|
| `POST` | `/auth/registro` | `nombre`, `email`, `password`, `telefono` *(opcional)* |
| `POST` | `/auth/login` | `email`, `password` |
| `POST` | `/establecimientos` | `nombre`, `direccion`, `adminId` |
| `GET` | `/establecimientos/{id}` | — |
| `POST` | `/canchas` | `nombre`, `deporte`, `establecimientoId` |
| `GET` | `/canchas` | `establecimientoId` |
| `POST` | `/reservas` | `clienteId`, `canchaId`, `fecha`, `horaInicio` |
| `GET` | `/reservas` | `clienteId` |
| `PATCH` | `/reservas/{id}/cancelar` | — |

---

## Pruebas de endpoints

Los 9 endpoints probados en cadena contra MongoDB Atlas: registro → login → establecimiento → cancha → reserva → historial → cancelación. Todos responden correctamente (`200 OK` o `204 No Content`). La reserva nace `CONFIRMADA` con `horaFin` calculada automáticamente y la contraseña se almacena como hash BCrypt.

### 1. Registrar Cliente

<img width="702" height="742" alt="Registrar cliente" src="https://github.com/user-attachments/assets/fbe277e8-e4f4-4701-aad5-4f6450800ec2" />

### 2. Login

<img width="707" height="748" alt="Login exitoso" src="https://github.com/user-attachments/assets/d6e670c0-b644-43c0-a9b7-de69f8f7f641" />

**Credenciales incorrectas → 401 Unauthorized**

<img width="702" height="755" alt="Login no autorizado" src="https://github.com/user-attachments/assets/b84a52ef-7e43-4ce4-a297-036d8f7ea33b" />

### 3. Crear Establecimiento

<img width="708" height="762" alt="Crear establecimiento" src="https://github.com/user-attachments/assets/2f4289b5-b65e-4510-a2a8-4e7857138dd3" />

### 4. Consultar Establecimiento

<img width="993" height="750" alt="Consultar establecimiento" src="https://github.com/user-attachments/assets/6add6e64-05ea-4ace-9201-d88f5631e3a7" />

### 5. Agregar Cancha

<img width="709" height="753" alt="Agregar cancha" src="https://github.com/user-attachments/assets/57618703-85e5-4834-b878-192557fbdd2d" />

### 6. Listar Canchas de un Establecimiento

<img width="709" height="763" alt="Listar canchas" src="https://github.com/user-attachments/assets/bd76762a-4683-4504-ad37-02edadd99e97" />

### 7. Crear Reserva (instantánea)

<img width="711" height="755" alt="Crear reserva" src="https://github.com/user-attachments/assets/f7238554-e199-439a-8ed0-2f3b97897006" />

### 8. Historial de Reservas del Cliente

<img width="715" height="753" alt="Historial reservas" src="https://github.com/user-attachments/assets/5c286342-88ce-46d1-9b26-17712bdc5df1" />

### 9. Cancelar Reserva

<img width="720" height="757" alt="Cancelar reserva" src="https://github.com/user-attachments/assets/6ed5706a-fa02-4ead-9fa9-5f1998cc816a" />

---

## Notas de diseño

- Sin DTOs ni capa de seguridad separada: el diagrama no las define, no se implementan.
- `Cliente` y `AdminEstablecimiento` comparten la colección `usuarios` (herencia con discriminador `_class`).
- Las franjas horarias no se persisten: la disponibilidad se calcula en tiempo de ejecución a partir de `horaApertura`, `horaCierre` y `duracionAlquilerMinutos`.
