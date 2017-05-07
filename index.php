<?php
session_start();
error_reporting(E_ALL & ~E_NOTICE);
 require_once('views/header.php') ;
  require_once('proiect.html') ;

$action = '';
if (isset($_REQUEST['action'])) $action = $_REQUEST['action'];

switch($action){
	case "register":
		require_once('class/class.user.php');

		register($_POST['username'], $_POST['password'], $_POST['email'] );
		

	break;
	case "login":
		require_once('class/class.user.php');
		login($_POST['username'], $_POST['password'] );
		

	break;
	case "logout":
		require_once('class/class.user.php');
		logout();
		header('Location: index.php');
		exit;
	break;

	default:
		require_once('views/home.php');
	break;
}
?>
