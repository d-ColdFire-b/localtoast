<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page false>

<div class="container ml-5 my-3">
    <h5><i>Requests:</i></h5>
</div>
<div class="container mx-2 my-3">
    <a href="/friends/outgoing-requests">Outgoing: ${outgoing}</a>
    <br>
    <a href="/friends/pending-requests">Pending: ${pending}</a>
</div>

<div class="container ml-5 mt-5">
    <h5>My friends:</h5>
</div>

<table class="table my-3" style="background-color:white;">
    <thead>
    <tr>
        <th scope="col"></th>
        <th scope="col">Name</th>
        <th scope="col">City</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
        <#list users as tempUser>
            <#if tempUser != user>
            <tr class="table-row" data-href="">
                <th scope="row">
                    <a href="/user/${tempUser.id}" >
                        <img src="/img/${tempUser.picname?html}" width="50px" height="50px" />
                    </a>
                </th>
                <td>
                    ${tempUser.name?html} ${tempUser.lastname?html}<#if tempUser.nickname??> ${tempUser.nickname?html}</#if>
                </td>
                <td>
                    <#if tempUser.city??>${tempUser.city?html}</#if>
                </td>
                <td>
                    <a class="btn btn-secondary" href="/main/dialog?user=${tempUser.id}" >Message</a>
                </td>
            </tr>
            </#if>
        </#list>
    </tbody>
</table>



</@c.page>