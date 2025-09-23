<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh; min-width: 200px;">
	<div class="w-100">
		<ul class="navbar-nav">
		
			<!-- 내 드라이브 시작 -->
			<li>
				<div class="nav-link text-dark d-flex justify-content-between">
					<div class="d-flex align-items-center">
						<a class="material-symbols-rounded opacity-5 fs-5" data-bs-toggle="collapse" href="#collapseMyDrive"
						   role="button" aria-expanded="false" aria-controls="collapseMyDrive">keyboard_arrow_down</a> 
						<span class="sub-nav-link-text ms-1 text-sm">내 드라이브</span>
					</div>
					<div class="d-flex text-center">
						<a class="nav-link text-dark d-flex align-items-center" href="/drive/create"> 
							<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">add</i>
						</a>
					</div>
				</div>
				<div class="collapse show" id="collapseMyDrive">
					<div class="d-flex">
						<ul class="navbar-nav" id="drive-my">
							<c:forEach items="${ myDriveList }" var="myDrive">
								<c:if test="${ myDrive.driveEnabled }">
									<li class="nav-item d-flex align-items-center justify-content-between" style="min-width: 170px">
										<a class="nav-link text-dark" href="/drive/${ myDrive.driveNum }">
											<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ myDrive.driveName }">folder_open</i>
											<span class="nav-link-text ms-1 text-sm text-truncate" style="max-width: 110px;" title="${myDrive.driveName}">${ myDrive.driveName }</span>
										</a>
										<c:if test="${ not empty driveDTO and myDrive.driveNum eq driveDTO.driveNum }">
											<div class="d-flex align-items-center drive-setting">
												<a href="/drive/${ myDrive.driveNum }/update" class="d-flex align-items-center" style="text-decoration: none;">
													<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ myDrive.driveNum }">settings</i>
												</a>
											</div>
										</c:if>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</li>
			<!-- 내 드라이브 끝 -->
			<li>
				<hr class="my-1 bg-dark">
			</li>
			<!-- 공유 드라이브 시작 -->
			<li>
				<div class="nav-link text-dark d-flex align-items-center">
					<a class="material-symbols-rounded opacity-5 fs-5" data-bs-toggle="collapse" href="#collapseShareDrive"
						   role="button" aria-expanded="false" aria-controls="collapseShareDrive">keyboard_arrow_down</a> 
					<span class="nav-link-text ms-1 text-sm">공유 드라이브</span>
				</div>
				<div class="collapse show" id="collapseShareDrive">
					<ul class="navbar-nav" id="drive-share">
						<c:forEach items="${ shareDriveList }" var="shareDrive">
							<c:if test="${ shareDrive.driveDTO.driveEnabled }">
								<li class="nav-item">
									<a class="nav-link text-dark" href="/drive/${ shareDrive.driveDTO.driveNum }">
										<i class="material-symbols-rounded opacity-5 fs-5" data-content="${ shareDrive.driveDTO.driveName }">folder_open</i>
										<span class="nav-link-text ms-1 text-sm text-truncate" title="${ shareDrive.driveDTO.driveName }" style="max-width: 180px;">${ shareDrive.driveDTO.driveName }</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</li>
			<!-- 공유 드라이브 끝 -->
		</ul>
	</div>
</aside>