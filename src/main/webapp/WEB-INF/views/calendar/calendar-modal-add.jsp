<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <!-- 일정 쓰기 모달 -->
  <div class="modal fade" id="addEventModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered" style="max-width:600px;">
      <div class="modal-content border-0 rounded-3 shadow">
        <div class="modal-header">
          <h5 class="modal-title">일정 쓰기</h5>
          <button type="button" class="btn-close btn-close-white bg-black" data-bs-dismiss="modal" aria-label="닫기"></button>
        </div>

        <div class="modal-body m-3">
          <form id="eventForm">
            <!-- 캘린더 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label for="calType" class="col-form-label pt-0 ps-5">캘린더<span class="text-danger"> *</span></label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <select class="form-select" id="calType">
                  <option value="">선택</option>
                  <option value="2001">점검 일정</option>
                  <option value="2002">사내 일정</option>
                  <option value="2003">부서 일정</option>
                </select>
              </div>
            </div>

            <!-- 시작 -->
            <div class="row form-row  mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label class="col-form-label pt-0 ps-5">시작<span class="text-danger"> *</span></label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <div class="d-flex inline-inputs flex-wrap">
                  <input type="date" id="calStartDate" class="form-control" style="max-width: 180px;">
                  <select class="form-select select-min-hour" id="calStartHour" style="max-width: 90px;">
                  	<c:forEach var="h" begin="0" end="23">
                  		<option value="${ h lt 10 ? '0' : '' }${ h }">
                  		   ${ h lt 10 ? '0' : '' }${ h }  		
                  		</option>
                  	</c:forEach>
                  </select>
                  <select class="form-select select-min-hour" id="calStartMin" style="max-width: 90px;">
                  	<c:forEach var="m" begin="0" end="59">
                  		<option value="${ m lt 10 ? '0' : '' }${ m }">
                  		${ m lt 10 ? '0' : '' }${ m }
                  		</option>
                  	</c:forEach>
                  </select>
                </div>
              </div>
            </div>

            <!-- 종료 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label class="col-form-label pt-0 ps-5">종료<span class="text-danger"> *</span></label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <div class="d-flex inline-inputs flex-wrap">
                  <input type="date" id="calEndDate" class="form-control" style="max-width: 180px;">
                  <select id="calEndHour" class="form-select select-min-hour" style="max-width: 90px;">
                  	<c:forEach var="h" begin="0" end="23">
                  	  <option value="${ h lt 10 ? '0' : '' }${ h }">
               		    ${ h lt 10 ? '0' : '' }${ h }  		
               		  </option>
                  	</c:forEach>
                  </select>
                  <select id="calEndMin" class="form-select select-min-hour" style="max-width: 90px;">
                  	<c:forEach var="m" begin="0" end="59">
                  	  <option value="${ m lt 10 ? '0' : '' }${ m }">
                  		${ m lt 10 ? '0' : '' }${ m }
                      </option>
                  	</c:forEach>
                  </select>
                </div>
              </div>
            </div>

            <!-- 종일 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label class="col-form-label pt-0 ps-5" for="allDayCheckBox">종일</label>
              </div>
              <div class="col-12 col-sm-9 input-col d-flex align-items-center">
                <div class="">
                  <input type="checkbox" id="allDayCheckBox">
                  <label class="form-check-label" for="allDayCheckBox">종일 일정</label>
                </div>
              </div>
            </div>

            <!-- 장소 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label for="place" class="col-form-label pt-0 ps-5">장소</label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <input type="text" id="calPlace" class="form-control" placeholder="">
              </div>
            </div>

            <!-- 제목 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label for="title" class="col-form-label pt-0 ps-5">제목 <span class="text-danger">*</span></label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <input type="text" id="calTitle" class="form-control" placeholder="">
              </div>
            </div>

            <!-- 내용 -->
            <div class="row form-row mb-2">
              <div class="col-12 col-sm-3 label-col text-start">
                <label for="content" class="col-form-label pt-0 ps-5">내용</label>
              </div>
              <div class="col-12 col-sm-9 input-col">
                <textarea id="calContent" class="form-control" rows="3" placeholder="" style="height: 100px; resize: none;"></textarea>
              </div>
            </div>
          </form>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-dark" id="btnAddCalendar" onclick="addCalendarEvent()">등록</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>