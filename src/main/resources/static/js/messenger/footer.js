/**
 * footer.jsp
 * 채팅창이 열리도록 하는 역할
 */
function openMessenger() {
    window.open(
        "/msg",
        "MessengerWindow",
        "width=400,height=600,resizable=no,scrollbars=yes"
    );
}

function openFault() {
	    window.open(
	      "/fault/write",
	      "FaultWindow",
	      "width=400,height=500,resizable=no,scrollbars=yes"
	    );
	  }