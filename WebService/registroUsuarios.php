<?php 
//Colocando hora correcta en el servidor
date_default_timezone_set('UTC');
date_default_timezone_set("America/El_Salvador");

require_once('Core/BD.php');

$conexion=new BaseDatos();


if($conexion->error)
{
	exit();
}

$carnet=$_POST['carnet'];
$nombres=$_POST['nombres'];
$apellidos=$_POST['apellidos'];
$clave=$_POST['clave'];
$correo=$_POST['correo'];
$telefono=$_POST['telefono'];
$sexo=$_POST['sexo'];
$fechaNacimiento=$_POST['fechaNacimiento'];
$cargo=$_POST['cargo'];
$rango=$_POST['rango'];
$permisos=$_POST['permisos'];

$cumpleanos = new DateTime($fechaNacimiento);
$hoy = new DateTime();
$annos = $hoy->diff($cumpleanos);

$resultado=$conexion->ejecutar("INSERT into Usuario values('$carnet','$nombres','$apellidos','$clave',".$annos->y.",'$sexo','$fechaNacimiento','$cargo','$rango','$correo',$permisos,'$telefono',1);");

if(gettype($resultado)=="string")
{
	$objJSON[0]["resultado"]=$resultado;
	echo json_encode($objJSON);
}
else
{
	$objJSON[0]["resultado"]="true";
	echo json_encode($objJSON);
}
?>