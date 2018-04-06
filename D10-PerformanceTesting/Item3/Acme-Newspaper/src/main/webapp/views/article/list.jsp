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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 	SEARCH -->
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
	
	

	<!-- ATRIBUTOS -->

	<spring:message code="article.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="article.format.publishedMoment" var="pattern"></spring:message>
	<spring:message code="article.publishedMoment" var="postedHeader" />
	<display:column property="publishedMoment" title="${postedHeader}"
		sortable="true" format="${pattern}" />	
	

</display:table>


<jstl:if test="${newspaper.publicationDate == null}">
<security:authorize access="hasRole('USER')">
	<div>
		<spring:url value="article/user/create.do" var="editURL">
			<spring:param name="newspaperId" value="${newspaper.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="article.create" /></a>
	</div>
</security:authorize>
</jstl:if>

