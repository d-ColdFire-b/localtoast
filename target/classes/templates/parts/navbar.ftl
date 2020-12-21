<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">LocalToast</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>


            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/feed">Feed</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/friends">Friends</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/user-search">Search</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/user/${id}">Profile</a>
                </li>
            </#if>


        </ul>

        <#if user??>
            <#if !activated>
                <div class="navbar-text mr-3 alert-danger">Please, activate your account</div>
            </#if>
        </#if>

        <div class="navbar-text mr-3">
            <#if username??>
            You are logged in as: <a href="/user/${id}">${username?html}</a>
            <#else>Please, log in
            </#if>
        </div>

        <@l.logout />

    </div>
</nav>