<#macro page isAuthorization>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/static/style.css">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Gothic+A1" rel="stylesheet">

    <!-- Bootstrap CSS old
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    -->

    <!-- design -->

    <!-- Styles -->
    <!-- Bootstrap CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font awesome CSS -->
    <link href="/static/css/font-awesome.min.css" rel="stylesheet">


    <!-- Main CSS -->
    <link href="/static/css/style-150.css" rel="stylesheet">

    <!-- Favicon -->
    <link rel="shortcut icon" href="/static/img/favicon.png">

    <!-- end design -->

    <title>LocalToast</title>
</head>
<body>
<#if isAuthorization>
    <#include "loginNavbar.ftl">
<#else>
    <#include "navbar.ftl">
</#if>

<!-- UI # -->
<div class="ui-150">
    <div class="container mt-5">
    <#nested>
    </div>
</div>
<!-- Javascript files -->
<!-- jQuery -->
<script src="/static/js/jquery.js"></script>
<!-- Bootstrap JS -->
<script src="/static/js/bootstrap.min.js"></script>
<!-- Placeholder JS -->
<script src="/static/js/placeholder.js"></script>
<!-- Respond JS for IE8 -->
<script src="/static/js/respond.min.js"></script>
<!-- HTML5 Support for IE -->
<script src="/static/js/html5shiv.js"></script>

<!-- Optional JavaScript -->
<!-- jQuery first, then Bootstrap JS, then Popper.js
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>

</body>
</html>
</#macro>