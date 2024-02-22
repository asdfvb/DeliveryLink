<%--
  Created by IntelliJ IDEA.
  User: 조참새
  Date: 2024-02-19
  Time: 오후 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  </head>
  <body>
    <form>
      <table>
        <colgroup>
          <col width="20%">
          <col width="80%">
        </colgroup>
        <tr>
          <th>사용 비밀번호</th>
          <td>
            <input type="password" id="pwd" name="pwd"/>
          </td>
        </tr>
        <tr>

          <th>파일</th>
          <td>
            <input type="file" id="files" name="files"/>
            <button type="button" id="excelUpload">업로드</button>
          </td>
        </tr>
      </table>
    </form>
  </body>


<script>
  document.addEventListener("DOMContentLoaded", function(){
    setButtonEvent();
  });

  function setButtonEvent(){
    document.getElementById("excelUpload").addEventListener("click", async (e) => {
      //${pageContext.request.contextPath}/copyToCJDelivery

      const formData = new FormData();
      const files = document.getElementById("files").files;

      for(let i = 0; i < files.length; i++){
        formData.append("files", files[i]);
      }
      formData.append("pwd", document.getElementById("pwd").value);

      const response = await fetch("/copyToCJDelivery", {
        method: "post"
        , body: formData
      });
    })
  }

</script>
</html>
