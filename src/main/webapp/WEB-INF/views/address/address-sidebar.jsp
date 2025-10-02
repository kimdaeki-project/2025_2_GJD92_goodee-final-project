<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh; min-width: 200px;">
	<div class="w-100">
		<ul class="navbar-nav">
			<li>
				<div class="nav-link text-dark d-flex justify-content-between">
					<div class="d-flex align-items-center">
						<a class="material-symbols-rounded opacity-5 fs-5" data-bs-toggle="collapse" href="#collapseMyDrive"
						   role="button" aria-expanded="false" aria-controls="collapseMyDrive">keyboard_arrow_down</a> 
							<a class="nav-link text-dark" href="/address">
								<span class="sub-nav-link-text ms-1 text-sm">전체</span>
							</a>
					</div>
				</div>
				<div class="collapse show" id="collapseMyDrive">
					<div class="d-flex">
						<ul class="navbar-nav" id="drive-my">
							<c:if test="${ deptList ne null}">
							<c:forEach items="${ deptList }" var="dept">
								<li class="nav-item d-flex align-items-center justify-content-between" style="min-width: 170px">
									<a class="nav-link text-dark" href="/address/${dept.deptCode}">
										<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ dept.deptDetail }">folder_open</i>
										<span class="nav-link-text ms-1 text-sm text-truncate" style="max-width: 110px;">&nbsp;${ dept.deptDetail }</span>
									</a>
								</li>
							</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
		</ul>
	</div>
</aside>