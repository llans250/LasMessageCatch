       <div class="reg">
           <h2 class="form-signin-heading">会员注册</h2>
           <form:form id="regUser" modelAttribute="user" method="post" class="form-signin" onsubmit="return check();">

               <fieldset>
                   <form:label path="userName">
                       用户名：
                   </form:label>
                   <form:input path="userName" name="user.username"  id="username" class="input"/>

                   <form:label path="password">
                       登录密码：
                   </form:label>
                   <form:password path="password" name="user.password" id="password" class="input"/>

                   <form:label path="repassword" >
                       再次输入密码：
                   </form:label>
                   <form:password path="repassword" name="user.repassword"  id="repassword" class="input" />

                   <form:label path="email">
                       E-mail（电子邮件）：
                   </form:label>
                   <form:input path="email" name="user.email" id="email" class="input" />

                   <form:label path="phone">
                       电话号码：
                   </form:label>
                   <form:input path="phone"/>

               </fieldset>

               <button class="btn btn-primary" type="submit">注册</button>
               <span class="clew_txt">如果您已有帐号，可<a href="<c:url value="/login" />">直接登录</a></span>

          </form:form>