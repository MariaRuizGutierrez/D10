<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	function confirmDelete(articleId) {
		confirm=confirm('<spring:message code="article.confirm.delete"/>');
		if (confirm)
		  window.location.href ="article/admin/delete.do?articleId=" + articleId;
		  else
			  window.location.href ="article/admin/list.do";
	}
</script>

<!-- 	SEARCH NO AUTENTICADOS -->
	<div>
<security:authorize access="isAnonymous()">
<form:form action="${requestURISearchArticle}"  method="get">
	<label><spring:message code="article.search.keyword"/></label>
	<input type="text" name="keyword"/> 
	<input type="submit" value="<spring:message code="article.search" />" /> 	 	
</form:form>
</security:authorize>
</div>
<br />

<!-- 	SEARCH USERS -->
	<div>
<security:authorize access="hasRole('USER')">
<form:form action="${requestURISearchArticle}"  method="get">
	<label><spring:message code="article.search.keyword"/></label>
	<input type="text" name="keyword"/> 
	<input type="submit" value="<spring:message code="article.search" />" /> 	 	
</form:form>
</security:authorize>
</div>
<br />

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="articles" requestURI="${requestURI}" id="row">
	
	<!--  EDIT -->
	
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${showButtonEdit}">
		<spring:message code="article.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
			<jstl:if test="${row.draftMode==true}">
				<spring:url value="article/user/edit.do" var="editURL">
					<spring:param name="articleId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="article.edit" /></a>
			</jstl:if>
		</display:column>
		</jstl:if>
	</security:authorize>

	<!-- DISPLAY -->	

	<security:authorize access="isAnonymous()">
	<spring:message code="article.display" var="Display" />
	<display:column title="${Display}" sortable="true" >
		<spring:url value="article/display.do" var="editURL">
			<spring:param name="articleId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="article.display" /></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="article.display" var="Display" />
	<display:column title="${Display}" sortable="true" >
		<spring:url value="article/user/display.do" var="editURL">
			<spring:param name="articleId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="article.display" /></a>
	</display:column>
	</security:authorize>
	

	<!-- ATRIBUTOS -->

	<spring:message code="article.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	
	<spring:message code="article.format.publishedMoment" var="pattern"></spring:message>
	<spring:message code="article.publishedMoment" var="startDateHeader" />
	<display:column property="publishedMoment" title="${startDateHeader}" sortable="true" format="${pattern}"/>
		
		<!-- Boton de delete para el admin ya que puede borrar los articles que quiera pero no editarlas -->
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="article.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}" sortable="true">
			<input type="button" name="delete"
				value="<spring:message code="article.delete" />"
				onclick="confirmDelete(${row.id});" />
		</display:column>
	</security:authorize>
	

	<spring:message code="article.draftMode" var="draftMode" />
	<display:column title="${draftMode}">
		<jstl:if test="${row.draftMode==true}">
			<div
				style=" width: 30px; height: 30px; margin-left: 20px; ">

				<img src="images/no.png" width="30" height="30">
			</div>
		</jstl:if>
		<jstl:if test="${row.draftMode==false}">
			<div
				style=" width: 30px; height: 30px; margin-left: 20px; ">

				<img src="images/yes.png" width="30" height="30">
			</div>
		</jstl:if>
		
</display:column>

<security:authorize access="hasRole('USER')">
		<spring:message code="followUp.createFollowUp"
			var="createFollowUp" />
		<display:column title="${createFollowUp}" sortable="true" >
		<%-- <jstl:if test="${util.organisedMoment(row.organisedMoment)==true}"> --%>
			<spring:url value="followUp/user/create.do" var="editURL">
				<spring:param name="articleId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message
					code="followUp.create" /></a>
		<%-- </jstl:if> --%>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="followU.listFollowUp"
			var="listFollowUp" />
		<display:column title="${listFollowUp}" sortable="true" >
		<%-- <jstl:if test="${util.organisedMoment(row.organisedMoment)==true}"> --%>
			<spring:url value="followUp/user/list.do" var="editURL">
				<spring:param name="articleId" value="${row.id}" />
			</spring:url>
			<a href="${editURL}"><spring:message
					code="followUp.list" /></a>
		<%-- </jstl:if> --%>
		</display:column>
	</security:authorize>

</display:table>


<jstl:if test="${newspaper.publicationDate == null}">
<jstl:if test="${showCreate}">
<security:authorize access="hasRole('USER')">
	<div>
		<spring:url value="article/user/create.do" var="editURL">
			<spring:param name="newspaperId" value="${newspaper.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="article.create" /></a>
	</div>
</security:authorize>
</jstl:if>
</jstl:if>

