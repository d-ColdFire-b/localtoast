<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page false>

<#if success??>
<div class="alert alert-success" role="alert">
    ${success}
</div>
</#if>

<#if alert??>
<div class="alert alert-danger" role="alert">
    ${alert}
</div>
</#if>


<div class="container my-3" >
    <a class="btn btn-secondary" href="/user/${targetUser.id}">
        Refresh
    </a>
</div>

<table class="border0">
    <tr>
        <td colspan="2">
            <h5>${targetUser.name?html} ${targetUser.lastname?html}<#if targetUser.nickname??> ${targetUser.nickname?html}</#if></h5>
        </td>
    </tr>
    <tr>
        <td rowspan="3">
            <div class="card my-3" style="width: 18rem;">
                <img class="card-img-top" src="/img/${targetUser.picname?html}" />
            </div>
        </td>
        <td>
            <div class="container ml=3">
                City: <#if targetUser.city??>${targetUser.city?html}<#else>Not set</#if>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <div class="container ml=3">
                Interests: <#if targetUser.interests??>${targetUser.interests?html}<#else>Not set</#if>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div class="container ml=3">
                Friends: ${friendsCount}
            </div>
        </td>
    </tr>
    <#if isAdmin>
        <tr>
            <td colspan="2">
                <div class="container mb-3">
                    <font color="red">Current role: <#list targetUser.roles as role>${role}<#sep>, </#list></font>
                </div>
            </td>
        </tr>
    </#if>
</table>


<#if targetUser == user>
    <a class="btn btn-info" href="/user/edit">Edit</a>
<#else>
    <table>
        <tr>
            <td>
                <div class="container mr-4">
                    <a class="btn btn-secondary" href="/main/dialog?user=${targetUser.id}" >Message</a>
                </div>
            </td>
            <td>
                <#if isStranger>
                    <div class="container">
                        <form action="/user/${targetUser.id}?subscribe" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-secondary my-1">Add to friends</button>
                        </form>
                    </div>
                </#if>
                <#if isSubscription>
                    <div class="container">
                        <form action="/user/${targetUser.id}?unsubscribe" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-secondary my-1">Unsubscribe</button>
                        </form>
                    </div>
                </#if>
                <#if isSubscriber>
                    <table class="border0">
                        <tr>
                            <td>
                                <div class="container mr-2">
                                    <form action="/user/${targetUser.id}?acceptRequest" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                        <button type="submit" class="btn btn-secondary my-1">Accept request</button>
                                    </form>
                                </div>
                            </td>
                            <td>
                                <div class="container">
                                    <form action="/user/${targetUser.id}?denyRequest" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                        <button type="submit" class="btn btn-secondary my-1">Deny request</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>
                </#if>
                <#if isFriend>
                    <div class="container">
                        <form action="/user/${targetUser.id}?deleteFriend" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-secondary my-1">Delete friend</button>
                        </form>
                    </div>
                </#if>
            </td>
        </tr>
    </table>
</#if>

<#if targetUser != user && isAdmin>
<a href="/user/edit/${targetUser.id}" class="btn btn-info">Administrative edit</a>
</#if>



<div class="container ml-5 my-3">
    <h5>Posts:</h5>
</div>



<#include "parts/postsList.ftl" />

</@c.page>