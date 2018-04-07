<%--
 * edit.jsp
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="newspaper" class="displaytag"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	
	
	<jstl:if test="${row.picture==''}">
	<spring:message code="nothing.found.to.display" />
	</jstl:if> 
	
	<jstl:if test="${!(row.picture=='') }">
	<spring:message code="newspaper.picture" var="pictuHeader" />
	<display:column title="${titleHeader}">

		<div
			style="position: relative; width: 500px; height: 300px; margin-left: auto; margin-right: auto;">

			<img src="${row.picture}" width="500" height="300">
		</div>
	</display:column>
	</jstl:if>
	<display:column>
	
	<B><spring:message code="newspaper.title" /></B>
		<jstl:out value="${row.title}"></jstl:out>
		<p>
		
	<B><spring:message code="newspaper.description" /></B>
		<jstl:out value="${row.description}"></jstl:out>
		<p>
	</display:column>
	
	
	</display:table>
	
	<security:authorize access="hasRole('USER')">
	<h2><spring:message code="newspaper.articles" /></h2>	
	<display:table name="articles" id="row" class="displaytag">

		
		<spring:message code="newspaper.article.summary" var="summary" />
		<display:column title="${summary}" sortable="true" >
		<spring:url value="article/user/listSummary.do" var="articleURL">
			<spring:param name="articleId" value="${row.id}" />
		</spring:url>
		<a href="${articleURL}"><spring:message code="newspaper.article.summary" /></a>
		</display:column>
		
		<spring:message code="newspaper.articles" var="writer" />
		<display:column title="${writer}" sortable="true" >
		<spring:url value="article/user/display.do" var="renURL">
			<spring:param name="articleId" value="${row.id}" />
		</spring:url>
		<a href="${renURL}"><jstl:out value="${row.title}"></jstl:out></a>
		</display:column> 
		
		<!-- CUANDO ESTÉ EL DISPLAY DE PROFILE LO PONGO AQUÍ -->
		<spring:message code="newspaper.article.writer" var="writer" />
		<display:column title="${writer}" sortable="true" >
		<spring:url value="article/user/displayUser.do" var="renURL">
			<spring:param name="userId" value="${row.writer.id}" />
		</spring:url>
		<a href="${renURL}"><spring:message code="newspaper.article.writer" /></a>
		</display:column> 
		
	
	</display:table>
	</security:authorize>	