<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page true>

<div class="mb-1"><h5>Add new user</h5></div>
${message?ifExists}
<@l.login "/registration" true />

</@c.page>