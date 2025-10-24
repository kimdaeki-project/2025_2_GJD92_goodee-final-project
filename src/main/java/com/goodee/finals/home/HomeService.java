package com.goodee.finals.home;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.approval.ApprovalDTO;
import com.goodee.finals.approval.ApprovalRepository;
import com.goodee.finals.notice.NoticeDTO;
import com.goodee.finals.notice.NoticeRepository;
import com.goodee.finals.ride.RideDTO;
import com.goodee.finals.ride.RideRepository;

@Service
public class HomeService {

	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private RideRepository rideRepository;
	@Autowired
	private ApprovalRepository approvalRepository;
	
	// 결재현황 최신글 5건 조회
	public List<ApprovalDTO> getRecentApprovalsForDashboard() {
		return approvalRepository.findAll();
	}
	
	// 공지사항 최신글 5건 조회
	public List<NoticeDTO> getRecentNoticesForDashboard() {
        return noticeRepository.findTop5ByNoticePinnedFalseAndNoticeDeleteFalseAndNoticeTmpFalseOrderByNoticeDateDescNoticeNumDesc();
    }
	
	// 어트랙션 대시보드 운휴현황 조회
    public List<RideDTO> homeList() throws Exception {
    	List<RideDTO> rides = rideRepository.findByRideDeletedFalseAndRideStateInOrderByRideStateAsc(Arrays.asList(300, 400, 500));
    	return rides;
    }
	
}
