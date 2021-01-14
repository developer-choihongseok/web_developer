<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="centerCont">
	<div class="profileBox">
		<div>
			<c:if test="${data.profile_img == null }">
				<img class="profileImg" src="/res/img/basic_profile.jpg">
			</c:if>
			<c:if test="${data.profile_img != null }">
				<img class="profileImg" src="/res/img/${loginUser.i_user }/${data.profile_img}">
			</c:if>
		</div>
		<div>
			<div>아이디 : ${data.user_id }</div>
			<div>이름 : ${data.nm }</div>
			<div>성별 : ${data.gender == 0 ? '여성' : '남성' }</div>
			<div>연락처 : ${data.ph }</div>
		</div>
		<div>
			<!-- enctype="multipart/form-data"를 쓰면 request.getParameter()로 받아올 수 없다. 그리고 텍스트, 파일 둘 다 보낼수 있다.-->
			<form action="/user/profileUpload.korea" method="post" enctype="multipart/form-data">
				<input type="file" name="profileImg">
				<input type="submit" value="업로드">
			</form>
		</div>
	</div>
</div>