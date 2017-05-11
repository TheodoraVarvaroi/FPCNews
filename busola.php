<html>
<head><title>busola</title></head>
<link rel="stylesheet" type="text/css" href="css/busola.css">
<link rel="icon" href="images/logoziarmic.png">
<body>

<!-- butonul functioneaza mai bine pe o pagina reala , http://students.info.uaic.ro/~alexandru.trifan/teste/busola.php
share-ul se face dinamic cu pagina curenta -->
<!-- aplica div-ul oriunde este necesar in site -->
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
<img src="images/facebook.png" height="30" width="30">
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
<img src="images/google.png" height="30" width="30">
</a>
</div>
</div>
<div id="coloana">
<!-- busola-->
<div id="busola">
<img src="images/busola.png" height="131" width="131">
</div>
</div>
<div id="coloana">

<!--twitter-->
<div id="twitter">
<?php
    $url="http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
    echo ("<a href=\"https://twitter.com/intent/tweet?url=".$url."\" target=\"_blank\">");
?>
<img src="images/twitter.png" height="30" width="30">
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
<img src="images/reddit.png" height="30" width="30">
</a>
</div>
</div>
<div id="coloana"></div>
</div>
</div>
</div>
</body>
</html>

