<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page false>

<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/user-search">
            <input type="text" name="filter" class="form-control" value="${filter?ifExists?html}" placeholder="Search">
            <input type="text" name="city" class="form-control" value="${city?ifExists?html}" placeholder="City">
            <button type="submit" class="btn btn-secondary ml-2">Search</button>
        </form>
    </div>
</div>

List of users

<table class="table my-3" style="background-color:white;">
    <thead>
    <tr>
        <th scope="col"></th>
        <th scope="col">Name</th>
        <th scope="col">City</th>
        <#if isAdmin><th scope="col" class="text-center">Role</th></#if>
        <th scope="col"></th>
        <#if isAdmin><th scope="col"></th></#if>
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
                <#if isAdmin><td class="text-center"><#list tempUser.roles as role>${role}<#sep>, </#list></td></#if>
                <td>
                    <a class="btn btn-secondary" href="/main/dialog?user=${tempUser.id}" >Message</a>
                </td>
                <#if isAdmin><td><a href="/user/edit/${tempUser.id}" class="btn btn-info">Edit</a></td></#if>
            </tr>
            </#if>
        </#list>
    </tbody>
</table>



</@c.page>