<#import "parts/common.ftl" as c>


<@c.page false>

<#if alert??>
    <div class="alert alert-danger" role="alert">
        ${alert}
    </div>
</#if>

<div class="container my-3" >
    <a class="btn btn-secondary" href="/feed">
        Refresh
    </a>
</div>

<div class="form-group mt-3">
    <form method="post" enctype="multipart/form-data">

        <div class="form-group">
            <textarea class="form-control ${(textError??)?string('is-invalid', '')}"
                      value="<#if post??>${post.text?html}</#if>" name="text" placeholder="Share your thoughts" ></textarea>
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

        <div class="form-group">
            <select class="form-control form-control-sm" name="access">
                <option value="public">Public</option>
                <option value="private">Private</option>
            </select>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="hidden" name="id" value="<#if post??>${post.id?ifExists}</#if>" />

        <div class="form-group">
            <button type="submit" class="btn btn-block">Add</button>
        </div>

    </form>
</div>

<#include "parts/postsList.ftl" />

</@c.page>