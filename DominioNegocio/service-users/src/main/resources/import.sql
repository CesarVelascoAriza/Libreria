insert into roles (nombre_rol) values('USER');
insert into roles (nombre_rol) values('ADMIN');
insert into roles (nombre_rol) values('PROFESOR');

insert into usuarios (user_name,password,enabled) values ('prueba','$2a$10$HI60fyOGi5OxvSZmo9vwYOLwM/RE2ZpXoD5DyFokzhII8ylhuJwcy',1);

insert into usuario_roles (usuario_id,role_id) values (1,1);
insert into usuario_roles (usuario_id,role_id) values (1,2);