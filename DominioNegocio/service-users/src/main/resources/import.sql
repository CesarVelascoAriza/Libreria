insert into roles(name_role) values ('ADMIN');
insert into roles(name_role) values ('USER');

INSERT INTO users (user_name,password,enabled) VALUES('prueba','$2a$10$rebGIepuIMXpuAhQCUoiEeAeyDbWdunWhfCBkWoQ9XnCkJknHtMHK',0);

INSERT INTO user_roles VALUES (1,1);
INSERT INTO user_roles VALUES (1,2);
