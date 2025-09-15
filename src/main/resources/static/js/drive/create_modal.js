document.addEventListener('DOMContentLoaded', function () {
  // TODO: 내일 fetch로 교체 (모달 열릴 때 DB에서 가져오기)
  var employees = [
    { id: 1, name: '권준하', title: '과장', team: '인사팀' },
    { id: 2, name: '이진우', title: '부장', team: '인사팀' },
    { id: 3, name: '김혜진', title: '전무', team: '운영팀' },
    { id: 4, name: '고은빈', title: '상무', team: '운영팀' },
    { id: 5, name: '박호안', title: '사장', team: '시설팀' }
  ];

  var currentTeam = 'all';
  var listenersBound = false; // 중복 바인딩 방지

  function refs() {
    return {
      employeeList: document.getElementById('employeeList'),
      selectedList: document.getElementById('selectedList'),
      searchInput:  document.getElementById('searchInput'),
      addBtn:       document.getElementById('addBtn'),
      teamBtns:     document.querySelectorAll('.team-btn')
    };
  }

  function renderEmployees(list) {
    var r = refs();
    if (!r.employeeList) return;
    r.employeeList.innerHTML = '';
    list.forEach(function (emp) {
      var li = document.createElement('li');
      li.className = 'list-group-item d-flex align-items-center';
      li.setAttribute('data-emp-id', emp.id);
      li.innerHTML =
        '<input type="checkbox" class="form-check-input me-2">' +
        '<span>' + emp.name + ' (' + emp.title + ') ' + emp.team + '</span>';
      r.employeeList.appendChild(li);
    });
  }

  function filterEmployees() {
    var r = refs();
    var keyword = (r.searchInput && r.searchInput.value ? r.searchInput.value : '')
      .trim().toLowerCase();

    var filtered = employees.filter(function (e) {
      return (currentTeam === 'all') ? true : e.team === currentTeam;
    });
    if (keyword) {
      filtered = filtered.filter(function (e) {
        return (e.name + e.title + e.team).toLowerCase().indexOf(keyword) !== -1;
      });
    }
    renderEmployees(filtered);
  }

  function bindOnce() {
    if (listenersBound) return;
    var r = refs();

    // 팀 버튼
    r.teamBtns.forEach(function (btn) {
      btn.addEventListener('click', function () {
        currentTeam = btn.getAttribute('data-team') || 'all';
        filterEmployees();
      });
    });

    // 검색
    if (r.searchInput) {
      r.searchInput.addEventListener('input', filterEmployees);
    }

    // 추가 버튼
    if (r.addBtn) {
      r.addBtn.addEventListener('click', function () {
        var r2 = refs();
        if (!r2.employeeList || !r2.selectedList) return;

        var checked = r2.employeeList.querySelectorAll('input[type="checkbox"]:checked');
        checked.forEach(function (chk) {
          var li = chk.closest('li');
          var text = li.querySelector('span').textContent;

          var added = document.createElement('li');
          added.className = 'list-group-item d-flex justify-content-between align-items-center';
          added.innerHTML =
            '<span>' + text + '</span>' +
            '<button class="btn-close remove-btn" aria-label="Remove"></button>';

          r2.selectedList.appendChild(added);
          added.querySelector('.remove-btn').addEventListener('click', function () {
            added.remove();
          });

          chk.checked = false; // 체크 해제
        });
      });
    }

    listenersBound = true;
  }

  // 문서 레벨에 모달 이벤트 바인딩(요소 존재/순서에 안전)
  document.addEventListener('shown.bs.modal', function (e) {
    if (e.target && e.target.id === 'shareModal') {
      // 내일 실제 DB 호출로 교체:
      // fetch('/api/employees').then(r => r.json()).then(function(data){ employees = data; filterEmployees(); });

      currentTeam = 'all';
      var r = refs();
      if (r.searchInput) r.searchInput.value = '';
      renderEmployees(employees);
      bindOnce();
    }
  });
});
