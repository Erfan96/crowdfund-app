async function loadUsers() {
    try {
        const response = await fetch('/api/users');
        if (!response.ok) throw new Error(await response.text());

        const users = await response.json();
        const userList = document.getElementById('user-list');
        userList.innerHTML = '';

        users.forEach(user => {
            const li = document.createElement('li');
            li.innerHTML = `<strong>${user.username}</strong> - ${user.email}`;
            li.style.cursor = 'pointer';
            li.onclick = () => loadUserContributions(user.id);
            userList.appendChild(li);
        });

    } catch (error) {
        alert('خطا در بارگذاری کاربران: ' + error.message);
    }
}

async function loadUserContributions(userId) {
    try {
        const response = await fetch(`/api/contributions/user/${userId}/contribution-percentage?status=COMPLETED`);
        if (!response.ok) throw new Error(await response.text());

        const contributions = await response.json();
        const list = document.getElementById('contribution-list');
        list.innerHTML = '';

        contributions.forEach(c => {
            const li = document.createElement('li');
            li.textContent = `پروژه: ${c.project.title} - درصد مشارکت: ${c.userContributionPercentage}%`;
            list.appendChild(li);
        });

        document.getElementById('contributions').style.display = 'block';

    } catch (error) {
        alert('خطا در بارگذاری مشارکت‌های کاربر: ' + error.message);
    }
}

loadUsers();
