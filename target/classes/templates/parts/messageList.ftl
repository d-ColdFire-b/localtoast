<#include "security.ftl">

<#list messages as message>
<div class="card my-3"
     style="
     float: <#if message.author.id == currentUserId>right<#else>left</#if>;
     max-width = 36rem" >

    <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top" style="max-width: 20rem; max-height: 20rem;" >
    </#if>

    <div class="m-2">
        <span>${message.text?html}</span><br/>
    </div>

    <div class="card-footer text-muted">
        <table>
            <tr>
                <#if message.author.id != currentUserId>
                <td>
                    <a href="/user/${message.author.id}" >
                        <img src="/img/${message.author.picname?html}" width="50px" height="50px" />
                    </a>
                </td>
                </#if>
                <td>
                    <div class="container mx-2">
                        ${message.authorName?html}
                    </div>
                </td>
                <#if message.author.id == currentUserId>
                <td>
                    <a href="/user/${message.author.id}" >
                        <img src="/img/${message.author.picname?html}" width="50px" height="50px" />
                    </a>
                </td>
                </#if>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="container mt-3">
                        <i>${message.date}</i>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div width = "100%" height = "1px" style="clear: both" />

<#else>
    No messages
</#list>