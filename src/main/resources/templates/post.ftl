<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page false>

<#if alert??>
<div class="alert alert-danger" role="alert">
    ${alert}
</div>
</#if>

<#if post??><!-- if access granted -->

<div class="container my-3" >
    <a class="btn btn-secondary" href="/post/${post.id}">
        Refresh
    </a>
</div>

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
                <#if user == post.creator || isAdmin>
                    <#if post.access??>
                        <td>
                            <div class="container mx-2">
                                <font color="red"><i>${post.access?html}</i></font>
                            </div>
                        </td>
                    </#if>
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


<div class="container ml-5 my-3">
    <h5>Comments: ${post.commentsNumber}</h5>
</div>

<div class="form-group mt-3">
    <form action="?addComment" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <textarea class="form-control ${(textError??)?string('is-invalid', '')}"
                      value="<#if post??>${post.text?html}</#if>" name="text" placeholder="Your comment" ></textarea>
            <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
            </#if>
        </div>

        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile" />
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="hidden" name="id" value="<#if post??>${post.id?ifExists}</#if>" />

        <div class="form-group">
            <button type="submit" class="btn btn-block">Add comment</button>
        </div>

    </form>
</div>

<#list post.comments as comment>
<div class="card my-3"
     style="max-width = 36rem" >

    <#if comment.filename??>
        <img src="/img/${comment.filename}" class="card-img-top" style="max-width: 20rem; max-height: 20rem;" >
    </#if>

    <div class="m-2">
        <span>${comment.text?html}</span><br/>
        <i>${comment.date}</i>
    </div>

    <div class="card-footer text-muted">
        <table>
            <tr>
                <td>
                    <a href="/user/${comment.sender.id}" >
                        <img src="/img/${comment.sender.picname?html}" width="50px" height="50px" />
                    </a>
                </td>
                <td>
                    <div class="container mx-2">
                        ${comment.senderName?html}
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<div width = "100%" height = "1px" style="clear: both" />

<#else>
No comments yet
</#list>

</#if><!-- end if post?? -->

</@c.page>