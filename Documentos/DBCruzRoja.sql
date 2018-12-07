create database CruzRojaSAGuardavidas;

use CruzRojaSAGuardavidas;

create table Usuario(
	carnet int auto_increment not null,
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

create table Entreno(
	idEntreno int auto_increment not null,
	fecha date not null,
	hora date not null,
	lugar varchar(30) not null,
	primary key pkEntreno(idEntreno)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table UsuarioXEntreno(
	idUsuarioXEntreno int auto_increment not null,
	carnet int not null,
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
	carnet int  not null,
	idReunion int  not null,
	primary key pkUsuarioXEntreno(idUsuarioXReunion),
	foreign key fkUsuarioXReunionXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXReunionXEntreno(idReunion) references Reunion(idReunion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Excursion(
	idExcursion int auto_increment not null,
	diaMultiple boolean not null,
	cantidadDias int null,
	fechaInicio date not null,
	fechaFin date null,
	horaSalida date not null,
	extraordinaria boolean,
	lugarLLegadaGuardavidas varchar(50) not null,
	estado varchar(20) not null,
	primary key pkExcursion(idExcursion)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table Acompanyante(
	idAcompanyante int auto_increment not null,
	carnet int null,
	carnetizado boolean not null,
	idExcursion int not null,
	primary key pkAcompanyante(idAcompanyante),
	foreign key fkAcompanyanteXExcursion(idExcursion) references Excursion(idExcursion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table UsuarioXExcursion(
	idUsuarioXExcursion int auto_increment not null,
	carnet int  not null,
	idExcursion int  not null,
	primary key pkUsuarioXExcursion(idUsuarioXExcursion),
	foreign key fkUsuarioXExcursionXUsuario(carnet) references Usuario(carnet) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key fkUsuarioXExcursionXEntreno(idExcursion) references Excursion(idExcursion) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



