create database CruzRojaSAGuardavidas;

use CruzRojaSAGuardavidas;

create table Usuario(
	carnet varchar(8) not null,
	nombres varchar(60) not null,
	apellidos varchar(60) not null,
	clave varchar(256) not null,
	edad int not null,
	sexo varchar(20) not null,
	fechaNacimiento date not null,
	cargo varchar(20) not null,
	rango varchar(20) not null,
	correo varchar(200) not null,
	tipoUsuario varchar(20) not null,
	telefono varchar(20) not null,
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
	fecha date not null,
	hora date not null,
	lugar varchar(30) not null,
	primary key pkEntreno(idEntreno)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table UsuarioXEntreno(
	idUsuarioXEntreno int auto_increment not null,
	carnet varchar(8) not null,
	idEntreno int not null,
	primary key pkUsuarioXEntreno(idUsuarioXEntreno),
	foreign key fkUsuarioXEntrenoXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXEntrenoXEntreno(idEntreno) references Entreno(idEntreno) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Reunion(
	idReunion int auto_increment not null,
	fecha date not null,
	hora date not null,
	lugar varchar(30) not null,
	primary key pkReunion(idReunion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



create table UsuarioXReunion(
	idUsuarioXReunion int auto_increment not null,
	carnet varchar(8)  not null,
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
	horaSalida date not null,
	extraordinaria boolean,
	lugarLLegadaGuardavidas varchar(50) not null,
	estado varchar(20) not null comment 'espera, en curso, pendiente, finalizada',
	primary key pkExcursion(idExcursion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Acompanyante(
	idAcompanyante int auto_increment not null,
	carnet varchar(8) null,
	carnetizado boolean not null,
	idExcursion int not null,
	primary key pkAcompanyante(idAcompanyante),
	foreign key fkAcompanyanteXExcursion(idExcursion) references Excursion(idExcursion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table UsuarioXExcursion(
	idUsuarioXExcursion int auto_increment not null,
	carnet varchar(8)  not null,
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
	carnet varchar(8) not null,
	idEvento int not null,
	primary key pkUsuarioXEvento(idUsuarioXEvento),
	foreign key fkUsuarioXEventoXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXEventoXEvento(idEvento) references Evento(idEvento) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into Usuario 
values('216-258','Roberto Enrique',
	'Funes Rivera','holamundo',20,'Masculino','1998-6-10','sub-jefe','novato','robertofunes98@gmail.com','admin','7504-8995');



insert into Excursion 
values(null,0,null,'Playa metalio rancho privado','2018-12-10',null,'6:00:00',1,'Cruz Roja Departamental Santa Ana','espera');
