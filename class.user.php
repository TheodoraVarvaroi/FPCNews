
<?php


 function register($username, $password, $email)
 { 	
	print_r($username);
	echo('   ');
	print_r($password);
	echo('   ');
	print_r($email);
			
	 	
 }

 function login($username,$password)
{ 
	print_r($username);
	print_r($password);

}


 function deletecont()
{
	echo('Contul tau este sters!');
}

 function logout (){
	 
	echo('Te ai deconectat cu succes!');
	
}
