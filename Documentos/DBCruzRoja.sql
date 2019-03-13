create database CruzRojaSAGuardavidas;

use CruzRojaSAGuardavidas;

create table Usuario(
	carnet varchar(12) not null,
	nombres varchar(60) not null,
	apellidos varchar(60) not null,
	clave varchar(256) not null,
	edad int not null,
	sexo varchar(20) not null,
	fechaNacimiento date not null,
	cargo varchar(20) not null,
	rango varchar(20) not null,
	correo varchar(200) not null,
	tipoUsuario int not null comment "1 = admin, 2 = acceso parcial, 3 = solo lectura",
	telefono varchar(20) not null,
	primerLogeo boolean not null,
	primary key pkCarnet(carnet)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Disponibilidad(
	idDisponibilidad int auto_increment not null,
	idCarnet varchar(8) not null,
	fecha date not null,
	primary key pkDisponibilidad(idDisponibilidad)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Entreno(
	idEntreno int auto_increment not null,
	patron varchar(15) null comment '0=lunes,1=martes,2=miercoles,3=jueves,4=viernes,5=sabado,6=domingo. ejemplo de patron "0,2,4"',
	hora varchar(20) null comment 'formato 24 Horas ejemplo "15:30"',
	lugar varchar(30) not null,
	primary key pkEntreno(idEntreno)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table UsuarioXEntreno(
	idUsuarioXEntreno int auto_increment not null,
	carnet varchar(12) not null,
	idEntreno int not null,
	primary key pkUsuarioXEntreno(idUsuarioXEntreno),
	foreign key fkUsuarioXEntrenoXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXEntrenoXEntreno(idEntreno) references Entreno(idEntreno) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Reunion(
	idReunion int auto_increment not null,
	fechaHora datetime not null,
	lugar varchar(30) not null,
	tipoReunion varchar(20) not null,
	primary key pkReunion(idReunion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table UsuarioXReunion(
	idUsuarioXReunion int auto_increment not null,
	carnet varchar(12)  not null,
	idReunion int  not null,
	primary key pkUsuarioXEntreno(idUsuarioXReunion),
	foreign key fkUsuarioXReunionXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXReunionXEntreno(idReunion) references Reunion(idReunion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Excursion(
	idExcursion int auto_increment not null,
	diaMultiple boolean not null,
	cantidadDias int null,
	lugarExcursion varchar(60) not null,
	fechaInicio date not null,
	fechaFin date null,
	horaSalida varchar(10) not null,
	extraordinaria boolean not null comment 'es extraordinaria si la salida es antes de las 6:30 am o no se recogera en base de cruz roja',
	motivoExtraordinario varchar(100) null,
	lugarLLegadaGuardavidas varchar(50) not null,
	encargadoExcursion varchar(100) not null,
	telefonoEncargado varchar(20) not null,
	estado varchar(20) not null comment 'espera, en curso, pendiente, finalizada',
	primary key pkExcursion(idExcursion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Acompanyante(
	idAcompanyante int auto_increment not null,
	carnet varchar(12) null,
	carnetizado boolean not null,
	idExcursion int not null,
	primary key pkAcompanyante(idAcompanyante),
	foreign key fkAcompanyanteXExcursion(idExcursion) references Excursion(idExcursion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table UsuarioXExcursion(
	idUsuarioXExcursion int auto_increment not null,
	carnet varchar(12)  not null,
	idExcursion int  not null,
	primary key pkUsuarioXExcursion(idUsuarioXExcursion),
	foreign key fkUsuarioXExcursionXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXExcursionXEntreno(idExcursion) references Excursion(idExcursion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Evento(
	idEvento int auto_increment not null,
	nombre varchar(60) not null,
	fecha date not null,
	hora date not null,
	lugar varchar(30) not null,
	primary key pkEvento(idEvento)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table UsuarioXEvento(
	idUsuarioXEvento int auto_increment not null,
	carnet varchar(12) not null,
	idEvento int not null,
	primary key pkUsuarioXEvento(idUsuarioXEvento),
	foreign key fkUsuarioXEventoXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXEventoXEvento(idEvento) references Evento(idEvento) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Tablas bolsas*/

create table Notificacion(
	idNotificacion int auto_increment not null,
	titulo varchar(60) not null,
	contenido varchar(200) not null,
	carnet varchar(12) not null,
	tipo int not null comment '0=excursion,1=evento,2=informativa,3=cambioClave,etc.',
	referencia varchar(20) not null comment 'Id de lo que se asigno. Ejemplo: idExcursion',
	fecha datetime,
	primary key pkNotificacion(idNotificacion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into Usuario 
values('216-258','Roberto Enrique',
	'Funes Rivera','holamundo',20,'Masculino','1998-6-10','sub-jefe','novato','robertofunes98@gmail.com',1,'7504-8995',1);

insert into Usuario 
values('216-001','Juan',
	'Perez','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

/*Descomentar si se necesita tener varios usuarios para pruebas*/
/*
insert into Usuario 
values('216-259','agdgUsuario',
	'1','holamundo',20,'Masculino','1998-6-10','sub-jefe','novato','robertofunes98@gmail.com',1,'7504-8995',1);

insert into Usuario 
values('216-001139','asdfUsuario',
	'2','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0123019','asfdUsuario',
	'3','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0012139','gasUsuario',
	'4','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0012319','dfgUsuario',
	'5','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-12319','jyUsuario',
	'6','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0132019','ijkUsuario',
	'7','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0319','ftyUsuario',
	'8','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0013192','aewUsuario',
	'9','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);

insert into Usuario 
values('216-0021s3123','ljUsuario',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-8973587','ljUsufario',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-897453','ljUgsuario',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-0321s3123','ljUfsuario',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-007s3123','ljUsuadrio',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-0561s3123','ljUsuasdrio',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


insert into Usuario 
values('216-0021673123','ljUsusario',
	'10','holamundo',40,'Masculino','1978-6-10','miembro','guardavidas','juanperez78@gmail.com',3,'7504-8995',1);


*/




insert into Excursion 
values(null,0,null,'Playa metalio rancho privado','2018-12-10',null,'6:00:00',1,'Cruz Roja Departamental Santa Ana','espera');

insert into Notificacion 
values(null,'Excursion','Se te ha asignado una excursion para: Metalio. Fecha: 25-12-2018. Hora: 6:00, Lugar de llegada: Base cruz roja'
	,'216-258',0,"1",null);

insert into Notificacion 
values(null,'Evento','Confirmacion de asistencia a plan belen. Fecha 1 de enero del 2019'
	,'216-258',1,"1",null);

insert into Notificacion 
values(null,'Entreno','Recordatorio: Hoy hay entreno en indes a las 7 pm'
	,'216-258',2,"2",null);

insert into Notificacion 
values(null,'Cambiar clave','Roberto Enrique Funes Rivera con carnet 216-258 necesita restablecimiento de clave'
	,'216-258',3,"216-258",null);


insert into Reunion 
values(null,'2019-4-30 16:30:00','Base cruz roja', 'general');

insert into Reunion 
values(null,'2019-4-25 16:30:00','Base cruz roja', 'diretiva');

