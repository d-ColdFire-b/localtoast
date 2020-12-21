<#import "parts/common.ftl" as c>

<@c.page false>

<div class="container ml-5 my-3">
    <h5>User editor:</h5>
</div>

<div class="form-group mt-3">
    <form method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label">Username:</label>
            <input type="text" value="${user.username?html}" name="username">
        </div>
        <div class="form-group">
            Current role: <#list user.roles as role>${role}<#sep>, </#list>
        </div>
        <div class="form-group">
            <select class="form-control form-control-sm" name="role">
                <#list roles as role>
                    <option value="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</option>
                </#list>
            </select>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-block">Save</button>
    </form>
</div>
</@c.page>