<#include "security.ftl">

<#list conversations as conversation>
<div class="card my-3" href="/main/dialog/${conversation.id}"
     style="width = 36rem" >

    <div class="m-2">

        <a href="/main/dialog/${conversation.id}" class="btn btn-light">
            <table>
                <tr>
                    <#list conversation.participants as participant>
                        <#if participant.username != user.getUsername()>
                            <td>
                                <img src="/img/${participant.picname?html}" width="50px" height="50px" />
                            </td>
                            <td>
                                <div class="container mx-2">
                                    <strong>
                                        ${participant.name?html} ${participant.lastname?html}
                                    </strong>
                                </div>
                            </td>
                        </#if>
                    </#list>
                </tr>
            </table>
        </a>

    </div>

    <div class="card-footer">
        <span>${conversation.last.text?html}</span><br/>

        <i>${conversation.last.date}</i>
    </div>
</div>

<div width = "100%" height = "1px" style="clear: both" />
<#else>
No conversations yet
</#list>