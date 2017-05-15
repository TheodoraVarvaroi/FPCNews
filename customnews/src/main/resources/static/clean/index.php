<html>
<head>
  <title>The vintage</title>
  <meta name="description" content="News Feed IP">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css\style.css" type="text/css" media="all"/>
  <link rel="icon" href="images/logoziarmic.png">
  
</head>

<body background="images\lemn.jpg" >
	<div class="body">
	<div id="st">
		
		<button name="register" class="util" onclick="document.getElementById('id01').style.display='block'" >Register</button>
		<button name="login" class="util" onclick="document.getElementById('id02').style.display='block'" >Login</button>
		<button name="logout" class="util" onclick="document.getElementById('id03').style.display='block'" >Logout</button>
	</div>
	<div id="id01" class="register">
		<form class="popup" action="index.php?action=register" method="POST">
			<div class="data">
				<span name="close" onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">
				&times;</span>
			
				<input type="text" placeholder="Enter Username" name="username" required>

				<input type="password" placeholder="Enter Password" name="password" required>

				<input type="password" placeholder=" Enter Password" name="password" required>

				<input type="text" placeholder="Enter Email" name="email" required>
				<input name="submit" type="submit" value="Submit">
			</div>
		</form>
	</div>
	<div id="id02" class="login">
		<form class="popup" action="index.php?action=login" method="POST">
			<div class="data">
				<span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
			
				<input type="text" placeholder="Enter Username" name="username" required>

				<input type="password" placeholder="Enter Password" name="password" required>
				
				<input name="submit" type="submit" value="Login">
			</div>
		</form>
	</div>
	<div class="flipbook-viewport">
		<div class="container">
			<div class="flipbook">
				<div style="background-image:url(pages/a.jpg)">
					<div class="content">
						<h1>Lately News </h1>
						<div class="image1"></div>​
						<div class="image2"></div>​
						<div class="image3"></div>​
						<div class="image4"></div>​
						<h5>-1-</h5>
					</div>

				</div>
				<div style="background-image:url(pages/2.jpg)">
					<div class="content">
						<h5>-2-</h5>
					</div>
				</div>

				<div style="background-image:url(pages/3.jpg)">
					<div class="content">
						<h5>-3-</h5>
					</div>

				</div>


				<div style="background-image:url(pages/4.jpg)">
					<div class="content">
						<h5>-4-</h5>
					</div>

				</div>

				<div style="background-image:url(pages/5.jpg)">
					<div class="content">
						<h5>-5-</h5>

					</div>
				</div>
				<div style="background-image:url(pages/6.jpg)">
					<div class="content">
						<h5>-6-</h5>
					</div>
				</div>
				<div style="background-image:url(pages/7.jpg)">
					<div class="content">
						<h5>-7-</h5>
					</div>
				</div>
				<div style="background-image:url(pages/8.jpg)">
					<div class="content">
						<h5>-8-</h5>
					</div>
				</div>
        <div style="background-image:url(pages/9.jpg)">
					<div class="content">
						<h5>-9-</h5>
					</div>
				</div>
				<div style="background-image:url(pages/10.jpg)">
					<div class="content">
						<h5>-10-</h5>
					</div>
				</div>
        <div style="background-image:url(pages/11.jpg)">
					<div class="content">
						<h5>-11-</h5>
					</div>
				</div>
				<div style="background-image:url(pages/12.jpg)">
					<div class="content">
						<h5>-12-</h5>
					</div>
				<div style="background-image:url(pages/a.jpg)"></div>
				<div style="background-image:url(pages/a.jpg)"></div>
				<div style="background-image:url(pages/a.jpg)"></div>
				<div style="background-image:url(pages/a.jpg)"></div>
				<div style="background-image:url(pages/a.jpg)"></div>
				<div style="background-image:url(pages/a.jpg)"></div>
			</div>
		</div>
	</div>
	</div>
	<div id="tot">
	<div id="tabel">
	<div id="linie">
	<div id="coloana"></div>
	<div id="coloana">
	<!--facebook -->
	<div id="facebook">
	<?php
		$url="http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
		echo ("<a href=\"https://www.facebook.com/sharer/sharer.php?u=".$url."\" target=\"_blank\">");
	?>
	<img src="images/facebook.png" height="25" width="25">
	</a>
	</div>
	</div>
	<div id="coloana"></div>
	</div>
	<div id="linie">
	<div id="coloana">

	<!--google -->
	<div id="google">
	<?php
		$url="http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
		echo ("<a href=\"https://plus.google.com/share?url=".$url."\" target=\"_blank\">");
	?>
	<img src="images/google.png" height="25" width="25">
	</a>
	</div>
	</div>
	<div id="coloana">
	<!-- busola-->
	<div id="busola">
	<img src="images/busola.png" height="116" width="116">
	</div>
	</div>
	<div id="coloana">

	<!--twitter-->
	<div id="twitter">
	<?php
		$url="http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
		echo ("<a href=\"https://twitter.com/intent/tweet?url=".$url."\" target=\"_blank\">");
	?>
	<img src="images/twitter.png" height="25" width="25">
	</a>
	</div>
	</div>
	</div>
	<div id="linie">
	<div id="coloana"></div>
	<div id="coloana">
	<!--reddit-->
	<div id="reddit">
	<?php
		$url="http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
		echo ("<a href=\"https://reddit.com/submit?url=".$url."\" target=\"_blank\">");
	?>
	<img src="images/reddit.png" height="25" width="25">
	</a>
	</div>
	</div>
	<div id="coloana"></div>
	</div>
	</div>
	</div>
	</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

<script type="text/javascript" src="js/turnjs4/lib/turn.js"></script>
<script src="js/main.js"></script>
</html>
