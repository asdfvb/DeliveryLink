<%--
  Created by IntelliJ IDEA.
  User: 조참새
  Date: 2024-02-19
  Time: 오후 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>Title</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/cover/">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@docsearch/css@3">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/cover.css">
</head>
<body class="d-flex h-100 text-center text-bg-dark">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header class="mb-auto">
        <div>
            <h3 class="float-md-start mb-0">Sambuja</h3>
            <nav class="nav nav-masthead justify-content-center float-md-end">
                <a class="nav-link fw-bold py-1 px-0" aria-current="page" href="/">스마트스토어</a>
                <a class="nav-link fw-bold py-1 px-0 active" href="/cj">CJ</a>
            </nav>
        </div>
    </header>
    <main class="px-3">
        <h3>CJ대한통운 -> 스마트스토어</h3>
        <p class="lead" style="font-size:1rem;">CJ대한통운 운송장출력 메뉴에서 다운받은 엑셀 파일과</p>
        <p class="lead" style="font-size:1rem;">업로드 후 'D드라이브 > FileUpload > excel > 해당 날짜 폴더' 경로에서 파일을 찾을수 있습니다.</p>
        <p class="lead" style="font-size:1rem;">File 명 : CjToNaver</p>
        <div>
            <table style='text-align: center;'>
                <colgroup>
                    <col width="30%">
                    <col width="70%">
                </colgroup>
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
        </div>
    </main>
    <footer class="mt-auto text-white-50">
        <p>© 2024 Sambuja</p>
    </footer>
</div>
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

    table {
        width: 100%;
        border: 2px solid white;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid white;
    }

    contentCenter {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

    }
    input[type="text"], input[type="password"] {
        width: 100%;

    }
</style>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        setButtonEvent();
    });

    function setButtonEvent() {
       document.getElementById("cjExcelUpload").addEventListener("click", async (e) => {
            //${pageContext.request.contextPath}/copyToCJDelivery

            const selectFilesCnt = $("#cjFiles")[0].files.length;
            const userPwd = $("#cjPwd").val();

            if( !userPwd ){
                alert("비밀번호를 입력해주세요.");
                return false;
            }

            if( selectFilesCnt == 0 ){
                alert("파일을 선택 후 업로드를 시도해주세요.");
                return false;
            }

            if( selectFilesCnt != 2 ){
                alert("1.CJ대한통운 운송장엑셀파일 \n2.스마트스토어 엑셀파일 \n2개의 파일을 선택 후 업로드를 시도해주세요.");
                return false;
            }

            const formData = new FormData();
            const files = document.getElementById("cjFiles").files;

            for (let i = 0; i < files.length; i++) {
                formData.append("files", files[i]);
            }
            formData.append("pwd", document.getElementById("cjPwd").value);

            await fetch("/copyToNaverDelivery", {
                method: "post"
                , body: formData
            })
                .then(response => response.json())
                .then(json => notifyRequestResultToCustomer(json))
            ;
        })
    }

    function notifyRequestResultToCustomer(response) {
        const json = response.dto;
        const msg = (json.message) ? json.message : "관리자에게 문의해주세요.";

        alert(msg, " (", json.code, ")");
    }
</script>
</html>
