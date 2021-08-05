create table habitacion (
	id bigint not null auto_increment,
    descripcion varchar(1024),
    img_url varchar(255),
    precio double precision,
    tipo varchar(255),
    titulo varchar(255),
    primary key (id));
    
create table items_reserva (
	id bigint not null auto_increment,
    cantidad integer not null,
    habitacion_id bigint,
    presupuesto_id bigint,
    usuario_id bigint,
    primary key (id));
    
create table presupuesto (
	id bigint not null auto_increment,
    email varchar(255), estado_presupuesto varchar(255),
    fecha_presupuesto date,
    nombre varchar(255),
    precio_total decimal(19,2),
    telefono varchar(255),
    usuario_id bigint,
    primary key (id));
    
create table rol_usuario (
	usuario_id bigint not null,
    roles varchar(255));
    
create table usuario (
	id bigint not null auto_increment,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id));
    
alter table items_reserva
	add constraint items_reserva_habitacion_fk
    foreign key (habitacion_id) references habitacion (id);
    
alter table items_reserva
	add constraint items_reserva_presupuesto_fk
    foreign key (presupuesto_id) references presupuesto (id);
    
alter table items_reserva
	add constraint items_reserva_usuario_fk
    foreign key (usuario_id) references usuario (id);
    
alter table presupuesto
	add constraint presupuesto_usuario_fk
    foreign key (usuario_id) references usuario (id);
    
alter table rol_usuario
	add constraint rol_usuario_fk
    foreign key (usuario_id) references usuario (id);