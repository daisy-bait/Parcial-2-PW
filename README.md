# Parcial II PW
Para la elaboración del parcial se utilizó la base de datos SQL phpMyAdmin, en mi caso, el proyecto se conecta al puerto 3307, si lo tu base de datos escucha el 3306, cámbialo en el ``application.properties`` del proyecto, la base de datos se nombró ``parcial_2``

>[!TIP]
>Las tablas de la base de datos las crea el proyecto o se pueden insertar en la base de datos manualmente

>[!WARNING]
>El proyecto viene por defecto sin encriptación
### SQL Para Creación Tablas
```SQL
CREATE TABLE role (
    rol_id BIGINT NOT NULL AUTO_INCREMENT,
    rol_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (rol_id)
) ENGINE=InnoDB;

CREATE TABLE type (
    typ_id BIGINT NOT NULL AUTO_INCREMENT,
    typ_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (typ_id)
) ENGINE=InnoDB;

CREATE TABLE vehicle (
    veh_id BIGINT NOT NULL AUTO_INCREMENT,
    veh_placa VARCHAR(7) NOT NULL,
    veh_entrada INT NOT NULL,
    veh_salida INT NULL,
    veh_ubicacion VARCHAR(255) NOT NULL,
    type_id BIGINT NOT NULL,
    PRIMARY KEY (veh_id),
    FOREIGN KEY (type_id) REFERENCES type(typ_id) ON DELETE CASCADE ON UPDATE CASCADE CONSTRAINT vehicle_type_fk
) ENGINE=InnoDB;

CREATE TABLE user (
    usr_id BIGINT NOT NULL AUTO_INCREMENT,
    usr_username VARCHAR(255) NOT NULL,
    usr_password VARCHAR(255) NOT NULL,
    PRIMARY KEY (usr_id),
    UNIQUE (usr_username)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
    usr_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usr_id, rol_id),
    FOREIGN KEY (usr_id) REFERENCES user(usr_id) ON DELETE CASCADE ON UPDATE CASCADE CONSTRAINT user_roles_user_fk,
    FOREIGN KEY (rol_id) REFERENCES role(rol_id) ON DELETE CASCADE ON UPDATE CASCADE CONSTRAINT user_roles_role_fk
) ENGINE=InnoDB;
```
### SQL Insertar Roles y Tipo Vehículo
```SQL
INSERT INTO `role`( `rol_name`) VALUES ('ACOMODADOR'),('CLIENTE');

INSERT INTO `type`( `typ_name`) VALUES ('Carro'),('Moto'),('Camión'),('Tractomula'),('Cicla'),('Patineta');
```
### SQL Usuarios de prueba
La encriptación se puede cambiar descomentando las líneas de código de la clase SecurityConfig que retornan el tipo de PasswordEncoder a un Bean que se inyecta en el Proveedor de autenticaciones.
#### Sin Encriptación
```SQL
INSERT INTO `users`(`usr_password`, `usr_username`) VALUES ('admin','admin'), ('user','user');
```
#### Con Encriptación
- Contraseña de admin: admin
- Contraseña de user: user

```SQL
INSERT INTO `users`(`usr_password`, `usr_username`) VALUES ('$2a$10$0Z4b3ZyhL0Ya3Y6PKFRqaOm9f0GRk04GNHLmfx2uwBXR7LljsyTRW','admin'), ('$2a$10$DhK92YfZc8rDC7zgrKtPdeC7sgHBAneEwqUrj9iZh5hDBVmGAMu0W','user');
```
### SQL Asignación Roles
```SQL
INSERT INTO `user_roles`(`rol_id`, `usr_id`) VALUES (1,1), (2,2);
```
