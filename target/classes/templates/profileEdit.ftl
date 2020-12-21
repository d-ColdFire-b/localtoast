<#import "parts/common.ftl" as c>

<@c.page false>

<#if message??>
    <div class="alert alert-${messageType}" role="alert">
        ${message}
    </div>
</#if>

<#if usernameError??>
<div class="invalid-feedback">
    ${usernameError}
</div>
</#if>
<#if passwordError??>
<div class="invalid-feedback">
    ${passwordError}
</div>
</#if>

<h5>${user.name?html} ${user.lastname?html}</h5>

<div class="card my-3" style="width: 18rem;">
    <img src="/img/${user.picname?html}" class="card-img-top" />
</div>

<div class="container ml-5 my-3">
    <h5><b>Edit profile:</b></h5>
</div>

<form method="post" enctype="multipart/form-data" id="profile_update_form" action="?update">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">New profile pic:</label>
        <div class="custom-file col-sm-6">
            <input type="file" name="file" id="customFile" placeholder="Choose your file"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" value="${user.email?html}"
                   class="form-control ${(emailError??)?string('is-invalid', '')}"
                   placeholder="some@some.com"/>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Name:</label>
        <div class="col-sm-6">
            <input type="text" name="name" value="<#if user??>${user.name?html}</#if>"
                   class="form-control ${(nameError??)?string('is-invalid', '')}"
                   placeholder="Name"/>
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
    </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Last name:</label>
        <div class="col-sm-6">
            <input type="text" name="lastname" value="<#if user??>${user.lastname?html}</#if>"
                   class="form-control ${(lastnameError??)?string('is-invalid', '')}"
                   placeholder="Last name"/>
            <#if lastnameError??>
                <div class="invalid-feedback">
                    ${lastnameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Nickname:</label>
        <div class="col-sm-6">
            <input type="text" name="nickname" value="${user.nickname?ifExists?html}"
                   class="form-control ${(nicknameError??)?string('is-invalid', '')}"/>
            <#if nicknameError??>
                <div class="invalid-feedback">
                    ${nicknameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">City:</label>
        <div class="col-sm-6">
            <input type="text" name="city" value="${user.city?ifExists?html}"
                   class="form-control ${(cityError??)?string('is-invalid', '')}"/>
            <#if cityError??>
                <div class="invalid-feedback">
                    ${cityError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Interests:</label>
        <div class="col-sm-6">
            <textarea name="interests" value="${user.interests?ifExists?html}"
                   class="form-control ${(interestsError??)?string('is-invalid', '')}"></textarea>
            <#if interestsError??>
            <div class="invalid-feedback">
                ${interestsError}
            </div>
        </#if>
    </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button id="upd_user" class="btn btn-block" type="submit">Save</button>
</form>

<div class="container ml-5 my-3">
    <h5><i>Change password:</i></h5>
</div>

<form method="post" id="password_change_form" action="?pass">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">New password:</label>
        <div class="col-sm-6">
            <input type="password" name="newpassword"
                   class="form-control ${(newpasswordError??)?string('is-invalid', '')}"/>
            <#if newpasswordError??>
                <div class="invalid-feedback">
                    ${newpasswordError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Confirm new password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2"
                   class="form-control ${(password2Error??)?string('is-invalid', '')}"/>
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button id="pass_change" class="btn btn-block" type="submit">Change password</button>
</form>
</@c.page>