<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page false>

<div class="container ml-5 my-3">
    <h5><i>Pending requests:</i></h5>
</div>

<table class="table my-3">
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
                    <form action="/friends/pending-requests?accept=${tempUser.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" class="btn btn-secondary my-1">Accept</button>
                    </form>
                </td>
                <td>
                    <form action="/friends/pending-requests?deny=${tempUser.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button type="submit" class="btn btn-secondary my-1">Deny</button>
                    </form>
                </td>
            </tr>
            </#if>
        </#list>
    </tbody>
</table>



</@c.page>