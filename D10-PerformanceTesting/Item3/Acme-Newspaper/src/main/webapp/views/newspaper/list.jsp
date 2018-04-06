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
<form:form action="${requestURISearchNewspaper}"  method="get">
	<label><spring:message code="newspaper.search.keyword"/></label>
	<input type="text" name="keyword"/> 
	<input type="submit" value="<spring:message code="newspaper.search" />" /> 	 	
</form:form>
</security:authorize>
</div>
<br />


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspapers" requestURI="${requestURI}" id="row">
	
	

	<!-- 	DISPLAY -->
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="newspaper.display" var="display" />
		<display:column title="${display}" sortable="true" >
			<spring:url value="newspaper/user/display.do" var="displayURL">
				<spring:param name="newspaperId" value="${row.id }" />
			</spring:url>
			<a href="${displayURL}"><spring:message
					code="newspaper.display" /></a>
		</display:column>
	</security:authorize>
	<!--  EDIT -->
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="newspaper.edit" var="Edit" />
		<display:column title="${Edit}" sortable="true">
			<jstl:if test="${row.publicationDate==null}">
				<spring:url value="newspaper/user/edit.do" var="editURL">
					<spring:param name="newspaperId" value="${row.id}" />
				</spring:url>
				<a href="${editURL}"><spring:message code="newspaper.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<!-- DISPLAY -->	

	<security:authorize access="isAnonymous()">
	<spring:message code="newspaper.display" var="Display" />
	<display:column title="${Display}" sortable="true" >
		<spring:url value="newspaper/display.do" var="editURL">
			<spring:param name="newspaperId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="newspaper.display" /></a>
	</display:column>
	</security:authorize>
	
	

	<!-- ATRIBUTOS -->

	<spring:message code="newspaper.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="newspaper.format.publicationDate" var="pattern"></spring:message>
	<spring:message code="newspaper.publicationDate" var="postedHeader" />
	<display:column property="publicationDate" title="${postedHeader}"
		sortable="true" format="${pattern}" />
		
		
	<!-- Articles -->
	
	<security:authorize access="isAnonymous()">
		<spring:message code="newspaper.articles" var="articles" />
		<display:column title="${articles}" sortable="true" >
			<spring:url value="article/list.do" var="articleURL">
				<spring:param name="newspaperId" value="${row.id }" />
			</spring:url>
			<a href="${articleURL}"><spring:message
					code="newspaper.articles" /></a>
		</display:column>
	</security:authorize>
	
	<!-- ESTE LO USA ESPI -->
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${showMyArticles}">
		<spring:message code="newspaper.articles" var="articles" />
		<display:column title="${articles}" sortable="true" >
			<spring:url value="article/user/listMyArticles.do" var="articleURL">
				<spring:param name="newspaperId" value="${row.id }" />
			</spring:url>
			<a href="${articleURL}"><spring:message
					code="newspaper.articles" /></a>
		</display:column>
	</jstl:if> 
	
	<jstl:if test="${showButtonPublish}">
	<spring:message code="newspaper.publish" var="publish" />
		<display:column title="${publish}" sortable="true" >
			<jstl:if test="${row.publicationDate == null}">
				<spring:url value="newspaper/user/publish.do" var="publishURL">
					<spring:param name="newspaperId" value="${row.id }" />
				</spring:url>
				<a href="${publishURL}"><spring:message
						code="newspaper.publish" /></a>
			</jstl:if>		
		</display:column>
	</jstl:if>	
	
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${showButtonSubscription}">
		<spring:message code="newspaper.subscription" var="subscription" />
		<display:column title="${subscription}" sortable="true" >
			<jstl:if test="${!newspapersSubscribed.contains(row)}">
				<spring:url value="subscription/customer/create.do" var="subscriptionURL">
					<spring:param name="newspaperId" value="${row.id }" />
				</spring:url>
				<a href="${subscriptionURL}"><spring:message
						code="newspaper.subscription" /></a>
			</jstl:if>		
		</display:column>
		</jstl:if>
	</security:authorize>
	

</display:table>

<security:authorize access="hasRole('USER')">
	<div>
		<a href="newspaper/user/create.do"> <spring:message
				code="newspaper.create" />
		</a>
	</div>
</security:authorize>

