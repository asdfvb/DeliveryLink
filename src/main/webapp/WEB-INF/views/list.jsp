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
            <h3>스마트스토어 -> CJ대한통운</h3>
            <ul>
                <li style='font-weight: bold;'>스마트스토어 발주(주문)확인/발송관리 메뉴에서 다운받은 엑셀파일을 올려주세요.</li>
                <li style='font-weight: bold;'>업로드 후 'D드라이브 > FileUpload > excel > 해당 날짜 폴더' 경로에서 파일을 찾을수 있습니다.</li>
            </ul>
            <table style='border-top:1px solid #ff1282;'>
                <tr>
                    <th class="thStyle">사용 비밀번호</th>
                    <td class="tdStyle">
                        <input type="password" id="pwd" name="pwd"/>
                    </td>
                </tr>
                <tr>
                    <th class="thStyle">파일</th>
                    <td class="tdStyle">
                        <input type="file" id="files" name="files"/>
                        <button type="button" id="excelUpload">업로드</button>
                    </td>
                </tr>
            </table>

        </tr>
    </table>


</form>
<form>
    <table>
        <colgroup>
            <col width="20%">
            <col width="80%">
        </colgroup>
        <tr>
            <h3>CJ대한통운 -> 스마트스토어</h3>
            <ul>
                <li style='font-weight: bold;'>CJ대한통운 운송장출력 메뉴에서 </li>
                <li style='font-weight: bold;'>업로드 후 'D드라이브 > FileUpload > excel > 해당 날짜 폴더' 경로에서 파일을 찾을수 있습니다.</li>
            </ul>
            <table style='border-top:1px solid #ff1282;'>
                <tr>
                    <th class="thStyle">사용 비밀번호</th>
                    <td class="tdStyle">
                        <input type="password" id="cjPwd" name="pwd"/>
                    </td>
                </tr>
                <tr>
                    <th class="thStyle">파일</th>
                    <td class="tdStyle">
                        <input type="file" id="cjFiles" name="Files" multiple/>
                        <button type="button" id="cjExcelUpload">업로드</button>
                    </td>
                </tr>
            </table>

        </tr>
    </table>
</form>
</body>
<style>
    thStyle {
        background: #dcdcdc;
        border-bottom: 1px solid #dcdcdc;
        color: #444;
        font-size: 12px;
        font-weight: bold;
        min-width: 100px;
    }

    tdStyle {
        padding: 1px;
        font-size: 13px;
        font-weight: bold;
        height: 26px;
        text-align: center;
        border: 0.3px solid rgb(149 149 149);
    }
</style>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        setButtonEvent();
    });

    function setButtonEvent() {
        document.getElementById("excelUpload").addEventListener("click", async (e) => {
            //${pageContext.request.contextPath}/copyToCJDelivery

            const formData = new FormData();
            const files = document.getElementById("files").files;

            for (let i = 0; i < files.length; i++) {
                formData.append("files", files[i]);
            }
            formData.append("pwd", document.getElementById("pwd").value);

            const response = await fetch("/copyToCJDelivery", {
                method: "post"
                , body: formData
            });
        })

        document.getElementById("cjExcelUpload").addEventListener("click", async (e) => {
            //${pageContext.request.contextPath}/copyToCJDelivery

            const formData = new FormData();
            const files = document.getElementById("cjFiles").files;

            for (let i = 0; i < files.length; i++) {
                formData.append("files", files[i]);
            }
            formData.append("pwd", document.getElementById("cjPwd").value);

            const response = await fetch("/copyToNaverDelivery", {
                method: "post"
                , body: formData
            });
        })
    }

</script>
</html>
