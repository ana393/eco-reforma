INSERT INTO usuario (username, email, password)
	VALUES ('Ana', 'admin@mail.com', '$2a$12$JF3bJb1efD9nO1tqmzpPouQ/cgbxq0DUojqYp4PexiwpGbyKJEq4K');
INSERT INTO rol_usuario (usuario_id, roles) VALUES (1, 'USER'), (1, 'ADMIN');
    