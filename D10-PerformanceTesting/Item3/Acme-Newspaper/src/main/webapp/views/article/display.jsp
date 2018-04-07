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


<display:table name="article" class="displaytag"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->

<display:column>
	<B><spring:message code="article.title" /></B>
	<jstl:out value="${row.title}"></jstl:out>
	

	<p>
		<B><spring:message code="article.summary" /></B>
		<jstl:out value="${row.summary}"></jstl:out>
	</p>
		
	<p>
		<B><spring:message code="article.body" /></B>
		<jstl:out value="${row.body}"></jstl:out>
	</p>
	
	<p>
		<B><spring:message code="article.draftMode" /></B>
		<jstl:out value="${row.draftMode}"></jstl:out>
	</p>
	
	<p>
			<jstl:if test="${row.publishedMoment!=null }">
 			<spring:message code="article.format.publishedMoment" var="pattern"></spring:message>
			<fmt:formatDate value="${row.publishedMoment}" pattern="${pattern}" var="newdatevar" />
			<B><spring:message code="article.publishedMoment"></spring:message></B>
			<c:out value="${newdatevar}" />
			</jstl:if>
			
			<jstl:if test="${row.publishedMoment==null}">
			<B><spring:message code="article.publishedMoment"></spring:message></B>
			<B><spring:message code ="nothing.found"></spring:message></B>
			</jstl:if>
	
	</p>
	
	
	<%--  <p>
	
	<spring:message code="article.pictures" var="titleHeader" />
	<jstl:out value="${row.pictures}"></jstl:out>
	
	</p> 
 --%>
	
</display:column>

	</display:table>
	
	