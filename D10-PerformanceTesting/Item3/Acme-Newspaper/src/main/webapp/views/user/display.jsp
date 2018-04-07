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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<display:table name="user" class="displaytag"
  requestURI="${RequestUri}" id="row">
  
  <!-- Attributes -->
	
	<display:column>
	<B><spring:message code="user.name" />:</B>
	<jstl:out value="${row.name}"></jstl:out>
	

	<p>
		<B><spring:message code="user.surname" />:</B>
		<jstl:out value="${row.surname}"></jstl:out>
	</p>
		
	<p>
		<B><spring:message code="user.postalAddress" />:</B>
		<jstl:out value="${row.postalAddress}"></jstl:out>
	</p>

	<p>
		<B><spring:message code="user.phoneNumber" />:</B>
		<jstl:out value="${row.phone}"></jstl:out>
	</p>
	
	<p>
		<B><spring:message code="user.emailAddress" />:</B>
		<jstl:out value="${row.email}"></jstl:out>
	</p>
	
	
	<p>
		<B><spring:message code="user.articles1" />:</B>
		<security:authorize access="isAnonymous()">
			<spring:url value="article/listb.do" var="articleURL">
			<spring:param name="userId" value="${row.id }" />
			</spring:url>
		</security:authorize>
		<security:authorize access="hasRole('USER')">
			<spring:url value="article/user/listb.do" var="articleURL">
			<spring:param name="userId" value="${row.id }" />
			</spring:url>
		</security:authorize>
			<a href="${articleURL}"><spring:message code="user.articles" /></a>
	</p>
		<p>
		<B><spring:message code="user.chirps1" />:</B>
		<security:authorize access="isAnonymous()">
			<spring:url value="chirp/listb.do" var="chirpURL">
			<spring:param name="userId" value="${row.id }" />
			</spring:url>
		</security:authorize>
		<security:authorize access="hasRole('USER')">
			<spring:url value="chirp/user/listb.do" var="chirpURL">
			<spring:param name="userId" value="${row.id }" />
			</spring:url>
		</security:authorize>
			<a href="${chirpURL}"><spring:message code="user.chirps" /></a>
	</p>
	
</display:column>
  
	
</display:table>