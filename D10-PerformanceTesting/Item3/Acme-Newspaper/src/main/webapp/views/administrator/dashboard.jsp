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


<table>
<!-- C1 -->
	<caption class="caption">

		<spring:message
			code="dashboard.theAvgAndStddevOfNewspapersForUser" />


	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${theAvgAndStddevOfNewspapersForUser}">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>

<table>
<!-- C2 -->
	<caption class="caption">

		<spring:message
			code="dashboard.theAvgAndStddevOfArticlesPerWriter" />


	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${theAvgAndStddevOfArticlesPerWriter}">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>
<table>
<!-- C3 -->
	<caption class="caption">

		<spring:message
			code="dashboard.theAvgAndStddevOfArticlePerNewspaper" />


	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${theAvgAndStddevOfArticlePerNewspaper}">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>











