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
$clave=$_POST['clave'];
//$correo=$_POST['correo'];


$hoy = new DateTime();


$resultado=$conexion->ejecutar("SELECT * from Usuario WHERE carnet='$carnet' AND clave='$clave';");
echo json_encode($resultado);

if(gettype($resultado)=="string")
{
	$objJSON[$resultado];
	echo json_encode($objJSON);
}
else
{
	$objJSON[$resultado]="true";
	echo json_encode($objJSON);
} 
?>