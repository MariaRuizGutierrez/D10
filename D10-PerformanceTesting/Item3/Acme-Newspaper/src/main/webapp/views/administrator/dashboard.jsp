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

<caption class="caption">
<!-- C4 -->
	<spring:message code="dashboard.newspaperHaveLeast10MorePercentFewerArtclesThanAverage" />
</caption>
<display:table name="newspaperHaveLeast10MorePercentFewerArtclesThanAverage" id="row"
	class="displaytag">
	<spring:message code="newspapers.title" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="newspaper/display.do" var="idURL">
			<spring:param name="newspaperId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.title}" /></a>
	</display:column>
</display:table>

<caption class="caption">
<!-- C5 -->
	<spring:message code="dashboard.newspaperHaveLeast10LeastPercentFewerArtclesThanAverage" />
</caption>
<display:table name="newspaperHaveLeast10LeastPercentFewerArtclesThanAverage" id="row"
	class="displaytag">
	<spring:message code="newspapers.title" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="newspaper/display.do" var="idURL">
			<spring:param name="newspaperId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.title}" /></a>
	</display:column>
</display:table>

<table>
<!-- C6 -->
	<caption class="caption">
		<spring:message
			code="dashboard.theRatioOfUsersWritingNewspaper" />
	</caption>

	<tr>
		<td><jstl:out
				value="${theRatioOfUsersWritingNewspaper}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- C7 -->
	<caption class="caption">
		<spring:message
			code="dashboard.theRatioOfUsersWritingArticle" />
	</caption>

	<tr>
		<td><jstl:out
				value="${theRatioOfUsersWritingArticle}"></jstl:out></td>
	</tr>
</table>

<table>
<!-- B1 -->
	<caption class="caption">
		<spring:message
			code="dashboard.avgFollowupsPerArticle" />
	</caption>

	<tr>
		<td><jstl:out
				value="${avgFollowupsPerArticle}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- B2 -->
	<caption class="caption">
		<spring:message
			code="dashboard.avgNumberOfFollowUpsPerArticleAfterOneWeek" />
	</caption>

	<tr>
		<td><jstl:out
				value="${avgNumberOfFollowUpsPerArticleAfterOneWeek}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- B3 -->
	<caption class="caption">
		<spring:message
			code="dashboard.avgNumberOfFollowUpsPerArticleAfterTwoWeek" />
	</caption>

	<tr>
		<td><jstl:out
				value="${avgNumberOfFollowUpsPerArticleAfterTwoWeek}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- B4 -->
	<caption class="caption">

		<spring:message
			code="dashboard.avgAndStddevOfNumberOfChirpPerUser" />


	</caption>

	<tr>
		<th><spring:message code="dashboard.AVG" /></th>
		<th><spring:message code="dashboard.STDDEV" /></th>
	</tr>
	<tr>
		<jstl:forEach var="medidas"
			items="${avgAndStddevOfNumberOfChirpPerUser}">
			<td><jstl:out value="${ medidas}"></jstl:out></td>
		</jstl:forEach>
	</tr>
</table>


<caption class="caption">
<!-- B5 -->
	<spring:message code="dashboard.ratioOfUserWhoHavePostedAbove75PercentTheAvgNumberOfChirpsPerUSer" />
</caption>
<display:table name="ratioOfUserWhoHavePostedAbove75PercentTheAvgNumberOfChirpsPerUSer" id="row"
	class="displaytag">
	<spring:message code="users.name" var="nameHeader" />
	<display:column title="${nameHeader}">
		<spring:url value="user/display.do" var="idURL">
			<spring:param name="userId" value="${row.id }" />
		</spring:url>
		<a href="${idURL}"><jstl:out value="${row.name}" /></a>
	</display:column>
</display:table>

<table>
<!-- A1 -->
	<caption class="caption">
		<spring:message
			code="dashboard.ratioOfNewspaperPublicPerNespaperProvate" />
	</caption>

	<tr>
		<td><jstl:out
				value="${ratioOfNewspaperPublicPerNespaperProvate}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- A2 -->
	<caption class="caption">
		<spring:message
			code="dashboard.avgArticlePerNewspapersPrivate" />
	</caption>

	<tr>
		<td><jstl:out
				value="${avgArticlePerNewspapersPrivate}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- A3 -->
	<caption class="caption">
		<spring:message
			code="dashboard.avgArticlesPerNewspapersPublic" />
	</caption>

	<tr>
		<td><jstl:out
				value="${avgArticlesPerNewspapersPublic}"></jstl:out></td>
	</tr>
</table>
<table>
<!-- A4 -->
	<caption class="caption">
		<spring:message
			code="dashboard.ratioOfSubscribersWhenNewspaperPrivatePerNumberCustomer" />
	</caption>

	<tr>
		<td><jstl:out
				value="${ratioOfSubscribersWhenNewspaperPrivatePerNumberCustomer}"></jstl:out></td>
	</tr>
</table>




