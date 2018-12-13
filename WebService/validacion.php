<?php 
require_once('Core/BD.php');

$conexion=new BaseDatos();


if($conexion->error)
{
	exit();
}

$usuario='658';//$_POST['usuario'];
$clave='1234';//$_POST['clave'];

$resultado=$conexion->login($usuario,$clave);

//ESTE SOLO HACE UN ECHO YA QUE SOLO DEVUELVE UN STRING
echo $resultado;

?>
