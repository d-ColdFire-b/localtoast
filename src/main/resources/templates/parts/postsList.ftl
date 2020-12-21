<#include "security.ftl">

<#list posts as post>
<div class="card my-3">

    <#if post.filename??>
        <img src="/img/${post.filename}" class="card-img-top" style="max-width: 20rem; max-height: 20rem;" >
    </#if>

    <div class="m-2">
        <span>${post.text?html}</span><br/>
        <i>${post.date}</i>
    </div>

    <div class="card-footer text-muted">
        <table>
            <tr>
                <td>
                    <a href="/user/${post.creator.id}" >
                        <img src="/img/${post.creator.picname?html}" width="50px" height="50px" />
                    </a>
                </td>
                <td>
                    <div class="container mx-2">
                        ${post.creatorName?html}
                    </div>
                </td>
                <td>
                    <a href="/post/${post.id}">Comments: ${post.commentsNumber}</a>
                </td>
                <#if post.access?? && post.access == "private">
                    <td>
                        <div class="container mx-2">
                            <font color="red"><i>${post.access?html}</i></font>
                        </div>
                    </td>
                </#if>
                <#if user == post.creator || isAdmin>
                    <td>
                        <div class="container ml-5">
                            <form action="/post/${post.id}?delete" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-danger my-1">Delete</button>
                            </form>
                        </div>
                    </td>
                </#if>
            </tr>
        </table>
    </div>
</div>

<#else>
No posts yet
</#list>