<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        id = user.getId()
        username = user.getUsername()
        name = user.getName()
        isAdmin = user.isAdmin()
        currentUserId = user.getId()

        hasEmail = user.getEmail()??
        hasCode = user.getActivationCode()??

        activated = hasEmail && !hasCode

    >
</#if>