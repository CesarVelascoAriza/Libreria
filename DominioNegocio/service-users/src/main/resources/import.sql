insert into roles (nombre_rol) values('USER');
insert into roles (nombre_rol) values('ADMIN');
insert into roles (nombre_rol) values('PROFESOR');

insert into users (user_name,password,enabled) values ('prueba','prueba',0);

insert into usuario_roles (usuario_id,role_id) values (1,1);
insert into usuario_roles (usuario_id,role_id) values (1,2);