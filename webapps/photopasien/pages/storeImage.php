<?php
    require_once('../../conf/conf.php');
    session_start();
    
    if(file_exists(host()."webapps/photopasien/pages/upload/".$_SESSION["pasien"].".jpeg")){
        @unlink(host()."webapps/photopasien/pages/upload/".$_SESSION["pasien"].".jpeg");
    }
    
    $img            = $_POST["image"];
    $folderPath     = "upload/";
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = $_SESSION['pasien'].".jpeg";
    $file           = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    Ubah2("personal_pasien","gambar='pages/upload/$fileName' where no_rkm_medis='".$_SESSION['pasien']."'");
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
    <center><br><br><font face='tahoma' size='4' color='#545454'>Proses uploud photo selesai, silahkan tutup browser ..!!  </font></center>
    <script type='text/javascript'>window.open('', '_self').close();window.open('','_self','').close();window.close();this.focus();self.opener=this;self.close();</script>
</body>
</html>
