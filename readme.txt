       <div class="reg">
           <h2 class="form-signin-heading">��Աע��</h2>
           <form:form id="regUser" modelAttribute="user" method="post" class="form-signin" onsubmit="return check();">

               <fieldset>
                   <form:label path="userName">
                       �û�����
                   </form:label>
                   <form:input path="userName" name="user.username"  id="username" class="input"/>

                   <form:label path="password">
                       ��¼���룺
                   </form:label>
                   <form:password path="password" name="user.password" id="password" class="input"/>

                   <form:label path="repassword" >
                       �ٴ��������룺
                   </form:label>
                   <form:password path="repassword" name="user.repassword"  id="repassword" class="input" />

                   <form:label path="email">
                       E-mail�������ʼ�����
                   </form:label>
                   <form:input path="email" name="user.email" id="email" class="input" />

                   <form:label path="phone">
                       �绰���룺
                   </form:label>
                   <form:input path="phone"/>

               </fieldset>

               <button class="btn btn-primary" type="submit">ע��</button>
               <span class="clew_txt">����������ʺţ���<a href="<c:url value="/login" />">ֱ�ӵ�¼</a></span>

          </form:form>