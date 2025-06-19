# 🚀 Comandos para ejecutar el proyecto

## 📌 Levantar y detener el stack

Levanta y detén el backend, frontend y las bases de datos con:

```sh
docker-compose -f .\plataforma.yml up -d
```

```sh
docker-compose -f .\plataforma.yml down
```

## 🔗 Puertos de cada servicio

* **Frontend** → localhost:3000
* **Backend** → localhost:8081
* **MySQL** → localhost:3306
* **MongoDB** → localhost:27017

## 🛠️ Persistencia de datos

Los datos se conservan aunque los contenedores se apaguen o eliminen.
Si quieres borrar todos los datos:

```sh
docker volume rm plataforma-web-proyecto_mysql_data plataforma-web-proyecto_mongo_data
```

Modificar `application.properties` también es una opción, pero no recomendable.


# 🛠️ Desarrollo de funcionalidades para la plataforma

## 🌿 Estructura de Ramas

| Rama        | Propósito                                  |
|-------------|---------------------------------------------|
| `main`      | Código estable y listo para producción.     |
| `dev`       | Rama de integración para pruebas.           |
| `feature/*` | Nuevas funcionalidades.                     |
| `fix/*`     | Corrección de errores detectados en `dev`.  |
| `hotfix/*`  | Corrección urgente directamente sobre `main`.|


## 📌 Reglas Generales

1. **No trabajar directamente en `main`.**
2. Todo trabajo debe partir desde `dev`.
3. Se debe crear una rama por cada funcionalidad, mejora o corrección.
4. El nombre de la rama debe ser:
   ```
   tipo/nombre-descriptivo
   ```
   Por ejemplo:
   - `feature/registro-estudiantes`
   - `fix/validacion-contraseña`
   - `hotfix/error-footer`

---

## 🔁 Flujo de Trabajo

### Crear una nueva rama
```bash
git checkout dev
git pull origin dev
git checkout -b feature/nombre-de-la-funcion
```

### Trabajar localmente
```bash
git add .
git commit -m "feat: descripción corta y clara"
```

> Usar convenciones como:
> - `feat:` para nuevas funciones
> - `fix:` para correcciones
> - `refactor:`, `test:`, `docs:` según aplique

### Subir al repositorio remoto
```bash
git push origin feature/nombre-de-la-funcion
```

### Crear un Pull Request (PR)
- PR hacia `dev` (no hacia `main`).
- Usa título y descripción clara.
- Asigna revisores si aplica.

---


## ✅ Revisión y Merge

- Todo PR debe pasar por una **revisión manual** o automática.
- El líder del equipo o revisor hace merge una vez aprobado.
- Solo se hace merge a `main` desde `dev` cuando todo está probado y aprobado.

---


## 🚨 Casos urgentes

Si es necesario aplicar una corrección inmediata en producción:

```bash
git checkout main
git pull origin main
git checkout -b hotfix/ajuste-urgente
```

- Luego se hacer merge tanto a `main` como a `dev`.

---
