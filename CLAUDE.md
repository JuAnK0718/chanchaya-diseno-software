# CLAUDE.md — Contexto del proyecto CanchaYa

Guía para Claude (u otro agente) que retome este repositorio. Léela antes de actuar.

## Qué es

**CanchaYa**: backend REST de reservas de canchas deportivas (ciudad de Pasto). Proyecto final de **Ingeniería de Software**, Universidad Cooperativa de Colombia (UCC), Campus Pasto, semestre **2026-1** (4.º semestre). Autor: **Juan Camilo Bastidas**. Objetivo académico: demostrar los pilares de la **POO** (herencia, polimorfismo, abstracción, encapsulamiento) sobre una arquitectura en capas.

- **Stack previsto:** Java 17+ · Spring Boot 3.x · Maven · MongoDB (Spring Data).
- **Auth:** HTTP Basic + Spring Security. Roles: `CLIENTE`, `ADMIN_ESTABLECIMIENTO`.
- **Sin** interfaz gráfica, **sin** pagos en línea (método de pago solo informativo), **sin** Swagger.
- **Reserva instantánea estilo Uber:** la franja libre se confirma al momento (`CONFIRMADA`).
- **Franjas (slots) NO se persisten:** se calculan en runtime desde `horaApertura/horaCierre/duracionSlotMinutos` de la cancha, descontando reservas `CONFIRMADA`.

## Estado actual (2026-05-30)

- **NO hay código** todavía. El repositorio contiene **solo documentación** (la wiki) y los diagramas especificados.
- La **wiki está completa**: dominio, enums, endpoints, requisitos, escenarios de calidad y la guía de los 5 diagramas UML (con Mermaid renderizable).
- Falta: **implementar el backend** y, opcionalmente, exportar/insertar los diagramas en las secciones 1.1–1.5 de la wiki.

## Estructura del repositorio

```
DISENO_FINAL_JUANCAMILO_JEN/
├── CLAUDE.md          ← este archivo (contexto para el agente)
├── .gitignore
├── .git/              ← repo principal (rama main)
└── wiki/              ← repo SEPARADO de la wiki de GitHub (rama master)
    ├── Home.md
    ├── 0. -Introducción.md
    ├── 1.-Descripción-General.md       (vistas: contexto, funcional, lógica, desarrollo, despliegue)
    ├── 2.-Características-del-Sistema.md (4 módulos · 20 historias HU-01..HU-20)
    ├── 3.-Requisitos-no-Funcionales.md  (OBJ, RNF001..014, escenarios EC)
    ├── 4.-Guía-de-Diagramas-UML.md      (specs + Mermaid de los 5 diagramas)
    └── RESUMEN-PROYECTO.md              ← documento maestro: modelo, enums, endpoints, reglas
```

> **`wiki/` es un repo git aparte** (remote `https://github.com/JuAnK0718/chanchaya-diseno-software.wiki.git`, rama `master`). Para publicar cambios de la wiki: `cd wiki && git add -A && git commit && git pull --rebase origin master && git push origin master`. NO confundir con el repo principal (rama `main`).

## Fuente de verdad

`wiki/RESUMEN-PROYECTO.md` es el **documento maestro**: contiene el modelo de dominio acordado, enums, reglas de negocio, los 16 endpoints REST y las decisiones técnicas. Ante cualquier duda de diseño, manda ese archivo.

## Modelo de dominio (resumen)

- **`Usuario`** (abstracta, `@Document "usuarios"`) → **`Cliente`** (`reservasIds`) y **`AdminEstablecimiento`** (`establecimientoId`). Método abstracto `getResumenPerfil()`.
- **`Establecimiento`** (`@Document "establecimientos"`) agrupa muchas **`Cancha`** (`@Document "canchas"`).
- **`Reserva`** (`@Document "reservas"`) referencia `canchaId` + `clienteId`.
- **Enums:** `RolUsuario`, `Deporte`, `EstadoCancha`, `EstadoReserva`, `MetodoPago`.

## Capas previstas (paquete `com.canchaya`)

`model/` · `repository/` (MongoRepository) · `dto/` (request/response + `ApiResponse<T>`) · `service/` (lógica, validación de solapamiento, generación de franjas) · `controller/` (REST por recurso) · `config/` (Spring Security HTTP Basic + roles, BCrypt, manejo de errores).

## Qué sigue (trabajo pendiente)

1. **Inicializar proyecto Spring Boot** (Maven, `pom.xml`, `mvn spring-boot:run` en un comando).
2. **Implementar capas** en el orden: `model` + enums → `repository` → `dto` → `service` → `controller` → `config`.
3. **Lógica clave:** generación de franjas (`DisponibilidadService`), validación de solapamiento y reserva instantánea (`ReservaService`), sobrecarga `buscarCanchas(...)` (`CanchaService`).
4. **Seguridad:** HTTP Basic, roles por endpoint (RNF001/002), BCrypt (RNF003), errores con código HTTP correcto y mensaje en español (RNF010).
5. **Diagramas:** los 5 ya están especificados con Mermaid en `wiki/4.-Guía-de-Diagramas-UML.md`. Si se piden como imágenes, exportar y enlazar en las secciones 1.1, 1.2, 1.3, 1.5.
6. Mantener **coherencia** entre código y wiki: si el código cambia el diseño, actualizar `RESUMEN-PROYECTO.md` y los diagramas.

## Convenciones

- Nombres de dominio en español (`Cancha`, `reservasIds`). Documentación en español.
- Los `HU-XX` y `EC-XX` identifican historias de usuario y escenarios de calidad descritos en la wiki.
- Entorno: **Windows + PowerShell**. Usar la herramienta PowerShell (no asumir comandos Unix).
- Al implementar, respetar las **6 reglas de negocio** y los **14 RNF** de `wiki/3.-Requisitos-no-Funcionales.md`.
