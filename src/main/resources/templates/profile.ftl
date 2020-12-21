<#import "parts/common.ftl" as c>

<@c.page>
${message?ifExists}

<h5>${user.name} ${user.lastname}</h5>

<div class="card my-3" style="width: 18rem;">
    <img src="/img/${user.picname}" class="card-img-top" />
    <div class="card-footer text-muted">

    </div>
</div>


<form method="post" enctype="multipart/form-data">
    <div class="form-group row">
        <div class="custom-file">
            <input type="file" name="file" id="customFile" placeholder="Choose your file"/>

        </div>
    </div>


    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${user.email!''}"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Nickname:</label>
        <div class="col-sm-6">
            <input type="text" name="nickname" class="form-control" placeholder="Nickname" value="${user.nickname!''}" />
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Save</button>
</form>
</@c.page>