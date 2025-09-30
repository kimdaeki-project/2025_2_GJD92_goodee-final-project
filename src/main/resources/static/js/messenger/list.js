/**
 * list.jsp
 */
// 이니셜라이즈
fetch("/msg/unread/count", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(rooms)
})
.then(res => res.json())
.then(data => {
    let unread = data.unread;
    let latest = data.latest;
    let today = new Date();
    if (unread != null && latest != null) {
        for (const chatRoomNum in unread) {
            let badge = document.querySelector('#unread-count-' + chatRoomNum);
            badge.innerText = unread[chatRoomNum] > 0 ? unread[chatRoomNum] : "";

            let latestMessage = document.querySelector('#chat-room-last-' + chatRoomNum);
            latestMessage.innerText = latest[chatRoomNum].chatBodyContent;

            let time = document.querySelector('#time-' + chatRoomNum);
            let timeFromJava = latest[chatRoomNum].chatBodyDtm;
            const timeToJs = new Date(timeFromJava);
            if (latest[chatRoomNum].chatBodyContent == '메시지 없음') {
                time.innerText = '';
            } else {
                if (timeToJs.getDate() == today.getDate()) {
                    time.innerText = timeToJs.getHours() + '시 ' + timeToJs.getMinutes() + '분';
                } else if (timeToJs.getDate() == today.getDate() - 1) {
                    time.innerText = '어제';
                } else {
                    time.innerText = timeToJs.getMonth() + 1 + '월 ' + timeToJs.getDate() + '일';
                }
            }
        }
    }
});
// 채팅방 입장
const forms = document.querySelectorAll('.chat-room');
forms.forEach(form => {
    form.addEventListener('click', () => {
        form.submit();
    });
});