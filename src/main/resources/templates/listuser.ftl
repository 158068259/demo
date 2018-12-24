<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td>编号</td>
        <td>用户</td>
        <td>密码</td>
        <td>年龄</td>
        <td>性别</td>
    </tr>
    <#list info.list as u>
    <tr>
       <td>${u.id}</td>
       <td>${u.uname}</td>
       <td>${u.pwd}</td>
       <td>${u.age}</td>
       <td>
            <#if u.sex=='1'>男</#if>
            <#if u.sex=='0'>女</#if>
       </td>
    </tr>
   </#list>
    <tr><td colspan="9">${info.toolBarOne}</td></tr>
</table>
</body>
</html>