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

### Prueba de CI/CD automática Paulax2
