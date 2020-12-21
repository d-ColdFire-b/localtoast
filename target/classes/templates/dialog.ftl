<#import "parts/common.ftl" as c>


<@c.page false>

<#if alert??>
    <div class="alert alert-danger" role="alert">
        ${alert}
    </div>
</#if>

<div class="container my-3" >
    <a class="btn btn-secondary" href="/main/dialog/${conversation.id}">
        Refresh
    </a>
</div>


<div class="form-group mt-3">
    <form action="/main/dialog/${conversation.id}" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <textarea class="form-control ${(textError??)?string('is-invalid', '')}"
                      value="<#if message??>${message.text?html}</#if>" name="text" placeholder="Type message" ></textarea>
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
        <input type="hidden" name="id" value="<#if message??>${message.id?ifExists}</#if>" />

        <div class="form-group">
            <button type="submit" class="btn btn-block">Send</button>
        </div>

    </form>
</div>

<#include "parts/messageList.ftl" />

</@c.page>