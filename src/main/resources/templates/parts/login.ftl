<#include "security.ftl">

<#macro login path isRegisterForm>
<div class="ui-form">
    <form action="${path}" method="post"  class="login-form">

        <div class="form-group row">
            <div class="col-sm-6">
                <label class="col-sm-2 col-form-label" for="usernameInput">Username<#if isRegisterForm>*</#if>:</label>
                <input id="usernameInput" type="text" name="username" value="<#if user??>${user.username?ifExists?html}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-6">
                <label class="col-sm-2 col-form-label" for="passwordInput">Password<#if isRegisterForm>*</#if>:</label>
                <input id="passwordInput" type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="form-group row">
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label" for="password2Input">Confirm password*:</label>
                    <input id="password2Input" type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label" for="emailInput">Email*:</label>
                    <input id="emailInput" type="email" name="email" value="<#if user??>${user.email?ifExists?html}</#if>"
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
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label" for="nameInput">Name*:</label>
                    <input id="nameInput" type="text" name="name" value="<#if user??>${user.name?ifExists?html}</#if>"
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
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label" for="lastnameInput">Last name*:</label>
                    <input id="lastnameInput" type="text" name="lastname" value="<#if user??>${user.lastname?ifExists?html}</#if>"
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
                <div class="col-sm-6">
                    <label class="col-sm-2 col-form-label" for="nicknameInput">Nickame:</label>
                    <input id="nicknameInput" type="text" name="nickname" value="<#if user??>${user.nickname?ifExists?html}</#if>"
                           class="form-control ${(nicknameError??)?string('is-invalid', '')}"
                           placeholder="Nick"/>
                    <#if nicknameError??>
                        <div class="invalid-feedback">
                            ${nicknameError}
                        </div>
                    </#if>
                </div>
            </div>

            <div><i>Fields marked with * are required.</i><br/></div>
            <i>
                <br/>The confirmation letter will be sent on your e-mail.<br/>
                Please, follow the instructions.
            </i>

        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button class="btn btn-block" type="submit"><#if isRegisterForm>Sign up<#else>Sign in</#if></button>

        <#if !isRegisterForm><a class="btn btn-block" href="/registration">Register</a></#if>

    </form>
</div>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-block" type="submit"><#if user?? && known>Log Out<#else>Log in</#if></button>
</form>
</#macro>